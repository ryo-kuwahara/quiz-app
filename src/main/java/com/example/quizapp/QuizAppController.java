package com.example.quizapp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/page")
public class QuizAppController {

    QuizFileDao quizFiledao = new QuizFileDao();

    List<Quiz> quizzes = new ArrayList<Quiz>();

    @GetMapping("/quiz")
    public String quiz(Model model) {
        int index = new Random().nextInt(quizzes.size());
        model.addAttribute("quiz", quizzes.get(index));
        return "quiz";
    }


    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("quizzes", quizzes);
        return "list";
    }

    @PostMapping("/create")
    public String create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz = new Quiz(question,answer);
        quizzes.add(quiz);
        return "redirect:/page/show";
    }

    @GetMapping("/check")
    public String check(Model model,@RequestParam String question, @RequestParam boolean answer) {
        for (Quiz quiz : quizzes) {
           if (quiz.getQuestion().equals(question)) {
               model.addAttribute("quiz", quiz);
                if (quiz.isAnswer() == answer) {
                   model.addAttribute("result", "正解！");
                } else {
                   model.addAttribute("result", "残念。・・・不正解です。");
                }
           }
        }
        return "answer";
    }

    @PostMapping("/save")
    public String save(RedirectAttributes attributes) {
        try {
            quizFiledao.write(quizzes);
            attributes.addFlashAttribute("successMessage","ファイルに保存しました。");
            return "redirect:/page/show";
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの保存に失敗しました。");
            return "redirect:/page/show";
        }
    }

    @GetMapping("load")
    public String load(RedirectAttributes attributes) {
        try {
            quizzes = quizFiledao.read();
            attributes.addFlashAttribute("successMessage","ファイルを読み込みました。");
            return "redirect:/page/show";
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの読み込みに失敗しました。");
            return "redirect:/page/show";
        }
    }
}
