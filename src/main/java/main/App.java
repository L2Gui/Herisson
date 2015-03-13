package main;

import controller.CommandHandler;
import controller.IOAlgorithm;
import controller.IOHandler;
import controller.KeyboardHandler;
import model.Graph;
import view.GraphCanvas;
import view.GraphWindow;
import view.IVisuAlgorythm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class App {
	private GraphWindow frame;
	private IOHandler ioHandler;
	private CommandHandler commandHandler;
	private KeyboardHandler keyboardHandler;
	private Collection<IOAlgorithm> ioAlgorithms;
	private Collection<IVisuAlgorythm> visuAlgorithms;
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
        this.commandHandler = new CommandHandler();
        this.keyboardHandler = new KeyboardHandler();
        this.ioAlgorithms = new ArrayList<IOAlgorithm>();
        this.visuAlgorithms = new ArrayList<IVisuAlgorythm>();
        this.graphs = new ArrayList<Graph>();

        this.keyboardHandler.setCommandHandler(commandHandler);
        this.canvas = new GraphCanvas();

        frame = new GraphWindow("Hérisson", new Dimension(600, 600));
        this.frame.getContentPane().add(this.canvas);
        this.frame.addKeyListener(this.keyboardHandler);
		this.frame.setVisible(true);
    }
}