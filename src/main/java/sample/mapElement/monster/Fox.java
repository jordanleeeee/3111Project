package sample.mapElement.monster;

import javafx.scene.image.Image;

public class Fox extends Monster{

	private static final Image icon = new Image("file:src/main/resources/fox.png", 30, 30, true, true);

	public Fox(int health, int earning,int speed) {
		super(health, earning, speed, icon );
	}

	@Override
	public String toString() {
		return "Fox";
	}
}
