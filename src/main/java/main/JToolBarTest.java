package main;

import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Pour tester les actions. Je pense qu'il faudra plutôt faire comme ça pour le menu
 * Ca permet (entre autre), de faire des vrai raccourcis, en plus de quoi, ça devrait être compatible de
 * façon très efficace avec les actions utilisées pour le undoredo ! :)
 * 
 * Les Mnemonics sont sympas aussi
 * 
 * En plus de quoi, c'est plus rapide (plus léger en lignes de code je veux dire), je pense.
 * @author Louis
 *
 */
public class JToolBarTest
{
    Search search;
  
    public JToolBarTest()
    {
        search = new Search();
    }
  
    private JToolBar getToolBar()
    {
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setName("Super barre !");
        toolBar.add(search);
        JButton button = (JButton)toolBar.getComponentAtIndex(0);
        button.setIcon((ImageIcon)search.getValue(search.LARGE_ICON));
        button.setFocusPainted(false);
        return toolBar;
    }
  
    private JMenuBar getMenuBar()
    {
        JMenu view = new JMenu("view");
        view.setMnemonic(KeyEvent.VK_V);	//alt+v
        view.add(search);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(view);
        return menuBar;
    }
  
    class Search extends AbstractAction
    {
        private static final String LARGE_ICON = "LargeIcon";
  
        public Search()
        {
            ImageIcon search16 = new ImageIcon("res/add204.png");	// pratique pour 2 images, nous on en a qu'une
            ImageIcon search24 = new ImageIcon("res/add204.png");
            //semble ne rien faire
            putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 0);
            putValue(NAME, "Search");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);   // Alt+S
            putValue(SHORT_DESCRIPTION, "Search");
            putValue(LONG_DESCRIPTION, "Locate a specific object");
            putValue(SMALL_ICON, search16);
            putValue(LARGE_ICON, search24);
            // le put value, je pense que c'est comme les bundle, on met absoluement n'importe quoi dedans,
            // ça c'est cool
        }
  
        public void actionPerformed(ActionEvent e)
        {
            String source = e.getSource().getClass().getName();
            System.out.println("source = " + source);
        }
    }
  
    public static void main(String[] args)
    {
    	JToolBarTest config = new JToolBarTest();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setJMenuBar(config.getMenuBar());
        f.getContentPane().add(config.getToolBar(), "South");
        f.setSize(200,140);
        f.setLocation(200,200);
        try {
			f.setIconImage(ImageIO.read(new File("res/help.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
        f.setName("Test Action & JToolBar");
        f.setVisible(true);
    }
}
