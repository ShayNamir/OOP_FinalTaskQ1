public class Employee implements Observer, User{
    private String ID;
    private String name;
    private String password;
    private Airline airline;

    // Constructor
    public Employee(String ID, String name, String password, Airline airline) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.airline = airline;
    }

    // Getters
    @Override
    public String getID() {
        return this.ID;
    }
    @Override
    public String getName() {
        return this.name;
    }
    public Airline getAirline() {
        return this.airline;
    }
    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    // Methods

    //Observer pattern
    @Override
    public void update(String message) {
        System.out.println("Employee " + this.name + " received message: " + message);
    }
}
