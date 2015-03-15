package main;

import controller.*;
import model.*;
import org.lwjgl.util.vector.Vector3f;
import view.GraphCanvas;
import view.GraphWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class App {
	private GraphWindow frame;
	private IOHandler ioHandler;
    private CommandContext context;
	private CommandHandler commandHandler;
	private KeyboardHandler keyboardHandler;
    private Collection<ICommand> commands;
	private Collection<IOAlgorithm> ioAlgorithms;
	private Collection<IDispoAlgorithm> dispoAlgorithms;
    private Collection<IColorAlgorithm> colorAlgorithms;
    private Collection<ISizeAlgorithm> sizeAlgorithms;
	private Collection<Graph> graphs;
    private GraphCanvas canvas;

    public static void main(String args[]) {
        App app = new App();
        try {
        	app.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void run() throws Exception {
        this.ioHandler = new IOHandler();
        this.context = new CommandContext();
        this.commandHandler = new CommandHandler(context);
        this.keyboardHandler = new KeyboardHandler();
        this.ioAlgorithms = new ArrayList<IOAlgorithm>();
        this.dispoAlgorithms = new ArrayList<IDispoAlgorithm>();
        this.colorAlgorithms = new ArrayList<IColorAlgorithm>();
        this.sizeAlgorithms = new ArrayList<ISizeAlgorithm>();
        this.graphs = new ArrayList<Graph>();

        /** Ajout des algorythmes **/
        this.dispoAlgorithms.add(new DispoRandomAlgorithm("Disposition aléatoire"));
        this.dispoAlgorithms.add(new DispoCircleAlgorithm("Disposition en cercle"));

        /** Fin ajout des algorythmes **/

        this.keyboardHandler.setCommandHandler(commandHandler);
        this.canvas = new GraphCanvas();

        frame = new GraphWindow("Hérisson", new Dimension(600, 600), this.canvas, dispoAlgorithms, colorAlgorithms, sizeAlgorithms);

        /* deux graphes bidon juste pour voir les onglets */
        frame.addGraph(new Graph());
        frame.addGraph(new Graph());
/*
        Graph g = new Graph();
        g.setName("graphe test");
        Vertex v0 = new Vertex(g);
        v0.setPosition(new Vector3f(0f, 2f, 0f));
        Vertex v1 = new Vertex(g);
        v1.setPosition(new Vector3f(4f,-3f,0f));
        Vertex v2 = new Vertex(g);
        v2.setPosition(new Vector3f(-4f,3f,0f));
        Vertex v3 = new Vertex(g);
        v3.setPosition(new Vector3f(0f,0f,0f));
        Vertex v4 = new Vertex(g);
        v4.setPosition(new Vector3f(2f,5f,0f));
        Vertex v5 = new Vertex(g);
        v5.setPosition(new Vector3f(-3f,-2f,5f));

        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        //frame.addGraph(g);*/

        this.frame.addKeyListener(this.keyboardHandler);
		this.frame.setVisible(true);
    }
}