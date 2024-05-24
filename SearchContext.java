import java.util.List;
/*
This is the context class which is used to store the search context.
 */
public class SearchContext {
    private SearchStrategy strategy;

    public SearchContext(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void setSearchStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void performSearch(List<Flight> components, String keyword) {
        this.strategy.search(components, keyword);
    }
}
