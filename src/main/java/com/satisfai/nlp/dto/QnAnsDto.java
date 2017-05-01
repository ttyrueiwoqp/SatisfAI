package com.satisfai.nlp.dto;

/**
 * @author Lu Fangjian
 */
public class QnAnsDto {

    private String qnGroupId;
    private String mainQnId;
    private String ansText;

    public QnAnsDto(String qnGroupId, String mainQnId, String ansText) {
        this.qnGroupId = qnGroupId;
        this.mainQnId = mainQnId;
        this.ansText = ansText;
    }

    public String getQnGroupId() {
        return qnGroupId;
    }

    public void setQnGroupId(String qnGroupId) {
        this.qnGroupId = qnGroupId;
    }

    public String getMainQnId() {
        return mainQnId;
    }

    public void setMainQnId(String mainQnId) {
        this.mainQnId = mainQnId;
    }

    public String getAnsText() {
        return ansText;
    }

    public void setAnsText(String ansText) {
        this.ansText = ansText;
    }
}
