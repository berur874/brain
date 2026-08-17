import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class RentalServer extends UnicastRemoteObject implements ClientRMI{
    
    private Connection conn;
    private String DB_URL = "jdbc:mysql://localhost:3306/Cinema?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private String USER = "root";
    private String PASS = "";
    
    public RentalServer() throws RemoteException {
        super();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Database connected!");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
    
    @Override
    public List<String> getCustomers() throws RemoteException {
        List<String> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fname FROM Clients");
            while (rs.next()) list.add(rs.getString("fname"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<String> getMoviesByGenre(String genre) throws RemoteException {
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT title_name FROM Movies WHERE genre_id = ?");
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) list.add(rs.getString("title_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<String> getAllGenres() throws RemoteException {
        List<String> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT title_name FROM Movies");
            while (rs.next()) list.add(rs.getString("title_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public boolean rentMovie(String customer, String movie) throws RemoteException {
        try {
            // Check if already rented
            PreparedStatement check = conn.prepareStatement("SELECT id FROM Rentals WHERE movieID= ? AND status = 'BORROWED'");
            check.setString(1, movie);
            ResultSet rs = check.executeQuery();
            if (rs.next()) return false;
            
            // Rent it
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Rentals (clientID, movieID, returned) VALUES (?, ?, 'BORROWED')");
            pstmt.setString(1, customer);
            pstmt.setString(2, movie);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean returnMovie(String movie) throws RemoteException {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Rentals SET status = 'RETURNED', return_date = NOW() WHERE movieID = ? AND status = 'BORROWED'");
            pstmt.setString(1, movie);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<String> getBorrowedMovies() throws RemoteException {
        List<String> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT movieID FROM Rentals WHERE status = 'BORROWED'");
            while (rs.next()) list.add(rs.getString("movieID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<String> getReturnedMovies() throws RemoteException {
        List<String> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT movieID FROM Rentals WHERE status = 'RETURNED' ORDER BY return_date DESC LIMIT 10");
            while (rs.next()) list.add(rs.getString("movieID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args) {
        try {
            RentalServer server = new RentalServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("MovieRental", server);
            System.out.println("RMI Server ready on port 1099");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}