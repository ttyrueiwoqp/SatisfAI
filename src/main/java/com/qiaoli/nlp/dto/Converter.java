package com.qiaoli.nlp.dto;

import com.google.cloud.bigquery.InsertAllRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lu Fangjian
 */
public class Converter {

    public static InsertAllRequest.RowToInsert qnAnsDtoToRow(QnAnsDto qnAnsDto) {
        Map<String, Object> m = new HashMap<>();
        m.put("ID", qnAnsDto.getId());
        m.put("QN", qnAnsDto.getQn());
        m.put("ANS", qnAnsDto.getAns());

        return InsertAllRequest.RowToInsert.of(qnAnsDto.getId(), m);
    }

    public static InsertAllRequest.RowToInsert qnSimilarDtoToRow(QnSimilarDto qnSimilarDto) {
        Map<String, Object> m = new HashMap<>();
        m.put("ID", qnSimilarDto.getId());
        m.put("QN_ANS_ID", qnSimilarDto.getQnAnsId());
        m.put("QN_SIMILAR", qnSimilarDto.getQnSimilar());

        return InsertAllRequest.RowToInsert.of(qnSimilarDto.getId(), m);
    }
}
