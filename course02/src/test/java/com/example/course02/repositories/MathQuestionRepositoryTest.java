package com.example.course02.repositories;


import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.course02.repositories.MathQuestionRepositoryTest.MathRepositoryTestData.mathQuestionsDummy;
import static org.junit.jupiter.api.Assertions.*;

class MathQuestionRepositoryTest {
    private final MathQuestionRepository sut = new MathQuestionRepository();

    @Test
    void add_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("2+2?", "4");

        var expected = new HashSet<>(mathQuestionsDummy);
        expected.add(q);

        assertEquals(q, sut.add(q));
        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_shouldThrowQuestionAlreadyExistExceptionIfQuestionAlreadyExist() {
        assertThrows(QuestionAlreadyExistException.class, () -> sut.add(new Question("1+1?", "2")));
    }

    @Test
    void remove_shouldRemoveQuestionFromCollectionAndReturnRemovedQuestion() {
        var q = new Question("1+1?", "2");

        var expected = new HashSet<>(mathQuestionsDummy);
        expected.remove(q);

        assertEquals(q, sut.remove(q));
        assertEquals(expected, sut.getAll());
    }


    @Test
    void remove_shouldThrowNoSuchElementExceptionIfProvidedQuestionIsNotInTheList() {
        assertThrows(NoSuchElementException.class, () -> sut.remove(new Question("2+2?", "4")));
    }

    @Test
    void getAll_shouldReturnImmutableCollectionOfAllQuestions() {
        var actual = sut.getAll();
        var expected = new HashSet<>(mathQuestionsDummy);

        assertEquals(actual, expected);
        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Question("a", "b")));
    }

    static class MathRepositoryTestData {
        static List<Question> mathQuestionsDummy = List.of(
                new Question("1+1?", "2"),
                new Question("13*2?", "26"),
                new Question("sin(90)?", "1"),
                new Question("sqrt(4)?", "2"),
                new Question("Значения числа Пи", "3.14")
        );
    }
}