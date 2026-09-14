import java.sql.*;
import java.util.Scanner;

public class AnimalManagementSystem {

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/animaldb",
            "root",
            "password"
        );

        while (true) {
            System.out.println("\n1.Add  2.View  3.Search  4.Delete  5.Exit");
            System.out.print("Choose: ");

            switch (sc.nextInt()) {
                case 1 -> addAnimal();
                case 2 -> viewAnimals();
                case 3 -> searchAnimal();
                case 4 -> deleteAnimal();
                case 5 -> System.exit(0);
            }
        }
    }

    static void addAnimal() throws Exception {
        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Type: ");
        String type = sc.next();

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO animals(name,type) VALUES(?,?)"
        );

        ps.setString(1, name);
        ps.setString(2, type);
        ps.executeUpdate();

        System.out.println("Animal added successfully");
    }

    static void viewAnimals() throws Exception {

        ResultSet rs = con.createStatement()
                .executeQuery("SELECT * FROM animals");

        while (rs.next()) {
            System.out.println(
                rs.getInt("id") + " | " +
                rs.getString("name") + " | " +
                rs.getString("type")
            );
        }
    }

    static void searchAnimal() throws Exception {

        System.out.print("Enter ID: ");

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM animals WHERE id=?"
        );

        ps.setInt(1, sc.nextInt());

        ResultSet rs = ps.executeQuery();

        if (rs.next())
            System.out.println(
                rs.getString("name") + " | " +
                rs.getString("type")
            );
        else
            System.out.println("Animal not found");
    }

    static void deleteAnimal() throws Exception {

        System.out.print("Enter ID: ");

        PreparedStatement ps = con.prepareStatement(
            "DELETE FROM animals WHERE id=?"
        );

        ps.setInt(1, sc.nextInt());
        ps.executeUpdate();

        System.out.println("Animal deleted");
    }
}