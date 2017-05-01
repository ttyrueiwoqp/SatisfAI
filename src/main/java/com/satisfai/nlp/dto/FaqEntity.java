package com.satisfai.nlp.dto;

/**
 * @author Lu Fangjian
 */
public class FaqEntity {

    private String qnText;
    private String matchedQnId;
    private String matchedQnText;
    private String qnGroupId;
    private String ansText;
    private String mainQnId;
    private String mainQnText;

    public String getQnText() {
        return qnText;
    }

    public void setQnText(String qnText) {
        this.qnText = qnText;
    }

    public String getMatchedQnId() {
        return matchedQnId;
    }

    public void setMatchedQnId(String matchedQnId) {
        this.matchedQnId = matchedQnId;
    }

    public String getMatchedQnText() {
        return matchedQnText;
    }

    public void setMatchedQnText(String matchedQnText) {
        this.matchedQnText = matchedQnText;
    }

    public String getQnGroupId() {
        return qnGroupId;
    }

    public void setQnGroupId(String qnGroupId) {
        this.qnGroupId = qnGroupId;
    }

    public String getAnsText() {
        return ansText;
    }

    public void setAnsText(String ansText) {
        this.ansText = ansText;
    }

    public String getMainQnId() {
        return mainQnId;
    }

    public void setMainQnId(String mainQnId) {
        this.mainQnId = mainQnId;
    }

    public String getMainQnText() {
        return mainQnText;
    }

    public void setMainQnText(String mainQnText) {
        this.mainQnText = mainQnText;
    }
}
