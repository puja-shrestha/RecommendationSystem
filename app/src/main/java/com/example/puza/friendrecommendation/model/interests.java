package com.example.puza.friendrecommendation.model;

public class interests {

    private String interest;
    private String checkbox;

    public interests(String interest, String checkbox) {
        this.interest = interest;
        this.checkbox = checkbox;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }
}
