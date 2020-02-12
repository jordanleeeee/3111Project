package sample.mapElement.monster;

import javafx.scene.image.Image;

/**
 * 
 *Unicorn Class for the monster Unicorn
 *
 */
public class Unicorn extends Monster {

	private static final Image icon = new Image("file:src/main/resources/unicorn.png", 30, 30, true, true);
	/**
	 * It is the constructor for Unicorn
	 * @param health monster hp
	 * @param earning monster reward
	 * @param speed monster speed
	 */
	public Unicorn(int health, int earning, int speed) {
		super(health, earning, speed, icon);
	}

	@Override
	public String toString() {
		return "Unicorn";
	}

}
