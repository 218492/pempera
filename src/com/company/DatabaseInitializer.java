package com.company;

import java.sql.*;

/**
 * Use this class only if there's no database and new one needs to be created
 */
public class DatabaseInitializer {

    public static void initializeNewDatabase(String fullFilePath) {
        createNewDatabase(fullFilePath);
        createOrdersTable(fullFilePath);
    }

    private static void createNewDatabase(String fullFilePath) {

        String url = "jdbc:sqlite:" + fullFilePath;

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createOrdersTable(String fullFilePath) {
        // SQLite connection string
        String url = "jdbc:sqlite:" + fullFilePath;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS orders(\n"
                + "id integer PRIMARY KEY,\n"
                + "quantity integer NOT NULL,\n"
                + "shape text NOT NULL,\n"
                + "material text NOT NULL,\n"
                + "thickness text NOT NULL,\n"
                + "x_dimension real NOT NULL,\n"
                + "y_dimension real NOT NULL\n"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
