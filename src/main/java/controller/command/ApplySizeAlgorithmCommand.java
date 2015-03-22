package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;
import model.algorithm.ISizeAlgorithm;
import org.javatuples.Pair;
import view.GraphView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Clement on 22/03/2015.
 */
public class ApplySizeAlgorithmCommand implements ICommand {
    private float minSize;
    private float maxSize;
    private GraphView graphView;
    private ISizeAlgorithm algorithm;
    private Map<Vertex, Float> oldSizes;

    public ApplySizeAlgorithmCommand(GraphView view, ISizeAlgorithm algorithm, float minSize, float maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.algorithm = algorithm;
        this.graphView = view;
    }

    @Override
    public void execute(Graph graph) {
        this.oldSizes = new HashMap<Vertex, Float>();
        Set<Pair<Vertex, Float>> newSizes = this.algorithm.execute(this.minSize, this.maxSize, graph);

        for (Pair<Vertex, Float> pair : newSizes) {
            this.oldSizes.put(pair.getValue0(), pair.getValue0().getSize());
            graphView.addResizingVertex(pair.getValue0(), pair.getValue1());
        }
    }

    @Override
    public void undo() {
        for (Map.Entry<Vertex, Float> entry : this.oldSizes.entrySet()) {
            graphView.addResizingVertex(entry.getKey(), entry.getValue());
        }
    }
}
