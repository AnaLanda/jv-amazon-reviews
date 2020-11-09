package com.amazon.reviews.service;

import com.amazon.reviews.model.dto.ReviewDto;
import java.util.List;

public interface FileParsingService {
    List<ReviewDto> parse(String path);
}
