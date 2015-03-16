package controller.commands;

import controller.ICommand;
import model.DispoCircleAlgorithm;
import model.Graph;
import model.IDispoAlgorithm;

import java.awt.event.ActionEvent;

/**
 * Created by Clement on 16/03/2015.
 */
public class ApplyCircleDispoAlgorithm implements ICommand {
    private IDispoAlgorithm algo;
    private Graph graph;

    public ApplyCircleDispoAlgorithm() {
        this.algo = new DispoCircleAlgorithm();
    }

    @Override
    public void undo() {

    }

    @Override
    public void execute(Graph graph) {

    }
}
