package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.Requests;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

class Tests {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanBase() {
        Requests.cleanDataBase();
    }

    @Test
    @SneakyThrows
    void shouldLogIn() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = Requests.getAuthCode();
        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.dashboardPageVisible();
    }

    @Test
    void shouldNotLogInByIncorrectPass() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoIncorrectPass();
        loginPage.loginOtherUsers(authInfo);
        loginPage.getInvalidPass();
    }

    @Test
    @SneakyThrows
    void shouldNotLogInByBlockedUser() {
        var loginPage = new LoginPage();
        Requests.setBlockedUser();
        var authInfo = DataHelper.getAuthInfoBlockedUser();
        loginPage.loginOtherUsers(authInfo);
        loginPage.getBlockedUser();
    }

    @Test
    @SneakyThrows
    void shouldBlockSystemByThreeNotLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoIncorrectPass();
        loginPage.loginOtherUsers(authInfo);
        loginPage.getBlockedSystem();
    }
}