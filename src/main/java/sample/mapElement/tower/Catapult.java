package sample.mapElement.tower;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.mapElement.monster.Monster;

import java.util.ArrayList;

/**
 * 
 * Catapult expend from Tower
 *
 */
public class Catapult extends Tower {

	private static final Image icon = new Image("file:src/main/resources/catapult.png", 40, 40, true, true);

	private int coolingTime = 3; //the reload time
	private int remainCoolingPeriod = 0;

	private static final int COST = 15;
	// i.e. 50<range<150
	private static final int RANGE = 150;
	private static final int BLIND_RANGE = 50;
	private static final int INITIAL_POWER = 6;

	private final int spatterRadius = 25;


	public Catapult(int coordinateX, int coordinateY) {
		super(coordinateX, coordinateY, RANGE, COST, INITIAL_POWER, icon);
	}

	private boolean isReCharging(){
		return remainCoolingPeriod > 0;
	}

	@Override
	public ArrayList<Monster> filterOutInRangeMonster(ArrayList<Monster> monsters) {
		ArrayList<Monster> inRangeMonster = new ArrayList<>();

		for(Monster oneMonster: monsters){
			double distanceDifference = oneMonster.getCurrentLocation().getDistanceDifferenceInPixel(currentLocation);
			if(distanceDifference <= range && distanceDifference >= BLIND_RANGE){
				inRangeMonster.add(oneMonster);
			}
		}
		return inRangeMonster;
	}

	private ArrayList<Monster> getMonstersNearTheVictim(ArrayList<Monster> monsters, Monster victim){
		ArrayList<Monster> sideVictims = new ArrayList<>();
		for(Monster oneMonster: monsters){
			if(oneMonster == victim){
				continue;
			}
			if(oneMonster.getCurrentLocation().getDistanceDifferenceInPixel(victim.getCurrentLocation()) <= spatterRadius ){
				sideVictims.add(oneMonster);
			}
		}
		return sideVictims;
	}

	public boolean attackMonster(ArrayList<Monster> monsters) {
		if(isReCharging()) {
			remainCoolingPeriod--;
			return false;
		}
		ArrayList<Monster> inRangeMonsters = filterOutInRangeMonster(monsters);
		Monster closestMonster = getClosestMonsterWithinRange(inRangeMonsters);
		target = closestMonster;
		if(closestMonster == null) {
			return false;
		}

		ArrayList<Monster> monstersNearTheVictim = getMonstersNearTheVictim(monsters, closestMonster);
		closestMonster.receiveDamage(power);

		int subPower = INITIAL_POWER / 2;
		for(Monster oneMonster: monstersNearTheVictim){
			oneMonster.receiveDamage(subPower);
		}

		printOutAttackInfo(closestMonster);
		remainCoolingPeriod = coolingTime;
		return true;
	}

	@Override
	public String toString() {
		return "catapult";
	}

	public void upgrade() {
		coolingTime--;
		level++;
	}

	@Override
	public Label getDescriptionLabel(){
		Label infoPane = super.getDescriptionLabel();
		infoPane.setText(infoPane.getText()+ "\nFreeze Time: " + coolingTime +
				"\nRemain Cool Down Period: " + remainCoolingPeriod);
		return infoPane;
	}

	@Override
	public Circle getAttackRegionDemo(){
		Circle shadedArea = super.getAttackRegionDemo();
		shadedArea.setRadius(100);
		shadedArea.setStroke(Color.RED);
		shadedArea.setStrokeWidth(100);
		shadedArea.setFill(null);
		return shadedArea;
	}

	public Circle getInflectedAttackedArea(){
		Circle dot = new Circle(target.getCurrentLocation().pixel_X, target.getCurrentLocation().pixel_Y,spatterRadius);
		dot.setFill(Color.RED);
		dot.setOpacity(0.5);
		return dot;
	}

}