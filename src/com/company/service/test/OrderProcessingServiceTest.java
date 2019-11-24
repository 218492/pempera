package com.company.service.test;

import com.company.entity.Order;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;
import com.company.service.OrderProcessingService;
import com.company.service.impl.OrderProcessingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrderProcessingServiceTest {

    private OrderProcessingService orderProcessingService = new OrderProcessingServiceImpl();
    private File tempFile;

    @BeforeEach
    public void setup() throws IOException {
        tempFile = File.createTempFile("orderProcessingServiceTest", "txt");
    }

    @Test
    public void shouldReturnEmptyListWhenFileEmpty() throws IOException, DataManipulationException {
        List<Order> orders = orderProcessingService.loadOrdersFromFile(tempFile.getPath());

        Assertions.assertTrue(orders.isEmpty());
    }

    @Test
    public void shouldThrowAnExceptionWhenWrongData() throws IOException, DataManipulationException {
        writeLinesToFIle("1321ewsad");

        Assertions.assertThrows(WrongDataException.class, () ->
                orderProcessingService.loadOrdersFromFile(tempFile.getPath()));
    }

    @Test
    public void shouldThrowCorrectLineExceptionWhenWrongData() throws IOException, DataManipulationException {
        String wrongLineNumber = "2";
        writeLinesToFIle(
                "2.00,1.00,RECTANGLE,THICK,ALUMINIUM",
                "dasdasda,da,dasd,asd,ada,da,ddsad"
                );

        Exception thrown = Assertions.assertThrows(WrongDataException.class, () ->
                orderProcessingService.loadOrdersFromFile(tempFile.getPath()));
        Assertions.assertTrue(thrown.getMessage().contains(wrongLineNumber));
    }

    private void writeLinesToFIle(String... lines) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        for(int i=0; i<lines.length; i++){
            bw.write(lines[i]);
            bw.newLine();
        }
        bw.close();
    }
}
