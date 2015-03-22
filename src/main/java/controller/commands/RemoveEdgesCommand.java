package controller.commands;

import controller.ICommand;
import model.Edge;
import model.Graph;

import java.util.ArrayList;
import java.util.Collection;


public class RemoveEdgesCommand implements ICommand {
    private Collection<Edge> edges;
    private Graph graph;

    public RemoveEdgesCommand(Collection<Edge> edges) {
        this.edges = new ArrayList<Edge>(edges);
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        for(Edge edge : edges) {
            edge.setGraph(graph);
            // si l'arete est présente dans la liste d'arete de ses 2 noeuds, on la supprime des deux listes
            if (edge.getDstVertex().getEdges().contains(edge)
                    && edge.getSrcVertex().getEdges().contains(edge)) {
                edge.getDstVertex().getEdges().remove(edge);
                edge.getSrcVertex().getEdges().remove(edge);
            }// sinon, c'est que c'est un noeud qui demande ma suppression. Il va donc s'occuper de me supprimer partout où il faut.
        }
        this.graph.removeEdges(edges);
    }

    @Override
    public void undo() {
        for(Edge edge : this.edges) {
            this.graph.addEdge(edge);
            edge.getDstVertex().getEdges().add(edge);
            edge.getSrcVertex().getEdges().add(edge);
        }
    }
}
