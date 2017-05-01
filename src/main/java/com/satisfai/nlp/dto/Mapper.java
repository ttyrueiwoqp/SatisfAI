package com.satisfai.nlp.dto;

import com.google.cloud.bigquery.InsertAllRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lu Fangjian
 */
public class Mapper {

    public static InsertAllRequest.RowToInsert qnAnsDtoToRow(QnAnsDto qnAnsDto) {
        Map<String, Object> m = new HashMap<>();
        m.put("qn_group_id", qnAnsDto.getQnGroupId());
        m.put("main_qn_id", qnAnsDto.getMainQnId());
        m.put("ans_text", qnAnsDto.getAnsText());

        return InsertAllRequest.RowToInsert.of(qnAnsDto.getQnGroupId(), m);
    }

    public static InsertAllRequest.RowToInsert qnGroupDtoToRow(QnGroupDto qnGroupDto) {
        Map<String, Object> m = new HashMap<>();
        m.put("qn_id", qnGroupDto.getQnId());
        m.put("qn_group_id", qnGroupDto.getQnGroupId());
        m.put("qn_text", qnGroupDto.getQnText());

        return InsertAllRequest.RowToInsert.of(qnGroupDto.getQnId(), m);
    }
}
