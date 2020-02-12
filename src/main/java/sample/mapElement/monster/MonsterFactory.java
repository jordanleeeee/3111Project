package sample.mapElement.monster;

import java.util.Random;

public class MonsterFactory {

    private static MonsterFactory INSTANCE = new MonsterFactory();

    private MonsterFactory(){}

    public Monster generateMonster(int round){
        Random random = new Random();
        int typeOfMonster = 3;

        int randInt = random.nextInt(typeOfMonster);
        switch (randInt){
            case 0: return new Fox((int)(15+ round* 1.2) ,8,20);
            case 1: return new Penguin((int)(30+ round* 1.2),15,10);
            default: return new Unicorn((int)(50+ round* 1.2),10,15);
        }
    }

    public static MonsterFactory getInstance(){
        return INSTANCE;
    }
}
