package sample.eventHandler;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;

//clean
public class DragEnterEventHandler implements EventHandler<DragEvent> {
    private Label target;

    public DragEnterEventHandler(Label target){
        this.target = target;
    }

    @Override
    public void handle(DragEvent event) {
        /* the drag-and-drop gesture entered the target */
        //System.out.println("onDragEntered");
        /* show to the user that it is an actual gesture target */
        if (event.getGestureSource() != target &&
                event.getDragboard().hasString()) {
            target.setStyle("-fx-border-color: blue;");
        }

        event.consume();
    }
}