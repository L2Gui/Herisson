package view;

import controller.MenuAction;
import controller.MenuActions;
import model.Graph;
import model.IColorAlgorithm;
import model.IDispoAlgorithm;
import model.ISizeAlgorithm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;


public class GraphWindow extends JFrame {
    private String title;
    private Dimension size;
    private JPanel contentPane;
    private TabbedGraph tabs;
    private GraphCanvas canvas;


    /**
     *  @param title
     * @param size
     * @param canvas
     * @param dispoAlgorithms
     * @param colorAlgorithms
     * @param sizeAlgorithms
     */
    public GraphWindow(String title, Dimension size, GraphCanvas canvas, Collection<IDispoAlgorithm> dispoAlgorithms, Collection<IColorAlgorithm> colorAlgorithms, Collection<ISizeAlgorithm> sizeAlgorithms) {
        super(title);
        this.title = title;
        this.size = size;
        this.canvas = canvas;
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
        /**
         * Hypothétique autre solution (je la laisse juste le temps que vous la voyez)
         */
        /********* GraphWindow *******************************
         * ------------Barre menu----------------------------*
         *                                                   *
         *                                                   *
         *   *******************ContentPane**************    *
         *   *                                          *    *
         *   *   *************************************  *    *
         *   *   *           toolbarre               *  *    *
         *   *   *************************************  *    *
         *   *                                          *    *
         *   *   *************JPANEL******************  *    *
         *   *   *                                   *  *    *
         *   *   *   ******************************  *  *    *
         *   *   *   *        onglets             *  *  *    *
         *   *   *   ******************************  *  *    *
         *   *   *                                   *  *    *
         *   *   *   ******************************  *  *    *
         *   *   *   *                            *  *  *    *
         *   *   *   *       CANVAS               *  *  *    *
         *   *   *   *                            *  *  *    *
         *   *   *   ******************************  *  *    *
         *   *   *                                   *  *    *
         *   *   *************************************  *    *
         *   *                                          *    *
         *   ********************************************    *
         *                                                   *
         * ***************************************************/
        super.setJMenuBar(generateMenuBar(dispoAlgorithms, colorAlgorithms, sizeAlgorithms));
        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.add(generateToolBar(), BorderLayout.NORTH);
        tabs=new TabbedGraph(canvas);
        this.contentPane.add(tabs, BorderLayout.CENTER);


    }

    /**
     * Génère la barre de menu en incorporant les algos dans leurs menus respectifs
     * @param AlgoDispo	Collection des algos de disposition
     * @param AlgoColor Collection des algos de couleur
     * @param AlgoSize Collection des algos de taille
     * @return
     */
    private JMenuBar generateMenuBar(Collection<IDispoAlgorithm> AlgoDispo,
                                     Collection<IColorAlgorithm> AlgoColor,
                                     Collection<ISizeAlgorithm> AlgoSize){
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
        newElem.setMnemonic('N');
        newElem.add(MenuActions.newVertex);
        newElem.add(MenuActions.newEdge);
        JMenu editElem = new JMenu("Modifier");
        newElem.setMnemonic('M');
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
        display.setMnemonic('A');

        JMenu disposition = new JMenu("Disposition");
        disposition.setMnemonic('D');
        ButtonGroup dispositionGroup = new ButtonGroup();
        // on essaie d'ajouter les éléments de la liste
        try{
            for (final IDispoAlgorithm algo : AlgoDispo) {
                JRadioButtonMenuItem dispositionRadioButton = new JRadioButtonMenuItem(algo.getName());
                dispositionGroup.add(dispositionRadioButton);
                //TODO affecter une action au clic sur le bouton
                dispositionRadioButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Appliquer sur le graphe '"+canvas.getGraph().getName()+"' l'algo "+algo.getName());
                    }
                });
                disposition.add(dispositionRadioButton);
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
        color.setMnemonic('C');
        ButtonGroup colorGroup = new ButtonGroup();
        // on essaie d'ajouter les éléments de la liste
        try{
            for (final IColorAlgorithm algo : AlgoColor) {
                JRadioButtonMenuItem colorRadioBtn = new JRadioButtonMenuItem(algo.getName());
                colorGroup.add(colorRadioBtn);
                //TODO affecter une action au clic sur le bouton
                colorRadioBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Appliquer sur le graphe '"+canvas.getGraph().getName()+"' l'algo "+algo.getName());
                    }
                });
                color.add(colorRadioBtn);
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
        size.setMnemonic('T');
        ButtonGroup sizeGroup = new ButtonGroup();
        // on essaie d'ajouter les éléments de la liste
        try{
            for (final ISizeAlgorithm algo : AlgoSize) {
                JRadioButtonMenuItem sizeRadioBtn = new JRadioButtonMenuItem(algo.getName());
                sizeGroup.add(sizeRadioBtn);
                //TODO affecter une action au clic sur le bouton
                sizeRadioBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Appliquer sur le graphe '" + canvas.getGraph().getName() + "' l'algo " + algo.getName());
                    }
                });
                size.add(sizeRadioBtn);
            }
        }catch(NullPointerException e){
            //Ignore
        }
        //Disposition personnalisée
        JRadioButtonMenuItem sizePerso = new JRadioButtonMenuItem("Personnalisée");
        sizeGroup.add(sizePerso);
        sizePerso.setSelected(true);
        size.add(sizePerso);

        display.add(MenuActions.zoomPlus);
        display.add(MenuActions.zoomMoins);
        display.add(disposition);
        display.add(color);
        display.add(size);

        menu.add(display);

        JMenu help = new JMenu("Aide");
        help.setMnemonic('h');
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

    /**
     * Génère une toolbarre
     * @return la toolbarre générée
     */
    private JToolBar generateToolBar(){
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
        toolBar.add(MenuActions.move);
        toolBar.add(MenuActions.newEdge);
        toolBar.add(MenuActions.newVertex);

        toolBar.add(MenuActions.editEdge);
        toolBar.add(MenuActions.editVertex);

        toolBar.add(MenuActions.remove);

        toolBar.addSeparator();

        toolBar.add(MenuActions.zoomPlus);
        toolBar.add(MenuActions.zoomMoins);

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
