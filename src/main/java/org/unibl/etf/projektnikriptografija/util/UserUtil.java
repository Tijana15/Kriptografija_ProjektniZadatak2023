package org.unibl.etf.projektnikriptografija.util;

import org.unibl.etf.projektnikriptografija.Application;
import org.unibl.etf.projektnikriptografija.opensslexecuter.DigestCommand;
import org.unibl.etf.projektnikriptografija.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class UserUtil {

    //registrovanje korisnika i stavljanje u datoteku prvo ime korisnika (zbog rezervisanosti imena, a onda para lozinka i ime radi prijave)
    public static void registerUser(User user) {
        String encodedUsernameAndPassword = DigestCommand.hashCode(user.getUsername() + "#" + user.getPassword());
        FileUtil.writeToFile(encodedUsernameAndPassword, "CA/registeredUsers.txt", true);
        FileUtil.writeToFile(user.getUsername(), "CA/registeredUsernames.txt", true);
    }

    public static boolean validateUserForSignIn(User user) {
        File file = new File("CA/registeredUsers.txt");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String encodedLine : lines) {
                if (DigestCommand.verifyHashCode(encodedLine, user.getUsername() + "#" + user.getPassword()) == 1)
                    return true;
            }
        } catch (IOException exception) {
            Logger.getLogger(Application.class.getName()).log(Level.WARNING, exception.fillInStackTrace().toString());
        }
        return false;
    }

    public static boolean checkUserHistoryFile(User user) {
        File file = new File("clientFiles/" + user.getUsername() + "History.txt");
        return !file.exists() || (file.exists() && "Verified OK".equals(DigestCommand.verifySign(user)));
    }

    //samo za provjeravanje da li je ime korisnika zauzeto
    public static boolean checkIfUsernameExists(String username) {
        try {
            File file = new File("CA/registeredUsernames.txt");
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.isEmpty()) {
                return true;
            }

            for (String line : lines) {
                if (line.equals(username))
                    return false;
            }
        } catch (IOException exception) {
            Logger.getLogger(Application.class.getName()).log(Level.WARNING, exception.fillInStackTrace().toString());
        }

        return true;
    }
}
