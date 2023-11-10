package com.example.course02.Service;

import com.example.course02.Exceptions.TooManyQuestionsException;
import com.example.course02.dto.Question;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService javaQuestionService;
    private final QuestionService mathQuestionService;
    private final Random rng;

    public ExaminerServiceImpl(@Qualifier("javaService") QuestionService javaQuestionService, @Qualifier("mathService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
        this.rng = new Random();
    }

    @Override
    public Collection<Question> getQuestions(int n) {
        if (n > mathQuestionService.getAll().size() + javaQuestionService.getAll().size()){
            throw new TooManyQuestionsException();
        }

        var result = new ArrayList<Question>();
        Question q;

        //оно работает, но алгоритм очень неочень.
        while (result.size() < n){
            if(rng.nextBoolean()) q = mathQuestionService.getRandomQuestion();
            else q = javaQuestionService.getRandomQuestion();

            if (!result.contains(q)) result.add(q);
        }
        return result;
    }
}
