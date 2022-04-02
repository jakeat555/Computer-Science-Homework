public class Faculty extends Employee{
    private String officeHours;
    private String rank;

    //Default constructor
    Faculty(){
        officeHours = "office hours unknown";
        rank="rank unknown";
    }

    //Overloaded constructor
    Faculty(String o, String r){
        officeHours = o;
        rank=r;
    }

    //Getter and Setters for the private variables
    public String toString(){
        return getName()+ " is of the Faculty class";
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public String getRank() {
        return rank;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
