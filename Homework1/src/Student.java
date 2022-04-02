public class Student extends Person{
    private String status;
    public String toString(){
        return getName()+ " is of the Student class";
    }

    //Default constructor
    Student(){
        status= "status unknown";
    }

    //Overloaded constructor
    Student(String s){
        status=s;
    }

    //Getter and Setters for the private variables
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equalsIgnoreCase("freshman")||status.equalsIgnoreCase("sophomore")||status.equalsIgnoreCase("junior")||status.equalsIgnoreCase("senior")){
            this.status = status;
        }
        else{
            System.out.println("Invalid status for Student Class");
        }
    }
}
