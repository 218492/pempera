package com.company.service.impl;

import com.company.common.OrderAssembler;
import com.company.entity.Order;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;
import com.company.service.OrderProcessingService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessingServiceImpl implements OrderProcessingService {
    private static final String DELIMITER = ",";
    private DataInputStream inputStream = null;

    @Override
    public List<Order> loadOrdersFromFile(String filePath) throws IOException, DataManipulationException {
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

    @Override
    public void saveOrdersToFile(List<Order> orders, String filePath) {
        //orders.stream().forEach();
    }
}
