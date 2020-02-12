package sample.config;

public class Coordinate {
    //0< x < 13  && 0< y < 13
    //the coordinate of grid

    public double x;
    public double y;
    //convert grid to pixel
    public double pixel_X;
    public double pixel_Y;
    //only controller will use that info
    public double img_X;
    public double img_Y;


    /**
     * Construct an Coordinate object
     * @param x x coordinate of the grid
     * @param y y coordinate of the grid
     */
    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
        pixel_X = x*40 + 20;
        pixel_Y = y*40 + 20;
        img_X = x*40+5;
        img_Y = y*40+5;
    }

    public boolean isColumnThatMonsterCanMoveUp(){
        return (pixel_X>80&&pixel_X<120) || (pixel_X>240&&pixel_X<280) || (pixel_X>400&&pixel_X<440);
    }

    public boolean isColumnThatMonsterCanMoveDown(){
        return (pixel_X<40) || (pixel_X>160&&pixel_X<200) || (pixel_X>320&&pixel_X<360);
    }

    public double getDistanceDifferenceInPixel(Coordinate coord){
        return Math.sqrt(Math.pow((coord.pixel_X-pixel_X),2)+Math.pow((coord.pixel_Y-pixel_Y),2));
    }

    public Double getSlopeInPixel(Coordinate coord){
        return (coord.pixel_Y - pixel_Y) / (coord.pixel_X - pixel_X);
    }

    public double getDistanceBetweenEndZone(){
        return Math.sqrt(Math.pow((pixel_X - 440), 2) + Math.pow((pixel_Y - 0), 2));
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Coordinate){
            return (((Coordinate) obj).x == x && ((Coordinate) obj).y == y);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString(){
        return "<"+pixel_X+", "+pixel_Y+">";
    }
}
