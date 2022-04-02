package Problem1;

public class Pyramid extends Shape{
    private double side;

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getArea(){
        // 4 triangle put together
        return side*Math.pow(3,.5)*side;
    }
}
