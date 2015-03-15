package model;

import org.javatuples.Pair;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashSet;
import java.util.Set;

public class DispoRandomAlgorithm implements IDispoAlgorithm {
    private String name;

    public DispoRandomAlgorithm(String name){
        this.name=name;
    }
    /**
     * Dispose les sommets de manière aléatoire (en prenant en compte la taille du graphe actuel)
     *
     * @param g graphe
     * @return un Set< Pair<Vertex, Vector3f> > avec les nouvelles coordonnées par sommet
     */
    @Override
    public Set<Pair<Vertex, Vector3f>> execute(Graph g) {
        Set set = new HashSet();

        //on récupère le min et le max
        Vector3f min=null;
        Vector3f max=null;
        Vector3f current;
        for( Vertex v : g.getVertices()){
            current=v.getPosition();

            if(min==null){
                min=new Vector3f(v.getPosition());
                max=new Vector3f(v.getPosition());
            }else{
                min.x=Math.min(min.x, current.x);
                min.y=Math.min(min.y, current.y);
                min.z=Math.min(min.z, current.z);


                max.x=Math.max(max.x, current.x);
                max.y=Math.max(max.y, current.y);
                max.z=Math.max(max.z, current.z);
            }
        }
        //attribution des nouvelles positions
        Vector3f newPos;
        for( Vertex v : g.getVertices()){
            newPos=new Vector3f((float)(Math.random()*max.x)+min.x,
                                (float)(Math.random()*max.y)+min.y,
                                (float)(Math.random()*max.z)+min.z);

            set.add(new Pair<Vertex, Vector3f>(v, newPos));
        }

        return set;
    }

    /**
     * @return Le nom d'affichage de l'algo
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Affecte à l'algo un nouveau nom d'affichage
     *
     * @param newName Le nouveau nom de l'algo
     */
    @Override
    public void setName(String newName) {
        this.name=newName;
    }
}
