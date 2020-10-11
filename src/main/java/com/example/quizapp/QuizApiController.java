package com.example.quizapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class QuizApiController {

    QuizFileDao quizFiledao = new QuizFileDao();

    List<Quiz> quizzes = new ArrayList<Quiz>();

    @GetMapping("/quiz")
    public Quiz quiz() {
        int index = new Random().nextInt(quizzes.size());
        return quizzes.get(index);
    }


    @GetMapping("/show")
    public List<Quiz> show() {
        return quizzes;
    }

    @PostMapping("/create")
    public void create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz = new Quiz(question,answer);
        quizzes.add(quiz);
    }

    @PostMapping("/check")
    public String check(@RequestParam String question, @RequestParam boolean answer) {
        for (Quiz quiz : quizzes) {
            if (quiz.getQuestion().equals(question)) {
                if (quiz.isAnswer() == answer) {
                    return "正解です！";
                } else {
                    return "残念。・・・不正解です。";
                }
            }
        }
        return "問題が見つかりませんでした！";
    }

    @PostMapping("/save")
    public String save() {
        try {
            quizFiledao.write(quizzes);
            return "保存しました！";
        } catch (IOException e) {
            e.printStackTrace();
            return "保存に失敗しました。";
        }
    }

    @GetMapping("load")
    public String load() {
        try {
            quizzes = quizFiledao.read();
            return "ファイルを読み込みました。";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルの読み込みに失敗しました。";
        }
    }
}
