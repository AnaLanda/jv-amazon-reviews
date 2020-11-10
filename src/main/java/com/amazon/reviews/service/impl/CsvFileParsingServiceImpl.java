package com.amazon.reviews.service.impl;

import com.amazon.reviews.model.dto.ReviewDto;
import com.amazon.reviews.service.FileParsingService;
import com.amazon.reviews.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class CsvFileParsingServiceImpl implements FileParsingService {
    private static final int ID_INDEX = 0;
    private static final int PRODUCT_ID_INDEX = 1;
    private static final int USER_ID_INDEX = 2;
    private static final int PROFILE_NAME_INDEX = 3;
    private static final int HELPFULNESS_NUMERATOR_INDEX = 4;
    private static final int HELPFULNESS_DENOMINATOR_INDEX = 5;
    private static final int SCORE_INDEX = 6;
    private static final int TIME_INDEX = 7;
    private static final int SUMMARY_INDEX = 8;
    private final static int TEXT_INDEX = 9;
    private final static int LAST_CSV_COMMA = 9;
    private final static int PARSED_LINE_SIZE = 10;
    private final FileReaderService fileReaderService;

    public CsvFileParsingServiceImpl(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public List<ReviewDto> parse(String path) {
        List<String> input = fileReaderService.read(path);
        List<String[]> parsedInput = parseData(input);
        List<ReviewDto> reviewDtos = formReviewDtos(parsedInput);
        return reviewDtos;
    }

    private List<String[]> parseData(List<String> input) {
        List<String[]> parsedLines = new ArrayList<>();
        String reviewDetails;
        String reviewText;
        int commaIndex;
        for (int i = 1; i < input.size(); i++) {
            String[] parsedLine = new String[PARSED_LINE_SIZE];
            String line = input.get(i);
            commaIndex = StringUtils.ordinalIndexOf(line, ",", LAST_CSV_COMMA);
            reviewDetails = line.substring(0, commaIndex);
            reviewText = line.substring(commaIndex + 1);
            String[] dataItems = reviewDetails.split(",");
            for (int j = 0; j < PARSED_LINE_SIZE - 1; j++) {
                parsedLine[j] = dataItems[j];
            }
            parsedLine[TEXT_INDEX] = reviewText;
            parsedLines.add(parsedLine);
        }
        return parsedLines;
    }

    private List<ReviewDto> formReviewDtos(List<String[]> parsedInput) {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (String[] data : parsedInput) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(Long.parseLong(data[ID_INDEX]));
            reviewDto.setProductId(data[PRODUCT_ID_INDEX]);
            reviewDto.setUserId(data[USER_ID_INDEX]);
            reviewDto.setProfileName(data[PROFILE_NAME_INDEX]);
            reviewDto.setHelpfulnessNumerator(Integer.parseInt(data[HELPFULNESS_NUMERATOR_INDEX]));
            reviewDto.setHelpfulnessDenominator(Integer.parseInt(data[HELPFULNESS_DENOMINATOR_INDEX]));
            reviewDto.setScore(Integer.parseInt(data[SCORE_INDEX]));
            reviewDto.setTime(data[TIME_INDEX]);
            reviewDto.setSummary(data[SUMMARY_INDEX]);
            reviewDto.setText(data[TEXT_INDEX]);
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }
}
