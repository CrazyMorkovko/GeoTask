import Tasks.MainWindow;

import javax.swing.*;

public class Main {

    /*
    * GeoTask
    * by OniBoov
    * */
    public static void main(String[] args) {
        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            // start application
            new MainWindow();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
