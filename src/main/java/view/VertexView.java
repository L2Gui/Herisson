package view;

import model.Vertex;
import org.lwjgl.util.vector.Matrix4f;

public class VertexView extends ViewElement {
	private Vertex model;

    public VertexView() {

    }

    public Vertex getModel() {
        return model;
    }

    public void setModel(Vertex model) {
        this.model = model;
    }

    @Override
	public void render(Matrix4f projectionViewMatrix)
	{
        super.render(projectionViewMatrix);
        /*
        GLShader colorShader = new GLShader("color3D.vert", "color.frag"); //vert pour vertex, pas pour la couleur vert

        List<GLColoredVertex> vertices = new ArrayList<GLColoredVertex>();

        GLColoredVertex v0 = new GLColoredVertex();
        GLColoredVertex v1 = new GLColoredVertex();
        GLColoredVertex v2 = new GLColoredVertex();
        GLColoredVertex v3 = new GLColoredVertex();

        v0.setPosition(-0.5f, -0.5f, 0.0f);
        v1.setPosition(0.5f, -0.5f, 0.0f);
        v2.setPosition(-0.5f, 0.5f, 0.0f);
        v3.setPosition(0.5f, 0.5f, 0.0f);

        v0.setColor(Color.red);
        v1.setColor(Color.red);
        v2.setColor(Color.red);
        v3.setColor(Color.red);

        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        int[] indices = {
                0, 1, 3,
                0, 2, 3
        };

        mesh.setup(vertices, indices, GLObjectUsage.STATIC);

        drawableObject.setShader(colorShader);
        drawableObject.setMesh(this.mesh);

        app.getCanvas().addResource(colorShader);
        app.getCanvas().addResource(this.mesh);
        this.drawableObject.render(projectionViewMatrix);

        System.out.println("paint appelée");*/
	}
}
