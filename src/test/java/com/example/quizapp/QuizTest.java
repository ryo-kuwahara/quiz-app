package com.example.quizapp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class QuizTest {

    @Test
    public void toStringWhenMaru() {
        Quiz quiz = new Quiz("問題文", true);
        assertThat(quiz.toString(), is("問題文 〇"));
    }
    @Test
    public void toStringWhenBatu() {
        Quiz quiz = new Quiz("問題文", false);
        assertThat(quiz.toString(), is("問題文 ×"));
    }

    @Test
    public void toFromWhenMaru() {
        String line = "葛西は江戸川区である 〇";
        Quiz quiz = Quiz.fromString(line);

        assertThat(quiz.getQuestion(), is("葛西は江戸川区である"));
        assertThat(quiz.isAnswer(), is(true));
    }

    @Test
    public void toFromWhenBatu() {
        String line = "葛西は足立区である ×";
        Quiz quiz = Quiz.fromString(line);

        assertThat(quiz.getQuestion(), is("葛西は足立区である"));
        assertThat(quiz.isAnswer(), is(false));
    }
}
