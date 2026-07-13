// implementing the server
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/* 
--> Registry is a remote interface to a simple remote object registry that
provides methods for storing and retriveing remote object refrences bound with
arbitrary names. 
--> The LocateRegistry class provides a programmatic API for constructing a 
bootstrap reference to a Registry at a remote address(getRegistry) and for creating
and exporting a Registry in the current VM on a particular local address(creatRegistry)
*/
import java.rmi.server.UnicastRemoteObject;

// The implementation class extends the UnicastRemoteObject
// This makes it available for remote calls
public class HelloServer extends UnicastRemoteObject implements HelloInterface {
    // construct a must throw RemoteException
    public HelloServer()  throws RemoteException {
        super();
    }
    // implement the remote method
    @Override
    public String sayHello(String name) throws RemoteException{
        return "Hello, " + name + "! welcome to RMI"; 
    }

    public static void main(String[] args) {
        try{
            // Create the remote object
            HelloServer server = new HelloServer();

            // Get the RMI registry(creates it on port 1099 if it does not exist)

            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind (register) the object with a name
            registry.rebind("HelloService", server);

            System.out.println("RMI Server is ready!");
            System.out.println("Service bound as 'HelloService'");
            System.out.println("Registry running on port 1099");

        } catch(Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
