package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumericField extends JTextField{
    private Double value;
    private Color defaultColor;

    public NumericField(Double value){
        super.setText(value.toString());
        defaultColor = new Color(super.getBackground().getRGB());
        this.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                System.out.println(e.getKeyCode());
                NumericField.this.setBackground(defaultColor);
            }
        });
    }
    public boolean isValid(){
        try{
            Double.parseDouble(super.getText());
            return true;
        }catch (NumberFormatException e){
            super.setBackground(Color.red);
            return false;
        }catch (NullPointerException e){
            return false;
        }
    }
}
