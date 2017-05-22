package com.epam.lab.mentoring.repository.filesystem;

import java.util.List;

public interface IBooksFileRepository {
    void writeFile(String filename, byte[] bytes);

    List<String> listFiles();

    void delete(Long aLong);
}
