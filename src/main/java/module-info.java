module org.unibl.etf.projektnikriptografija {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens org.unibl.etf.projektnikriptografija to javafx.fxml;
    exports org.unibl.etf.projektnikriptografija;
    exports org.unibl.etf.projektnikriptografija.controllers;
    opens org.unibl.etf.projektnikriptografija.controllers to javafx.fxml;
    exports org.unibl.etf.projektnikriptografija.opensslexecuter;
    opens org.unibl.etf.projektnikriptografija.opensslexecuter to javafx.fxml;
    exports org.unibl.etf.projektnikriptografija.user;
    opens org.unibl.etf.projektnikriptografija.user to javafx.fxml;
}