package sample.mapElement.tower;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import sample.mapElement.monster.Monster;

import java.util.ArrayList;

/**
 * Laser sample.mapElement.tower class expend from Tower
 */
public class LaserTower extends Tower {

    private static final Image icon = new Image("file:src/main/resources/laserTower.png", 40, 40, true, true);

    private int attackCost = 2;

    private static final int COST = 20;
    private static final int RANGE = 160;
    private static final int INITIAL_POWER = 8;
    private static final int laserWidth = 3;
    //private int subPower = INITIAL_POWER - 2;

    LaserTower(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY, RANGE, COST, INITIAL_POWER, icon);
    }

    @Override
    public Label getDescriptionLabel(){
        Label infoPane = super.getDescriptionLabel();
        infoPane.setText(infoPane.getText()+ "\nMoney Consume: " + attackCost);
        return infoPane;
    }

    private void storeVictims(ArrayList<Monster> monsters, Monster closestMonster, ArrayList<Monster> victim){
        Double slopOfLine = currentLocation.getSlopeInPixel(closestMonster.getCurrentLocation());
        double A, B, C;
        if(slopOfLine.isNaN()){
            A = 1;
            B = 0;
            C = -currentLocation.pixel_X;

        }
        else{
            A = -slopOfLine;
            B = 1;
            C = -currentLocation.pixel_Y + slopOfLine* currentLocation.pixel_X;
        }

        for(Monster oneMonster: monsters){
            if(closestMonster.getCurrentLocation().pixel_X > currentLocation.pixel_X
                    && oneMonster.getCurrentLocation().pixel_X < currentLocation.pixel_X){
                continue;
            }
            else if (closestMonster.getCurrentLocation().pixel_X < currentLocation.pixel_X
                    && oneMonster.getCurrentLocation().pixel_X > currentLocation.pixel_X) {
                continue;
            }
            else{
                double distance = Math.abs((A* oneMonster.getCurrentLocation().pixel_X)
                        + (B* oneMonster.getCurrentLocation().pixel_Y) + C) / Math.sqrt(A*A + B*B);
                if(distance < laserWidth){
                    victim.add(oneMonster);
                }
            }
        }
    }
    /**
     * attack monster
     */
    public boolean attackMonster(ArrayList<Monster> monsters) {

        ArrayList<Monster> inRangeMonsters = filterOutInRangeMonster(monsters);
        Monster closestMonster = getClosestMonsterWithinRange(inRangeMonsters);
        target = closestMonster;
        if(closestMonster == null) {
            return false;
        }

        ArrayList<Monster> victims = new ArrayList<>();
        storeVictims(monsters, closestMonster, victims);

        for(Monster oneMonster: victims){
            oneMonster.receiveDamage(power);
        }
        printOutAttackInfo(closestMonster);
        return true;
    }

    @Override
    public String toString() {
        return "laserTower";
    }

    /**
     * upgrade
     */
    public void upgrade() {
        power += 2;
        attackCost++;
        level++;
    }

    public int getAttackCost() { return attackCost; }

    @Override
    public Line getAttackLine() {
        Double slopOfLine = currentLocation.getSlopeInPixel(target.getCurrentLocation());

        Line laserLine = super.getAttackLine();
        laserLine.setStrokeWidth(6);
        double x = target.getCurrentLocation().pixel_X;
        double y = target.getCurrentLocation().pixel_Y;
        //Monster is on LHS of sample.mapElement.tower
        if(target.getCurrentLocation().pixel_X < currentLocation.pixel_X){
            while (x>0 && y<480 && y>0){
                x--;
                y -= slopOfLine;
            }
        }
        //Monster is on RHS of sample.mapElement.tower
        if(target.getCurrentLocation().pixel_X> currentLocation.pixel_X){
            while (x<480 && y<480 && y>0){
                x++;
                y += slopOfLine;
            }
        }
        //Monster is exactly below the sample.mapElement.tower
        if (target.getCurrentLocation().pixel_X == currentLocation.pixel_X && target.getCurrentLocation().pixel_Y > currentLocation.pixel_Y) {
            y = 480;
        }
        //Monster is exactly on top of the sample.mapElement.tower
        if (target.getCurrentLocation().pixel_X == currentLocation.pixel_X && target.getCurrentLocation().pixel_Y < currentLocation.pixel_Y) {
            y = 0;
        }
        laserLine.setEndX(x);
        laserLine.setEndY(y);

        return laserLine;
    }
}