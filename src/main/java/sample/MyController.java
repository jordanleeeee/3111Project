package sample;

import sample.body.Arena;
import sample.eventHandler.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import sample.mapElement.monster.Monster;

import java.util.Stack;

/**
 * Construction the Arena, process all the mouse event perform by the player
 */
//clean
public class MyController {
    @FXML
    private Button buttonNextFrame;
    @FXML
    private Button buttonSimulate;
    @FXML
    private Button buttonPlay;
    @FXML
    private AnchorPane paneArena;
    @FXML
    private Label labelBasicTower;
    @FXML
    private Label labelIceTower;
    @FXML
    private Label labelCatapult;
    @FXML
    private Label labelLaserTower;
    @FXML
    private Label labelMoneyLeft;

    private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 480;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int MAX_H_NUM_GRID = 12;
    private static final int MAX_V_NUM_GRID = 12;

    private Label[][] grids = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; //the grids on arena
    private Arena arena = null;
    private Stack<ImageView> monsterIcons = new Stack<>();

    /**
     * will be call when the player press the play button
     * <p>enable the next frame button and set the drag and drop event</p>
     */
    @FXML
    private void play(){
        System.out.println("Game Start");
        buttonNextFrame.setDisable(false);
        buttonPlay.setDisable(true);
        setDragAndDropAndMove();
        labelMoneyLeft.setText(String.valueOf(arena.getMoney()));
        //add a faster method to process to next round
        buttonNextFrame.setOnMouseDragged(e->nextFrame());
    }

    private void setDragAndDropAndMove() {
        labelBasicTower.setOnDragDetected(new DragEventHandler(labelBasicTower));
        labelIceTower.setOnDragDetected(new DragEventHandler(labelIceTower));
        labelCatapult.setOnDragDetected(new DragEventHandler(labelCatapult));
        labelLaserTower.setOnDragDetected(new DragEventHandler(labelLaserTower));

        for (int i = 0; i < MAX_V_NUM_GRID; i++) {
            for (int j = 0; j < MAX_H_NUM_GRID; j++) {
                if (!(j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1))) {   //those are green grid
                    Label target = grids[i][j];
                    target.setOnDragDropped(new DragDroppedEventHandler(target, j, i, arena, labelMoneyLeft, paneArena));
                    target.setOnDragOver(new DragOverEventHandler(target));
                    target.setOnDragEntered(new DragEnterEventHandler(target));
                    target.setOnDragExited(new DragExitedEventHandler(target));
                }
            }
        }
        paneArena.setOnMouseMoved(e-> arena.clearAttackLine());
    }

    /**
     * create the arena which consist of white and green grid.
     * Add symbolic graphic to end zone and monster generate zone
     */

    @FXML
    public void createArena() {
        arena =  Arena.getInstance();
        arena.setAnchorPane(paneArena);

        buttonNextFrame.setDisable(true);
        buttonSimulate.setDisable(true);

        addGridToArena();
        addIconToArena();
    }

    private void addGridToArena(){
        for (int i = 0; i < MAX_V_NUM_GRID; i++) {
            for (int j = 0; j < MAX_H_NUM_GRID; j++) {
                Label newLabel = new Label();
                if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1)) {
                    newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    newLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                newLabel.setLayoutX(j * GRID_WIDTH);
                newLabel.setLayoutY(i * GRID_HEIGHT);
                newLabel.setMinWidth(GRID_WIDTH);
                newLabel.setMaxWidth(GRID_WIDTH);
                newLabel.setMinHeight(GRID_HEIGHT);
                newLabel.setMaxHeight(GRID_HEIGHT);
                newLabel.setStyle("-fx-border-color: black;");
                grids[i][j] = newLabel;
                paneArena.getChildren().addAll(newLabel);
            }
        }
    }

    private void addIconToArena(){
        Node endZone = new ImageView(new Image("file:src/main/resources/endZone.jpg", 35, 35, true, true));
        Node startZone = new ImageView(new Image("file:src/main/resources/startZone.png", 35, 35, true, true));
        grids[0][0].setGraphic(startZone);
        grids[0][0].setAlignment(Pos.CENTER);
        grids[0][11].setGraphic(endZone);
        grids[0][11].setAlignment(Pos.CENTER);
    }

    @FXML
    private void nextFrame(){
        arena.nextRound();
        updateMonsterIcon();

        if(arena.isGameOver()){
            handleGameOver();
            return;
        }
        //update resources
        labelMoneyLeft.setText(String.valueOf(arena.getMoney()));
    }

    private void updateMonsterIcon(){
        //first clear all the monster icon
        while (!monsterIcons.empty()){
            paneArena.getChildren().remove(monsterIcons.pop());
        }
        //then place monster in updated location
        for(Monster oneMonster: arena.getMonsters()){
            ImageView imageView = new ImageView(oneMonster.getIcon());
            imageView.setX(oneMonster.getCurrentLocation().img_X);
            imageView.setY(oneMonster.getCurrentLocation().img_Y);
            paneArena.getChildren().add(imageView);
            //add event handler if the monster is not dead yet
            if (!oneMonster.isDead()) {
                MouseExitMonsterEventHandler exitEvent = new MouseExitMonsterEventHandler(paneArena);
                imageView.setOnMouseEntered(new MouseEnterMonsterEventHandler(paneArena, oneMonster, exitEvent));
                imageView.setOnMouseExited(exitEvent);
            }
            monsterIcons.push(imageView);
        }
    }

    private void handleGameOver(){
        System.out.println("Game Over");
        buttonNextFrame.setDisable(true);
        labelBasicTower.setOnDragDetected(null);
        labelIceTower.setOnDragDetected(null);
        labelCatapult.setOnDragDetected(null);
        labelLaserTower.setOnDragDetected(null);
        String info = "Game Over ! _ ! You pass " + arena.getTurn() + " turn";
        Alert loseInfo = new Alert(Alert.AlertType.INFORMATION, info);
        loseInfo.setHeaderText("Opps...");
        loseInfo.show();
    }
}