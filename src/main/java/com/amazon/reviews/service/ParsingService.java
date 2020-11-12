package com.amazon.reviews.service;

public interface ParsingService<T> {
    T parse(String line);
}
