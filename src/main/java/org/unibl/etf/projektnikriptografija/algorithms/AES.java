package org.unibl.etf.projektnikriptografija.algorithms;

import org.unibl.etf.projektnikriptografija.opensslexecuter.CommandPrompt;
import org.unibl.etf.projektnikriptografija.user.User;
import org.unibl.etf.projektnikriptografija.util.FileUtil;

import java.io.File;

public class AES {
    //svaki korisnikov unos se serijalizuje i enkriptuje tako da mu samo korisnik unutar aplikacije moze pristupiti jer je kljuc korisnikova sifra
    public static void encryptAndSerialize(String text, User user) {
        File tempFile = FileUtil.createTempFile(text);
        assert tempFile != null;
        String command = "openssl enc -aes-256-cbc -pbkdf2 -in " + tempFile.getAbsoluteFile() + " -out CA/clientFiles/" + user.getUsername() +
                "History.txt -k " + user.getPassword();

        CommandPrompt.executeCommand(command);
    }

    //za prikaz istorije korisniku
    public static String deserializeAndDecrypt(User user) {
        File file = new File("CA/clientFiles/" + user.getUsername() + "History.txt");
        if (file.exists() && file.length() != 0) {
            String command = "openssl enc -d -aes-256-cbc -pbkdf2 -in CA/clientFiles/" + user.getUsername() + "History.txt -k " + user.getPassword();
            return CommandPrompt.executeCommand(command);

        } else {
            return "";
        }
    }

    //enkripcija sertifikata
    public static void encryptCertificate(User user) {
        String command = "openssl enc -aes-256-cbc -in CA/certs/" + user.getUsername() +
                "TempCert.crt -out CA/certs/" + user.getUsername() + "Cert.crt -k " + user.getPassword();

        CommandPrompt.executeCommand(command);
    }

    //dekripcija neophodna kod provjere dana isteka
    public static String decryptCertificate(User user) {
        String command = "openssl enc -d -aes-256-cbc -in CA/certs/" + user.getUsername() +
                "Cert.crt -out CA/certs/" + user.getUsername() + "TempCert.crt -k " + user.getPassword();

        return CommandPrompt.executeCommand(command);
    }
}
