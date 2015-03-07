package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class MenuTest {

    public static void main(String[] argv) throws Exception {
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Un menu
        JMenu menu = new JMenu("Fichier");
        BufferedImage image = ImageIO.read(new File("res/file.png"));
        menu.setHorizontalTextPosition(SwingConstants.CENTER);
        menu.setVerticalTextPosition(SwingConstants.BOTTOM);
        menu.setIcon(new ImageIcon(image));
        menuBar.add(menu);
        // un autre
        JMenu menuu = new JMenu("Editer");
        BufferedImage imagee = ImageIO.read(new File("res/pencil.png"));
        menuu.setHorizontalTextPosition(SwingConstants.CENTER);
        menuu.setVerticalTextPosition(SwingConstants.BOTTOM);
        menuu.setIcon(new ImageIcon(imagee));
        menuBar.add(menuu);
        
        // un item
        JMenuItem item = new JMenuItem("Test Item");

        menu.add(item);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(menuBar);
        frame.setSize(500, 550);
        frame.setVisible(true);
        System.out.println("ok");
    }
}