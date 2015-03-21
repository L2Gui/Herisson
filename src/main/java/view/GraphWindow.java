package view;

import controller.Controller;
import controller.MenuAction;
import controller.actions.*;
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
import java.util.Map;


public class GraphWindow extends JFrame {
    private String title;
    private Dimension size;
    private JPanel contentPane;
    private TabbedGraph tabs;
    private GraphCanvas canvas;
    private Controller controller;

    private MenuAction newFile = new NewFileAction();
    private MenuAction openFile = new OpenFileAction();
    private MenuAction save = new SaveAction();
    private MenuAction saveAs = new SaveAsAction();
    private MenuAction newVertex = new NewVertexAction();
    private MenuAction newEdge = new NewEdgeAction();
    private MenuAction editVertex = new EditVertexAction();
    private MenuAction editEdge = new EditEdgeAction();
    private MenuAction copy = new CopyAction();
    private MenuAction cut = new CutAction();
    private MenuAction paste = new PasteAction();
    private MenuAction remove = new RemoveAction();
    private MenuAction undo = new UndoAction();
    private MenuAction redo = new RedoAction();
    private MenuAction zoomPlus = new ZoomPlusAction();
    private MenuAction zoomMoins = new ZoomLessAction();
    private MenuAction move = new MoveAction();
    private MenuAction changeBGColor = new ChangeBackgroundColorAction();

    /**
     *  @param title
     * @param size
     * @param canvas
     */
    public GraphWindow(String title, Dimension size, GraphCanvas canvas) {
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
        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.add(generateToolBar(), BorderLayout.NORTH);
        tabs=new TabbedGraph(canvas);
        this.contentPane.add(tabs, BorderLayout.CENTER);
    }

    public GraphCanvas getCanvas() {
        return this.canvas;
    }

    /**
     * @param dispoAlgorithms
     * @param colorAlgorithms
     * @param sizeAlgorithms
     */
    public void setAlgorithms(Map<String, IDispoAlgorithm> dispoAlgorithms,
                              Map<String, IColorAlgorithm> colorAlgorithms,
                              Map<String, ISizeAlgorithm> sizeAlgorithms) {
        super.setJMenuBar(generateMenuBar(dispoAlgorithms, colorAlgorithms, sizeAlgorithms));
    }

    public void setController(Controller controller) {
        this.controller = controller;
        this.newFile.setController(controller);
        this.openFile.setController(controller);
        this.save.setController(controller);
        this.saveAs.setController(controller);
        this.newVertex.setController(controller);
        this.newEdge.setController(controller);
        this.editVertex.setController(controller);
        this.editEdge.setController(controller);
        this.copy.setController(controller);
        this.cut.setController(controller);
        this.paste.setController(controller);
        this.remove.setController(controller);
        this.undo.setController(controller);
        this.redo.setController(controller);
        this.zoomPlus.setController(controller);
        this.zoomMoins.setController(controller);
        this.move.setController(controller);
        this.tabs.setController(controller);
        this.changeBGColor.setController(controller);
    }

