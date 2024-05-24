/*
This is the component interface which is the base interface for all the classes in the composite pattern.
In this case, the component interface is the AirlineComponent interface.
 */
public interface AirlineComponent {
    void add(AirlineComponent component); //add a component to the composite
    void remove(AirlineComponent component);//remove a component from the composite
    void displayDetails();//display the details of the component
}