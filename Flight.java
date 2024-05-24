import java.util.ArrayList;
import java.util.List;

public class Flight implements AirlineComponent{
    private final String flightNumber;
    private double price;
    private String departureTime;// Format: "HH:mm"
    private double duration;//duration of the flight in hours
    private boolean isActive;
    private List<Passenger> passengers;
    private List<Observer> observers;

    // Constructor
    public Flight(String flightNumber, double price, String departureTime, double duration) {
        this.flightNumber = flightNumber;
        this.price = price;
        this.departureTime = departureTime;
        this.duration = duration;
        this.isActive = true;
        this.passengers = new ArrayList<>();//initialize the list of passengers
        this.observers = new ArrayList<>();//initialize the list of observers
    }

    // Getters
    public String getFlightNumber() {
        return this.flightNumber;
    }
    public double getPrice() {
        return this.price;
    }
    public String getDepartureTime() {
        return this.departureTime;
    }
    public double getDuration() {
        return this.duration;
    }
    public List<Observer> getObservers() {
        return this.observers;
    }
    public boolean isActive() {
        return this.isActive;
    }
    public List<Passenger> getPassengers() {
        return this.passengers;
    }
    // Setters
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void addPassenger(Passenger passenger) {
        //Check if the passenger is already in the list
        if (!passengers.contains(passenger)) {//If the passenger is not in the list
            passengers.add(passenger);
        }
        else
            System.out.println("Passenger " + passenger.getName() + " is already in the flight " + flightNumber);
    }
    // Observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }


    @Override
    public void add(AirlineComponent component) {
        // Leaf node cannot have children, so this method is not applicable
        throw new UnsupportedOperationException("Cannot add to a flight.");
    }

    @Override
    public void remove(AirlineComponent component) {

        // Leaf node cannot have children, so this method is not applicable
        throw new UnsupportedOperationException("Cannot remove from a flight.");
    }

    @Override
    public void displayDetails() {
        // Print the details of the flight
        System.out.println("Flight Number: " + flightNumber+", Price: " + price + "$, Departure Time: " + departureTime + ", Duration: " + duration + " hours");
    }
}
