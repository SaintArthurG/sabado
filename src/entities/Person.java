package entities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Person {
    private String name;
    private String birth;
    private String CEP;
    private String email;
    private String phone;
    private String password;

    public Person (){
    }

    public Person (String name, String birth, String CEP, String email, String phone, String password) {
        this.name = name;
        this.birth = birth;
        this.CEP = CEP;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            PreparedStatement ps = null;
            ps.setDate(3, new Date(sdf.parse(birth).getTime()));
            this.birth = birth;
        }
        catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }

}
