package view;

import controller.Controller;
import controller.action.*;
import controller.command.CreateVertexCommand;
import controller.command.MoveVertexCommand;
import controller.command.RemoveEdgeCommand;
import controller.command.RemoveVertexCommand;
import model.GraphElement;
import model.Vertex;
import opengl.GLCanvas;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.utils.GLRay;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
    private GraphElement pasteBuffer;
    private boolean isMousePressed = false;
    private VertexView selectedVertex;
    private EdgeView selectedEdge;

    private Vector2f oldPosition;

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

                        case SELECTION:
                            if (selectedVertex != null) {
                                positions[0] = selectedVertex.getModel().getPosition();
                            }
                            break;
                        case MOVE:
                            oldPosition= new Vector2f(arg0.getX(), arg0.getY());
                            break;

                        case DELETION:
                            if(intersectedVertexView != null) {
                                GraphCanvas.this.controller.executeCommand(new RemoveVertexCommand(selectedVertex.getModel()));
                            }else if(intersectedEdgeView != null) {
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
                        case SELECTION:
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
                        getPopupOnNothing(arg0.getX(), arg0.getY()).show(arg0.getComponent(), arg0.getX(), arg0.getY());
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

                        case SELECTION:
                            if (selectedVertex != null) {
                                positions[1] = selectedVertex.getModel().getPosition();
                                GraphCanvas.this.controller.executeCommand(new MoveVertexCommand(graphView, selectedVertex, positions[0], positions[1]));
                                positions[0] = null;
                                positions[1] = null;
                                selectedVertex = null;
                            }
                            break;
                        case MOVE:
                            oldPosition=null;
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
                        if(oldPosition !=null){
                            Vector2f delta = Vector2f.sub(new Vector2f(arg0.getX(), arg0.getY()), oldPosition, null);
                            GraphCanvas.this.camera.rotate((float)(delta.getX()*0.5), new Vector3f(0f,-1f,0f));
                            GraphCanvas.this.camera.rotate((float)(delta.getY()*0.5), new Vector3f(1f,0f,0f));
                            oldPosition.setX(arg0.getX());
                            oldPosition.setY(arg0.getY());
                        }
                        /*GLRay ray = GraphCanvas.this.camera.getCursorRay(new Vector2f(arg0.getX(), GraphCanvas.this.getSize().height - arg0.getY()));
                        GraphCanvas.this.camera.lookToDirection(ray.getDirection());*/

                        break;
                    case SELECTION:
                        if (selectedVertex != null) {
                            selectedVertex.setPosition(getLookAtPosition(arg0.getX(), arg0.getY()));
                            selectedVertex.getModel().setPosition(getLookAtPosition(arg0.getX(), arg0.getY()));
                        } else if (false) {
                            //TODO selection multiple zone
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
        contextMenu.add(new CopyNowAction(this.controller, vertexView.getModel()));
        contextMenu.add(new CutNowAction(this.controller, vertexView.getModel()));
        contextMenu.add(new RemoveNowAction(this.controller, vertexView.getModel()));
        contextMenu.add(new JPopupMenu.Separator());
        contextMenu.add(new UndoAction(this.controller));
        contextMenu.add(new RedoAction(this.controller));
        contextMenu.add(new ZoomPlusAction(this.controller));
        contextMenu.add(new ZoomLessAction(this.controller));
        return contextMenu;
    }
    private JPopupMenu getPopupOnNothing(int x, int y){
        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(new PasteNowAction(this.controller, x, y));
        contextMenu.add(new JPopupMenu.Separator());
        contextMenu.add(new UndoAction(this.controller));
        contextMenu.add(new RedoAction(this.controller));
        contextMenu.add(new ZoomPlusAction(this.controller));
        contextMenu.add(new ZoomLessAction(this.controller));
        return contextMenu;
    }
    ////////////////////////////////////////////////  ANIMATION //////////////////////////////////////////////
    public void animationLoop() {
        lockDraw();
        graphView.animate();
        unlockDraw();
    }

    public GraphElement getPasteBuffer() {
        return pasteBuffer;
    }

    public void setPasteBuffer(GraphElement pasteBuffer) {
        this.pasteBuffer = pasteBuffer;
    }


}
