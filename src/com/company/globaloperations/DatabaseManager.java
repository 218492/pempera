package com.company.globaloperations;

import com.company.entity.Element;
import com.company.entity.ElementWithQuantity;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static String fullFilePath;

    private static final String DELIMITER = ",";
    private static DataInputStream inputStream = null;

    public static void setDatabaseLocation(String fullFilePath) {
        DatabaseManager.fullFilePath = fullFilePath;
    }

    public static List<Element> loadOrdersFromFile(String filePath) throws IOException, DataManipulationException {
        List<Element> elements = new ArrayList<>();

        try {
            inputStream = new DataInputStream(new FileInputStream(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while (line != null) {
                String[] dataSet = line.split(DELIMITER);
                Element element = ElementAssembler.assembleElement(dataSet);
                elements.add(element);
                line = reader.readLine();
            }

        } catch (WrongDataException e) {
            Integer currentLineNumber = elements.size() + 1;
            throw new WrongDataException(currentLineNumber);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return elements;
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

    public static void insertElement() {
        String sql = "INSERT INTO elements(x_dimension, y_dimension, shape, material, thickness) VALUES(?,?,?,?,?);";

        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, 6.66);
            pstmt.setDouble(2, 7.77);
            pstmt.setString(3, "RECTANGLE");
            pstmt.setString(4, "GALVANIZED");
            pstmt.setString(5, "THICK");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertOrder() {
        String sql = "INSERT INTO orders(name) VALUES(?);";

        try {
            Connection conn = DatabaseManager.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "Czarek");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertOrdersElements() {

    }

    public static void loadAllElements() {
        String sql = "SELECT * FROM elements";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getDouble("x_dimension") + "\t" +
                        rs.getDouble("y_dimension") + "\t" +
                        rs.getString("shape") + "\t" +
                        rs.getString("material") + "\t" +
                        rs.getString("thickness"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadOrder(String name) {
        String sql = "SELECT * FROM orders where name=";
    }

    public static void loadElementFromDatabase() {

    }


    public static void saveOrderToDatabase(List<ElementWithQuantity> elements, String orderName) {

//        elements.stream().forEach(
//
//        );
        //orders.stream().forEach();
    }
}
