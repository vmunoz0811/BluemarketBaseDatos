package co.edu.unbosque.bluemarketbasedatos.dtos;

import com.opencsv.bean.CsvBindByName;

public class User {

    @CsvBindByName
    private String name;
    @CsvBindByName
    private String mail;

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String password;

    @CsvBindByName
    private String roll;
    @CsvBindByName
    private String fcois;



    public User() {
    }

    public User(String username, String name, String mail, String password, String roll, String fcoins) {
        this.name = name;
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.roll = roll;
        this.fcois= fcoins;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getFcois() {
        return fcois;
    }

    public void setFcois(String fcois) {
        this.fcois = fcois;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roll='" + roll + '\'' +
                ", fcois='" + fcois + '\'' +
                '}';
    }
}
