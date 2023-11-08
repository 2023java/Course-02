package com.example.course02.Service;

import com.example.course02.dto.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int n);
}
