package com.example.course02.interfaces;

import com.example.course02.dto.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question add (Question question);
    Question remove (Question question);
    Collection<Question> getAll();
}
