package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agaf.QuizBuilder.jpa.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {
    Page<Answer> findByQuestionId(Long questionId, Pageable pageable);
}