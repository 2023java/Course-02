package com.example.course02.repositories;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import com.example.course02.interfaces.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service("mathRepository")
public class MathQuestionRepository implements QuestionRepository {
    private Set<Question> questionSet;

    public MathQuestionRepository() {
        this.init();
    }

    @Override
    public Question add(Question question) {
        if (questionSet.contains(question)) throw new QuestionAlreadyExistException();
        questionSet.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questionSet.contains(question)) throw new NoSuchElementException();
        questionSet.remove(question);
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Set.copyOf(questionSet);
    }

    //@PostConstruct
    private void init() {
        this.questionSet = new HashSet<Question>();
        questionSet.add(new Question("1+1?", "2"));
        questionSet.add(new Question("13*2?", "26"));
        questionSet.add(new Question("sin(90)?", "1"));
        questionSet.add(new Question("sqrt(4)?", "2"));
        questionSet.add(new Question("Значения числа Пи", "3.14"));
    }
}
