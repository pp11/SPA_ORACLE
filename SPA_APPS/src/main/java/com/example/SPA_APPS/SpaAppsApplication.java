package com.example.SPA_APPS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class SpaAppsApplication{

	public static void main(String[] args) {

        SpringApplication.run(SpaAppsApplication.class, args);
        System.out.println("the application is running");
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "sales";
        String password = "sales";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to Oracle database!");
        } catch (Exception e) {
            e.printStackTrace();
        }

	}


}
