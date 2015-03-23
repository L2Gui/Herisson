package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumericField extends JTextField{

    public NumericField(Double value){
        super.setText(value.toString());
    }

    public boolean isValid(){
        try{
            Double.parseDouble(super.getText());
            return true;
        }catch (NumberFormatException e){
            return false;
        }catch (NullPointerException e){
            return false;
        }
    }

    public NumericField(String value){

        super.setText(value);
    }

}
