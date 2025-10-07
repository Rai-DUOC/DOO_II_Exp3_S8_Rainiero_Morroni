package app;

import view.MainFrame;

/**
 *
 * @author Rai
 */
public class CinesMagenta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
