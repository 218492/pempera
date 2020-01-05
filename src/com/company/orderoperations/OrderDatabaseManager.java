package com.company.orderoperations;

import com.company.entity.Order;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDatabaseManager {
    private static String fullFilePath;

    private static final String DELIMITER = ",";
    private static DataInputStream inputStream = null;

    public static void setDatabaseLocation(String fullFilePath){
        OrderDatabaseManager.fullFilePath = fullFilePath;
    }

    public static List<Order> loadOrdersFromFile(String filePath) throws IOException, DataManipulationException {
        List<Order> orders = new ArrayList<>();

        try {
            inputStream = new DataInputStream(new FileInputStream(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while (line != null) {
                String[] dataSet = line.split(DELIMITER);
                Order order = OrderAssembler.assembleOrder(dataSet);
                orders.add(order);
                line = reader.readLine();
            }

        } catch (WrongDataException e) {
            Integer currentLineNumber = orders.size() + 1;
            throw new WrongDataException(currentLineNumber);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return orders;
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:" + fullFilePath;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void insert() {
        String sql = "INSERT INTO ORDERS(quantity, shape, material, thickness, x_dimension, y_dimension) VALUES(?,?,?,?,?,?);";

        try{
            Connection conn = OrderDatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, "RECTANGLE");
            pstmt.setString(3, "GALVANIZED");
            pstmt.setString(4, "THICK");
            pstmt.setDouble(5,  6.66);
            pstmt.setDouble(6,  7.77);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll() {
        String sql = "SELECT * FROM ORDERS";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // quantity, shape, material, thickness, x_dimension, y_dimension
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("quantity") + "\t" +
                        rs.getString("shape"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void saveOrdersToFile(List<Order> orders, String filePath) {
        //orders.stream().forEach();
    }
}
