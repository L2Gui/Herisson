package controller.command;

import controller.ICommand;
import model.Vertex;
import model.Graph;
import model.algorithm.IDispoAlgorithm;
import org.javatuples.Pair;
import org.lwjgl.util.vector.Vector3f;
import view.GraphView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Clement on 16/03/2015.
 */
public class ApplyDispoAlgorithmCommand implements ICommand {
    private IDispoAlgorithm algo;
    private GraphView graphView;
    private Map<Vertex, Vector3f> oldPositions;

    public ApplyDispoAlgorithmCommand(GraphView graphView, IDispoAlgorithm algo) {
        this.graphView = graphView;
        this.algo = algo;
    }

    @Override
    public void execute(Graph graph) {
        this.oldPositions = new HashMap<Vertex, Vector3f>();
        Set<Pair<Vertex, Vector3f>> newPositions = this.algo.execute(graph);

        for (Pair<Vertex, Vector3f> pair : newPositions) {
            this.oldPositions.put(pair.getValue0(), pair.getValue0().getPosition());
            graphView.addTranslatingVertex(pair.getValue0(), pair.getValue1());
        }
    }

    @Override
    public void undo() {
        for (Map.Entry<Vertex, Vector3f> entry : this.oldPositions.entrySet()) {
            graphView.addTranslatingVertex(entry.getKey(), entry.getValue());
        }
    }
}
