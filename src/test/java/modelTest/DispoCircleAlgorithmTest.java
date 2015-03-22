package modelTest;

import model.algorithms.DispoCircleAlgorithm;
import model.Graph;
import model.Vertex;
import org.javatuples.Pair;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DispoCircleAlgorithmTest {
    Graph g;
    /*      -5 -4 -3 -2 -1 0 1 2 3 4 5 *
          5                |   x
          4                |
          3     x          |
          2                x
          1                |
          0 ---------------x-------------
         -1                |
         -2        x       |
         -3                |       x
         -4                |
         -5                |
            -5 -4 -3 -2 -1 0 1 2 3 4 5 *
         */
    public DispoCircleAlgorithmTest(){
        g = new Graph();
        g.setName("graphe test");
        Vertex v0 = new Vertex(g);
        v0.setPosition(new Vector3f(0f, 2f, 0f));
        Vertex v1 = new Vertex(g);
        v1.setPosition(new Vector3f(4f,-3f,0f));
        Vertex v2 = new Vertex(g);
        v2.setPosition(new Vector3f(-4f,3f,0f));
        Vertex v3 = new Vertex(g);
        v3.setPosition(new Vector3f(0f,0f,0f));
        Vertex v4 = new Vertex(g);
        v4.setPosition(new Vector3f(2f,5f,0f));
        Vertex v5 = new Vertex(g);
        v5.setPosition(new Vector3f(-3f,-2f,5f));

        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
    }

	@Test
	public void CircleDistribMakesNoVerticesOutOfExpectedAreaWhenAllVerticesHaveTheSameSize() {

        float maxSize = g.getStyleManager().getDefaultVertexStyle().getSize();
        float radiusTh = (float)Math.sqrt(4*maxSize*maxSize);
        float wrongRadius = radiusTh/2f;

        Vector3f minTh = new Vector3f(-radiusTh,-radiusTh,0f);
        Vector3f maxTh = new Vector3f(radiusTh,radiusTh,0f);


        Vector3f wrongMinTh = new Vector3f(-wrongRadius,-wrongRadius,0f);
        Vector3f wrongMaxTh = new Vector3f(wrongRadius,wrongRadius,0f);



        DispoCircleAlgorithm algorithm = new DispoCircleAlgorithm();
        Set<Pair<Vertex, Vector3f>> real;
        for(int i=0; i<100; i++){
            real = algorithm.execute(g);
            for(Pair<Vertex, Vector3f> p : real){
                assertTrue("La valeur en x d'un sommet sort de la zone attendue", minTh.getX() <= p.getValue1().getX() && p.getValue1().getX() <= maxTh.getX());
                assertTrue("La valeur en y d'un sommet sort de la zone attendue", minTh.getY() <= p.getValue1().getY() && p.getValue1().getY() <= maxTh.getY());

                assertTrue("La valeur en z d'un sommet a été modifiée", p.getValue0().getPosition().getZ()==p.getValue1().getZ());

                assertFalse("La position (x,y) d'un sommet est dans une zone inatendue",
                        /* la valeur en X est dans la zone */
                        wrongMinTh.getX() <= p.getValue1().getX() && p.getValue1().getX() <= wrongMaxTh.getX() &&
                        /* et la valeur en y est aussi dans la zone */
                        wrongMinTh.getY() <= p.getValue1().getY() && p.getValue1().getY() <= wrongMaxTh.getY());
            }
        }
    }
    @Test
    public void CircleDistribGetsTheRightMaxVertexSize() {
        float maxSize = 10;

        Vertex v = new Vertex(g);
        //TODO faire diffréremment, il faudra utiliser une méthode de vertex quand il y en aura une
        v.getStyle().setSize(maxSize);
        v.setPosition(new Vector3f(2f,2f,0f));

        g.addVertex(v);

        DispoCircleAlgorithm algorithm = new DispoCircleAlgorithm();
        for(int i=0; i<100; i++){
            algorithm.execute(g);
            assertEquals(maxSize, algorithm.getMaxSize(), 0.01);
        }
    }
}
