package modelTest;

import model.algorithm.DispoRandomAlgorithm;
import model.Graph;
import model.Vertex;
import org.javatuples.Pair;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class DispoRandomAlgorithmTest {
    Graph g;
    /*  -5 -4 -3 -2 -1 0 1 2 3 4 5 *
          5                |   x
          4                |
          3     x          |
          2                x
          1                |
          0 ---------------x-------------
         -1                |
         -2                |
         -3                |       x
         -4                |
         -5                |
            -5 -4 -3 -2 -1 0 1 2 3 4 5 *
         */
    public DispoRandomAlgorithmTest(){
        g = new Graph();
        g.setName("graphe test");
        Vertex v0 = new Vertex();
        v0.setPosition(new Vector3f(0f, 2f, 0f));
        Vertex v1 = new Vertex();
        v1.setPosition(new Vector3f(4f,-3f,0f));
        Vertex v2 = new Vertex();
        v2.setPosition(new Vector3f(-4f,3f,0f));
        Vertex v3 = new Vertex();
        v3.setPosition(new Vector3f(0f,0f,0f));
        Vertex v4 = new Vertex();
        v4.setPosition(new Vector3f(2f,5f,0f));

        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
    }

	@Test
	public void RandomDistribMakesNoVerticesOutOfGraphSize() {

        Vector3f minTh = new Vector3f(-4f,-3f,0f);
        Vector3f maxTh = new Vector3f(4f,5f,0f);

        DispoRandomAlgorithm algorithm = new DispoRandomAlgorithm();
        Set<Pair<Vertex, Vector3f>> real;
        for(int i=0; i<100; i++){
            real = algorithm.execute(g);
            for(Pair<Vertex, Vector3f> p : real){
                assertTrue("La valeur en x d'un sommet sort de l'ancienne taille du graphe", minTh.getX() <= p.getValue1().getX() && p.getValue1().getX() <= maxTh.getX());
                assertTrue("La valeur en y d'un sommet sort de l'ancienne taille du graphe", minTh.getY() <= p.getValue1().getY() && p.getValue1().getY() <= maxTh.getY());
                assertTrue("La valeur en z d'un sommet sort de l'ancienne taille du graphe", minTh.getZ() <= p.getValue1().getZ() && p.getValue1().getZ() <= maxTh.getZ());
            }
        }
    }
    @Test
    public void RandomDistribChangeVerticesLocation() {

        DispoRandomAlgorithm algorithm = new DispoRandomAlgorithm();
        Set<Pair<Vertex, Vector3f>> real;
        boolean posChanged = false;
        for(int i=0; i<100 && !posChanged; i++){
            real = algorithm.execute(g);
            for(Pair<Vertex, Vector3f> p : real){
                posChanged = posChanged || (p.getValue0().getPosition().getX() != p.getValue1().getX());
                posChanged = posChanged || (p.getValue0().getPosition().getY() != p.getValue1().getY());
                posChanged = posChanged || (p.getValue0().getPosition().getZ() != p.getValue1().getZ());
            }
        }

        assertTrue("Les locations de tous les sommets avant et après l'algorythme sont identiques", posChanged);
    }
}
