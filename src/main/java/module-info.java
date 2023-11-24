module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.datatransfer;
    requires java.desktop;

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;

    opens stickhhero to javafx.fxml;

    exports stickhhero;
}
