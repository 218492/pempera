package com.company.service.impl;

import com.company.entity.Order;
import com.company.service.OrderProcessingService;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class OrderProcessingServiceImpl implements OrderProcessingService {
    DataInputStream inputStream = null;

    @Override
    public List<Order> loadOrdersFromFile(String filePath) throws IOException {
        try{
            inputStream = new DataInputStream(new FileInputStream(filePath));
        } finally{
            if (inputStream!=null){
                inputStream.close();
            }
        }

        return null;
    }

    @Override
    public void saveOrdersToFIle(String filePath) {

    }
}
