package com.company.service.test;

import com.company.entity.Order;
import com.company.exception.DataManipulationException;
import com.company.exception.WrongDataException;
import com.company.service.impl.OrderProcessingService;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class OrderProcessingServiceTest {

    private File tempFile;

    @Before
    public void setup() throws IOException {
        tempFile = File.createTempFile("orderProcessingServiceTest", "txt");
    }

    @Test
    public void shouldReturnEmptyListWhenFileEmpty() throws IOException, DataManipulationException {
        List<Order> orders = OrderProcessingService.loadOrdersFromFile(tempFile.getPath());

        assertTrue(orders.isEmpty());
    }

    @Test(expected= WrongDataException.class)
    public void shouldThrowAnExceptionWhenWrongData() throws IOException, DataManipulationException {
        writeLinesToFile("1321ewsad");

        OrderProcessingService.loadOrdersFromFile(tempFile.getPath());
    }

    @Test(expected = WrongDataException.class)
    public void shouldThrowAnExceptionWhenWrongLineNumber() throws IOException, DataManipulationException {
        String wrongLineNumber = "2";
        writeLinesToFile(
                "2.00,1.00,RECTANGLE,THICK,ALUMINIUM",
                "dasdasda,da,dasd,asd,ada,da,ddsad"
                );
        OrderProcessingService.loadOrdersFromFile(tempFile.getPath());
    }

    private void writeLinesToFile(String... lines) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        for(int i=0; i<lines.length; i++){
            bw.write(lines[i]);
            bw.newLine();
        }
        bw.close();
    }
}
