package org.unibl.etf.projektnikriptografija.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.projektnikriptografija.Application;
import org.unibl.etf.projektnikriptografija.user.User;

public final class FileUtil {

    public static File createTempFile(String textContent) {
        File tempFile;
        try {
            tempFile = File.createTempFile("tempText", ".txt");
            Files.writeString(tempFile.toPath(), textContent, StandardOpenOption.CREATE);
        } catch (IOException exception) {
            Logger.getLogger(Application.class.getName()).log(Level.WARNING, exception.fillInStackTrace().toString());
            return null;
        }
        return tempFile;
    }

    public static void writeToFile(String content, String filePath, boolean append) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, append)))) {
            writer.println(content);
        } catch (IOException exception) {
            Logger.getLogger(Application.class.getName()).log(Level.WARNING, exception.fillInStackTrace().toString());
        }
    }

    public static File findFile(User user) {
        Path directoryPath = Paths.get("CA/clientFiles/");
        if (directoryPath.toFile().isDirectory()) {
            File[] files = directoryPath.toFile().listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().contains(user.getUsername())) {
                        return file;
                    }
                }
            }
        }
        return null;
    }
}
