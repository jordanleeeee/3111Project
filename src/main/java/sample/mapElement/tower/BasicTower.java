package sample.mapElement.tower;

import javafx.scene.image.Image;
import sample.mapElement.monster.Monster;

import java.util.ArrayList;

public class BasicTower extends Tower{

    private static final Image icon = new Image("file:src/main/resources/basicTower.png", 40, 40, true, true);

    private static final int COST = 10;
    private static final int RANGE = 65;
    private static final int INITIAL_POWER = 4;

    public BasicTower(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY, RANGE, COST, INITIAL_POWER, icon);
    }

    public boolean attackMonster(ArrayList<Monster> monsters) {

        ArrayList<Monster> inRangeMonsters = filterOutInRangeMonster(monsters);
        Monster closestMonster = getClosestMonsterWithinRange(inRangeMonsters);

        target = closestMonster;
        if(closestMonster == null) {
        	return false;
        }

        closestMonster.receiveDamage(power);
        printOutAttackInfo(closestMonster);
        return true;
    }

    @Override
    public String toString() {
        return "basicTower";
    }

    public void upgrade() {
        power += 3;
        level++;
    }

}