package com.example.course02.Service;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import com.example.course02.repositories.JavaQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.example.course02.Service.JavaQuestionServiceImplTest.JavaQuestionServiceTestData.fillDummy;
import static com.example.course02.Service.JavaQuestionServiceImplTest.JavaQuestionServiceTestData.questionsDummy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceImplTest {
    @Mock
    private JavaQuestionRepository questionRepository;
    private JavaQuestionServiceImpl sut;

    @BeforeEach
    private void resetDummy() {
        sut = new JavaQuestionServiceImpl(questionRepository);
        questionsDummy.clear();
        fillDummy();
    }

    @Test
    void add_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("C#?", "No");

        when(questionRepository.add(q)).thenReturn(q);
        when(questionRepository.getAll()).thenReturn(questionsDummy);

        assertEquals(q, sut.add("C#?", "No"));

        var expected = new HashSet<>(questionsDummy);
        questionsDummy.add(q);
        expected.add(q);

        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_Question_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("Haskell?", "No");

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
        assertThrows(QuestionAlreadyExistException.class, () -> sut.add("Java?", "Yes"));
    }

    @Test
    void remove_shouldRemoveQuestionFromCollectionAndReturnRemovedQuestion() {
        var q = new Question("Java?", "Yes");

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
        assertThrows(NoSuchElementException.class, () -> sut.remove(new Question("Brainfuck?", "Yes")));
    }

    @Test
    void getAll_shouldReturnImmutableCollectionOfAllQuestions() {
        when(questionRepository.getAll()).thenReturn(Set.copyOf(questionsDummy));

        var actual = sut.getAll();
        var expected = new HashSet<>(questionsDummy);

        assertEquals(expected, actual);
        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Question("a", "b")));
    }

    @Test
    void getRandomQuestion_shouldReturnRandomQuestionFromTheList() {
        when(questionRepository.getAll()).thenReturn(Set.copyOf(questionsDummy));

        var actual = sut.getRandomQuestion();
        assertTrue(questionsDummy.contains(actual));
    }

    static class JavaQuestionServiceTestData {
        static Set<Question> questionsDummy = new HashSet<>();

        public static void fillDummy() {
            questionsDummy.add(new Question("Java?", "Да"));
            questionsDummy.add(new Question("Сколько примитивных типов в Java?", "8"));
            questionsDummy.add(new Question("Сколько битов занимает \"int\"?", "32"));
            questionsDummy.add(new Question("Нужно ли вам явно обрабатывать RuntimeException и его дочерние элементы", "Нет"));
            questionsDummy.add(new Question("Является ли коллекцией \"Map\"", "Да, но нет"));
        }
    }
}