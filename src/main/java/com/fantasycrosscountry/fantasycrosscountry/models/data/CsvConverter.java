package com.fantasycrosscountry.fantasycrosscountry.models.data;

import com.fantasycrosscountry.fantasycrosscountry.models.Runner;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CsvConverter {

    @Autowired
    RunnerDao runnerDao;

    public HashMap<String, String> convertCsvToJava(String file) {
        String csvFileToRead = file;
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        HashMap<String, String> runnerList = new HashMap<>();

        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {

                // split on comma(',')
                String[] runners = line.split(splitBy);


                // add values from csv to car object
                runnerList.put(runners[0], runners[1]);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return runnerList;

    }



}
