package org.unibl.etf.projektnikriptografija.opensslexecuter;

import org.unibl.etf.projektnikriptografija.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandPrompt {

    public static String executeCommand(String command) {
        StringBuilder text = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    text.append(line);
                }
            }
        } catch (IOException exception) {
            Logger.getLogger(Application.class.getName()).log(Level.WARNING, exception.fillInStackTrace().toString());
        }

        return text.toString();
    }
}