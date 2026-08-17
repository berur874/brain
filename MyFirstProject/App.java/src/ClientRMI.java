import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientRMI extends Remote {
    List<String> getCustomers() throws RemoteException;
    List<String> getMoviesByGenre(String genre) throws RemoteException;
    List<String> getAllGenres() throws RemoteException;
    boolean rentMovie(String customer, String movie) throws RemoteException;
    boolean returnMovie(String movie) throws RemoteException;
    List<String> getBorrowedMovies() throws RemoteException;
    List<String> getReturnedMovies() throws RemoteException;
}