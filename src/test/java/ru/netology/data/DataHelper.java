package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthErrorNotification() {
        return new AuthInfo("petya", "12345");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(){
        String verificationCode = "";
        val codesSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://0.0.0.0:3306/app", "vasya", "qwerty123")) {
            val usersCode = runner.query(conn, codesSQL, new BeanHandler<>(User.class));
            verificationCode = usersCode.getCode();
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return new VerificationCode(verificationCode);
    }

    public static void cleanDataBase(){
        val cleanCards = "DELETE FROM cards";
        val cleanAuthCodes = "DELETE FROM auth_codes";
        val cleanUser = "DELETE FROM users";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://0.0.0.0:3306/app", "vasya", "qwerty123")) {
            val cleanCardsUser = runner.execute(conn, cleanCards);
            val cleanAuthCodesUser = runner.execute(conn, cleanAuthCodes);
            val cleanUserUser = runner.execute(conn, cleanUser);
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}