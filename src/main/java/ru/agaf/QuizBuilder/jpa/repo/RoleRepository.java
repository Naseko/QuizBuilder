package ru.agaf.QuizBuilder.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agaf.QuizBuilder.jpa.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}