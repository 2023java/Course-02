package com.example.course02.Service;


import com.example.course02.Exceptions.TooManyQuestionsException;
import com.example.course02.dto.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.List;

import static com.example.course02.Service.ExaminerServiceImplTest.ExaminerServiceTestData.questionsDummy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    @Mock
    private JavaQuestionServiceImpl javaQuestionService;
    private ExaminerServiceImpl sut;

    @BeforeEach
    private void initializeSut() {
        when(javaQuestionService.getAll()).thenReturn(new HashSet<>(questionsDummy));

        sut = new ExaminerServiceImpl(javaQuestionService);
    }

    @Test
    void getQuestions_shouldReturnCollectionOfUniqueRandomQuestionsFromTheList() {
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(questionsDummy.get(2))
                .thenReturn(questionsDummy.get(3))
                .thenReturn(questionsDummy.get(2))
                .thenReturn(questionsDummy.get(3))
                .thenReturn(questionsDummy.get(0))
                .thenReturn(questionsDummy.get(1));

        int count = 4;
        var actual = sut.getQuestions(count);
        verify(javaQuestionService, times(6)).getRandomQuestion();
        assertEquals(count, actual.stream().distinct().count());
    }

    @Test
    void getQuestions_shouldThrowTooManyQuestionsExceptionIfAmountProvidedIsMoreThanAmountOfQuestionsExisting() {
        assertThrows(TooManyQuestionsException.class, () -> sut.getQuestions(6));
    }

    static class ExaminerServiceTestData {
        static List<Question> questionsDummy = List.of(
                new Question("Java?", "Да"),
                new Question("Сколько примитивных типов в Java?", "8"),
                new Question("Сколько битов занимает \"int\"", "32"),
                new Question("Нужно ли вам явно обрабатывать RuntimeException и его дочерние элементы", "Нет"),
                new Question("Является ли коллекцией \\\"Map\\\"", "Да, но нет"));
    }
}