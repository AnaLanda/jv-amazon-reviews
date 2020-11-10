package com.amazon.reviews.service.impl;

import com.amazon.reviews.model.dto.ReviewDto;
import com.amazon.reviews.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileParsingServiceImplTest {
    private final FileReaderService reader = new FileReaderServiceImpl();
    private final CsvFileParsingServiceImpl service = new CsvFileParsingServiceImpl(reader);
    private final String[] parsedReview1 = {"1", "B001E4KFG0",
            "A3SGXH7AUHU8GW", "delmartian", "1", "1", "5", "1303862400",
            "Good Quality Dog Food", "I have bought several of the Vitality "
            + "canned dog food products, and I have found them all to be of good quality."};
    private static final String TEST_FILE = "src/test/resources/test_reviews.csv";

    @Test
    public void parse_Ok() {
        ReviewDto reviewDto1 = new ReviewDto();
        reviewDto1.setId(1L);
        reviewDto1.setProductId("B001E4KFG0");
        reviewDto1.setUserId("A3SGXH7AUHU8GW");
        reviewDto1.setProfileName("delmartian");
        reviewDto1.setHelpfulnessNumerator(1);
        reviewDto1.setHelpfulnessDenominator(1);
        reviewDto1.setScore(5);
        reviewDto1.setTime("1303862400");
        reviewDto1.setSummary("Good Quality Dog Food");
        reviewDto1.setText("I have bought several of the Vitality canned dog food products, "
                + "and I have found them all to be of good quality. The product looks more like a stew "
                + "than a processed meat and it smells better. My Labrador is finicky, "
                + "and she appreciates this product better than most.");

        ReviewDto reviewDto2 = new ReviewDto();
        reviewDto2.setId(2L);
        reviewDto2.setProductId("B00813GRG4");
        reviewDto2.setUserId("A1D87F6ZCVE5NK");
        reviewDto2.setProfileName("dll pa");
        reviewDto2.setHelpfulnessNumerator(0);
        reviewDto2.setHelpfulnessDenominator(0);
        reviewDto2.setScore(1);
        reviewDto2.setTime("1346976000");
        reviewDto2.setSummary("Not as Advertised");
        reviewDto2.setText("\"Product arrived labeled as Jumbo Salted Peanuts..."
                + "the peanuts were actually small sized unsalted. Not sure if this "
                + "was an error or if the vendor intended to represent the product as \"\"Jumbo\"\".\"");

        List<ReviewDto> actualResult = service.parse(TEST_FILE);
        List<ReviewDto> expectedResult = List.of(reviewDto1, reviewDto2);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
