package com.example.course02.repositories;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.course02.repositories.JavaQuestionRepositoryTest.JavaRepositoryTestData.javaQuestionsDummy;
import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionRepositoryTest {
    private final JavaQuestionRepository sut = new JavaQuestionRepository();

    @Test
    void add_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("C#?", "Нет");

        var expected = new HashSet<>(javaQuestionsDummy);
        expected.add(q);

        assertEquals(q, sut.add(q));
        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_shouldThrowQuestionAlreadyExistExceptionIfQuestionAlreadyExist() {
        assertThrows(QuestionAlreadyExistException.class, () -> sut.add(new Question("Java?", "Да")));
    }

    @Test
    void remove_shouldRemoveQuestionFromCollectionAndReturnRemovedQuestion() {
        var q = new Question("Java?", "Да");

        var expected = new HashSet<>(javaQuestionsDummy);
        expected.remove(q);

        assertEquals(q, sut.remove(q));
        assertEquals(expected, sut.getAll());
    }


    @Test
    void remove_shouldThrowNoSuchElementExceptionIfProvidedQuestionIsNotInTheList() {
        assertThrows(NoSuchElementException.class, () -> sut.remove(new Question("Brainfuck?", "Да")));
    }

    @Test
    void getAll_shouldReturnImmutableCollectionOfAllQuestions() {
        var actual = sut.getAll();
        var expected = new HashSet<>(javaQuestionsDummy);

        assertEquals(actual, expected);
        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Question("a", "b")));
    }
    static class JavaRepositoryTestData {
        static List<Question> javaQuestionsDummy = List.of(
                new Question("Java?", "Да"),
                new Question("Сколько примитивных типов в Java?", "8"),
                new Question("Сколько битов занимает \"int\" ?", "32"),
                new Question("Нужно ли вам явно обрабатывать RuntimeException и его дочерние элементы?", "Нет"),
                new Question("Является ли коллекцией \"Map\"?", "Да, но нет")
        );

    }
}