    /**
     * Génère la barre de menu en incorporant les algos dans leurs menus respectifs
     * @param dispoAlgorithms Map des algos de disposition
     * @param colorAlgorithms Map des algos de couleur
     * @param sizeAlgorithms Map des algos de taille
     * @return
     */
    private JMenuBar generateMenuBar(Map<String, IDispoAlgorithm> dispoAlgorithms,
                                     Map<String, IColorAlgorithm> colorAlgorithms,
                                     Map<String, ISizeAlgorithm> sizeAlgorithms){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("Fichier");
        file.setMnemonic('F');
        file.add(this.newFile);
        file.add(this.openFile);
        file.add(this.save);
        file.add(this.saveAs);
        menu.add(file);

        JMenu edit = new JMenu("Edition");
        edit.setMnemonic('E');

        JMenu newElem = new JMenu("Nouveau");
        newElem.setMnemonic('N');
        newElem.add(this.newVertex);
        newElem.add(this.newEdge);
        JMenu editElem = new JMenu("Modifier");
        newElem.setMnemonic('M');
        editElem.add(this.editVertex);
        editElem.add(this.editEdge);

        edit.add(this.copy);
        edit.add(this.cut);
        edit.add(this.paste);
        edit.add(newElem);
        edit.add(this.remove);
        edit.add(editElem);
        edit.add(this.undo);
        edit.add(this.redo);

        menu.add(edit);

        JMenu display = new JMenu("Affichage");
        display.setMnemonic('A');

        JMenu disposition = new JMenu("Disposition");
        disposition.setMnemonic('D');
        ButtonGroup dispositionGroup = new ButtonGroup();
        // on essaie d'ajouter les éléments de la liste
        try{
            for (final Map.Entry<String, IDispoAlgorithm> algo : dispoAlgorithms.entrySet()) {
                JRadioButtonMenuItem dispositionRadioButton = new JRadioButtonMenuItem(algo.getKey());
                dispositionGroup.add(dispositionRadioButton);
                //TODO affecter une action au clic sur le bouton
                dispositionRadioButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Appliquer sur le graphe '"+ controller.getCurrentGraph().getName()+"' l'algo "+ algo.getKey());
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
            for (final Map.Entry<String, IColorAlgorithm> algo : colorAlgorithms.entrySet()) {
                JRadioButtonMenuItem colorRadioBtn = new JRadioButtonMenuItem(algo.getKey());
                colorGroup.add(colorRadioBtn);
                //TODO affecter une action au clic sur le bouton
                colorRadioBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Appliquer sur le graphe '"+controller.getCurrentGraph().getName()+"' l'algo "+algo.getKey());
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
            for (final Map.Entry<String, ISizeAlgorithm> algo : sizeAlgorithms.entrySet()) {
                JRadioButtonMenuItem sizeRadioBtn = new JRadioButtonMenuItem(algo.getKey());
                sizeGroup.add(sizeRadioBtn);
                //TODO affecter une action au clic sur le bouton
                sizeRadioBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Appliquer sur le graphe '" + controller.getCurrentGraph().getName() + "' l'algo " + algo.getKey());
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

        display.add(this.zoomPlus);
        display.add(this.zoomMoins);
        display.add(disposition);
        display.add(color);
        display.add(size);
        display.add(this.changeBGColor);

        menu.add(display);

        JMenu help = new JMenu("Aide");
        help.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent arg0) {
                new HelpAction().actionPerformed(new ActionEvent(arg0.getSource(), arg0.getID(), "help us !"));
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

        toolBar.add(this.newFile);
        //Aussi étonnant que ça puisse paraître, swing récupère de base l'attribut SMALL_ICON... Maaaaaaagic !
        //JButton button = (JButton)toolBar.getComponentAtIndex(0);
        //button.setIcon((ImageIcon)this.newFile.getValue(MenuAction.SMALL_ICON));
        //button.setFocusPainted(false);

        toolBar.add(this.save);

        toolBar.addSeparator();

        toolBar.add(this.undo);
        toolBar.add(this.redo);

        toolBar.addSeparator();

        toolBar.add(this.copy);
        toolBar.add(this.cut);
        toolBar.add(this.paste);

        toolBar.addSeparator();
        ButtonGroup modes = new ButtonGroup();
        JToggleButton toggleMove = new JToggleButton(this.move);
        toggleMove.setText("");
        toolBar.add(toggleMove);
        modes.add(toggleMove);

        JToggleButton toggleNewEdge = new JToggleButton(this.newEdge);
        toggleNewEdge.setText("");
        toolBar.add(toggleNewEdge);
        modes.add(toggleNewEdge);

        JToggleButton toggleNewVertex = new JToggleButton(this.newVertex);
        toggleNewVertex.setText("");
        toolBar.add(toggleNewVertex);
        modes.add(toggleNewVertex);
        toggleNewVertex.setSelected(true);

        JToggleButton toggleEditEdge = new JToggleButton(this.editEdge);
        toggleEditEdge.setText("");
        toolBar.add(toggleEditEdge);
        modes.add(toggleEditEdge);

        JToggleButton toggleEditVertex = new JToggleButton(this.editVertex);
        toggleEditVertex.setText("");
        toolBar.add(toggleEditVertex);
        modes.add(toggleEditVertex);

        JToggleButton toggleDeletion = new JToggleButton(this.remove);
        toggleDeletion.setText("");
        toolBar.add(toggleDeletion);
        modes.add(toggleDeletion);

        toolBar.addSeparator();

        toolBar.add(this.zoomPlus);
        toolBar.add(this.zoomMoins);

        toolBar.addSeparator();

        toolBar.add(this.changeBGColor);

        JButton button;
        JToggleButton tbutton;
        for(Component c : toolBar.getComponents()){
            try {
                button = (JButton) c;
                button.setFocusPainted(false);
            }catch (ClassCastException e) {
                //Ne rien faire, on évite ainsi les JToolBarSeparators qui sont incastables en JButton
            }
            try {
                tbutton = (JToggleButton) c;
                tbutton.setFocusPainted(false);
            }catch (ClassCastException e) {
                //Ne rien faire, on évite ainsi les JToolBarSeparators qui sont incastables en JButton
            }
        }
        return toolBar;
    }

    public void addGraph(String name){
        tabs.addGraphTab(name);
    }
}
