package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement message = $("[data-test-id=error-notification] .notification__content");

    public VerificationPage validLogin(DataHelper.UserInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void loginOtherUsers(DataHelper.UserInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void getInvalidPass() {
        message.shouldBe(Condition.visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void getBlockedUser() {
        message.shouldBe(Condition.visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    public void getBlockedSystem() {
        loginButton.click();
        loginButton.click();
        message.shouldBe(Condition.visible).shouldHave(text("Ошибка! Система заблокирована"));
    }
}