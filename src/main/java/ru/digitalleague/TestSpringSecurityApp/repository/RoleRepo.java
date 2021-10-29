package ru.digitalleague.TestSpringSecurityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalleague.TestSpringSecurityApp.entity.RoleEntity;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
