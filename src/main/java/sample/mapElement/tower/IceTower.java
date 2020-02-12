package sample.mapElement.tower;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import sample.mapElement.monster.Monster;

import java.util.ArrayList;

/**
 * IceTower expend from Tower
 */
public class IceTower extends Tower {

	private static final Image icon = new Image("file:src/main/resources/iceTower.png", 40, 40, true, true);

	private static final int COST = 13;
	private static final int RANGE = 65;
	private static final int INITIAL_POWER = 0;
	private int freezeTime = 3;

	public IceTower(int coordinateX, int coordinateY) {
		super(coordinateX, coordinateY, RANGE, COST, INITIAL_POWER, icon);
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
		closestMonster.slowDown(freezeTime);
		printOutAttackInfo(closestMonster);
		return true;
	}

	@Override
	public Label getDescriptionLabel(){
		Label infoPane = super.getDescriptionLabel();
		infoPane.setText(infoPane.getText()+ "\nCool Down Time: " + freezeTime);
		return infoPane;
	}

	@Override
	public String toString() {
		return "iceTower";
	}

	public void upgrade() {
		freezeTime+=3;
		level++;
	}

}