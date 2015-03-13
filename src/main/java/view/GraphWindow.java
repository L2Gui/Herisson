package view;

import main.MenuActions;
import model.Graph;
import model.IColorAlgorythm;
import model.IDispoAlgorythm;
import model.ISizeAlgorythm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphWindow extends JFrame {
    private String title;
    private Dimension size;
    private JPanel contentPane;
    private TabbedGraph tabs;

    public GraphWindow(String title, Dimension size, GraphCanvas canvas) {
        super(title);
        this.title = title;
        this.size = size;
        this.contentPane = new JPanel();

        super.setSize(size);
        super.setContentPane(this.contentPane);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setLocationByPlatform(true);

        try {
            super.setIconImage(ImageIO.read(new File("res/logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.setJMenuBar(generateMenuBar(null, null, null));
        super.add(generateToolBar(null, null, null));
        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.add(generateToolBar(null, null, null), BorderLayout.NORTH);
        tabs = new TabbedGraph(canvas);
        this.contentPane.add(tabs);


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
        help.addMouseListener(new MouseAdapter() {

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
        //Aussi étonnant que ça puisse paraître, swing récupère de base l'attribut SMALL_ICON... Maaaaaaagic !
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
            }catch (ClassCastException e) {
                //Ne rien faire, on évite ainsi les JToolBarSeparators qui sont incastables en JButton
            }
        }
        return toolBar;
    }

    public void addGraph(Graph g){
        tabs.addGraphTab(g);
    }
}
