package co.edu.unbosque.bluemarketbasedatos.resources;


import co.edu.unbosque.bluemarketbasedatos.dtos.ExceptionMessage;
import co.edu.unbosque.bluemarketbasedatos.dtos.User;
import co.edu.unbosque.bluemarketbasedatos.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UsersResources {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/VAM";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "";
    @Context
    ServletContext context;
  /*  @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User> users = new UserService().getUsers();

            return Response.ok()
                    .entity(users)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }*/

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("name") String name,
            @FormParam("lastname") String lastname,
            @FormParam("mail") String mail
    ) throws SQLException {
        Connection con =null;
        System.out.println("Entró");
        User user=null;
        try {

            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            user = new UserService().createUser(username, name, lastname, mail, password, "0", con);
            System.out.println("creo el usuario");
            con.close();

        } catch (IOException e) {
            return Response.serverError().build();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(con!= null){
                con.close();
            }
        }
        return Response.created(UriBuilder.fromResource(UsersResources.class).path(username).build())
                .entity(user)
                .build();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username) throws SQLException {
        Connection con =null;
        List<User> users=new ArrayList<>();
        User user = null;
        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            users =  new UserService().getUsers(con);
            System.out.println("entró");
            user= users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

                con.close();
        } catch (IOException e) {
            return Response.serverError().build();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(con!=null){
                con.close();
            }

        }
        if (user != null) {
            System.out.println("lo hizo");
            System.out.println(user);
            return Response.ok()
                    .entity(user)
                    .build();
        } else {
            return Response.status(404)
                    .entity(new ExceptionMessage(404, "User not found"))
                    .build();
        }
    }
/*
    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm2(
            @FormParam("username") String username,
            @FormParam("fcoins") String fcoins

    ) {
        try {
            List<User> users = new UserService().getUsers();
            System.out.println("entró");
            User user = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                System.out.println("lo encontró");
                user.setFcoins(fcoins);
                System.out.println("lo actualizo");
                return Response.ok()
                        .entity(user)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }*/
}