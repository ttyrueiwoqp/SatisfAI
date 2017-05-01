package com.satisfai.nlp;

import com.google.cloud.bigquery.*;
import com.satisfai.nlp.dto.FaqEntity;
import com.satisfai.nlp.dto.Mapper;
import com.satisfai.nlp.dto.QnAnsDto;
import com.satisfai.nlp.dto.QnGroupDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lu Fangjian
 */
@Repository
public class NLPDao {

    private static String SATISFAI_NLP_DATASET = "satisfai_nlp";

    private static String FAQ_QN_ANS_TABLE = "faq_qn_ans";
    private static String FAQ_QN_GROUP_TABLE = "faq_qn_group";

    public void initDB() {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        Dataset dataset = initDataset(bigQuery);
        System.out.printf("Dataset %s created.%n", dataset.getDatasetId().getDataset());

        List<Table> tables = initTables(bigQuery);
        tables.forEach(table -> System.out.printf("Tables %s created.%n", table.getTableId().getTable()));
    }

    private Dataset initDataset(BigQuery bigQuery) {
        DatasetInfo datasetInfo = DatasetInfo.newBuilder(SATISFAI_NLP_DATASET).build();
        return bigQuery.create(datasetInfo);
    }

    private List<Table> initTables(BigQuery bigQuery) {
        List<Table> tables = new ArrayList<>();
        tables.add(initTable(
                bigQuery,
                SATISFAI_NLP_DATASET,
                FAQ_QN_ANS_TABLE,
                Schema.of(
                        Field.newBuilder("qn_group_id", Field.Type.string()).setMode(Field.Mode.REQUIRED).build(),
                        Field.of("main_qn_id", Field.Type.string()),
                        Field.of("ans_text", Field.Type.string())
                )
        ));

        tables.add(initTable(
                bigQuery,
                SATISFAI_NLP_DATASET,
                FAQ_QN_GROUP_TABLE,
                Schema.of(
                        Field.newBuilder("qn_id", Field.Type.string()).setMode(Field.Mode.REQUIRED).build(),
                        Field.of("qn_group_id", Field.Type.string()),
                        Field.of("qn_text", Field.Type.string())
                )
        ));

        return tables;
    }

    private Table initTable(BigQuery bigQuery, String dataset, String table, Schema schema) {
        TableId tableId = TableId.of(dataset, table);
        TableDefinition tableDefinition = StandardTableDefinition.of(schema);
        TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
        return bigQuery.create(tableInfo);
    }

    public void initData() {
        List<QnAnsDto> qnAnsDtos = new ArrayList<>();
        List<QnGroupDto> qnGroupDtos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String qnGroupId = UUID.randomUUID().toString();

            for (int j = 0; j < 10; j++) {
                String qnId = UUID.randomUUID().toString();
                qnGroupDtos.add(new QnGroupDto(qnId, qnGroupId, "QN " + i + "." + j));
            }

            qnAnsDtos.add(new QnAnsDto(qnGroupId, qnGroupDtos.get(i * 10).getQnId(), "ANS " + i));
        }

        insertQnAnsDtos(qnAnsDtos);
        insertQnGroupDtos(qnGroupDtos);

    }

    public boolean insert(InsertAllRequest request) {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        InsertAllResponse response = bigQuery.insertAll(request);
        if (response.hasErrors()) {
            System.out.println(response.getInsertErrors());
        }

        return response.hasErrors();
    }

    public boolean insertQnAnsDto(QnAnsDto qnAnsDto) {
        return insertQnAnsDtos(Collections.singletonList(qnAnsDto));
    }

    public boolean insertQnAnsDtos(List<QnAnsDto> qnAnsDtos) {

        return insert(
                InsertAllRequest.newBuilder(
                        SATISFAI_NLP_DATASET,
                        FAQ_QN_ANS_TABLE,
                        qnAnsDtos.stream().map(Mapper::qnAnsDtoToRow).collect(Collectors.toList())
                ).build());
    }

    public boolean insertQnGroupDto(QnGroupDto qnGroupDto) {
        return insertQnGroupDtos(Collections.singletonList(qnGroupDto));
    }

    public boolean insertQnGroupDtos(List<QnGroupDto> qnGroupDtos) {

        return insert(
                InsertAllRequest.newBuilder(
                        SATISFAI_NLP_DATASET,
                        FAQ_QN_GROUP_TABLE,
                        qnGroupDtos.stream().map(Mapper::qnGroupDtoToRow).collect(Collectors.toList())
                ).build());
    }

    public List<FaqEntity> findByQnText(String qnText) {

        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

        String sql = " select g.qn_id as matched_qn_id, g.qn_text as matched_qn_text," +
                " a.qn_group_id as qn_group_id, a.ans_text as ans_text," +
                " g2.qn_id as main_qn_id, g2.qn_text as main_qn_text " +
                " from satisfai_nlp.faq_qn_group g, satisfai_nlp.faq_qn_group g2, satisfai_nlp.faq_qn_ans a " +
                " where g.qn_text = @qn_text " +
                " and g.qn_group_id = a.qn_group_id " +
                " and a.main_qn_id = g2.qn_id ";

        QueryRequest queryRequest =
                QueryRequest.newBuilder(sql)
                        .addNamedParameter("qn_text", QueryParameterValue.string(qnText))
                        .setUseLegacySql(false)
                        .build();

        QueryResponse response = bigquery.query(queryRequest);

        // Wait for the job to finish (if the query takes more than 10 seconds to complete).
        while (!response.jobCompleted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = bigquery.getQueryResults(response.getJobId());
        }

        // Check for errors.
        if (response.hasErrors()) {
            String firstError = "";
            if (response.getExecutionErrors().size() != 0) {
                firstError = response.getExecutionErrors().get(0).getMessage();
            }
            throw new RuntimeException(firstError);
        }

        QueryResult result = response.getResult();

        List<FaqEntity> faqEntities = new ArrayList<>();
        while (result != null) {
            FaqEntity faqEntity = new FaqEntity();

            for (List<FieldValue> row : result.iterateAll()) {
                Iterator<FieldValue> it = row.iterator();
                faqEntity.setQnText(qnText);
                faqEntity.setMatchedQnId(it.next().getStringValue());
                faqEntity.setMatchedQnText(it.next().getStringValue());
                faqEntity.setQnGroupId(it.next().getStringValue());
                faqEntity.setAnsText(it.next().getStringValue());
                faqEntity.setMainQnId(it.next().getStringValue());
                faqEntity.setMainQnText(it.next().getStringValue());
            }
            faqEntities.add(faqEntity);

            result = result.getNextPage();
        }

        return faqEntities;
    }

}
