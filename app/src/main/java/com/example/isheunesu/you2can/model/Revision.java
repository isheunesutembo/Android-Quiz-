package com.example.isheunesu.you2can.model;

public class Revision {
    private String Question,CorrectAnswer,AnswerA,AnswerB,AnswerC,AnswerD,CategoryId;

    public Revision() {
    }

    public Revision(String question, String correctAnswer,String AnswerA,String AnswerB,String AnswerC,String AnswerD,String CategoryId) {
        this.Question = question;
        this.CorrectAnswer = correctAnswer;
        this.AnswerA=AnswerA;
        this.AnswerB=AnswerB;
        this.AnswerC=AnswerC;
        this.AnswerD=AnswerD;
        this.CategoryId=CategoryId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
