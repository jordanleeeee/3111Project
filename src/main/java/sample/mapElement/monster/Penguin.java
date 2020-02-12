package sample.mapElement.monster;

import javafx.scene.image.Image;

public class Penguin extends Monster{

	private static final Image icon = new Image("file:src/main/resources/penguin.png", 30, 30, true, true);
	/**
	 * it store the total amount of the HP at the start time
	 */
	private int originalHP;

	public Penguin(int health, int earning, int speed) {
		super(health, earning, speed, icon);
		originalHP = health;

	}

	/**
	 * This is the function to move the Penguin
	 * Also gain some HP after each round
	 */
	public void move() {
		super.move();
		if(health+5 <= originalHP){
			health+=5;
		}
	}

	@Override
	public String toString() {
		return "Penguin";
	}

}
