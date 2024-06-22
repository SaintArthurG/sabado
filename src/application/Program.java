package application;

import db.DB;
import entities.Person;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Person person = new Person();

        System.out.print("CHOOSE A OPTION \n1. INSERT \n2. CHECK \n3. UPDATE \n4. DELETE \n5. EXIT\n");
        int asw = sc.nextInt();
        sc.nextLine();

        try (Connection conn = DB.getConnection(); Statement st = conn.createStatement()) {
            switch (asw) {
                case 1:
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO person (Name, Birth, CEP , Email, Phone, Password)" + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    person.setName(name);
                    ps.setString(1, person.getName());

                    System.out.print("Birth date: (dd/MM/yyyy): ");
                    String date = sc.nextLine();
                    person.setBirth(date);
                    ps.setString(2, person.getBirth());

                    System.out.print("CEP: ");
                    String CEP = sc.nextLine();
                    person.setCEP(CEP);
                    ps.setString(3, person.getCEP());

                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    person.setEmail(email);
                    ps.setString(4, person.getEmail());


                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    person.setPhone(phone);
                    ps.setString(5, person.getPhone());

                    System.out.print("Password: ");
                    String pass = sc.nextLine();
                    String passHex = "";
                    try {
                        MessageDigest alg = MessageDigest.getInstance("SHA-256");
                        byte messageDigestPass[] = alg.digest(pass.getBytes(("UTF-8")));
                        StringBuilder hextPass = new StringBuilder();
                        for (byte b : messageDigestPass) {
                            hextPass.append(String.format("%02X", 0xFF & b));
                        }
                        passHex = hextPass.toString();
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    person.setPassword(passHex);
                    ps.setString(6, person.getPassword());

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet rs = ps.getGeneratedKeys();
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            System.out.println("ID: " + id);
                        }
                    } else {
                        System.out.println("NO ROWS AFFECTED!");
                    }
                    break;

                case 2:
                    ResultSet rs = st.executeQuery("SELECT * FROM person");
                    while (rs.next()) {
                        System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
                    }
                    break;
                case 3:
                    PreparedStatement ps1 = conn.prepareStatement(
                            "UPDATE person "
                                    + " SET Phone = ? "
                                    + " WHERE "
                                    + " (Id = ?)"
                    );
                    System.out.print("New Phone: ");
                    ps1.setString(1, sc.next());
                    System.out.print("Witch Person? (ID)");
                    ps1.setString(2, sc.next());
                    int rowsAffected1 = ps1.executeUpdate();
                    System.out.println("DONE! Rows Affected: " + rowsAffected1);
                    break;

                case 4:
                    PreparedStatement ps2 = conn.prepareStatement(
                            "DELETE FROM person "
                                    + " WHERE "
                                    + " Id = ? "
                    );
                    System.out.print("DELETE Person? (ID): ");
                    ps2.setString(1, sc.next());
                    int rowsAffected2 = ps2.executeUpdate();
                    System.out.println("DONE! Rows Affected: " + rowsAffected2);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

