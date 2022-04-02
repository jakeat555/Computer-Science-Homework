
public class Employee extends Person {
    private String office;
    private double salary;
    private MyDate hireDate;

    //Default constructor
    Employee(){
        office = "unknown office";
        salary = -1;
        hireDate = new MyDate(0,0,0);
    }

    //Overloaded constructor
    Employee(String o, double s, MyDate d){
        office=o;
        salary=s;
        hireDate=d;
    }

    //Getter and Setters for the private variables
    public String toString() {
        return getName() + " is of the Employee class";
    }

    public String getOffice() {
        return office;
    }

    public double getSalary() {
        return salary;
    }

    public MyDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(MyDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
