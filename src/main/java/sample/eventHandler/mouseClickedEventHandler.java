package sample.eventHandler;

import sample.body.Arena;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.mapElement.tower.Tower;

//clean
public class mouseClickedEventHandler implements EventHandler<MouseEvent> {

    private Label target;
    private Tower tower;
    private Arena arena;
    private Label labelMoneyLeft;

    mouseClickedEventHandler(Label target, Tower tower, Arena arena, Label labelMoneyLeft) {
        this.target = target;
        this.tower = tower;
        this.arena = arena;
        this.labelMoneyLeft = labelMoneyLeft;
    }

    private Stage getOperationSelectionStage(){
        Stage stage = new Stage();

        HBox btnPlatform = new HBox();
        btnPlatform.setAlignment(Pos.CENTER);

        Button destroy = new Button("Destroy Tower");
        destroy.setOnAction(new DestroyActionHandler(stage, tower, arena, target));

        Button upgrade = new Button("Upgrade Tower");
        upgrade.setOnAction(new UpgradeActionHandler(stage, tower, arena, labelMoneyLeft));

        btnPlatform.getChildren().add(destroy);
        btnPlatform.getChildren().add(upgrade);

        VBox operationPane = new VBox();
        Label notes = new Label("Tower max Level Reached");

        if(tower.isMaxLevelReached()){
            upgrade.setDisable(true);
            operationPane.getChildren().add(notes);
        }
        operationPane.getChildren().add(btnPlatform);

        operationPane.setAlignment(Pos.CENTER);
        //the new scene pop up to let player choose sample.mapElement.tower operation
        Scene scene = new Scene(operationPane, 220, 50);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Tower Operation");
        return stage;
    }

    @Override
    public void handle (MouseEvent event) {
        //System.out.println("mouse clicked");
        Stage stage = getOperationSelectionStage();
        stage.show();
        event.consume();
    }
}
