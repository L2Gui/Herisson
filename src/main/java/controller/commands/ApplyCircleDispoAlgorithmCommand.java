package controller.commands;

import controller.ICommand;
import model.DispoCircleAlgorithm;
import model.Graph;
import model.IDispoAlgorithm;

import java.awt.event.ActionEvent;

/**
 * Created by Clement on 16/03/2015.
 */
public class ApplyCircleDispoAlgorithmCommand implements ICommand {
    private IDispoAlgorithm algo;
    private Graph graph;

    public ApplyCircleDispoAlgorithmCommand() {
        this.algo = new DispoCircleAlgorithm();
    }

    @Override
    public void undo() {

    }

    @Override
    public void execute(Graph graph) {

    }
}
