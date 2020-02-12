package sample.mapElement.monster;

import sample.config.Coordinate;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.mapElement.mapElement;

abstract public class Monster implements mapElement{

    private static final Image deadIcon = new Image("file:src/main/resources/collision.png", 30, 30, true, true);

    int health;
    private int speed;
    private int earning;
    private int freezeTime;
    private int normalSpeed;
    private Image icon;
    private Coordinate currentLocation;

    public Monster(int health, int earning, int speed, Image icon) {
        this.health = health;
        this.earning = earning;
        this.speed = normalSpeed = speed;
        this.icon = icon;
        currentLocation = new Coordinate(0,0);

        System.out.println( toString() +" "+ health + " hp generated");
    }

    @Override
    public abstract String toString();

    public Coordinate getCurrentLocation(){ return currentLocation; }

    public int getEarning() { return earning; }


    public void receiveDamage(int health)
    {
        this.health -= health;
    }

    /**
     * update the freeze time by ice sample.mapElement.tower, the time cannot more then 3, and the monster would been stopped
     * @param freezeTime the time freeze by ice sample.mapElement.tower
     */
    public void slowDown(int freezeTime)  {
        this.freezeTime = freezeTime;
        if(speed == normalSpeed){
            speed-=9;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    private boolean canMove(){ return speed>0; }

    public Image getIcon(){
        return  (isDead())? deadIcon: icon;
    }

    public Label getDescriptionLabel(){
        double x = currentLocation.x;
        double y = currentLocation.y;
        Label infoPane = new Label();
        infoPane.setPadding(new Insets(5));
        //todo place the infoPane in a better way
        infoPane.setLayoutY((y+0.3) * 40);
        if(x<6){        //x = 1,2,3
            infoPane.setLayoutX((1+x) * 40);
        }
        else{           //x = 4, 5, 6
            infoPane.setLayoutX((x-3) * 40);
        }
        infoPane.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        infoPane.setStyle("-fx-border-color: black;");
        infoPane.setText("Hp remaining: " + health);
        return infoPane;
    }

    /**
     * move the monster
     * Also update the remaining freeze time, if any
     */
    public void move(){
        if(!canMove()){ return; }

        double distance;

        double moveX = 0, moveY = 0;

        if(currentLocation.isColumnThatMonsterCanMoveDown()) {
            distance = 460 - currentLocation.pixel_Y - speed; //420 means in the middle of the grid, monster not move to the bottom of the bottom grid

            if(currentLocation.pixel_Y==20&&currentLocation.pixel_X!=20) {
                if(currentLocation.pixel_X-320>0&&currentLocation.pixel_X+speed>=340) {
                    moveX=340-currentLocation.pixel_X;
                    moveY=speed-moveX;
                }
                else if(currentLocation.pixel_X-160>0&&currentLocation.pixel_X+speed>=180) {
                    moveX=180-currentLocation.pixel_X;
                    moveY=speed-moveX;
                }
                else {
                    moveX=speed;
                }
            }
            else if(distance >=0) {
                 moveY=speed;
            }
            else {
                moveY=460-currentLocation.pixel_Y;
                moveX=speed-moveY;
            }
            currentLocation.pixel_X+=moveX;
            currentLocation.pixel_Y+=moveY;
            currentLocation=new Coordinate((currentLocation.pixel_X-20)/40,(currentLocation.pixel_Y-20)/40);
        }
        else if(currentLocation.isColumnThatMonsterCanMoveUp()) {
            distance=currentLocation.pixel_Y-speed-20;
            if(currentLocation.pixel_Y==460) {
                if(currentLocation.pixel_X-400>0&&currentLocation.pixel_X+speed>=420) {
                    moveX=420-currentLocation.pixel_X;
                    moveY=speed-moveX;
                }
                else if(currentLocation.pixel_X-240>0&&currentLocation.pixel_X+speed>=260) {
                    moveX=260-currentLocation.pixel_X;
                    moveY=speed-moveX;
                }
                else if(currentLocation.pixel_X-80>0&&currentLocation.pixel_X+speed>=100) {
                    moveX=100-currentLocation.pixel_X;
                    moveY=speed-moveX;
                }
                else {
                    moveX=speed;
                }
            }
            else if(distance >=0) {
                moveY=speed;
            }
            else {
                moveY=currentLocation.pixel_Y-20;
                moveX=speed-moveY;
            }

            currentLocation.pixel_X+=moveX;
            currentLocation.pixel_Y-=moveY;
            currentLocation=new Coordinate((currentLocation.pixel_X-20)/40,(currentLocation.pixel_Y-20)/40);
        }
        else{
            currentLocation.pixel_X+=speed;
            currentLocation= new Coordinate((currentLocation.pixel_X-20)/40,(currentLocation.pixel_Y-20)/40);
        }

        if(freezeTime>0) {
            freezeTime--;
        }
        if(freezeTime==0){
            speed = normalSpeed;
        }
    }
}