package Problem1;

import java.util.ArrayList;
import java.util.Random;

public class TestShape {
    public static void main(String[] args) {
        ArrayList<Shape> list = new ArrayList<Shape>();
        Shape a = new Square();
        Shape b = new Cube();
        Shape c = new Triangle();
        Shape d = new Pyramid();
        Shape [] seed = new Shape[] {a,b,c,d};
        for(int i = 0; i < 10; i++){
            // generate side lengths for each shape
            Random rnd = new Random();
            Shape temp = seed[i%4];
            temp.setSide(rnd.nextDouble()*10);
            list.add(temp);
        }

        for(Shape x: list){
            String temp = x.toString().substring(x.toString().indexOf('.')+1,x.toString().indexOf('@'));
            System.out.println("Area of " + temp +" : " + x.getArea());
        }
    }
}
