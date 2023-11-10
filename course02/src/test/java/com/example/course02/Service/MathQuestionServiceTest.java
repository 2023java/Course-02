package com.example.course02.Service;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import com.example.course02.repositories.MathQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.example.course02.Service.MathQuestionServiceTest.MathQuestionServiceTestData.fillDummy;
import static com.example.course02.Service.MathQuestionServiceTest.MathQuestionServiceTestData.questionsDummy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {
    @Mock
    private MathQuestionRepository questionRepository;
    private MathQuestionService sut;

    @BeforeEach
    private void resetDummy() {
        sut = new MathQuestionService(questionRepository);
        questionsDummy.clear();
        fillDummy();
    }

    @Test
    void add_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("2+2?", "4");

        when(questionRepository.add(q)).thenReturn(q);
        when(questionRepository.getAll()).thenReturn(questionsDummy);

        assertEquals(q, sut.add("2+2?", "4"));

        var expected = new HashSet<>(questionsDummy);
        questionsDummy.add(q);
        expected.add(q);

        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_Question_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("3+3?", "6");

        when(questionRepository.add(q)).thenReturn(q);
        when(questionRepository.getAll()).thenReturn(questionsDummy);

        assertEquals(q, sut.add(q));

        var expected = new HashSet<>(questionsDummy);
        questionsDummy.add(q);
        expected.add(q);

        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_shouldThrowQuestionAlreadyExistExceptionIfQuestionAlreadyExist() {
        when(questionRepository.add(any())).thenThrow(QuestionAlreadyExistException.class);
        assertThrows(QuestionAlreadyExistException.class, () -> sut.add("1+1?", "2"));
    }

    @Test
    void remove_shouldRemoveQuestionFromCollectionAndReturnRemovedQuestion() {
        var q = new Question("1+1?", "2");

        when(questionRepository.remove(q)).thenReturn(q);
        when(questionRepository.getAll()).thenReturn(questionsDummy);

        assertEquals(q, sut.remove(q));

        var expected = new HashSet<>(questionsDummy);
        questionsDummy.remove(q);
        expected.remove(q);

        assertEquals(expected, sut.getAll());
    }


    @Test
    void remove_shouldThrowNoSuchElementExceptionIfProvidedQuestionIsNotInTheList() {
        when(questionRepository.remove(any())).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> sut.remove(new Question("2+2?", "4")));
    }

    @Test
    void getAll_shouldReturnImmutableCollectionOfAllQuestions() {
        when(questionRepository.getAll()).thenReturn(Set.copyOf(questionsDummy));

        var actual = sut.getAll();
        var expected = new HashSet<>(questionsDummy);

        assertEquals(actual, expected);
        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Question("a", "b")));
    }

    @Test
    void getRandomQuestion_shouldReturnRandomQuestionFromTheList() {
        when(questionRepository.getAll()).thenReturn(Set.copyOf(questionsDummy));

        var actual = sut.getRandomQuestion();
        assertTrue(questionsDummy.contains(actual));
    }

    static class MathQuestionServiceTestData {
        static Set<Question> questionsDummy = new HashSet<>();

        public static void fillDummy() {
            questionsDummy.add(new Question("1+1?", "2"));
            questionsDummy.add(new Question("13*2?", "26"));
            questionsDummy.add(new Question("sin(90)?", "1"));
            questionsDummy.add(new Question("sqrt(4)?", "2"));
            questionsDummy.add(new Question("Pi, two decimal places", "3.14"));
        }
    }
}