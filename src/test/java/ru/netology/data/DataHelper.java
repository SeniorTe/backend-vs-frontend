package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }
    @Value
    public static class UserInfo {
        private String login;
        private String password;
        private String status;
    }

    public static UserInfo getAuthInfo() {
        return new UserInfo("vasya", "qwerty123", "active");
    }

    public static UserInfo getAuthInfoIncorrectPass() {
        Faker faker = new Faker();
        String pass = faker.internet().password();
        return new UserInfo("petya", pass, "active");
    }

    public static UserInfo getAuthInfoBlockedUser() {
        return new UserInfo("valerian", "qwerty123", "blocked");
    }
}