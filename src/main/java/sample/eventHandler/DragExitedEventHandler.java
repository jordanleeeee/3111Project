package sample.eventHandler;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;

//clean
public class DragExitedEventHandler implements EventHandler<DragEvent> {

    private Label target;
    /**
     * same to process the target item
     * @param target the target item
     */
    public DragExitedEventHandler(Label target){
        this.target = target;
    }
    @Override
    public void handle(DragEvent event) {
        target.setStyle("-fx-border-color: black;");
        //System.out.println("Exit");
        event.consume();
    }
}
