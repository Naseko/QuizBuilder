package ru.agaf.QuizBuilder.jpa.repo;

import ru.agaf.QuizBuilder.jpa.model.Answer;

import java.util.List;

public interface AnswerRepositoryCustom {
    List<Answer> findAllByQuestionIdAndUserId(Long questionId, Long id);

}
