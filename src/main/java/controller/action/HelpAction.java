package controller.action;

import controller.MenuAction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;

public class HelpAction extends MenuAction {
    /**
     * Constructeur de Help Action
     */
    public HelpAction() {
        super(null, "Aide", null, KeyEvent.VK_H, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            URI uri = URI.create("http://info-timide.iut.u-bordeaux1.fr/perso/2015/loudeguillaume/documents/manuel_utilisateur.pdf");
            Desktop.getDesktop().browse(uri);
        } catch (IOException ex) {
            System.err.println("Erreur ouverture site web");
            ex.printStackTrace();
        }
        System.out.println("Aide ("+e.getSource().getClass().getName()+")");
    }
}
