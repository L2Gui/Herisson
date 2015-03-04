package main;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
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
    	
    		
    	//frame.setJMenuBar(generateMenuBar(null, null, null));
    	frame.add(generateToolBar(null, null, null));
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
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/file.png"));
	        file.setHorizontalTextPosition(SwingConstants.CENTER);
	        file.setVerticalTextPosition(SwingConstants.BOTTOM);
	        file.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
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
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/tools.png"));
	        edit.setHorizontalTextPosition(SwingConstants.CENTER);
	        edit.setVerticalTextPosition(SwingConstants.BOTTOM);
	        edit.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
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
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/screen.png"));
	        display.setHorizontalTextPosition(SwingConstants.CENTER);
	        display.setVerticalTextPosition(SwingConstants.BOTTOM);
	        display.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
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
					//Ignore
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
					//Ignore
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
					//Ignore
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
		
		
		
		
		
		JMenu help = new JMenu("Aide");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/site.png"));
	        help.setHorizontalTextPosition(SwingConstants.CENTER);
	        help.setVerticalTextPosition(SwingConstants.BOTTOM);
	        help.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		help.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					URI uri = URI.create("https://docs.google.com/document/d/1O6I38bpy3eshSb-ov9bVoirzjHJtZEO15fjJBlTmeho/edit?usp=sharing");
					Desktop.getDesktop().browse(uri);
				} catch (IOException e) {
					System.err.println("Erreur ouverture site web");
					e.printStackTrace();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//Empty
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				//Empty
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				//Empty
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Empty
			}
		});
		
		menu.add(help);
		return menu;
    }
    
    private JToolBar generateToolBar(Collection<IDispoAlgorythm> AlgoDispo,
    		Collection<IColorAlgorythm> AlgoColor,
    		Collection<ISizeAlgorythm> AlgoSize){
JToolBar shortcut = new JToolBar();
		
		JMenu copySC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/copy1.png"));
	        copySC.setHorizontalTextPosition(SwingConstants.CENTER);
	        copySC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        copySC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(copySC);
		
		JMenu cutSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/cut3.png"));
	        cutSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        cutSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        cutSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(cutSC);
		
		JMenu pasteSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/paste3.png"));
	        pasteSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        pasteSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        pasteSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(pasteSC);
		
		JMenu newElemSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/add204.png"));
	        newElemSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        newElemSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        newElemSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		JMenuItem newVertex = new JMenuItem("Noeud");
		JMenuItem newEdge = new JMenuItem("Trait");
		newElemSC.add(newVertex);
		newElemSC.add(newEdge);
		shortcut.add(newElemSC);
		
		JMenu deleteSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/cancel21.png"));
	        deleteSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        deleteSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        deleteSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(deleteSC);
		
		JMenu editElemSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/pencil43.png"));
	        editElemSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        editElemSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        editElemSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		JMenuItem editVertex = new JMenuItem("Noeud");
		JMenuItem editEdge = new JMenuItem("Trait");
		editElemSC.add(editVertex);
		editElemSC.add(editEdge);
		shortcut.add(editElemSC);
		
		JMenu undoSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/return13.png"));
	        undoSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        undoSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        undoSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(undoSC);
		
		JMenu redoSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/send.png"));
	        redoSC.setHorizontalTextPosition(SwingConstants.CENTER);
	        redoSC.setVerticalTextPosition(SwingConstants.BOTTOM);
	        redoSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(redoSC);
		
		JMenu zoomInSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/zoom.png"));
			zoomInSC.setHorizontalTextPosition(SwingConstants.CENTER);
			zoomInSC.setVerticalTextPosition(SwingConstants.BOTTOM);
			zoomInSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(zoomInSC);
		
		JMenu zoomOutSC = new JMenu("");
		try {
			BufferedImage image;
			image = ImageIO.read(new File("res/dezoom.png"));
			zoomOutSC.setHorizontalTextPosition(SwingConstants.CENTER);
			zoomOutSC.setVerticalTextPosition(SwingConstants.BOTTOM);
			zoomOutSC.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			//Ignore
		}
		shortcut.add(zoomOutSC);
		return shortcut;
    }
}