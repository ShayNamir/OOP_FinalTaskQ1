import java.util.List;

public class SeachByPrice implements SearchStrategy{
    private static boolean flag=true;
    @Override
    public void search(List<Flight> components, String keyword) {
        // Declare the searching to the user
        if(flag){
            System.out.println("Searching for flights with price less than or equal to " + keyword+"$");
            flag=false;
        }
        //Convert the keyword to a double
        double price = Double.parseDouble(keyword);//Assuming the keyword is a valid double

        for (Flight flight : components) {
            if (flight.getPrice() <= price) {
                flight.displayDetails();
            }
        }
    }
}
