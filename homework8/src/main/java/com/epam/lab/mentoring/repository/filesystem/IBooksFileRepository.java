package com.epam.lab.mentoring.repository.filesystem;

import java.util.List;

public interface IBooksFileRepository {

    List<String> listFiles();

    void writeFile(String filename, byte[] bytes);
}
