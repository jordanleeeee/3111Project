package sample.eventHandler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * event handler for removing showing monster remaining HP
 */
public class MouseExitMonsterEventHandler implements EventHandler<MouseEvent> {
    //show sample.mapElement.tower info and shaded fire area
    private AnchorPane paneArena;
    private Node infoPane = null;

    public MouseExitMonsterEventHandler(AnchorPane paneArena) {
        this.paneArena = paneArena;
    }

    void setInfoPane(Node infoPane) {
        this.infoPane = infoPane;
    }

    @Override
    public void handle (MouseEvent event) {
        //System.out.println("mouse exited a monster");
        paneArena.getChildren().remove(infoPane);
        event.consume();
    }
}