package main;

import controller.*;
import model.*;
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

        /** Fin ajout des algorythmes **/

        this.keyboardHandler.setCommandHandler(commandHandler);
        this.canvas = new GraphCanvas();

        frame = new GraphWindow("Hérisson", new Dimension(600, 600), this.canvas, dispoAlgorithms, colorAlgorithms, sizeAlgorithms);

        /* deux graphes bidon juste pour voir les onglets */
        frame.addGraph(new Graph());
        frame.addGraph(new Graph());

        this.frame.addKeyListener(this.keyboardHandler);
		this.frame.setVisible(true);
    }
}