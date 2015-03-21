package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public abstract class MenuAction extends AbstractAction {

    private Controller controller;

    /**
     * Constructeur de MenuAction
     *
     * @param name  le nom de l'action (sera également la description courte)
     * @param icon  le chemin de l'icone à affecter (Peut être null)
     * @param mnemonicKey   La touche mnemonique a affecter (utiliser KeyEvent.***) (peut être null)
     * @param mnemonicIndex L'index du nom qui sera souligné pour indiquer le mnemonic (peut être null)
     */
    public MenuAction(Controller controller, String name, String icon, Integer mnemonicKey, Integer mnemonicIndex) {

        this.controller = controller;
        if(icon!=null) {
            ImageIcon smallIcon = new ImageIcon(icon);
            putValue(SMALL_ICON, smallIcon);
        }
        if(mnemonicKey!=null) {
            switch (mnemonicKey) {
                case KeyEvent.VK_ADD:
                    putValue(SHORT_DESCRIPTION, name+" (alt + '" + "+" + "')");
                    putValue(LONG_DESCRIPTION, name+" (alt + '" + "+" + "')");
                    putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, mnemonicIndex);
                    break;
                case KeyEvent.VK_SUBTRACT:
                    putValue(SHORT_DESCRIPTION, name+" (alt + '" + "-" + "')");
                    putValue(LONG_DESCRIPTION, name+" (alt + '" + "-" + "')");
                    putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, mnemonicIndex);
                    break;
                default:
                    putValue(SHORT_DESCRIPTION, name+" (alt+" + (char)mnemonicKey.byteValue() + ")");
                    putValue(LONG_DESCRIPTION, name+" (alt+" + (char)mnemonicKey.byteValue() + ")");
                    if(mnemonicIndex == null) {
                        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, mnemonicIndex);
                    }
                    break;
            }
        } else {
            putValue(LONG_DESCRIPTION, name);
            putValue(SHORT_DESCRIPTION, name);
        }

        putValue(MNEMONIC_KEY, mnemonicKey);   // Alt + valeur

        putValue(NAME, name);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);


}
