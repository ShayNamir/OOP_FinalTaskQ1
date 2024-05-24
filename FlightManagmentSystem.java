import java.util.*;

/*
This class represents the flight management system. It contains a list of users (e.g., passengers, crew members, etc.)
 who can interact with the system.
This system can use one instance at the time. (Singleton pattern)
 */
public class FlightManagmentSystem {
    private List<User> users;
    private List<Airline> airlines;
    private static FlightManagmentSystem instance = null;

    // Constructor
    private FlightManagmentSystem() {
        this.users = new ArrayList<>();
        this.airlines = new ArrayList<>();
    }

    // Getters
    public List<User> getUsers() {
        return this.users;
    }
    public List<Airline> getAirlines() {
        return this.airlines;
    }
    public Airline getAirline(String name) {
        return this.getAirlineRec(this.airlines, name);
    }
    private Airline getAirlineRec(List<Airline> airL,String name) {
        for (Airline airline : airL) {
            if (airline.getName().equals(name)) {
                return airline;
            }
            Airline temp = getAirlineRec(airline.getSubCompanies(), name);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }
    // Singleton pattern
    public static FlightManagmentSystem getInstance() {
        if (instance == null) {
            instance = new FlightManagmentSystem();
        }
        return instance;
    }

    // Methods
    public User getUser(String passportID) {
        for (User user : users) {
            if (user.getID().equals(passportID)) {
                return user;
            }
        }
        return null;
    }
    public User registerUser(String passportID, String name, String password, Airline airline) {
        //Check if the user is already in the list
        for(User user : users){
            if(user.getID().equals(passportID)){
                System.out.println("User with ID " + passportID + " already exists");
                return null;//return null if the user already exists
            }
        }
        //If Airline is null, add the user as a passenger
        if (airline == null) {
            Passenger passenger = new Passenger(passportID, name, password);
            users.add(passenger);
            return passenger;
        } else {
            Employee employee = new Employee(passportID, name, password, airline);
            users.add(employee);
            return employee;
        }
    }
    /*
    This function creates a few Airlines for the example.
    ElAl, Arkia, Israir and ElalBusiness as a sub-company of ElAl
     */
    public void exampleInit() {
        Airline ElAl = new Airline("ElAl");
        Airline Arkia = new Airline("Arkia");
        Airline Israir = new Airline("Israir");
        Airline ElalBusiness = new Airline("ElalBusiness");
        ElAl.add(ElalBusiness);//add ElalBusiness as a sub-company of ElAl

        this.airlines.add(ElAl);
        this.airlines.add(Arkia);
        this.airlines.add(Israir);
    }
    //User Interface
    public void welcomeInterface() {
        System.out.println("Welcome to the Flight Management System");
        System.out.println("Please choose an option:");
        System.out.println("1. Register a new user");
        System.out.println("2. Login");
        System.out.println("in every stage you can type '9' to exit the system");
        //User input
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();//Assuming the user will enter a valid integer
        switch (option) {
            case 1:
                System.out.println("Please enter the following information:");
                System.out.println("Passport ID:");
                String passportID = scanner.next();
                //Check if the user is already in the list
                for(User user : users){
                    if(user.getID().equals(passportID)){
                        System.out.println("User with ID: " + passportID + " already exists");
                        welcomeInterface();
                        return;
                    }
                }
                System.out.println("Name:");
                String name = scanner.next();
                System.out.println("Password:");
                String password = scanner.next();
                System.out.println("Are you an employee? (y/n)");
                String isEmployee = scanner.next();
                if (isEmployee.equals("y")) {
                    System.out.println("Please enter the Airline name:");
                    String airlineName = scanner.next();
                    Airline airline = this.getAirline(airlineName);
                    if (airline == null) {
                        System.out.println("Airline " + airlineName + " does not exist");
                        welcomeInterface();
                        return;
                    }
                    System.out.println("Welcome to the system, " + name + " from " + airlineName + " airline");
                    this.employeeInterface((Employee)(this.registerUser(passportID, name, password, airline)));
                } else {
                    System.out.println("Welcome to the system, " + name);
                    this.passengersInterface((Passenger) this.registerUser(passportID, name, password, null));
                }
                break;
            case 2://Login
                System.out.println("ID:");
                String passportIDLogin = scanner.next();
                System.out.println("Password:");
                String passwordLogin = scanner.next();
                User user = this.getUser(passportIDLogin);
                if (user == null) {
                    System.out.println("User with ID " + passportIDLogin + " does not exist");
                    welcomeInterface();
                    return;
                }
                if (user.checkPassword(passwordLogin)) {
                    if (user instanceof Passenger) {//Passenger
                        System.out.println("Welcome to the system, " + user.getName());
                        this.passengersInterface((Passenger) user);

                    } else {//Employee
                        System.out.println("Welcome to the system, " + user.getName() + " from " + ((Employee) user).getAirline().getName() + " airline");
                        this.employeeInterface((Employee) user);

                    }
                } else {
                    System.out.println("Incorrect password");
                    welcomeInterface();
                    return;
                }
                break;
            case 9:
                System.out.println("Goodbye");
                System.exit(0);
            default:
                System.out.println("Invalid option");
                welcomeInterface();
                return;
        }
    }
    /*
    This function represents the interface for an employee.
     */
    private void employeeInterface(Employee employee) {
        System.out.println("Please choose an option:");
        System.out.println("1. Add a new flight");
        System.out.println("2. Edit an existing flight");
        System.out.println("3. Activate/DeActivate a flight");
        System.out.println("4. Display all flights");
        System.out.println("5. Send a notification about new deal to all AirLine followers");
        System.out.println("6. Register as a follower to an AirLine");
        System.out.println("7. Display all followers of this AirLine");
        System.out.println("8. Search for a flight in the airline (including sub-companies)");
        System.out.println("10. Logout");
        System.out.println("in every stage you can type '9' to exit the system");
        //User input
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();//Assuming the user will enter a valid integer
        switch (option) {
            case 1://Add a new flight
                System.out.println("Please enter the following information:");//Asuming the user will enter the correct information
                System.out.println("Flight number:");
                String flightNumber = scanner.next();
                System.out.println("Price:");
                double price = scanner.nextDouble();
                System.out.println("Departure time: In a format of hh:mm:");
                String departureTime = scanner.next();
                System.out.println("Duration: In hours (Format: 1.5 for 1 hour and 30 minutes)");
                double duration = scanner.nextDouble();
                employee.getAirline().createFlight(flightNumber, price, departureTime, duration);
                employeeInterface(employee);
                return;
            case 2://Edit an existing flight
                System.out.println("Please enter the flight number:");
                String flightNumberEdit = scanner.next();
                Flight flightEdit = null;
                for (AirlineComponent component : employee.getAirline().getAllFlights()) {
                    Flight temp = (Flight) component;
                    if (temp.getFlightNumber().equals(flightNumberEdit)) {
                        flightEdit = temp;
                        break;
                    }
                }
                if (flightEdit == null) {
                    System.out.println("Flight " + flightNumberEdit + " does not exist in your airline (including sub-companies)");
                    employeeInterface(employee);
                    return;
                }
                System.out.println("Please choose an option:");
                System.out.println("1. Edit price");
                System.out.println("2. Edit departure time");
                System.out.println("3. Edit duration");
                System.out.println("4. Return to the main employee menu");
                int optionEdit = scanner.nextInt();//Assuming the user will enter a valid integer
                switch (optionEdit) {
                    case 1:
                        System.out.println("Please enter the new price:");
                        double priceEdit = scanner.nextDouble();//Assuming the user will enter a valid double
                        flightEdit.setPrice(priceEdit);
                        System.out.println("Price of flight " + flightNumberEdit + " was updated");
                        //Notify all followers about the change
                        flightEdit.notifyObservers("Price of flight " + flightNumberEdit + " was updated to " + priceEdit + "$");
                        employeeInterface(employee);
                        return;
                    case 2:
                        System.out.println("Please enter the new departure time in a format of hh:mm:");//Assuming the user will enter a valid time
                        String departureTimeEdit = scanner.next();
                        flightEdit.setDepartureTime(departureTimeEdit);
                        System.out.println("Departure time of flight " + flightNumberEdit + " was updated to " + departureTimeEdit);
                        //Notify all followers about the change
                        flightEdit.notifyObservers("Departure time of flight " + flightNumberEdit + " was updated to " + departureTimeEdit);
                        employeeInterface(employee);
                        return;
                    case 3:
                        System.out.println("Please enter the new duration:");
                        double durationEdit = scanner.nextDouble();
                        flightEdit.setDuration(durationEdit);
                        System.out.println("Duration of flight " + flightNumberEdit + " was updated to " + durationEdit + " hours");
                        //Notify all followers about the change
                        flightEdit.notifyObservers("Duration of flight " + flightNumberEdit + " was updated to " + durationEdit + " hours");
                        employeeInterface(employee);
                        return;
                    case 4://Return to the main employee menu
                        employeeInterface(employee);
                        return;
                    default:
                        System.out.println("Invalid option");
                        employeeInterface(employee);
                        return;
                }
            case 3://Change activity of this flight
                System.out.println("Please enter the flight number:");
                String flightNumberRemove = scanner.next();
                Flight flightRemove = null;
                for (AirlineComponent component : employee.getAirline().getAllFlights()) {
                    Flight temp = (Flight) component;
                    if (temp.getFlightNumber().equals(flightNumberRemove)) {
                        flightRemove = temp;
                        break;
                    }
                }
                if (flightRemove == null) {
                    System.out.println("Flight " + flightNumberRemove + " does not exist");
                    employeeInterface(employee);
                    return;
                }
                String status = flightRemove.isActive() ? "active" : "not active";
                System.out.println("This flight is now " + status+" Do you want to change it? (y/n)");
                String change = scanner.next();
                if (change.equals("y")) {
                    flightRemove.setActive(!flightRemove.isActive());
                    status = flightRemove.isActive() ? "active" : "not active";
                    System.out.println("Flight " + flightNumberRemove + " is now " + status);
                    //Notify all followers about the change
                    flightRemove.notifyObservers("Flight " + flightNumberRemove + " is now " + status);
                }
                employeeInterface(employee);
                return;
            case 4://Display all flights
                System.out.println("All flights of " + employee.getAirline().getName() + " airline:");
                employee.getAirline().displayDetails();
                employeeInterface(employee);
                return;
            case 5://Send a notification about new deal to all AirLine followers
                System.out.println("Please enter the message:");
                String message = scanner.next();
                employee.getAirline().notifyObservers(message);
                System.out.println("Notification was sent to all followers of " + employee.getAirline().getName() + " airline");
                employeeInterface(employee);
                return;
            case 6://Register as a follower to an AirLine
                System.out.println("Please enter the Airline name:");
                String airlineName = scanner.next();
                Airline airline = this.getAirline(airlineName);
                if (airline == null) {
                    System.out.println("Airline " + airlineName + " does not exist");
                    employeeInterface(employee);
                    return;
                }
                airline.addObserver(employee);
                employeeInterface(employee);
                return;
            case 7://Display all followers of this AirLine
                System.out.println("All followers of " + employee.getAirline().getName() + " airline:");
                int i=1;
                for (Observer observer : employee.getAirline().getObservers()) {
                    System.out.println(i++ +": "+observer.getName());
                }
                System.out.println();
                employeeInterface(employee);
                return;
            case 8://Search for a flight in the airline (including sub-companies)
                System.out.println("Please choose a criteria:");
                System.out.println("1. Search by flight number");
                System.out.println("2. Search by price");
                System.out.println("3. Search by departure time");
                System.out.println("4. Search by duration");
                System.out.println("5. Return to the main employee menu");
                System.out.println("9. Exit");
                int optionSearch = scanner.nextInt();//Assuming the user will enter a valid integer
                SearchContext searchContext = new SearchContext(new SearchByFlightNumber());
                switch (optionSearch) {
                    case 1://Search by flight number
                        System.out.println("Please enter the flight number:");
                        String flightNumberSearch = scanner.next();
                        searchContext.performSearch(employee.getAirline().getAllFlights(), flightNumberSearch);
                        employeeInterface(employee);
                        return;
                    case 2://Search by price
                        System.out.println("Please enter the price:");
                        double priceSearch = scanner.nextDouble();
                        searchContext.setSearchStrategy(new SeachByPrice());
                        searchContext.performSearch(employee.getAirline().getAllFlights(), String.valueOf(priceSearch));
                        employeeInterface(employee);
                        return;
                    case 3://Search by departure time
                        System.out.println("Please enter the departure time in a format of hh:mm:");
                        String departureTimeSearch = scanner.next();
                        searchContext.setSearchStrategy(new SearchByDepartureTime());
                        searchContext.performSearch(employee.getAirline().getAllFlights(), departureTimeSearch);
                        employeeInterface(employee);
                        return;
                    case 4://Search by duration
                        System.out.println("Please enter the duration: (The search will be around this duration within "+SeachByDuration.getGAP()+ "-hour difference)");
                        double durationSearch = scanner.nextDouble();
                        searchContext.setSearchStrategy(new SeachByDuration());
                        searchContext.performSearch(employee.getAirline().getAllFlights(), String.valueOf(durationSearch));
                        employeeInterface(employee);
                        return;
                    case 5://Return to the main employee menu
                        employeeInterface(employee);
                        return;
                    case 9://Exit
                        System.out.println("Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option");
                        employeeInterface(employee);
                        return;
                }

            case 9://Exit
                System.out.println("Goodbye");
                System.exit(0);
            case 10://Logout
                System.out.println("Goodbye "+ employee.getName());
                welcomeInterface();
        }
    }
    private void passengersInterface(Passenger passenger){
        System.out.println("Please choose an option:");
        System.out.println("1. Book a flight");
        System.out.println("2. Display all my flights");
        System.out.println("3. Register as a follower to an AirLine");
        System.out.println("4. Search for a flight in the system");
        System.out.println("10. Logout");
        System.out.println("in every stage you can type '9' to exit the system");
        //User input
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();//Assuming the user will enter a valid integer
        switch (option) {
            case 1://Book a flight
                System.out.println("Please enter the flight number:");
                String flightNumber = scanner.next();
                Flight flight = null;
                for (Airline airline : this.getAirlines()) {
                    for (Flight temp : airline.getAllFlights()) {
                        if (temp.getFlightNumber().equals(flightNumber)) {
                            flight = temp;
                            break;
                        }
                    }
                }
                if (flight == null) {
                    System.out.println("Flight " + flightNumber + " does not exist");
                    passengersInterface(passenger);
                    return;

                }
                passenger.bookFlight(flight);
                // Ask the user if he wants to be notified about this flight (Changes, availability, etc.)
                System.out.println("Do you want to be notified about this flight? (y/n)");
                String notify = scanner.next();
                if (notify.equals("y")) {
                    flight.addObserver(passenger);
                }
                passengersInterface(passenger);
                return;
            case 2://Display all my flights
                System.out.println("All my flights:");
                Set<Flight> flights = new HashSet<>();
                for (Airline airline : this.getAirlines()) {
                    for (Flight temp : airline.getAllFlights()) {
                        if (temp.getPassengers().contains(passenger)) {
                            flights.add(temp);
                        }
                    }
                }
                for (Flight temp : flights) {
                    System.out.println(temp.getFlightNumber());
                }
                passengersInterface(passenger);
                return;
            case 3://Register as a follower to an AirLine
                System.out.println("Please enter the Airline name:");
                String airlineName = scanner.next();
                Airline airline = this.getAirline(airlineName);
                if (airline == null) {
                    System.out.println("Airline " + airlineName + " does not exist");
                    passengersInterface(passenger);
                    return;
                }
                airline.addObserver(passenger);//Register as a follower
                passengersInterface(passenger);
                return;
            case 4://Search for a flight in the system
                System.out.println("Please choose a criteria:");
                System.out.println("1. Search by flight number");
                System.out.println("2. Search by price");
                System.out.println("3. Search by departure time");
                System.out.println("4. Search by duration");
                System.out.println("5. Return to the main passenger menu");
                System.out.println("9. Exit");
                int optionSearch = scanner.nextInt();//Assuming the user will enter a valid integer
                SearchContext searchContext = new SearchContext(new SearchByFlightNumber());
                switch (optionSearch) {
                    case 1://Search by flight number
                        System.out.println("Please enter the flight number:");
                        String flightNumberSearch = scanner.next();
                        searchContext.performSearch(this.getAllFlightsInSystem(), flightNumberSearch);
                        passengersInterface(passenger);
                        return;
                    case 2://Search by price
                        System.out.println("Please enter the price:");
                        double priceSearch = scanner.nextDouble();
                        searchContext.setSearchStrategy(new SeachByPrice());
                        searchContext.performSearch(this.getAllFlightsInSystem(), String.valueOf(priceSearch));
                        passengersInterface(passenger);
                        return;
                    case 3://Search by departure time
                        System.out.println("Please enter the departure time in a format of hh:mm:");
                        String departureTimeSearch = scanner.next();
                        searchContext.setSearchStrategy(new SearchByDepartureTime());
                        searchContext.performSearch(this.getAllFlightsInSystem(), departureTimeSearch);
                        passengersInterface(passenger);
                        return;
                    case 4://Search by duration
                        System.out.println("Please enter the duration: (The search will be around this duration within "+SeachByDuration.getGAP()+ "-hour difference)");
                        double durationSearch = scanner.nextDouble();
                        searchContext.setSearchStrategy(new SeachByDuration());
                        searchContext.performSearch(this.getAllFlightsInSystem(), String.valueOf(durationSearch));
                        passengersInterface(passenger);
                        return;
                    case 5://Return to the main passenger menu
                        passengersInterface(passenger);
                        return;
                    case 9://Exit
                        System.out.println("Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option");
                        passengersInterface(passenger);
                        return;
                }
            case 10://Logout
                System.out.println("Goodbye "+ passenger.getName());
                welcomeInterface();
            case 9:// Exit
                System.out.println("Goodbye");
                System.exit(0);
        }
    }
    /*
    This function will return all flights of the system (Without duplicates) as a list
     */
    public List<Flight> getAllFlightsInSystem() {
        Set<Flight> flights = new HashSet<>();
        for (Airline airline : this.getAirlines()) {
            for (Flight temp : airline.getAllFlights()) {
                flights.add(temp);
            }
        }
        //Convert the Set to List and return it
        return new ArrayList<>(flights);
    }
}
