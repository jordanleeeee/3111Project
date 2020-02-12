package sample.eventHandler;

import sample.body.Arena;
import sample.config.Coordinate;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import sample.mapElement.tower.Tower;

public class DragDroppedEventHandler implements EventHandler<DragEvent> {
    private Label target;
    private int x;
    private int y;
    private Label labelMoneyLeft;
    private AnchorPane paneArena;
    private Arena arena = Arena.getInstance();

    public DragDroppedEventHandler(Label target, int x, int y, Arena arena, Label labelMoneyLeft, AnchorPane paneArena){
        this.target = target;
        this.x = x;
        this.y = y;
        this.labelMoneyLeft = labelMoneyLeft;
        this.paneArena = paneArena;
    }

    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (target.getGraphic() == null) {  //if it already have sample.mapElement.tower, cannot build sample.mapElement.tower
            if(arena.addTower(x, y, db.getString())){
                success = true;
                Tower newTower = arena.getTowerAt(new Coordinate(x, y));
                target.setGraphic(new ImageView(newTower.getIcon()));
                target.setAlignment(Pos.CENTER);
                labelMoneyLeft.setText(String.valueOf(arena.getMoney()));

                Circle shadedArea = newTower.getAttackRegionDemo();
                MouseExitedTowerEventHandler exitEvent = new MouseExitedTowerEventHandler(target, paneArena, shadedArea);
                target.setOnMouseExited(exitEvent);
                MouseEnterTowerEventHandler mouseEnter = new MouseEnterTowerEventHandler(target, paneArena, shadedArea, newTower, exitEvent);
                target.setOnMouseEntered(mouseEnter);
                target.setOnMouseClicked(new mouseClickedEventHandler(target, newTower, arena, labelMoneyLeft));
            }
            else {
                Alert info = new Alert(Alert.AlertType.WARNING, "Not enough resources to build the sample.mapElement.tower");
                info.show();
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
