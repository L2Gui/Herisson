package model;

/**
 * Created by Clement on 21/03/2015.
 */
public class GraphUpdate {
    public static enum UpdateType {
        ADD_VERTEX,
        REMOVE_VERTEX,
        ADD_EDGE,
        REMOVE_EDGE
    }

    private Object subject;
    private UpdateType type;

    public GraphUpdate(UpdateType type, Object subject) {
        this.type = type;
        this.subject = subject;
    }

    public UpdateType getType() {
        return this.type;
    }

    public Object getSubject() {
        return this.subject;
    }
}
