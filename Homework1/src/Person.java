public class Person {
    private String name;
    private String email;
    private String address;
    private String phoneNum;

    //Default constructor
    Person(){
        name = "Jane Doe";
        email = "example@example.net";
        address = "123 Main street";
        phoneNum = "555-555-1738";
    }

    //Overloaded constructor
    Person(String n, String e, String a, String p){
        name = n;
        email = e;
        address= a;
        phoneNum = p;
    }

    public String toString(){
        return name+ " is of the Person class";
    }

    //Getter and Setters for the private variables
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}