package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class LoginPageTest {

    @AfterAll
    static void cleanBase() {
        DataHelper.cleanDataBase();
    }

    @Test
    void shouldAuthWithSmsCode()  {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.dashboardPage();
    }

    @Test
    void shouldAuthErrorNotification(){
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthErrorNotification();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
    }

    @Test
    void shouldBlockAuth() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthErrorNotification();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
        loginPage.cleanField();

        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
        loginPage.cleanField();

        loginPage.validLogin(authInfo);
        loginPage.searchErrorMessage();
    }
}
