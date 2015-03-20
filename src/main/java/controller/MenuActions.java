package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class MenuActions {

    private MenuActions(){}
    /**
     * Nouveau Fichier
     */
    public static MenuAction newFile = new MenuAction("Nouveau Graphe", "res/file16.png", KeyEvent.VK_N, 0) {
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
    /**
     * Copier
     */
    public static MenuAction copy = new MenuAction("Copier", "res/copy.png", KeyEvent.VK_C, 0) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Copier ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Couper
     */
    public static MenuAction cut = new MenuAction("Couper", "res/cut.png", KeyEvent.VK_X, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Couper ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Coller
     */
    public static MenuAction paste = new MenuAction("Coller", "res/paste.png", KeyEvent.VK_V, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Coller ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Nouveau noeud
     */
    public static MenuAction newVertex = new MenuAction("Nouveau noeud", "res/node3.png", null, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("nouveau vertex ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Nouveau trait
     */
    public static MenuAction newEdge = new MenuAction("Nouveau trait", "res/edge3.png", null, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("nouvelle edge ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Supprimer
     */
    public static MenuAction remove = new MenuAction("Supprimer", "res/cancel.png", KeyEvent.VK_R, 4) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("remove ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Modifier noeud
     */
    public static MenuAction editVertex = new MenuAction("Modifier Noeud", "res/edit_node3.png", null, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("edit vertex ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Modifier trait
     */
    public static MenuAction editEdge = new MenuAction("Modifier Noeud", "res/edit_edge3.png", null, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("edit edge ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Annuler
     */
    public static MenuAction undo = new MenuAction("Annuler", "res/prev.png", KeyEvent.VK_Z, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("annuler ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Restaurer
     */
    public static MenuAction redo = new MenuAction("Restaurer", "res/next.png", KeyEvent.VK_Y, null) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("restaurer ("+e.getSource().getClass().getName()+")");
        }
    };

    /**
     * Zoom +
     */
    public static MenuAction zoomPlus = new MenuAction("Zoom +", "res/zoom.png", KeyEvent.VK_ADD, 5) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("zoom ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Zoom -
     */
    public static MenuAction zoomMoins = new MenuAction("Zoom -", "res/dezoom.png", KeyEvent.VK_SUBTRACT, 5) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Dezoom ("+e.getSource().getClass().getName()+")");
        }
    };
    /**
     * Mode déplacement
     */
    public static MenuAction move = new MenuAction("Mode déplacement", "res/move.png", KeyEvent.VK_D, 5) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Passage en mode déplacement ("+e.getSource().getClass().getName()+")");
        }
    };
}
