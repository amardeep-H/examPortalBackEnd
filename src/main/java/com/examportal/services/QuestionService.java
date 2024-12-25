package com.examportal.services;

import com.examportal.models.Question;
import com.examportal.models.Quiz;

import java.util.Set;

public interface QuestionService {

    public Question addQuestion(Question question);
    public Question updateQuestion(Question question);
    public Set<Question> getQuestions();
    public Question getQuestion(Long queId);

    public Set<Question> getQuestionsOFQuiz(Quiz quiz);
    public void deleteQuestion(Long queId);

    public Question getQbyId(Long queId);

}
