package com.amazon.reviews.impl;

import com.amazon.reviews.exception.FileProcessingException;
import com.amazon.reviews.service.FileReaderService;
import com.amazon.reviews.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String INVALID_PATH = "src/test/resources/invalid_path.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static final List<String> EMPTY_LIST = new ArrayList<>();
    private static final FileReaderService reader = new FileReaderServiceImpl();

    @Test
    public void read_Ok() {
        List<String> actualResult = reader.read(TEST_FILE);
        List<String> expectedResult = List.of("Id,ProductId,UserId",
                "1,B001E4KFG0,A3SGXH7AUHU8GW,delmartian,1,1,5,1303862400,Good Quality Dog Food");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void read_invalidPath() {
        Assert.assertThrows(FileProcessingException.class, () -> {
            reader.read(INVALID_PATH);
        });
    }

    @Test
    public void read_emptyFile() {
        List<String> actualResult = reader.read(EMPTY_FILE);
        Assert.assertEquals("Test failed! An empty list was expected.", EMPTY_LIST, actualResult);
    }
}
