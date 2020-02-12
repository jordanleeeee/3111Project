package sample.eventHandler;

import sample.body.Arena;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.mapElement.tower.Tower;

//clean
public class UpgradeActionHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private Tower tower;
    private Arena arena;
    private Label labelMoneyLeft;

    UpgradeActionHandler(Stage stage, Tower tower, Arena arena, Label labelMoneyLeft){
        this.stage = stage;
        this.tower = tower;
        this.arena = arena;
        this.labelMoneyLeft = labelMoneyLeft;
    }
    @Override
    public void handle(ActionEvent event) {
        boolean hasUpgradeTower = arena.upgradeTower(tower);
        if(hasUpgradeTower){
            labelMoneyLeft.setText(String.valueOf(arena.getMoney()));
        }
        stage.close();
    }
}