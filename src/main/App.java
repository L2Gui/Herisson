package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.WindowConstants;

import model.Graph;
import model.IColorAlgorythm;
import model.IDispoAlgorythm;
import model.ISizeAlgorythm;
import controller.CommandHandler;
import controller.IOAlgorithm;
import controller.IOHandler;
import controller.KeyboardHandler;
import opengl.GLCanvas;
import view.IVisuAlgorythm;

public class App {
	private JFrame frame;
	private IOHandler ioHandler;
	private CommandHandler commandHandler;
	private KeyboardHandler keyboardHandler;
	private Collection<IOAlgorithm> ioAlgorithms;
	private Collection<IVisuAlgorythm> visuAlgorithms;
	private Collection<Graph> graphs;
	
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
        createWindow();
        this.ioHandler = new IOHandler();
        this.commandHandler = new CommandHandler();
        this.keyboardHandler = new KeyboardHandler();
        this.ioAlgorithms = new ArrayList<IOAlgorithm>();
        this.visuAlgorithms = new ArrayList<IVisuAlgorythm>();
        this.graphs = new ArrayList<Graph>();
		
        this.keyboardHandler.setCommandHandler(commandHandler);
        
        this.frame.addKeyListener(this.keyboardHandler);
		this.frame.setVisible(true);
    }
    
    private void createWindow() throws Exception {
    	frame = new JFrame("PT_Graphe_Hérisson");
    	frame.setSize(600, 600);
    	frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	frame.setLocationByPlatform(true);
    	
    		
    	frame.setJMenuBar(generateMenuBar(null, null, null));
    	
    	GLCanvas canvas = new GLCanvas();
		
		frame.getContentPane().add(canvas);
    }
    /**
     * Génère la barre de menu en incorporant les algos dans leurs menus respectifs
     * @param AlgoDispo	Collection des algos de disposition
     * @param AlgoColor Collection des algos de couleur
     * @param AlgoSize Collection des algos de taille
     * @return
     */
    private JMenuBar generateMenuBar(Collection<IDispoAlgorythm> AlgoDispo,
    		Collection<IColorAlgorythm> AlgoColor,
    		Collection<ISizeAlgorythm> AlgoSize){
    	JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("Fichier");
			JMenuItem newFile = new JMenuItem("Nouveau");
			JMenuItem open = new JMenuItem("Ouvrir");
			JMenuItem save = new JMenuItem("Enregistrer");
			JMenuItem saveAs = new JMenuItem("Enregistrer sous");
			
			file.add(newFile);
			file.add(open);
			file.add(save);
			file.add(saveAs);
		menu.add(file);
		
		JMenu edit = new JMenu("Edition");
			JMenuItem copy = new JMenuItem("Copier");
			JMenuItem cut = new JMenuItem("Couper");
			JMenuItem paste = new JMenuItem("Coller");
			
			JMenu newElem = new JMenu("Nouveau");
				JMenuItem newVertex = new JMenuItem("Noeud");
				JMenuItem newEdge = new JMenuItem("Trait");
			newElem.add(newVertex);
			newElem.add(newEdge);
			
			JMenuItem delete = new JMenuItem("Supprimer");
			
			JMenu editElem = new JMenu("Modifier");
				JMenuItem editVertex = new JMenuItem("Noeud");
				JMenuItem editEdge = new JMenuItem("Trait");
			editElem.add(editVertex);
			editElem.add(editEdge);
			
			JMenuItem undo = new JMenuItem("Annuler");
			JMenuItem redo = new JMenuItem("Restaurer");
		
			edit.add(copy);
			edit.add(cut);
			edit.add(paste);
			edit.add(newElem);
			edit.add(delete);
			edit.add(editElem);
			edit.add(undo);
			edit.add(redo);
		
		menu.add(edit);
		
		JMenu display = new JMenu("Affichage");
			JMenuItem zoomIn = new JMenuItem("Zoom +");
			JMenuItem zoomOut = new JMenuItem("Zoom -");
			
			JMenu disposition = new JMenu("Disposition");
				ButtonGroup dispositionGroup = new ButtonGroup();
				// on essaie d'ajouter les éléments de la liste 
				try{
					for (IDispoAlgorythm algo : AlgoDispo) {
						JRadioButtonMenuItem DispositionRadioBtn = new JRadioButtonMenuItem(algo.getName());
						dispositionGroup.add(DispositionRadioBtn);
						//TODO affecter une action au clic sur le bouton
						disposition.add(DispositionRadioBtn);
					}
				}catch(NullPointerException e){
					
				}
				//Disposition personnalisée
				JRadioButtonMenuItem dispositionPerso = new JRadioButtonMenuItem("Personnalisée");
				dispositionGroup.add(dispositionPerso);
				dispositionPerso.setSelected(true);
			disposition.add(dispositionPerso);
			
			JMenu color = new JMenu("Couleur des noeuds");
				ButtonGroup colorGroup = new ButtonGroup();
				// on essaie d'ajouter les éléments de la liste 
				try{
					for (IColorAlgorythm algo : AlgoColor) {
						JRadioButtonMenuItem ColorRadioBtn = new JRadioButtonMenuItem(algo.getName());
						colorGroup.add(ColorRadioBtn);
						//TODO affecter une action au clic sur le bouton
						color.add(ColorRadioBtn);
					}
				}catch(NullPointerException e){
					
				}
				//Disposition personnalisée
				JRadioButtonMenuItem colorPerso = new JRadioButtonMenuItem("Personnalisée");
				colorGroup.add(colorPerso);
				colorPerso.setSelected(true);
			color.add(colorPerso);
			
			JMenu size = new JMenu("Taille des noeuds");
				ButtonGroup sizeGroup = new ButtonGroup();
				// on essaie d'ajouter les éléments de la liste 
				try{
					for (ISizeAlgorythm algo : AlgoSize) {
						JRadioButtonMenuItem SizeRadioBtn = new JRadioButtonMenuItem(algo.getName());
						sizeGroup.add(SizeRadioBtn);
						//TODO affecter une action au clic sur le bouton
						size.add(SizeRadioBtn);
					}
				}catch(NullPointerException e){
					
				}
				//Disposition personnalisée
				JRadioButtonMenuItem sizePerso = new JRadioButtonMenuItem("Personnalisée");
				sizeGroup.add(sizePerso);
				sizePerso.setSelected(true);
			size.add(sizePerso);
			
			display.add(zoomIn);
			display.add(zoomOut);
			display.add(disposition);
			display.add(color);
			display.add(size);
			
		menu.add(display);
		return menu;
    }
}