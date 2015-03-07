package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import main.MenuAction;

/**
 * Created by kwetzakwak on 07/03/15.
 */
public class MenuActions {
    /**
     * Nouveau Fichier
     */
    public static MenuAction newFile = new MenuAction("Nouveau fichier", "res/file16.png", KeyEvent.VK_N, 0) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Nouveau fichier ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Ouvrir Fichier
     */
    public static MenuAction openFile = new MenuAction("Ouvrir", null, KeyEvent.VK_O,0) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Ouvrir fichier ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Enregistrer
     */
    public static MenuAction save = new MenuAction("Enregistrer", "res/save.png", KeyEvent.VK_S,6) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Enregistrer fichier ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Enregistrer sous
     */
    public static MenuAction saveAs = new MenuAction("Enregistrer sous", null, null, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Enregistrer sous ... ("+e.getSource().getClass().getName()+")");
        }
    };


}
