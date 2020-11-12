package com.amazon.reviews.service.impl;

import com.amazon.reviews.exception.FileProcessingException;
import com.amazon.reviews.model.dto.ReviewDto;
import com.amazon.reviews.service.ParsingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CsvToReviewDtoParsingServiceImpl implements ParsingService<ReviewDto> {
    private static final int ID_INDEX = 0;
    private static final int PRODUCT_ID_INDEX = 1;
    private static final int USER_ID_INDEX = 2;
    private static final int PROFILE_NAME_INDEX = 3;
    private static final int HELPFULNESS_NUMERATOR_INDEX = 4;
    private static final int HELPFULNESS_DENOMINATOR_INDEX = 5;
    private static final int SCORE_INDEX = 6;
    private static final int TIME_INDEX = 7;
    private static final int SUMMARY_INDEX = 8;
    private static final int TEXT_INDEX = 9;
    private static final int LAST_CSV_COMMA = 9;
    private static final int PARSED_LINE_SIZE = 10;

    @Override
    public ReviewDto parse(String line) {
        String reviewDetails;
        String reviewText;
        int commaIndex;
        String[] parsedLine = new String[PARSED_LINE_SIZE];
        try {
            commaIndex = StringUtils.ordinalIndexOf(line, ",", LAST_CSV_COMMA);
            reviewDetails = line.substring(0, commaIndex);
            reviewText = line.substring(commaIndex + 1);
            String[] dataItems = reviewDetails.split(",");
            for (int j = 0; j < PARSED_LINE_SIZE - 1; j++) {
                parsedLine[j] = dataItems[j];
            }
            parsedLine[TEXT_INDEX] = reviewText;
        } catch (IndexOutOfBoundsException e) {
            throw new FileProcessingException("The line " + line
                    + "is in the wrong format to parse it to a ReviewDto", e);
        }
        return formReviewDto(parsedLine);
    }

    private ReviewDto formReviewDto(String[] parsedInput) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(Long.parseLong(parsedInput[ID_INDEX].trim()));
        reviewDto.setProductId(parsedInput[PRODUCT_ID_INDEX].trim());
        reviewDto.setUserId(parsedInput[USER_ID_INDEX].trim());
        reviewDto.setProfileName(parsedInput[PROFILE_NAME_INDEX].trim());
        reviewDto.setHelpfulnessNumerator(Integer.parseInt(
                parsedInput[HELPFULNESS_NUMERATOR_INDEX].trim()));
        reviewDto.setHelpfulnessDenominator(Integer.parseInt(
                parsedInput[HELPFULNESS_DENOMINATOR_INDEX].trim()));
        reviewDto.setScore(Integer.parseInt(parsedInput[SCORE_INDEX].trim()));
        reviewDto.setTime(parsedInput[TIME_INDEX].trim());
        reviewDto.setSummary(parsedInput[SUMMARY_INDEX].trim());
        reviewDto.setText(parsedInput[TEXT_INDEX].trim());
        return reviewDto;
    }
}
