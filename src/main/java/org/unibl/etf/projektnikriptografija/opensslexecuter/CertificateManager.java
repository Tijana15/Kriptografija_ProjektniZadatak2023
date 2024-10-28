package org.unibl.etf.projektnikriptografija.opensslexecuter;

import org.unibl.etf.projektnikriptografija.algorithms.AES;
import org.unibl.etf.projektnikriptografija.algorithms.RSA;
import org.unibl.etf.projektnikriptografija.user.User;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CertificateManager {
    public static boolean isCertificateExpired(User user) {
        AES.decryptCertificate(user);
        String command = "openssl x509 -in certs/" + user.getUsername() + "TempCert.crt -noout -checkend 0";
        String result = CommandPrompt.executeCommand(command);
        deleteTempTextCertificate(user);
        return "Certificate will expire".equals(result);
    }

    public static void generateUserCertificate(User user) {
        RSA.generateKey(user);
        String requestCommand = "openssl req -new -key CA/keys/" + user.getUsername() + ".key -passin pass:" + user.getPassword() +
                " -out CA/requests/" + user.getUsername() + "Req.csr -subj /C=BA/ST=RS/L=Mrkonjic/O=Jenica/OU=ETF/CN=" +
                user.getUsername();

        CommandPrompt.executeCommand(requestCommand);

        //  Zasto temp cert? ovaj sertifikat nije enkriptovan pa iz tog razloga se kreira privremeni koji se onda enkriptuje i sacuva kao crt
        String signCommand = "openssl ca -in CA/requests/" + user.getUsername() + "Req.csr -out CA/certs/" +
                user.getUsername() + "TempCert.crt -config C:/Users/PC/Desktop/KIRZ/ProjektniKriptografija/CA/openssl.cnf -subj /C=BA/ST=RS/L=Belgrade/O=Organisation/OU=Unit/CN="
                + user.getUsername() + "/emailAddress=" + user.getUsername() + "@mail.com -batch -passin pass:sigurnost";

        System.out.println(CommandPrompt.executeCommand(signCommand));

        AES.encryptCertificate(user);
        deleteUserRequest(user);
        deleteTempCertificate(user);
    }

    private static void deleteUserRequest(User user) {
        Path directory = Paths.get("CA/requests/");
        if (directory.toFile().isDirectory()) {
            File[] files = directory.toFile().listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().contains(user.getUsername())) {
                        file.delete();
                        break;
                    }
                }
            }
        }
    }

    private static void deleteTempCertificate(User user) {
        File file = new File("CA/certs/" + user.getUsername() + "TempCert.crt");
        if (file.exists()) {
            file.delete();
        }
    }

    private static void deleteTempTextCertificate(User user) {
        File file = new File("CA/certs/" + user.getUsername() + "TempCert.crt");
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean isCertificateRevoked(User user) {
        String certificatePath = "CA/certs/" + user.getUsername() + "Cert.crt";
        String crlPath = "CA/crl.pem";

        File certificateFile = new File(certificatePath);
        if (!certificateFile.exists()) {
            System.out.println("Certificate file does not exist.");
            return true; // ako sertifikat ne postoji, tretiramo ga kao povučen
        }

        String command = "openssl verify -crl_check -CAfile CA/rootca.pem -CRLfile " + crlPath + " " + certificatePath;
        String result = CommandPrompt.executeCommand(command);

        //ako rezultat sadrži "OK", sertifikat nije povučen
        return !result.contains("OK");
    }
}
