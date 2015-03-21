package view;

import controller.Controller;
import controller.actions.*;
import controller.commands.MoveVertexCommand;
import controller.commands.RemoveEdgeCommand;
import controller.commands.RemoveVertexCommand;
import opengl.GLCanvas;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.utils.GLRay;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import utils.MathUtils;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphCanvas extends GLCanvas {
    private GraphView graphView;
    private Controller controller;
    private GLPerspectiveCamera camera;

    //attributs utilitaires
    private Object pasteBuffer;
    private boolean isMousePressed = false;
    private VertexView selectedVertex;
    private EdgeView selectedEdge;

    public GraphCanvas() throws LWJGLException {}

    public GraphView getGraphView() {
        return this.graphView;
    }

    public void setGraphView(GraphView graphView) {
        this.graphView = graphView;
        try {
            super.makeCurrent();
            this.graphView.init();
        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // Not yet displayable
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);
        this.camera.lookToDirection(new Vector3f(0.0f, 0.0f, -1.0f));
        this.camera.setPosition(new Vector3f(0.0f, 0.0f, 10.0f));
        super.setCamera(this.camera);

        if (this.graphView != null)
            this.graphView.init();

        final Vector3f[] positions = new Vector3f[2]; //obligé pour commande MoveVertexCommand

        /** MouseListener **/
        super.addMouseListener(new MouseInputAdapter() {
            public void initGraphCanvas() {
                try {
                    GraphCanvas.this.makeCurrent();
                } catch (LWJGLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                lockDraw();
                initGraphCanvas();

                if (arg0.getButton() == MouseEvent.BUTTON1) { // CLIC GAUCHE


                    VertexView intersectedVertexView = getIntersectedVertexView(arg0.getX(), arg0.getY());
                    EdgeView intersectedEdgeView = getIntersectedEdgeView(arg0.getX(), arg0.getY());
                    if (intersectedVertexView != null) {
                        selectedVertex = intersectedVertexView;
                        isMousePressed = true;
                    }
                    if (intersectedEdgeView != null) {
                        selectedEdge = intersectedEdgeView;
                    }
                    switch (GraphCanvas.this.controller.getState()) {
                        case VERTEX_CREATION:
                            if (intersectedVertexView == null) {
                                GraphCanvas.this.createVertex(arg0.getX(), arg0.getY());
                            }
                            break;

                        case EDGE_CREATION:
                            break;

                        case VERTEX_EDITION:
                            break;

                        case EDGE_EDITION:
                            break;

                        case MOVE:
                            if (selectedVertex != null) {
                                positions[0] = selectedVertex.getModel().getPosition();
                            }
                            break;

                        case DELETION:
                            if(intersectedVertexView != null) {
                                GraphCanvas.this.controller.executeCommand(new RemoveVertexCommand(selectedVertex.getModel()));
                            }
                            if(intersectedEdgeView != null) {
                                GraphCanvas.this.controller.executeCommand(new RemoveEdgeCommand(selectedEdge.getModel()));
                            }
                            break;

                        default:
                    }


                } else if (arg0.getButton() == MouseEvent.BUTTON3) { // CLIC DROIT

                    VertexView intersectedVertexView = getIntersectedVertexView(arg0.getX(), arg0.getY());
                    switch (GraphCanvas.this.controller.getState()) {
                        case VERTEX_CREATION:

                            break;

                        case EDGE_CREATION:
                            break;

                        case VERTEX_EDITION:
                            break;

                        case EDGE_EDITION:
                            break;

                        case MOVE:
                            break;

                        case DELETION:
                            break;

                        default:
                            System.out.println("ERREUR STATE");
                    }

                    if (intersectedVertexView != null) {
                        getPopupOnVertex(intersectedVertexView).show(arg0.getComponent(), arg0.getX(), arg0.getY());
                    } else {
                        System.out.println("PAS INTERSECTION !!!!");
                    }
                }
                unlockDraw();
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                lockDraw();

                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    initGraphCanvas();
                    isMousePressed = false;
                    VertexView intersectedVertexView = getIntersectedVertexView(arg0.getX(), arg0.getY());
                    switch (GraphCanvas.this.controller.getState()) {
                        case VERTEX_CREATION:
                            break;

                        case EDGE_CREATION:
                            if (intersectedVertexView != null && selectedVertex != null)
                                GraphCanvas.this.graphView.addEdge(GraphCanvas.this.selectedVertex.getModel(), intersectedVertexView.getModel());
                            break;

                        case VERTEX_EDITION:
                            break;

                        case EDGE_EDITION:
                            break;

                        case MOVE:
                            if (selectedVertex != null) {
                                positions[1] = selectedVertex.getModel().getPosition();
                                GraphCanvas.this.controller.executeCommand(new MoveVertexCommand(selectedVertex.getModel(), positions[0], positions[1]));
                                positions[0] = null;
                                positions[1] = null;
                                selectedVertex = null;
                            }
                            break;

                        case DELETION:
                            break;

                        default:
                            break;
                    }
                }
                unlockDraw();
            }
        });

        super.addMouseMotionListener(new MouseMotionAdapter() {

            public void initGraphCanvas() {
                try {
                    GraphCanvas.this.makeCurrent();
                } catch (LWJGLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void mouseDragged(MouseEvent arg0) {
                // ici, la souris est forcément appuyée
                lockDraw();
                initGraphCanvas();

                switch (GraphCanvas.this.getController().getState()) {
                    case MOVE:
                        if (selectedVertex != null) {
                            selectedVertex.setPosition(getLookAtPosition(arg0.getX(), arg0.getY()));
                            selectedVertex.getModel().setPosition(getLookAtPosition(arg0.getX(), arg0.getY()));
                        } else if (false) {
                            //TODO Si on est sur les bords, rotation de la caméra
                        } else {
                            System.out.print(".");
                        }
                        break;
                }
                unlockDraw();
            }
        });
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {
        if (this.graphView != null) {
            this.graphView.paint(transformationMatrix);
        }
    }

    ////////////////////////////// CREATION D'UN NOEUD //////////////////////////////////////////////////////
    private void createVertex(int x, int y) {
        this.graphView.addVertex(getLookAtPosition(x, y));
    }

    ////////////////////////////// RECUPERE POSITION PLAN 2D //////////////////////////////////////////////////////
    private Vector3f getLookAtPosition(int x, int y){
        GLRay ray = this.camera.getCursorRay(new Vector2f(x, super.getSize().height - y));

        /**
         * La partie suivante sert à trouver la distance à laquelle afficher le sommet de manière à ce qu'il soit sur le même plan 2D que les autres.
         * Pour comprendre la démonstration mathématique, demandez à Louis, Corentin ou Clément
         */
        Vector3f vecDirection = ray.getDirection(); //vecteur direction clic souris
        Vector3f vecCamera = new Vector3f(0,0, -this.camera.getPosition().getZ());

        vecDirection = vecDirection.normalise(null);
        vecCamera = vecCamera.normalise(null);

        double alpha; //angle entre les deux vecteurs (camera et direction du clic)
        float hypothenuse;

        alpha = Math.acos(Vector3f.dot(vecCamera, vecDirection)); //produit scalaire entre les deux vecteurs !
        hypothenuse = this.camera.getPosition().getZ() / (float)Math.cos(alpha);

        Vector3f position = Vector3f.add(ray.getPosition(), (Vector3f) ray.getDirection().scale(hypothenuse), null);

        return position;
    }

    /////////////////////////////// RETOURNE VERTEX VIEW INTERSECTED //////////////////////////////////////////
    private VertexView getIntersectedVertexView(int x, int y) {
        y = GraphCanvas.this.getHeight() - y; //car y swing et y canvas sont inversés
        GLRay ray = GraphCanvas.this.camera.getCursorRay(new Vector2f(x, y));

        return this.graphView.getIntersectedVertexView(ray);
    }

    /////////////////////////////// RETOURNE EDGE VIEW INTERSECTED //////////////////////////////////////////
    private EdgeView getIntersectedEdgeView(int x, int y) {
        y = GraphCanvas.this.getHeight() - y; //car y swing et y canvas sont inversés
        GLRay ray = GraphCanvas.this.camera.getCursorRay(new Vector2f(x, y));

        return this.graphView.getIntersectedEdgeView(ray);
    }

    ////////////////////////////////////////// GENERATION DES POPUPS //////////////////////////////////////////
    private JPopupMenu getPopupOnVertex(VertexView vertexView){
        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(new EditVertexNowAction(this.controller, vertexView));    // passer le vertexview en question en param
        contextMenu.add(new CopyNowAction(this.controller, vertexView));
        contextMenu.add(new JPopupMenu.Separator());
        contextMenu.add(new UndoAction());
        contextMenu.add(new RedoAction());
        contextMenu.add(new ZoomPlusAction());
        contextMenu.add(new ZoomLessAction());
        return contextMenu;
    }

    public void animationLoop() {
        lockDraw();
        graphView.animate();
        unlockDraw();
    }

    public Object getPasteBuffer() {
        return pasteBuffer;
    }

    public void setPasteBuffer(Object pasteBuffer) {
        this.pasteBuffer = pasteBuffer;
    }
}
