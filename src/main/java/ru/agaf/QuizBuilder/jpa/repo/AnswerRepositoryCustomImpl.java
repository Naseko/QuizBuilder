package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.stereotype.Repository;
import ru.agaf.QuizBuilder.jpa.model.Answer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AnswerRepositoryCustomImpl implements AnswerRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Answer> findAllByQuestionIdAndUserId(Long questionId, Long userId) {
        Query query = entityManager.createNativeQuery(
                "select a.* FROM answers as a WHERE a.question_id = :qid AND a.user_id = :uid", Answer.class);
        query.setParameter("qid", questionId);
        query.setParameter("uid", userId);
        return query.getResultList();
    }
}
