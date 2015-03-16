package controller;

import model.*;

import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Created by Clement on 13/03/2015.
 */
public class CommandContext extends ActionEvent {
    private Collection<IOAlgorithm> ioAlgorithms;
    private Collection<IDispoAlgorithm> dispoAlgorythms;
    private Collection<IColorAlgorithm> colorAlgorythms;
    private Collection<ISizeAlgorithm> sizeAlgorythms;
    private Graph currentGraph;

    public CommandContext(ActionEvent e) {
        super(e.getSource(), e.getID(), e.getActionCommand());
    }

    public Collection<IOAlgorithm> getIoAlgorithms() {
        return ioAlgorithms;
    }

    public Collection<IDispoAlgorithm> getDispoAlgorythms() {
        return dispoAlgorythms;
    }

    public void setDispoAlgorythms(Collection<IDispoAlgorithm> dispoAlgorythms) {
        this.dispoAlgorythms = dispoAlgorythms;
    }

    public Collection<IColorAlgorithm> getColorAlgorythms() {
        return colorAlgorythms;
    }

    public void setColorAlgorythms(Collection<IColorAlgorithm> colorAlgorythms) {
        this.colorAlgorythms = colorAlgorythms;
    }

    public Collection<ISizeAlgorithm> getSizeAlgorythms() {
        return sizeAlgorythms;
    }

    public void setSizeAlgorythms(Collection<ISizeAlgorithm> sizeAlgorythms) {
        this.sizeAlgorythms = sizeAlgorythms;
    }

    public void setIoAlgorithms(Collection<IOAlgorithm> ioAlgorithms) {
        this.ioAlgorithms = ioAlgorithms;
    }

    public Graph getCurrentGraph() {
        return currentGraph;
    }

    public void setCurrentGraph(Graph currentGraph) {
        this.currentGraph = currentGraph;
    }
}
