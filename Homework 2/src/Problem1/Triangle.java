package Problem1;
import java.lang.Math;
public class Triangle extends Shape {
    private double side;

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getArea(){
        //Thanks Google, I would have forgotten this formula
        return .25*side*Math.pow(3,.5)*side;
    }
}
