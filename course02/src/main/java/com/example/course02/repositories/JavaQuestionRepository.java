package com.example.course02.repositories;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import com.example.course02.interfaces.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service("javaRepository")
public class JavaQuestionRepository implements QuestionRepository {
    private Set<Question> questionSet;

    public JavaQuestionRepository() {
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
        this.questionSet = new HashSet<>();
        questionSet.add(new Question("Java?", "Да"));
        questionSet.add(new Question("Сколько примитивных типов в Java?", "8"));
        questionSet.add(new Question("Сколько битов занимает \"int\" ?", "32"));
        questionSet.add(new Question("Нужно ли вам явно обрабатывать RuntimeException и его дочерние элементы?", "Нет"));
        questionSet.add(new Question("Является ли коллекцией \"Map\"?", "Да, но нет"));

    }
}
