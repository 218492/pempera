package com.company.service;

import com.company.entity.Order;

import java.io.IOException;
import java.util.List;

public interface OrderProcessingService {
    public List<Order> loadOrdersFromFile(String filePath) throws IOException;
    public void saveOrdersToFIle(String filePath);
}
