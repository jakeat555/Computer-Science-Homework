public class Staff extends Employee {
    private String title;

    //Default constructor
    Staff(){
        title = "title unknown";
    }

    //Overloaded constructor
    Staff(String t){
        title=t;
    }

    public String toString(){
        return getName()+ " is of the Staff class";
    }

    //Getter and Setters for the private variables
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
