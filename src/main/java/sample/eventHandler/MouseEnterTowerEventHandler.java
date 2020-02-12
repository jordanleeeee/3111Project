package sample.eventHandler;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import sample.mapElement.tower.Tower;

public class MouseEnterTowerEventHandler implements EventHandler<MouseEvent> {
    private Label target;
    private AnchorPane paneArena;
    private Circle shadedArea;
    private Tower tower;
    private MouseExitedTowerEventHandler exitEvent;

    MouseEnterTowerEventHandler(Label target, AnchorPane paneArena, Circle shadedArea, Tower tower, MouseExitedTowerEventHandler exitEvent) {
        this.target = target;
        this.paneArena = paneArena;
        this.shadedArea = shadedArea;
        this.tower = tower;
        this.exitEvent = exitEvent;
    }

    @Override
    public void handle (MouseEvent event) {
        paneArena.getChildren().add(shadedArea);
        Label infoPane = tower.getDescriptionLabel();
        paneArena.getChildren().add(infoPane);
        exitEvent.setInfoPane(infoPane);
        target.toFront();
    }
}