import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FlightManagmentSystem fms = FlightManagmentSystem.getInstance();
        fms.exampleInit();//Create a few Airlines: ElAl, Arkia, Israir and ElalBusiness as a sub-company of ElAl
        fms.welcomeInterface();//Welcome interface
    }
}
