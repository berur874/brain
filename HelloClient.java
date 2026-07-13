import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient{
    public static void main(String[] args) {
        try{
            // connect to the RMI registry on localhost, port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object by its registered name
            HelloInterface stub = (HelloInterface) registry.lookup("HelloService");

            // call the remote method - it feels like a local call
            String response = stub.sayHello("Alice");
            System.out.println("Server response: " + response);
        } catch(Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}