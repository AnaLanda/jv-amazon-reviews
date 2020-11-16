package com.amazon.reviews.service.impl;

import com.amazon.reviews.exception.FileProcessingException;
import com.amazon.reviews.model.dto.ReviewDto;
import com.amazon.reviews.service.ParsingService;
import org.junit.Assert;
import org.junit.Test;

public class CsvParsingServiceImplTest {
    private static final String LINE = "1, B001E4KFG0, A3SGXH7AUHU8GW, delmartian, 1, 1, 5, "
            + "1303862400, Good Quality Dog Food, I have bought several of the Vitality "
            + "canned dog food products, and I have found them all to be of good quality. "
            + "The product looks more like a stew than a processed meat and it smells better. "
            + "My Labrador is finicky, and she appreciates this product better than most.";
    private static final String BAD_LINE = "1, B001E4KFG0";
    private final ParsingService<ReviewDto> service = new CsvToReviewDtoParsingServiceImpl();

    @Test
    public void parse_Ok() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(1L);
        reviewDto.setProductId("B001E4KFG0");
        reviewDto.setUserId("A3SGXH7AUHU8GW");
        reviewDto.setProfileName("delmartian");
        reviewDto.setHelpfulnessNumerator(1);
        reviewDto.setHelpfulnessDenominator(1);
        reviewDto.setScore(5);
        reviewDto.setTime("1303862400");
        reviewDto.setSummary("Good Quality Dog Food");
        reviewDto.setText("I have bought several of the Vitality canned dog food products, "
                + "and I have found them all to be of good quality. The product looks more like "
                + "a stew than a processed meat and it smells better. My Labrador is finicky, "
                + "and she appreciates this product better than most.");

        ReviewDto actualResult = service.parse(LINE);
        ReviewDto expectedResult = reviewDto;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseWrongFormat() {
        Assert.assertThrows(FileProcessingException.class, () -> {
            service.parse(BAD_LINE);
        });
    }
}
