package controller.action;

import controller.Controller;
import controller.MenuAction;
import view.EdgeView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Kwetzakwak on 23/03/2015.
 */
public class EditEdgeNowAction extends MenuAction {

    public EditEdgeNowAction(Controller controller, EdgeView edgeView) {
        super(controller, "Modifier trait", "res/edit_edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
