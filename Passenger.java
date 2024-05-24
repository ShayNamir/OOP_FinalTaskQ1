public class Passenger implements Observer, User{
    private String passportID;
    private String name;
    private String password;

    // Constructor
    public Passenger(String passportID, String name, String password) {
        this.passportID = passportID;
        this.name = name;
        this.password = password;
    }

    // Getters
    @Override
    public String getID() {
        return this.passportID;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // Methods
    public void bookFlight(Flight flight) {
        flight.addPassenger(this);
        System.out.println("Passenger " + this.name + " booked to flight " + flight.getFlightNumber());
    }

    @Override
    public void update(String message) {
        System.out.println("Passenger " + this.name + " received message: " + message);
    }
    //To use contains
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Passenger) {
            Passenger passenger = (Passenger) obj;
            return this.passportID.equals(passenger.passportID);
        }
        return false;
    }
}
