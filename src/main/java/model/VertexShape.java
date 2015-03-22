package model;

public enum VertexShape {
	SQUARE("Carré"),
	CIRCLE("Cercle"),
	DIAMOND("Losange");

    private String name ="";

    VertexShape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
