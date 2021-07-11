package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agaf.QuizBuilder.jpa.model.Quiz;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryCustom {
}
