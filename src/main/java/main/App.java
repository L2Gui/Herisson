package main;

import controller.*;
import model.*;
import org.lwjgl.util.vector.Vector3f;
import view.GraphCanvas;
import view.GraphWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String args[]) {
        try {
            // Model
            /* deux graphes bidon juste pour voir les onglets */
            Graph g1 = new Graph();
            g1.setName("swappez d'onglet pour");
            Graph g2 = new Graph();
            g2.setName("charger un graphe random");

            // View
            GraphCanvas canvas = new GraphCanvas();
            GraphWindow window = new GraphWindow("Hérisson", new Dimension(600, 600), canvas);

            // Controller
            Controller controller = new Controller();
            controller.addGraph(g1);
            controller.addGraph(g2);
            controller.setGraphWindow(window);

            window.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}