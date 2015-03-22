package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;
import model.algorithm.IColorAlgorithm;
import org.javatuples.Pair;
import view.GraphView;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Clement on 22/03/2015.
 */
public class ApplyColorAlgorithmCommand implements ICommand {
    private GraphView graphView;
    private IColorAlgorithm algorithm;
    private Color minColor;
    private Color maxColor;
    private Map<Vertex, Color> oldColors;

    public ApplyColorAlgorithmCommand(GraphView graphView, IColorAlgorithm algorithm, Color minColor, Color maxColor) {
        this.algorithm = algorithm;
        this.minColor = minColor;
        this.maxColor = maxColor;
        this.graphView = graphView;
    }

    @Override
    public void execute(Graph graph) {
        this.oldColors = new HashMap<Vertex, Color>();
        Set<Pair<Vertex, Color>> newColors = this.algorithm.execute(this.minColor, this.maxColor, graph);

        for (Pair<Vertex, Color> pair : newColors) {
            this.oldColors.put(pair.getValue0(), pair.getValue0().getBackgroundColor());
            graphView.addColoringVertex(pair.getValue0(), pair.getValue1());
        }
    }

    @Override
    public void undo() {
        for (Map.Entry<Vertex, Color> entry : this.oldColors.entrySet()) {
            graphView.addColoringVertex(entry.getKey(), entry.getValue());
        }
    }
}
