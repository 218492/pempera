package com.company.globaloperations;

import com.company.entity.Dimensions;
import com.company.entity.Element;
import com.company.entity.ElementWithQuantity;
import com.company.entity.Order;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class DatabaseManager {
    private static String fullFilePath;

    private static final String DELIMITER = ",";
    private static DataInputStream inputStream = null;
    private Connection conn;

    public DatabaseManager(){
        connect();
    }

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

    public Connection connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + fullFilePath;
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void saveElementToDatabase(Element element) {
        Element foundElement = findElement(element);

        if (foundElement == null || foundElement.getId()==null) {
            String sql = "INSERT INTO elements(x_dimension, y_dimension, shape, material, thickness) VALUES(?,?,?,?,?);";
            try {
                PreparedStatement pstmt = this.conn.prepareStatement(sql);
                pstmt.setDouble(1, element.getDimensions().getX_dimension());
                pstmt.setDouble(2, element.getDimensions().getY_dimension());
                pstmt.setString(3, element.getPlateShape().toString());
                pstmt.setString(4, element.getPlateMaterialType().toString());
                pstmt.setString(5, element.getPlateThickness().toString());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadAllElements() {
        String sql = "SELECT * FROM elements";

        try {
            Statement stmt = this.conn.createStatement();
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

    public String[] loadAllOrders() {
        String sql = "SELECT * FROM orders";

        List<String> listOr = new Vector<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                listOr.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String[] orders = new String[listOr.size()];
        orders = listOr.toArray(orders);
        return orders;
    }

    public Order findOrder(String name) {
        String sql = "SELECT * FROM orders where name='" + name + "';";
        Order order = null;

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public Element findElement(Element element) {
        Double x_dimension = element.getDimensions().getX_dimension();
        Double y_dimension = element.getDimensions().getY_dimension();
        String shape = element.getPlateShape().toString();
        String materialType = element.getPlateMaterialType().toString();
        String thickness = element.getPlateThickness().toString();

        String sql = "SELECT * FROM elements where x_dimension=" + x_dimension
                + " and y_dimension=" + y_dimension
                + " and shape='" + shape
                + "' and material='" + materialType
                + "' and thickness='" + thickness + "'";

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            element.setId(rs.getInt("id"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return element;
    }

    public List<ElementWithQuantity> getElementsListFromOrder(Integer orderId){
        String sql = "SELECT * FROM orders_elements where order_id='" + orderId + "';";
        List<ElementWithQuantity> elementsList = new Vector<>();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()==true) {
                Integer el_id = rs.getInt("element_id");
                Integer q = rs.getInt("quantity");

                Statement stmt2 = this.conn.createStatement();
                ResultSet el = stmt2.executeQuery("SELECT * FROM elements WHERE id ='" + el_id + "';");
                elementsList.add(new ElementWithQuantity(new Element(PlateShape.valueOf(el.getString( "shape")), PlateMaterialType.valueOf(el.getString( "material"))   ,
                        PlateThickness.valueOf(el.getString( "thickness")), new Dimensions(el.getDouble( "x_dimension"), el.getDouble( "y_dimension"))   ), q));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return elementsList;
    }

    public void saveOrderToDatabase(String orderName) {
        Order foundOrder = findOrder(orderName);

        if (foundOrder == null || foundOrder.getId()==null) {
            String sql = "INSERT INTO orders(name) VALUES(?);";
            try {
                PreparedStatement pstmt = this.conn.prepareStatement(sql);
                pstmt.setString(1, orderName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void saveFullOrderToDatabase(List<ElementWithQuantity> elements, String orderName) {
        saveOrderToDatabase(orderName);
        Order order = findOrder(orderName);

        Integer orderId = order.getId();
        removeElementOrderConnections(orderId);
        elements.forEach(x -> {
                    Element element = x.getElement();
                    saveElementToDatabase(element);
                    Integer elementId = Objects.requireNonNull(findElement(element)).getId();
                    Integer quantity = x.getQuantity();
                    attachElementToOrder(orderId, elementId, quantity);
                }
        );


    }

    public void removeElementOrderConnections(Integer orderId) {
        String sql = "DELETE FROM orders_elements where order_id=" + orderId + ";";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void attachElementToOrder(Integer orderId, Integer elementId, Integer quantity) {
        String sql = "INSERT INTO orders_elements(order_id, element_id, quantity) VALUES(?,?,?);";
        try {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, elementId);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
