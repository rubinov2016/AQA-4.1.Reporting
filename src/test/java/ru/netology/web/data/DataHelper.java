package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {

        var codeSQL = "select ac.code from auth_codes ac , users u where u.login ='vasya' and u.id =ac.user_id ORDER by ac.created DESC LIMIT 1;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app?allowPublicKeyRetrieval=true&useSSL=false", "app", "pass"
                );
        ) {
            var codeObject = runner.query(conn, codeSQL, new ScalarHandler<>());
            var codeString = codeObject.toString();
            return new VerificationCode(codeString);
        }
    }

    @SneakyThrows
    public static void truncateTables() {
        var dataSQLAuth = "delete from auth_codes;";
        var dataSQLUsers = "delete from users;";
        var dataSQLCards = "delete from cards;";
        var dataSQLTransactions = "delete from card_transactions;";
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app?allowPublicKeyRetrieval=true&useSSL=false", "app", "pass"
                );
                var dataStmtCards = conn.prepareStatement(dataSQLCards);
                var dataStmtransactions = conn.prepareStatement(dataSQLTransactions);
                var dataStmtAuth = conn.prepareStatement(dataSQLAuth);
                var dataStmtUsers = conn.prepareStatement(dataSQLUsers);
        ) {
            dataStmtCards.executeUpdate();
            dataStmtransactions.executeUpdate();
            dataStmtAuth.executeUpdate();
            dataStmtUsers.executeUpdate();
        }
    }

}
