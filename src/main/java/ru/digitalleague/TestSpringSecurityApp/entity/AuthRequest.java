package ru.digitalleague.TestSpringSecurityApp.entity;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
