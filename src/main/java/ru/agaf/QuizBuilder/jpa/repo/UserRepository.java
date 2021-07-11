package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.agaf.QuizBuilder.jpa.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
