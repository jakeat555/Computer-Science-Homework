public class MyDate {
    private int day;
    private int month;
    private int year;

    //Default constructor
    MyDate() {
        day = 0;
        month = 0;
        year = 0;
    }

    //Overloaded constructor
    MyDate(int d, int m, int y) {
        day = d;
        month = m;
        year = y;
    }

    //Getter and Setters for the private variables
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
