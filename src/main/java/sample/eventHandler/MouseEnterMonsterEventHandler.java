package sample.eventHandler;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.mapElement.monster.Monster;

/**
 * event handler for showing monster remaining HP
 */

//clean
public class MouseEnterMonsterEventHandler implements EventHandler<MouseEvent> {
    //show sample.mapElement.tower info and shaded fire area
    private AnchorPane paneArena;
    private Monster monster;
    private MouseExitMonsterEventHandler exitEvent;

    public MouseEnterMonsterEventHandler(AnchorPane paneArena, Monster monster, MouseExitMonsterEventHandler exitEvent) {
        this.paneArena = paneArena;
        this.monster = monster;
        this.exitEvent = exitEvent;
    }
    @Override
    public void handle (MouseEvent event) {
        //System.out.println("mouse over a monster");
        Label infoPane = monster.getDescriptionLabel();
        exitEvent.setInfoPane(infoPane);
        paneArena.getChildren().add(infoPane);
        event.consume();
    }
}
