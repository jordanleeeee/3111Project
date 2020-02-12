package sample.body;

import sample.config.Coordinate;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import sample.mapElement.monster.Monster;
import sample.mapElement.monster.MonsterFactory;
import sample.mapElement.tower.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Is the arena of game, contain information about the current game state
 */
public class Arena {

	private static Arena INSTANCE = new Arena();
	private int money;
	private int turn;
	private ArrayList<Monster> monsters = new ArrayList<>();
	private ArrayList<Tower> towers = new ArrayList<>();
	private AnchorPane paneArena = null;
	private Stack<Shape> attackGraphic = new Stack<>();
	private final Coordinate endZone = new Coordinate(11, 0);

	private Arena() {
		money = 200;         //initial amount of resources (60)
		turn = 1;
	}

	public void setAnchorPane(AnchorPane anchorPane){
		this.paneArena = anchorPane;
	}

	public static Arena getInstance(){
		return INSTANCE;
	}

	public int getTurn() {
		return turn;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public int getMoney() {
		return money;
	}

	/**
	 * check weather the player lose the game or not
	 * @return True if lose, False if otherwise
	 */
	public boolean isGameOver() {
		for (Monster monster : monsters) {
			Coordinate coord = monster.getCurrentLocation();
			if ((coord.pixel_X > endZone.pixel_X - 20)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get the sample.mapElement.tower form a given green grid
	 * @param coord the coordinate of a grid
	 * @return the sample.mapElement.tower in given coordinate
	 */
	public Tower getTowerAt(Coordinate coord) {
		for(Tower oneTower: towers){
			if(oneTower.getCurrentLocation().equals(coord)){
				return oneTower;
			}
		}
		return null;
	}

	public boolean addTower(int x, int y, String type) {
		Tower newTower = TowerFactory.getInstance().getTower(x, y, type);
		if(money-newTower.getCost()<0){
			System.out.println("not enough resource to build the sample.mapElement.tower");
			return false;
		}
		money-=newTower.getCost();
		towers.add(newTower);
		return true;
	}

	/**
	 * Perform upgrade to the sample.mapElement.tower. A sample.mapElement.tower can only being upgraded for three times
	 * otherwise the game will be too easy.
	 * @param tower	the sample.mapElement.tower that will receive the upgrade
	 * @return true if update successful i.e. have enough resources to upgrade, False if otherwise
	 */
	public boolean upgradeTower(Tower tower){
		if (tower == null){
			throw new NullPointerException();
		}
		if(money < tower.getUpgradeCost()){
			System.out.println("not enough resource to upgrade " + tower.toString());
			return false;
		}
		System.out.println(tower.toString() + " is being upgraded");
		money -= tower.getUpgradeCost();
		tower.upgrade();
		return true;
	}

	private void removeDeadMonster(){
		for(int i=0; i<monsters.size(); i++){
			Monster m = monsters.get(i);
			if(m.isDead()){
				monsters.remove(i);
				i--;
			}
		}
	}

	private void moveEveryMonster(){
		for(Monster oneMonster: monsters){
			oneMonster.move();
		}
	}

	private int collectEarning(){
		int earning = 0;
		for(Monster oneMonster: monsters){
			if(oneMonster.isDead()){
				earning += oneMonster.getEarning();
			}
		}
		return earning;
	}

	private void everyTowerAttack(){
		for(Tower oneTower: towers){
			if(oneTower instanceof LaserTower){
				if(money - ((LaserTower)oneTower).getAttackCost() < 0){
					continue;
				}
			}
			if(oneTower.attackMonster(monsters)){
				if(oneTower instanceof LaserTower){
					//reduce money if laserTower shoot
					money -= ((LaserTower) oneTower).getAttackCost();
				}
				Line line = oneTower.getAttackLine();
				//draw Catapult attacked region
				if(oneTower instanceof Catapult){
					Circle dot = ((Catapult) oneTower).getInflectedAttackedArea();
					paneArena.getChildren().add(dot);
					attackGraphic.push(dot);
				}
				attackGraphic.push(line);
				paneArena.getChildren().add(line);
			}
		}
	}

	public void removeTower(Tower tower){
		for(int i=0; i<towers.size(); i++){
			if(towers.get(i) == tower){
				towers.remove(i);
				return;
			}
		}
	}

	public void clearAttackLine(){
		while(!attackGraphic.empty()){
			paneArena.getChildren().remove(attackGraphic.pop());
		}
	}

	public void nextRound(){
		clearAttackLine();
		removeDeadMonster();
		moveEveryMonster();
		if(isGameOver()){ return; }
		everyTowerAttack();

		//collect resources due to monster dead
		money += collectEarning();
		if(turn == 1 || turn % 3 == 0){
			monsters.add(MonsterFactory.getInstance().generateMonster(turn));
		}
		turn++;
	}
}
