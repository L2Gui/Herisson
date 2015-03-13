package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphWindow extends JFrame {
    private String title;
    private Dimension size;
    private JPanel contentPane;

    public GraphWindow(String title, Dimension size) {
        super(title);
        this.title = title;
        this.size = size;
        this.contentPane = new JPanel();

        super.setSize(size);
        super.setContentPane(this.contentPane);
    }
}
