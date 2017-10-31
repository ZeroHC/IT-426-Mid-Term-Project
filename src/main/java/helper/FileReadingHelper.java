package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReadingHelper
{
    public static ArrayList<String> fileChecker()
    {
        ArrayList<String> dataReader = new ArrayList<>();

        //writes all input to a history file
        File hikeHistory = new File("data/hikeHistory.txt");
        try
        {
            hikeHistory.getParentFile().mkdir();
            hikeHistory.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            BufferedReader fileReader = new BufferedReader(new FileReader(hikeHistory));

            String lineReader;

            while ((lineReader = fileReader.readLine()) != null)
            {
                String temp = "";
                while (!lineReader.trim().equals(""))
                {
                    temp = temp + lineReader + "\n";
                    lineReader = fileReader.readLine();
                }

                dataReader.add(temp);
            }

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return dataReader;
    }
}
