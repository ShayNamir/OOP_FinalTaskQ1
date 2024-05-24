public interface Observer {
    void update(String message);

    Object getID();

    String getName();
}
