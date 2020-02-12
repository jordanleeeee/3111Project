package sample.eventHandler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class MouseExitedTowerEventHandler implements EventHandler<MouseEvent> {
    //remove sample.mapElement.tower info and shaded fire area
    private Label target;
    private AnchorPane paneArena;
    private Circle shadedArea;
    private Node infoPane;

    MouseExitedTowerEventHandler(Label target, AnchorPane paneArena, Circle shadedArea) {
        this.target = target;
        this.paneArena = paneArena;
        this.shadedArea = shadedArea;
        infoPane = null;
    }

    void setInfoPane(Node infoPane) {
        this.infoPane = infoPane;
    }
    @Override
    public void handle (MouseEvent event) {
        //System.out.println("mouse exit");
        paneArena.getChildren().remove(shadedArea);
        paneArena.getChildren().remove(infoPane);
        event.consume();
    }
}