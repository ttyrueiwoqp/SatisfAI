package com.qiaoli.nlp.dto;

/**
 * @author Lu Fangjian
 */
public class QnSimilarDto {

    private String id;
    private String qnAnsId;
    private String qnSimilar;

    public QnSimilarDto(String id, String qnAnsId, String qnSimilar) {
        this.id = id;
        this.qnAnsId = qnAnsId;
        this.qnSimilar = qnSimilar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQnAnsId() {
        return qnAnsId;
    }

    public void setQnAnsId(String qnAnsId) {
        this.qnAnsId = qnAnsId;
    }

    public String getQnSimilar() {
        return qnSimilar;
    }

    public void setQnSimilar(String qnSimilar) {
        this.qnSimilar = qnSimilar;
    }
}
