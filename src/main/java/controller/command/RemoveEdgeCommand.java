package controller.command;

import controller.ICommand;
import model.Edge;
import model.Graph;

/**
 * Created by Corentin on 21/03/2015.
 */
public class RemoveEdgeCommand implements ICommand {
    private Edge edge;
    private Graph graph;

    public RemoveEdgeCommand(Edge edge) {
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.edge.setGraph(graph);
        // si l'arete est présente dans la liste d'arete de ses 2 noeuds, on la supprime des deux listes
        if(this.edge.getDstVertex().getEdges().contains(edge)
                && this.edge.getSrcVertex().getEdges().contains(edge)){
            this.edge.getDstVertex().getEdges().remove(this.edge);
            this.edge.getSrcVertex().getEdges().remove(this.edge);
        }// sinon, c'est que c'est un noeud qui demande ma suppression. Il va donc s'occuper de me supprimer partout où il faut.
        this.graph.removeEdge(this.edge);
    }

    @Override
    public void undo() {
        this.graph.addEdge(this.edge);
        this.edge.getDstVertex().getEdges().add(this.edge);
        this.edge.getSrcVertex().getEdges().add(this.edge);
    }
}
