package ru.digitalleague.TestSpringSecurityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalleague.TestSpringSecurityApp.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
}
