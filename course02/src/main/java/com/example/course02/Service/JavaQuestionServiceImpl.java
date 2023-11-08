package com.example.course02.Service;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionServiceImpl implements QuestionService {
    private final Set<Question> questionList;
    private final Random rng = new Random();
    public JavaQuestionServiceImpl() {
        questionList = seedQuestions();
    }

    @Override
    public Question add(String question, String answer) {
        var result = new Question(question, answer);
        if (questionList.contains(result)) throw new QuestionAlreadyExistException();
        questionList.add(result);
        return result;
    }

    @Override
    public Question add(Question question) {
        questionList.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questionList.contains(question)) throw new NoSuchElementException();
        questionList.remove(question);
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Set.copyOf(questionList);
    }

    @Override
    public Question getRandomQuestion() {
        return questionList.toArray(Question[]::new)[rng.nextInt(questionList.size())];
    }

    private HashSet<Question> seedQuestions(){
        var q = new HashSet<Question>();
        q.add(new Question("Java?", "Да"));
        q.add(new Question("Сколько примитивных типов в Java?", "8"));
        q.add(new Question("Сколько битов занимает \"int\" ?", "32"));
        q.add(new Question("Нужно ли вам явно обрабатывать RuntimeException и его дочерние элементы?", "Нет"));
        q.add(new Question("Является ли коллекцией \"Map\"?", "Да, но нет"));
        return q;
    }
}

