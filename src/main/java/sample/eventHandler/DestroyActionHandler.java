package sample.eventHandler;

import sample.body.Arena;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.mapElement.tower.Tower;

//clean
public class DestroyActionHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private Tower tower;
    private Label target;

    DestroyActionHandler(Stage stage, Tower tower, Arena arena, Label target){
        this.stage = stage;
        this.tower = tower;
        this.target = target;
    }

    @Override
    public void handle(ActionEvent event) {
        target.setOnMouseEntered(null);
        target.setOnMouseExited(null);
        target.setOnMouseClicked(null);
        target.setGraphic(null);
        Arena.getInstance().removeTower(tower);
        stage.close();
    }
}
