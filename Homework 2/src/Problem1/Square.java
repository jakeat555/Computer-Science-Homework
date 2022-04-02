package Problem1;

public class Square extends Shape {
    private double side;

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getArea(){
        return side*side;
    }
}
