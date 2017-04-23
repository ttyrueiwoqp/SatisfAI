package com.qiaoli.nlp;

import com.google.cloud.bigquery.*;
import com.qiaoli.nlp.dto.Converter;
import com.qiaoli.nlp.dto.QnAnsDto;
import com.qiaoli.nlp.dto.QnSimilarDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author Lu Fangjian
 */
@Repository
public class NLPDao {

    private static String SATISFAI_NLP_DATASET = "SATISFAI_NLP";

    private static String FAQ_QN_ANS_TABLE = "FAQ_QN_ANS";
    private static String FAQ_QN_SIMILAR_TABLE = "FAQ_QN_SIMILAR";

    public void initDB() {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        Dataset dataset = initDataset(bigQuery);
        System.out.printf("Dataset %s created.%n", dataset.getDatasetId().getDataset());

        List<Table> tables = initTables(bigQuery);
        tables.forEach(table -> System.out.printf("Tables %s created.%n", table.getTableId().getTable()));

        initData();
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
                        Field.of("ID", Field.Type.string()),
                        Field.of("QN", Field.Type.string()),
                        Field.of("ANS", Field.Type.string())
                )
        ));

        tables.add(initTable(
                bigQuery,
                SATISFAI_NLP_DATASET,
                FAQ_QN_SIMILAR_TABLE,
                Schema.of(
                        Field.of("ID", Field.Type.string()),
                        Field.of("QN_ANS_ID", Field.Type.string()),
                        Field.of("QN_SIMILAR", Field.Type.string())
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

    private void initData() {
    }

    public boolean insertQnAnsDto(QnAnsDto qnAnsDto) {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        InsertAllRequest request = InsertAllRequest.newBuilder(
                SATISFAI_NLP_DATASET,
                FAQ_QN_ANS_TABLE,
                Converter.qnAnsDtoToRow(qnAnsDto)
        ).build();

        InsertAllResponse response = bigQuery.insertAll(request);
        if (response.hasErrors()) {
            System.out.println(response.getInsertErrors());
        }

        return response.hasErrors();
    }

    public boolean insertQnAnsDtos(List<QnAnsDto> qnAnsDtos) {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        InsertAllRequest request = InsertAllRequest.newBuilder(
                SATISFAI_NLP_DATASET,
                FAQ_QN_ANS_TABLE,
                qnAnsDtos.stream().map(Converter::qnAnsDtoToRow).collect(Collectors.toList())
        ).build();

        InsertAllResponse response = bigQuery.insertAll(request);
        if (response.hasErrors()) {
            System.out.println(response.getInsertErrors());
        }

        return response.hasErrors();
    }

    public boolean insertQnSimilar(QnSimilarDto qnSimilar) {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        InsertAllRequest request = InsertAllRequest.newBuilder(
                SATISFAI_NLP_DATASET,
                FAQ_QN_SIMILAR_TABLE,
                Converter.qnSimilarDtoToRow(qnSimilar)
        ).build();

        InsertAllResponse response = bigQuery.insertAll(request);
        if (response.hasErrors()) {
            System.out.println(response.getInsertErrors());
        }

        return response.hasErrors();
    }

    public boolean insertQnSimilarDtos(List<QnSimilarDto> qnSimilarDtos) {
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();

        InsertAllRequest request = InsertAllRequest.newBuilder(
                SATISFAI_NLP_DATASET,
                FAQ_QN_SIMILAR_TABLE,
                qnSimilarDtos.stream().map(Converter::qnSimilarDtoToRow).collect(Collectors.toList())
        ).build();

        InsertAllResponse response = bigQuery.insertAll(request);
        if (response.hasErrors()) {
            System.out.println(response.getInsertErrors());
        }

        return response.hasErrors();
    }

    public void query(String text) {

    }

    public void sample() throws TimeoutException, InterruptedException {

        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(
                        "SELECT "
                                + "APPROX_TOP_COUNT(corpus, 10) as title, "
                                + "COUNT(*) as unique_words "
                                + "FROM `publicdata.samples.shakespeare`;")
                        // Use standard SQL syntax for queries.
                        // See: https://cloud.google.com/bigquery/sql-reference/
                        .setUseLegacySql(false)
                        .build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        queryJob = queryJob.waitFor();

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results.
        QueryResponse response = bigquery.getQueryResults(jobId);

        QueryResult result = response.getResult();

        // Print all pages of the results.
        while (result != null) {
            for (List<FieldValue> row : result.iterateAll()) {
                List<FieldValue> titles = row.get(0).getRepeatedValue();
                System.out.println("titles:");

                for (FieldValue titleValue : titles) {
                    List<FieldValue> titleRecord = titleValue.getRecordValue();
                    String title = titleRecord.get(0).getStringValue();
                    long uniqueWords = titleRecord.get(1).getLongValue();
                    System.out.printf("\t%s: %d\n", title, uniqueWords);
                }

                long uniqueWords = row.get(1).getLongValue();
                System.out.printf("total unique words: %d\n", uniqueWords);
            }

            result = result.getNextPage();
        }
    }

}
