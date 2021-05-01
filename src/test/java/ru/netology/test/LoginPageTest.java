package ru.netology.test;


import com.github.javafaker.Faker;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;


public class LoginPageTest {



    @Test
    void shouldAuthWithSmsCode() throws SQLException {
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
    void shouldBlockAuth() throws SQLException {
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
