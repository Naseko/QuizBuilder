package ru.agaf.QuizBuilder.jpa.repo;

import ru.agaf.QuizBuilder.jpa.model.Quiz;
import java.util.List;

public interface QuizRepositoryCustom {
    List<Quiz> getAllByUserId(Long id);
    List<Quiz> findAllActiveQuizes();
}
