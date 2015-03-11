package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Graph;
import model.IColorAlgorythm;
import model.IDispoAlgorythm;
import model.ISizeAlgorythm;
import controller.CommandHandler;
import controller.IOAlgorithm;
import controller.IOHandler;
import controller.KeyboardHandler;
import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.camera.IGLCamera;
import opengl.resource.object.drawable.GLDrawableObject;
import opengl.resource.object.drawable.IGLDrawable;;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.resource.object.mesh.GLTextMesh;
import opengl.resource.texture.GLTexture;
import opengl.utils.GLRay;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import utils.MathUtils;
import view.IVisuAlgorythm;




public class App {
	private JFrame frame;
	private IOHandler ioHandler;
	private CommandHandler commandHandler;
	private KeyboardHandler keyboardHandler;
	private Collection<IOAlgorithm> ioAlgorithms;
	private Collection<IVisuAlgorythm> visuAlgorithms;
	private Collection<Graph> graphs;

    private IGLCamera camera; //C
    private GLCanvas canvas; //C
    private float distance; //C
    private Vector3f translation; //C
    private int z_index = 0; //C
    private GLTexture quadTexture; //C
    private Collection<IGLDrawable> drawables = new ArrayList<IGLDrawable>(); //C
    private GLColoredMesh mesh; //C
    private GLDrawableObject drawableObject; //C

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

        this.mesh = new GLColoredMesh();//C
        this.drawableObject = new GLDrawableObject();//C
        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);//C
		
        this.keyboardHandler.setCommandHandler(commandHandler);

        canvas.setCamera(camera); //C

        distance = 5.0f;
        translation = new Vector3f(0.0f, 0.0f, - distance);



        canvas.addMouseListener(new MouseListener(){ //C
            @Override
            public void mouseClicked(MouseEvent e) {
                //Empty
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    App.this.createObject(arg0.getX(), arg0.getY(), distance);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Empty
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Empty
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Empty
            }
        });

        this.frame.addKeyListener(this.keyboardHandler);
		this.frame.setVisible(true);
    }

    private void createObject(int x, int y, float distance) { //C

        GLShader colorShader = new GLShader("color3D.vert", "color.frag");
        GLShader textureShader = new GLShader("texture3D.vert", "texture.frag");

        List<GLColoredVertex> vertices = new ArrayList<GLColoredVertex>();

        GLColoredVertex v0 = new GLColoredVertex();
        GLColoredVertex v1 = new GLColoredVertex();
        GLColoredVertex v2 = new GLColoredVertex();
        GLColoredVertex v3 = new GLColoredVertex();

        v0.setPosition(-0.5f, -0.5f, 0.0f);
        v1.setPosition(0.5f, -0.5f, 0.0f);
        v2.setPosition(-0.5f, 0.5f, 0.0f);
        v3.setPosition(0.5f, 0.5f, 0.0f);

        v0.setColor(Color.red);
        v1.setColor(Color.blue);
        v2.setColor(Color.green);
        v3.setColor(Color.white);

        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        int[] indices = {
                0, 1, 3,
                0, 2, 3
        };

        this.mesh.setup(vertices, indices, GLObjectUsage.STATIC);

        this.drawableObject.setShader(colorShader);
        this.drawableObject.setMesh(this.mesh);

        this.canvas.addResource(colorShader);
        this.canvas.addResource(textureShader);
        this.canvas.addResource(this.mesh);
        this.canvas.addDrawable(0, this.drawableObject);
        this.canvas.setCamera(this.camera);

        Vector3f eye = new Vector3f(1.0f, 2.0f, 5.0f);

        Quaternion rotation = this.camera.getRotation();
        Quaternion look = MathUtils.quaternionFromAxisAngle(new Vector3f(0.0f, 1.0f, 0.0f), 0.05f);
        rotation = Quaternion.mul(rotation, look, null);

        this.camera.setPosition(eye);
        this.camera.setRotation(rotation);

    }

    private void createWindow() throws Exception {
    	frame = new JFrame("PT_Graphe_Hérisson");
    	frame.setSize(600, 600);
    	frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	frame.setLocationByPlatform(true);
        try {
            this.frame.setIconImage(ImageIO.read(new File("res/logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    		
    	frame.setJMenuBar(generateMenuBar(null, null, null));
    	frame.add(generateToolBar(null, null, null));
    	canvas = new GLCanvas();
    	frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(generateToolBar(null, null, null), BorderLayout.NORTH);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
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
        file.setMnemonic('F');
        file.add(MenuActions.newFile);
        file.add(MenuActions.openFile);
        file.add(MenuActions.save);
        file.add(MenuActions.saveAs);
		menu.add(file);
		
		JMenu edit = new JMenu("Edition");
        edit.setMnemonic('E');
			
			JMenu newElem = new JMenu("Nouveau");
                newElem.add(MenuActions.newVertex);
                newElem.add(MenuActions.newEdge);
			JMenu editElem = new JMenu("Modifier");
                editElem.add(MenuActions.editVertex);
                editElem.add(MenuActions.editEdge);
		
			edit.add(MenuActions.copy);
			edit.add(MenuActions.cut);
			edit.add(MenuActions.paste);
			edit.add(newElem);
			edit.add(MenuActions.remove);
			edit.add(editElem);
			edit.add(MenuActions.undo);
			edit.add(MenuActions.redo);
		
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
        JToolBar toolBar = new JToolBar();
        toolBar.setName("Raccourcis");

        toolBar.add(MenuActions.newFile);
        //Aussi étonnant que ça puisse parraître, swing récupère de base l'attribut SMALL_ICON... Maaaaaaagic !
        //JButton button = (JButton)toolBar.getComponentAtIndex(0);
        //button.setIcon((ImageIcon)MenuActions.newFile.getValue(MenuAction.SMALL_ICON));
        //button.setFocusPainted(false);

        toolBar.add(MenuActions.save);

        toolBar.addSeparator();

        toolBar.add(MenuActions.undo);
        toolBar.add(MenuActions.redo);

        toolBar.addSeparator();

        toolBar.add(MenuActions.copy);
        toolBar.add(MenuActions.cut);
        toolBar.add(MenuActions.paste);

        toolBar.addSeparator();

        toolBar.add(MenuActions.newEdge);
        toolBar.add(MenuActions.newVertex);

        toolBar.add(MenuActions.editEdge);
        toolBar.add(MenuActions.editVertex);

        toolBar.add(MenuActions.remove);

        toolBar.addSeparator();

        JButton button;
        for(Component c : toolBar.getComponents()){
            try {
                button = (JButton) c;
                button.setFocusPainted(false);
            }catch (ClassCastException e){
                //Ne rien faire, on évite ainsi les JToolBarSeparators qui sont incastables en JButton
            }
        }
		return toolBar;
    }
}