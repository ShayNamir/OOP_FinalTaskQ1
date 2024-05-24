import java.util.List;
/*
This is the strategy interface which is used to define the search strategy.
 */
public interface SearchStrategy {
    void search(List<Flight> components, String keyword);
}
