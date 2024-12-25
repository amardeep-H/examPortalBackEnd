package com.examportal.controllers;


import com.examportal.models.Question;
import com.examportal.models.Quiz;
import com.examportal.services.QuestionService;
import com.examportal.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;


    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    @PutMapping("/")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question){
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

//    get all questions of any quiz
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("quizId") Long quizId){
//        Quiz quiz = new Quiz();
//        quiz.setqId(quizId);
//        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOFQuiz(quiz);
//        return ResponseEntity.ok(questionsOfQuiz);
        Quiz quiz = this.quizService.getQuiz(quizId);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList(questions);
        if(list.size()>Integer.parseInt(quiz.getNumOfQue())){
            list = list.subList(0, Integer.parseInt(quiz.getNumOfQue()+1));
        }

        list.forEach((q)->{
            q.setAns("");
        });

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/quiz/all/{quizId}")
    public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("quizId") Long quizId){
        Quiz quiz = new Quiz();
        quiz.setqId(quizId);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOFQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);

    }

    //get Single question
    @GetMapping("/{queId}")
    public Question getQue(@PathVariable("queId") Long queId){
        return this.questionService.getQuestion(queId);
    }


    @DeleteMapping("/{queId}")
    public void deleteQue(@PathVariable("queId") Long queId){
        this.questionService.deleteQuestion(queId);
    }

    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
//        System.out.println(questions);
        double marksGot = 0;
        int correctAns = 0;
        int attempted=0;
        for(Question que:questions){
           Question question = this.questionService.getQbyId(que.getQueId());
            System.out.println(question);
           if(question.getAns().equals(que.getGivenAns())){
                correctAns++;

               double marksSingleQ = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / (double)questions.size();
               marksGot += marksSingleQ;

           }

           if (que.getGivenAns() != "") {
               System.out.println("getGivenAns :"+que.getGivenAns());
               attempted++;
           }
        }
        System.out.println("attempted : "+ attempted);
        Map<Object, Object> map = Map.of("marksGot",marksGot,"correctAns",correctAns,"attempted",attempted);
        return ResponseEntity.ok(map);
    }
}
