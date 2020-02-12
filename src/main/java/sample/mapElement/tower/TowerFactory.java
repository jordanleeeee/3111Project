package sample.mapElement.tower;

public class TowerFactory {
    private static final TowerFactory INSTANCE = new TowerFactory();

    public Tower getTower(int x, int y, String type){
        switch (type){
            case "Basic Tower": return new BasicTower(x, y);
            case "Ice Tower": return new IceTower(x, y);
            case "Laser Tower": return new LaserTower(x, y);
            case "Catapult": return new Catapult(x, y);
        }
        throw new IllegalArgumentException();
    }

    private TowerFactory(){}

    public static TowerFactory getInstance(){
        return INSTANCE;
    }
}
