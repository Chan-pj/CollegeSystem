package com.chan.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
public class DbConnectionChecker implements CommandLineRunner {

    private final DataSource dataSource;

    public DbConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        System.out.println("===== DB 연결 테스트 시작 =====");
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("✅ DB 연결 성공!");
            System.out.println("DB 제품명   : " + meta.getDatabaseProductName());
            System.out.println("DB 버전     : " + meta.getDatabaseProductVersion());
        } catch (Exception e) {
            System.out.println("❌ DB 연결 실패!");
            System.out.println("에러 메시지 : " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("================================");
    }
}