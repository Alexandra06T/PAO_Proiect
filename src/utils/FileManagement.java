package utils;

import java.io.*;
public class FileManagement {
    public static void writeIntoFile(String fileName, String row) {

        try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(row);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
