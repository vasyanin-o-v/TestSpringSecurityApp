package ru.digitalleague.TestSpringSecurityApp.service;

import ru.digitalleague.TestSpringSecurityApp.entity.UserEntity;

public interface UserService {
    public UserEntity saveUser(UserEntity userEntity);

    public UserEntity findByLogin(String login);

    public UserEntity findByLoginAndPassword(String login, String password);

}
