package co.edu.unbosque.bluemarketbasedatos.services;

import co.edu.unbosque.bluemarketbasedatos.dtos.User;
import com.opencsv.bean.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {


    public List<User> getUsers(Connection conn) throws IOException, SQLException {
        Statement stmt = null;
        List<User> users= new ArrayList<>();
        users = null;

        try {
            stmt= conn.createStatement();
            String sql = "SELECT * FROM userapp";
            ResultSet rs= stmt.executeQuery(sql);

            while (rs.next()){
             String username= rs.getString("id_user");
             String password= rs.getString("password");
             String email = rs.getString("email");
             String fullname = rs.getString("fullname");
             String roll = rs.getString("role");
             String fcoins = rs.getString("fcoins");
                User user= new User(username,fullname,email,password,roll,fcoins);
                users.add(user);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(stmt!=null){
                stmt.close();
            }

        }
        return users;
    }

    public void updateUser(User user, Connection conn) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        try {

            // Executing a SQL query
            System.out.println("=> Updating user...");
            stmt = conn.prepareStatement("UPDATE buyer SET fcoins = ? WHERE idNumber = ?");
            stmt.setString(1, user.getFcois());
            stmt.setString(2, user.getUsername());
            int rowsUpdated = stmt.executeUpdate(); // executeUpdate is also used for inserting records

            // Printing results
            System.out.println("Rows updated: " + rowsUpdated + "\n");

            // Closing resources
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public User createUser(String username,String password,String mail,String name, String rol,String fcoins, Connection conn) throws IOException, SQLException {
        PreparedStatement stmt= null;
        Statement stmt2=null;
        User user=new User();
        try {
            String sql= "INSERT INTO usuarios(id_user,password, email,fullname, role, fcoins)VALUES(?,?,?,?,?,?)";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, mail);
            stmt.setString(4, name);
            stmt.setString(5, rol);
            stmt.setString(6, fcoins);
            stmt.executeUpdate();

            String squ2= "SELECT * FROM userapp X WJERE x.usurios ="+username+"";
            stmt2 = conn.createStatement();
            ResultSet rs= stmt.executeQuery(squ2);
            rs.next();
             user.setUsername(rs.getString(username));
             user.setPassword(rs.getString(password));
             user.setMail(rs.getString(mail));
             user.setName(rs.getString(name));
             user.setRoll(rs.getString(rol));
             user.setFcois(rs.getString(fcoins));

             rs.close();
             stmt.close();
             stmt2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(stmt!=null ){
                stmt.close();
            }
            if( stmt2!=null){

                stmt2.close();
            }
        }
        return user;
    }
}
