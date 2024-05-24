import java.util.ArrayList;
import java.util.List;

public class Airline implements AirlineComponent{
    private String name;
    private List<AirlineComponent> components;// Sub-companies and flights
    private List<Observer> observers;

    // Constructor
    public Airline(String name) {
        this.name = name;
        this.components = new ArrayList<>();//initialize the subCompanies list
        this.observers = new ArrayList<>();//initialize the list of observers
    }
    // Getters
    public String getName() {
        return this.name;
    }
    public List<AirlineComponent> getComponents() {
        return this.components;
    }
    public List<Observer> getObservers() {
        return this.observers;
    }
    public List<Airline> getSubCompanies() {
        List<Airline> subCompanies = new ArrayList<>();
        for (AirlineComponent component : this.components) {
            if (component instanceof Airline) {
                subCompanies.add((Airline)component);
            }
        }
        return subCompanies;
    }
    /*
    This function will return all the flights of the airline and the sub-companies of the airline.
     */
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        for (AirlineComponent component : this.components) {
            if (component instanceof Flight) {
                flights.add((Flight)component);
            } else {
                Airline subCompany = (Airline) component;
                flights.addAll(subCompany.getAllFlights());
            }
        }
        return flights;
    }
    // Observer pattern
    public void addObserver(Observer observer) {
        //Check if the observer is already in the list
        for (Observer obs : this.observers) {
            if (obs.getID().equals(observer.getID())) {
                System.out.println("You are already exists in the airline " + this.name+ " as an observer");
                return;
            }
        }
        System.out.println("You are now a follower of " + this.name + " airline");
        this.observers.add(observer);
    }
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
    public void notifyObservers(String message) {
        for (Observer observer : this.observers) {
            observer.update(message);
        }
    }
    public void createFlight(String flightNumber, double price, String departureTime, double duration) {
        //Check if the flight is already in the list
        for (AirlineComponent component : this.components) {
            if (component instanceof Flight) {
                Flight flight = (Flight) component;
                if (flight.getFlightNumber().equals(flightNumber)) {
                    System.out.println("Flight " + flightNumber + " already exists in the airline " + this.name);
                    return;
                }
            }
        }
        Flight flight = new Flight(flightNumber, price, departureTime, duration);
        this.components.add(flight);
        System.out.println("Flight: " + flightNumber + " was added to " + this.getName()+" AirLine");
    }

    // Search
    public void search(SearchStrategy strategy, String keyword) {
        //strategy.search(this.subCompanies, keyword);
    }
    @Override
    public void add(AirlineComponent component) {
        this.components.add(component);
    }

    @Override
    public void remove(AirlineComponent component) {
        this.components.remove(component);
    }

    @Override
    public void displayDetails() {
        sortComponents();//sort the components list
        //System.out.println("Airline: " + name);
        System.out.println("Sub-Companies and Flights of airline: " + name);
        //System.out.println("Number of components: " + this.components.size());
        for (AirlineComponent subC : this.components) {
            subC.displayDetails();
        }
    }
    /*
    This function will sort the components list: FIRST, the flights and THEN the sub-companies.
     */
    public void sortComponents() {
        List<AirlineComponent> flights = new ArrayList<>();
        List<AirlineComponent> subCompanies = new ArrayList<>();
        for (AirlineComponent component : this.components) {
            if (component instanceof Flight) {
                flights.add(component);
            } else {
                subCompanies.add(component);
            }
        }
        this.components.clear();
        this.components.addAll(flights);
        this.components.addAll(subCompanies);
    }
}
