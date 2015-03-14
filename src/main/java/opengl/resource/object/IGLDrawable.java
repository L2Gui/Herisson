package opengl.resource.object;

import opengl.utils.GLRay;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public interface IGLDrawable {
	void render(Matrix4f transformationMatrix);

    /**
     * Retourne les facteurs d'homothétie de l'objet
     * @return Vecteur 3D représentant les facteurs d'homothétie de l'objet
     */
    Vector3f getScale();

    /**
     * Modifie les facteurs d'homothétie de l'objet
     * @param scale Vecteur 3D représentant les facteurs d'homothétie
     */
    void setScale(Vector3f scale);

    /**
     * Modifie les facteurs d'homothétie de l'objet
     * @param x Facteur d'homothétie sur l'axe X
     * @param y Facteur d'homothétie sur l'axe Y
     * @param z Facteur d'homothétie sur l'axe Z
     */
    void setScale(float x, float y, float z);

    /**
     * Applique des facteurs d'homothétie à l'objet
     * @param scale Vecteur 3D représentant les facteurs d'homothétie
     */
    void scale(Vector3f scale);

    /**
     * Applique des facteurs d'homothétie à l'objet
     * @param x Facteur d'homothétie sur l'axe X
     * @param y Facteur d'homothétie sur l'axe Y
     * @param z Facteur d'homothétie sur l'axe Z
     */
    void scale(float x, float y, float z);

    boolean isIntersected(GLRay ray);
    float getDistance(GLRay ray);
}
