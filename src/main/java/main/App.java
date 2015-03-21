package main;

import controller.*;
import model.*;
import org.javatuples.Pair;
import org.lwjgl.util.vector.Vector3f;
import view.GraphCanvas;
import view.GraphView;
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
            Graph graph1 = createSampleGraph("graphe1");
            Graph graph2 = createSampleGraph("graphe2");

            // View
            GraphCanvas canvas = new GraphCanvas();
            GraphWindow window = new GraphWindow("Hérisson", new Dimension(600, 600), canvas);

            // Controller
            Controller controller = new Controller();

            // Construction des liens
            controller.setGraphWindow(window);
            controller.addGraph(graph1);
            controller.addGraph(graph2);

            window.setVisible(true);

            /*while (window.isShowing()) {
                canvas.animationLoop();
                Thread.sleep(16L);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Graph createSampleGraph(String name) {
        Graph g = new Graph();
        g.setName(name);
        Vertex v0 = new Vertex();
        v0.setPosition(new Vector3f(5f, 5f, 0f));
        v0.setLabel("Coucou");
        Vertex v1 = new Vertex();
        v1.setPosition(new Vector3f(4f,-3f,0f));
        v1.setLabel("Tranquille ?");
        Vertex v2 = new Vertex();
        v2.setPosition(new Vector3f(-4f, 3f, 0f));
        Vertex v3 = new Vertex();
        v3.setPosition(new Vector3f(0f,0f,0f));
        Vertex v4 = new Vertex();
        v4.setPosition(new Vector3f(2f,5f,0f));
        Edge edge = new Edge();
        edge.setSrcVertex(v0);
        edge.setDstVertex(v1);
        v0.addEdge(edge);
        v1.addEdge(edge);

        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(edge);

        for (int i = 0; i < 5; i++) {
            Vertex v = new Vertex();
            v.setPosition(new Vector3f(0f,0f,0f));
            v.setLabel("Noeud "+(i+4));
            g.addVertex(v);
        }

        DispoRandomAlgorithm algorithm = new DispoRandomAlgorithm();

        for (Pair<Vertex, Vector3f> p : algorithm.execute(g)){
            p.getValue0().setPosition(p.getValue1());
        }

        return g;
    }
}