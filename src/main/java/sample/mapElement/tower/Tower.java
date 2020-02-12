package sample.mapElement.tower;


import sample.config.Coordinate;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import sample.mapElement.monster.Monster;
import sample.mapElement.mapElement;

import java.util.ArrayList;

/**
 * Tower class is used to generated sample.mapElement.tower in the game
 * This class is the base class of the Basic, Ice, Catapult and laser Tower.
 */
public abstract class Tower implements mapElement{

    private Image icon;
    double range;
    int power;
    private int cost;
    int level = 1;
    Coordinate currentLocation;

    Monster target = null;

    public Tower(int x, int y, double range, int cost, int power, Image icon) {
        this.currentLocation = new Coordinate(x, y);
        this.range = range;
        this.cost = cost;
        this.power = power;
        this.icon = icon;
    }

    public Image getIcon() {
        return icon;
    }

    public abstract void upgrade(); // you should handle player's recourse in arena

    public abstract boolean attackMonster(ArrayList<Monster> monsters);

    public abstract String toString();

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public int getUpgradeCost() {return (int)(cost*1.2);}//it is the cost for upgrade the sample.mapElement.tower

    public int getCost() {
        return cost;
    }

    public Circle getAttackRegionDemo(){
        Circle shadedArea = new Circle();
        shadedArea.setCenterX(currentLocation.x* 40 + 20);
        shadedArea.setCenterY(currentLocation.y* 40 + 20);
        shadedArea.setRadius(range);
        shadedArea.setFill(Color.RED);
        shadedArea.setOpacity(0.4);
        return shadedArea;
    }

    public boolean isMaxLevelReached(){
        return level>3;
    }

    public Label getDescriptionLabel(){
        double x = currentLocation.x;
        double y = currentLocation.y;
        Label infoPane = new Label();
        if(x<6){        //x = 1,2,3
            infoPane.setLayoutX((3+x) * 40);
            if(y<6) infoPane.setLayoutY((3+y) * 40);

            else infoPane.setLayoutY((y-3) * 40);
        }
        else{           //x = 4, 5, 6
            infoPane.setLayoutX((x-3) * 40);
            if(y<6) infoPane.setLayoutY((3+y) * 40);
            else infoPane.setLayoutY((y-3) * 40);

        }
        infoPane.setPadding(new Insets(5));
        infoPane.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        infoPane.setStyle("-fx-border-color: black;");
        infoPane.setText("Type: " + toString() + "\nPower: " + power +
                "\nUpgrade Cost: " + getUpgradeCost()+ "\nRange: " + range + "\nLevel = "+ level);

        return infoPane;
    }

    ArrayList<Monster> filterOutInRangeMonster(ArrayList<Monster> monsters) {
        ArrayList<Monster> inRangeMonster = new ArrayList<>();

        for(Monster oneMonster: monsters){
            double distanceDifference = oneMonster.getCurrentLocation().getDistanceDifferenceInPixel(currentLocation);
            if(distanceDifference <= range){
                inRangeMonster.add(oneMonster);
            }
        }
        return inRangeMonster;
    }

    Monster getClosestMonsterWithinRange(ArrayList<Monster> monsters){
        double minDistanceDiff = 10000;
        Monster monster = null;
        for(Monster oneMonster: monsters){
            double diff = oneMonster.getCurrentLocation().getDistanceBetweenEndZone();
            if(diff < minDistanceDiff){
                minDistanceDiff = diff;
                monster = oneMonster;
            }
        }
        return monster;
    }

    void printOutAttackInfo(Monster attackedMonster){
        System.out.println(toString() + " at location "+ currentLocation.toString() +" -> " +
                attackedMonster.toString() + " at location " + attackedMonster.getCurrentLocation().toString());
    }

    public Line getAttackLine(){
        Line line = new Line(currentLocation.pixel_X, currentLocation.pixel_Y,
                target.getCurrentLocation().pixel_X, target.getCurrentLocation().pixel_Y);
        line.setStyle("-fx-stroke: red;");
        return line;
    }

}