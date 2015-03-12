package view;

import main.App;
import model.Vertex;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.drawable.GLDrawableObject;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VertexView extends ViewElement {

    private App app;
	private Vertex model;
    private Vector3f position;
    //private Vector3f translation; //C
    //private int z_index = 0; //C
    //private Collection<IGLDrawable> drawables = new ArrayList<IGLDrawable>(); //C
    private GLColoredMesh mesh; //C
    private GLDrawableObject drawableObject; //C

    public VertexView(Vertex model, App app)
    {
        this.app = app;
        this.model = model;
        this.position = model.getPosition();
    }

	public void paint()
	{
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
        app.getCanvas().addDrawable(0, this.drawableObject);

        System.out.println("paint appelée");
	}
}
