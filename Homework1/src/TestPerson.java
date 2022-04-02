class TestPerson{
    public static void main(String[] args) {
        //Creating sample people to test that the default constructors get called in the right manner
        System.out.println("*********Testing default constructors********");
        Person p = new Person();
        Student q = new Student();
        Employee r = new Employee();
        Faculty s = new Faculty();
        Staff t = new Staff();
        System.out.println("p:" + p.toString() + " and lives at " + p.getAddress());
        System.out.println("q:" + q.toString() + " and has status: " + q.getStatus());
        System.out.println("r:" + r.toString() + " and has salary: $" + r.getSalary());
        System.out.println("s:" + s.toString() + " and has office hours: " + s.getOfficeHours());
        System.out.println("t:" + t.toString() + " and has title: " + t.getTitle());

        //Altering sample people to test the varoius setter, getter, toString and constructors of People class children
        System.out.println("\n*******Testing overloaded constructors*******");
        p = new Person("Andy Brim","andrew.brim@usu.edu"," 1600 Old Main Hill Logan, Utah 84322","435-797-0643");
        q = new Student("Sophomore");
        q.setName("Jacob Johns");
        r = new Employee("Old Main 401E",56000,new MyDate(10,10,2008));
        r.setName("Andy Brim");
        s = new Faculty("MW 11:30am-12:30pm","3");
        s.setName("Andy Brim");
        t = new Staff("Professor");
        t.setName("Andy Brim");
        System.out.println("p:" + p.toString() + " and lives at " + p.getAddress());
        System.out.println("q:" + q.toString() + " and has status: " + q.getStatus());
        System.out.println("r:" + r.toString() + " and has salary: $" + r.getSalary());
        System.out.println("s:" + s.toString() + " and has office hours: " + s.getOfficeHours());
        System.out.println("t:" + t.toString() + " and has title: " + t.getTitle());
    }
}