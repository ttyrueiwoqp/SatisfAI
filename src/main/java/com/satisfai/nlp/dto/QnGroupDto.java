package com.satisfai.nlp.dto;

/**
 * @author Lu Fangjian
 */
public class QnGroupDto {

    private String qnId;
    private String qnGroupId;
    private String qnText;

    public QnGroupDto(String qnId, String qnGroupId, String qnText) {
        this.qnId = qnId;
        this.qnGroupId = qnGroupId;
        this.qnText = qnText;
    }

    public String getQnId() {
        return qnId;
    }

    public void setQnId(String qnId) {
        this.qnId = qnId;
    }

    public String getQnGroupId() {
        return qnGroupId;
    }

    public void setQnGroupId(String qnGroupId) {
        this.qnGroupId = qnGroupId;
    }

    public String getQnText() {
        return qnText;
    }

    public void setQnText(String qnText) {
        this.qnText = qnText;
    }
}
