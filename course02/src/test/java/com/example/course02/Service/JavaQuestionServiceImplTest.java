package com.example.course02.Service;

import com.example.course02.Exceptions.QuestionAlreadyExistException;
import com.example.course02.dto.Question;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;

import static com.example.course02.Service.JavaQuestionServiceImplTest.JavaQuestionServiceTestData.fillDummy;
import static com.example.course02.Service.JavaQuestionServiceImplTest.JavaQuestionServiceTestData.questionsDummy;
import static org.junit.jupiter.api.Assertions.*;


class JavaQuestionServiceImplTest {
    private final JavaQuestionServiceImpl sut;

    JavaQuestionServiceImplTest() {
        // вероятно, следует сбрасывать макет перед каждым тестом, а не копировать его в каждом тесте, изменяя список;
        fillDummy();
        this.sut = new JavaQuestionServiceImpl();
    }

    @Test
    void add_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("C#?", "Нет");
        var actual = sut.add("C#?", "Нет");
        assertEquals(q, actual);

        var expected = new HashSet<>(questionsDummy);
        expected.add(q);
        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_Question_shouldAddQuestionToTheSetAndReturnAddedQuestion() {
        var q = new Question("Haskell?", "Нет");
        var actual = sut.add(q);
        assertEquals(q, actual);
        var expected = new HashSet<>(questionsDummy);
        expected.add(q);
        assertEquals(expected, sut.getAll());
    }

    @Test
    void add_shouldThrowQuestionAlreadyExistExceptionIfQuestionAlreadyExist() {
        assertThrows(QuestionAlreadyExistException.class, () -> sut.add("Java?", "Да"));
    }

    @Test
    void remove_shouldRemoveQuestionFromCollectionAndReturnRemovedQuestion() {
        var q = new Question("Java?", "Да");
        var actual = sut.remove(q);
        assertEquals(q, actual);
        var expected = new HashSet<>(questionsDummy);
        expected.remove(q);
        assertEquals(expected, sut.getAll());
    }


    @Test
    void remove_shouldThrowNoSuchElementExceptionIfProvidedQuestionIsNotInTheList() {
        assertThrows(NoSuchElementException.class, () -> sut.remove(new Question("Brainfuck?", "Да")));
    }

    @Test
    void getAll_shouldReturnImmutableCollectionOfAllQuestions() {
        var actual = sut.getAll();
        var expected = new HashSet<>(questionsDummy);
        assertEquals(actual, expected);
        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Question("a", "b")));
    }

    @Test
    void getRandomQuestion_shouldReturnRandomQuestionFromTheList() {
        var actual = sut.getRandomQuestion();
        assertTrue(questionsDummy.contains(actual));
    }

    static class JavaQuestionServiceTestData {
        static HashSet<Question> questionsDummy = new HashSet<>();

        public static void fillDummy() {
            questionsDummy.add(new Question("Java?", "Да"));
            questionsDummy.add(new Question("Сколько примитивных типов в Java?", "8"));
            questionsDummy.add(new Question("Сколько битов занимает \"int\"?", "32"));
            questionsDummy.add(new Question("Нужно ли вам явно обрабатывать RuntimeException и его дочерние элементы", "Нет"));
            questionsDummy.add(new Question("Является ли коллекцией \"Map\"", "Да, но нет"));
        }
    }
}