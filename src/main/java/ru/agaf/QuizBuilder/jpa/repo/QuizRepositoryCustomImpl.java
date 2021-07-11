package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.stereotype.Repository;
import ru.agaf.QuizBuilder.jpa.model.Quiz;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Quiz> getAllByUserId(Long ids) {
        Query query = entityManager.createNativeQuery(
                "select q.*, u.* from quizs as q inner join users as u ON q.id=u.id WHERE u.id = ?", Quiz.class);
        query.setParameter(1, ids);
        return query.getResultList();
    }

    @Override
    public List<Quiz> findAllActiveQuizes() {
        Query query = entityManager.createNativeQuery(
                "SELECT e.* FROM quizs as e WHERE e.stop_date > ?", Quiz.class);
        query.setParameter(1, LocalDateTime.now());
        System.out.println("[" + LocalDateTime.now() + "]");
        return query.getResultList();
    }
}
