package ru.agaf.QuizBuilder.jpa.repo;

import ru.agaf.QuizBuilder.jpa.model.Question;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<Question> findAllByQuizId(Long id);
}
