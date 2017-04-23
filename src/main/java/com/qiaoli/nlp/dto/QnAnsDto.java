package com.qiaoli.nlp.dto;

/**
 * @author Lu Fangjian
 */
public class QnAnsDto {

    private String id;
    private String qn;
    private String ans;

    public QnAnsDto(String id, String qn, String ans) {
        this.id = id;
        this.qn = qn;
        this.ans = ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
