package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class Requests {

    @SneakyThrows
    public static String getAuthCode() {
        var codeSQLFromSelectCode = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");
                ) {
                    var request = runner.query(conn, codeSQLFromSelectCode, new ScalarHandler<>());
                    return (String) request;
        }
    }

    @SneakyThrows
    public static void setBlockedUser() {
        var codeSQLFromInsertUser = "insert into users values (952, 'valerian', '$2a$10$rSNcfu.kl4sQZ5BjClzBieeHAFF4/ElA/G1jCLNt9nZNFUCKkpgv2', 'blocked')";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.update(conn, codeSQLFromInsertUser);
        }
    }
    @SneakyThrows
    public static void cleanDataBase() {
        var codes = "DELETE FROM auth_codes";
        var cardTransaction = "DELETE FROM card_transactions";
        var cards = "DELETE FROM cards";
        var users = "DELETE FROM users";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.update(conn, codes);
            runner.update(conn, cardTransaction);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
    }


}