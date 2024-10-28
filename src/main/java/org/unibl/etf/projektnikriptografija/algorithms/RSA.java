package org.unibl.etf.projektnikriptografija.algorithms;

import org.unibl.etf.projektnikriptografija.opensslexecuter.CommandPrompt;
import org.unibl.etf.projektnikriptografija.user.User;

public class RSA {
    public static void generateKey(User user) {
        String command = "openssl genrsa -out CA/keys/" + user.getUsername() + ".key -aes256 -passout pass:" + user.getPassword();
        System.out.println(CommandPrompt.executeCommand(command));
        extractPublicKey(user);
    }

    public static void extractPublicKey(User user) {
        String command = "openssl rsa -in CA/keys/" + user.getUsername() + ".key -pubout -out CA/keys/" + user.getUsername() + "Public.key -passin pass:" + user.getPassword();
        System.out.println(CommandPrompt.executeCommand(command));
    }
}
