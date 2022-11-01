open module org.functions {
    requires java.base;
    requires transitive java.sql;
    requires javax.mail.api;
    requires itextpdf;
    requires transitive org.json;
    requires jasypt;
    requires activation;
    requires org.apache.commons.codec;
    requires log4j;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.swing;
    requires org.controlsfx.controls;
  

    exports com.functions;
    exports com.functions.models;
    exports com.functions.dao;
}
