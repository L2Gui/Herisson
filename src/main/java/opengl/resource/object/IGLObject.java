package opengl.resource.object;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

/**
 * Interface qui permet de modifier un objet
 */
public interface IGLObject {
    /**
     * Retourne la position actuelle de l'objet
     * @return Vecteur 3D représentant la position de l'objet
     */
    Vector3f getPosition();

    /**
     * Retourne la rotation actuelle de l'objet
     * @return Quaternion représentant la rotation de l'objet
     */
	Quaternion getRotation();

    /**
     * Modifie la position de l'objet
     * @param position Vecteur 3D de la nouvelle position
     */
	void setPosition(Vector3f position);

    /**
     * Modifie la position de l'objet
     * @param x Valeur de la coordonnée sur l'axe X de la nouvelle position
     * @param y Valeur de la coordonnée sur l'axe Y de la nouvelle position
     * @param z Valeur de la coordonnée sur l'axe Z de la nouvelle position
     */
    void setPosition(float x, float y, float z);

    /**
     * Modifie la rotation de l'objet
     * @param angle Angle de rotation (en degré)
     * @param axis Axe de rotation
     */
	void setRotation(float angle, Vector3f axis);

    /**
     * Modifie la rotation de l'objet
     * @param angle Angle de rotation (en degré)
     * @param xAxis Valeur de la coordonnée sur l'axe X de l'axe de rotation
     * @param yAxis Valeur de la coordonnée sur l'axe Y de l'axe de rotation
     * @param zAxis Valeur de la coordonnée sur l'axe Z de l'axe de rotation
     */
    void setRotation(float angle, float xAxis, float yAxis, float zAxis);

    /**
     * Applique une translation à l'objet
     * @param translation Vecteur 3D représentant la translation
     */
	void translate(Vector3f translation);

    /**
     * Applique une translation à l'objet
     * @param x Valeur de la translation sur l'axe X
     * @param y Valeur de la translation sur l'axe Y
     * @param z Valeur de la translation sur l'axe Z
     */
    void translate(float x, float y, float z);

    /**
     * Applique une rotation à l'objet
     * @param angle Angle de rotation (en degré)
     * @param axis Vecteur 3D représentant l'axe de rotation
     */
	void rotate(float angle, Vector3f axis);

    /**
     * Applique une rotation à l'objet
     * @param angle Angle de rotation (en degré)
     * @param xAxis Valeur de la coordonnée sur l'axe X de l'axe de rotation
     * @param yAxis Valeur de la coordonnée sur l'axe Y de l'axe de rotation
     * @param zAxis Valeur de la coordonnée sur l'axe Z de l'axe de rotation
     */
    void rotate(float angle, float xAxis, float yAxis, float zAxis);

    /**
     * Recalcule la matrice des transformations de l'objet
     */
	void computeMatrix();

    /**
     * Retourne la matrice des transformations de l'objet
     * @return Matrice des transformations
     */
	Matrix4f getModelMatrix();
}
