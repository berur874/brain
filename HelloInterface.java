import java.rmi.Remote; /*  the remote interface serves to identify interfaces
whose methods may be invoked from a non-local virtual machine */
import java.rmi.RemoteException;
/* A RemoteException is the common superclass for a number of communication-
related exceptions that may occur during the execution of a remote method call. */

// Any interface that will be used remotely MUST extend Remote
public interface HelloInterface extends Remote{
    // All remote methods Must throw RemoteException
    public String sayHello(String name) throws RemoteException;
}