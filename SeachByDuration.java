import java.util.List;

public class SeachByDuration implements SearchStrategy{
    private static final int GAP = 1;// The GAP-hour difference
    /*
     This method searches for the components that have the duration that is around the given duration (within GAP-hour difference).
     */
    @Override
    public void search(List<Flight> flights, String keyword) {
        // Declare the searching to the user
        System.out.println("Searching for flights with duration around " + keyword + " hours (within "+GAP+"-hour difference)");
        // Parse the keyword to get the duration
        double duration = Double.parseDouble(keyword);// Assume that the keyword is a double
        // Search for the components that have the duration that is around the given duration (within 1-hour difference)
        for (Flight flight : flights) {
            if (flight.getDuration() >= duration - GAP && flight.getDuration() <= duration + GAP) {
                flight.displayDetails();
            }
        }
    }
    public static int getGAP() {
        return GAP;
    }
}
