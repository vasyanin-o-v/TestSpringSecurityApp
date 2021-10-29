package ru.digitalleague.TestSpringSecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.digitalleague.TestSpringSecurityApp.entity.RoleEntity;
import ru.digitalleague.TestSpringSecurityApp.entity.UserEntity;
import ru.digitalleague.TestSpringSecurityApp.repository.RoleRepo;
import ru.digitalleague.TestSpringSecurityApp.repository.UserRepo;

/**
 * Сервис, реализующий {@link UserService}
 * Сохраняет пользователей и выполняет поиск по Login and Password
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleRepo.findByName("ROLE_USER");
        userEntity.setRole(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public UserEntity findByLoginAndPassword(String login, String password) {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}
