package com.example.course02.Service;

import com.example.course02.dto.Question;
import com.example.course02.interfaces.QuestionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;

@Service("mathService")
public class MathQuestionService implements QuestionService {
    private final Random rng = new Random();
    private final QuestionRepository questionRepository;

    public MathQuestionService(@Qualifier("mathRepository") QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question add(String question, String answer) {
        var q = new Question(question, answer);
        return questionRepository.add(q);
    }

    @Override
    public Question add(Question question) {
        return questionRepository.add(question);
    }

    @Override
    public Question remove(Question question) {
        return questionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        var questions = questionRepository.getAll();
        return questions.toArray(Question[]::new)[rng.nextInt(questions.size())];
    }
}
