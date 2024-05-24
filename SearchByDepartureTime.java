import java.util.List;

public class SearchByDepartureTime implements SearchStrategy{
    @Override
    public void search(List<Flight> flights, String keyword) {
        // Declare the searching to the user
        System.out.println("Searching for flights with departure time equals to " + keyword);

        // Search for the components that have the departure time that is around the given time
        for (Flight flight : flights) {
            if (flight.getDepartureTime().equals(keyword)) {// Compare the departure time
                flight.displayDetails();
            }
        }
    }
}
