package co.edu.unbosque.bluemarketbasedatos.services;

import co.edu.unbosque.bluemarketbasedatos.dtos.User;
import com.opencsv.bean.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                User user= new User(username,fullname,email,password,roll);
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

   /* public User createUser(String username, String name, String mail, String password,String roll, String path) throws IOException {

            String newLine = username + "," + name + "," + "," + mail + "," + password + "," + Fcoins + "\n";
        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        System.out.println("WEB-INF/classes/");
        os.write(newLine.getBytes());
        os.close();
        return new User(username, name, lastname, mail, password, Fcoins);
    }*/


}
