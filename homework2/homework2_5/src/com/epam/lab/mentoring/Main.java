package com.epam.lab.mentoring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String FILENAME = "Task #5 - Data.txt";

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(line.substring(0, 3));
                }
            } finally {
                br.close();
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        Thread.sleep(20000);
    }

}
