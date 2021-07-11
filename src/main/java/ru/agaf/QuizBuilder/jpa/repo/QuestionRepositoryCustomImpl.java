package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.stereotype.Repository;
import ru.agaf.QuizBuilder.jpa.model.Question;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Question> findAllByQuizId(Long ids) {
        Query query = entityManager.createNativeQuery(
                "select z.*, s.* from questions as z inner join quizs as s ON z.id=s.id WHERE s.id = ?", Question.class);
        query.setParameter(1, ids);
        return query.getResultList();
    }
}
