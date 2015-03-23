package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;
import view.GraphView;
import view.VertexView;

/**
 * Created by Corentin on 23/03/2015.
 */
public class ChangeVertexThroughBoxCommand implements ICommand {

    private GraphView graphView;
    private Vertex oldVertex;
    private VertexView newVertexView;

    public ChangeVertexThroughBoxCommand(GraphView graphView, Vertex oldVertex, VertexView newVertexView) {
        this.graphView = graphView;
        this.oldVertex = oldVertex;
        this.newVertexView = newVertexView;
    }

    @Override
    public void execute(Graph graph) {

    }

    @Override
    public void undo() {
        newVertexView.setModel(oldVertex);
    }
}
