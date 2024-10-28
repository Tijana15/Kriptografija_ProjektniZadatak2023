package org.unibl.etf.projektnikriptografija.opensslexecuter;

import org.unibl.etf.projektnikriptografija.user.User;
import org.unibl.etf.projektnikriptografija.util.FileUtil;

import java.io.File;

public class DigestCommand {
    //korisnicko ime i lozinka se hesiraju
    public static String hashCode(String input) {
        File tempFile = FileUtil.createTempFile(input);
        String command = "openssl dgst -sha256 " + tempFile;

        return CommandPrompt.executeCommand(command).split(" ")[1];
    }

    //potrebno prilikom prijave korisnika na aplikaciju
    public static int verifyHashCode(String inputHash, String outputHash) {
        File tempFile = FileUtil.createTempFile(outputHash);
        String command = "openssl dgst -sha256 " + tempFile;
        return CommandPrompt.executeCommand(command).split(" ")[1].equals(inputHash) ? 1 : 0;
    }

    //kreiranje digitalnog potpisa korisnika
    public static void sign(User user) {
        String keyPath = "CA/keys/" + user.getUsername() + ".key";
        String filePath = String.valueOf(FileUtil.findFile(user));
        String signaturePath = "CA/digitalSignatures/" + user.getUsername() + "Sign.sgn";

        // Proveravamo da li potpis već postoji
        File signatureFile = new File(signaturePath);
        if (!signatureFile.exists()) {
            // Ako potpis ne postoji, kreiramo ga
            String command = "openssl dgst -sha512 -sign " + keyPath +
                    " -passin pass:" + user.getPassword() +
                    " -out " + signaturePath + " " + filePath;

            CommandPrompt.executeCommand(command);
        }
    }

    //verifikacija digitalnog potpisa korisnika zarad pokusaja pristupanja njegovoj istoriji
    public static String verifySign(User user) {
        String command = "openssl dgst -sha512 -prverify keys/" + user.getUsername() + ".key -passin pass:" + user.getPassword() +
                " -signature digitalSignatures/" + user.getUsername() + "Sign.sgn " + FileUtil.findFile(user);

        return CommandPrompt.executeCommand(command);
    }

}
