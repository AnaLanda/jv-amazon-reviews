package com.amazon.reviews.service.impl;

import com.amazon.reviews.exception.FileProcessingException;
import com.amazon.reviews.service.FileReaderService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public List<String> read(String path) {
        try {
            Stream<String> stream = Files.lines(Paths.get(path));
            return stream.collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new FileProcessingException("The file at the path "
                    + path + " cannot be found.", e);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to read the file at the path " + path, e);
        }
    }
}
