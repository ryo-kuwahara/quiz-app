package com.example.quizapp;

public class Quiz {

    private String question;
    private boolean answer;

    public Quiz(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        String maruBatu = answer ? "〇" : "×" ;
        return question + " " + maruBatu;
    }

    public static Quiz fromString(String line) {
        String question = line.substring(0, line.length() - 2);
        boolean answer = line.endsWith("〇");
        return new Quiz(question, answer);
    }

}
