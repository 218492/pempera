package com.company.service;

import com.company.entity.Order;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;

import java.io.IOException;
import java.util.List;

public interface OrderProcessingService {
    public List<Order> loadOrdersFromFile(String filePath) throws IOException, DataManipulationException, WrongDataException;

    public void saveOrdersToFile(List<Order> orders, String filePath);
}
