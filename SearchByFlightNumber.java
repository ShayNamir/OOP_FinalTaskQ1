import java.util.List;

public class SearchByFlightNumber implements SearchStrategy{
    @Override
    public void search(List<Flight> flights, String keyword) {
        System.out.println("Searching for flights with flight number " + keyword);// Declare the searching to the user
        // Search for the components that have the flight number that is equal to the given flight number
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(keyword)) {// Compare the flight number
                flight.displayDetails();
            }
        }
    }
}
