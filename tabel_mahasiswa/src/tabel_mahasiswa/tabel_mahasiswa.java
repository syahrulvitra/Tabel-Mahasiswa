package tabel_mahasiswa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tabel_mahasiswa {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mahasiswa";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);


    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertData();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateData();
                    break;
                case 4:
                    deleteData();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (IOException | NumberFormatException e) {
        }
    }

    static void showData() {
        String sql = "SELECT * FROM mahasiswa";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|         Data Mahasiswa|         ");
            System.out.println("+--------------------------------+");
            

            while (rs.next()) {
                System.out.println("id : "+rs.getInt("id"));
                System.out.println("Nama : "+rs.getString("nama"));
                System.out.println("Alamat : "+rs.getString("alamat"));
                System.out.println("  ");
            }

        } catch (SQLException e) {
        }

    }

    static void insertData() {
        try {
            System.out.print("id : ");
            String id = input.readLine().trim();
            System.out.print("Nama : ");
            String nama = input.readLine().trim();
            System.out.print("Alamat : ");
            String alamat = input.readLine().trim();
 
            String sql = "INSERT INTO mahasiswa (id, nama, alamat) VALUE('%s', '%s')";
            sql = String.format(sql, id, nama, alamat);

            boolean execute = stmt.execute(sql);
            
        } catch (IOException | SQLException e) {
        }

    }

    static void updateData() {
        try {
            
            System.out.print("ID yang mau di edit: ");
            int id = Integer.parseInt(input.readLine());
            System.out.print("Nama: ");
            String nama = input.readLine().trim();
            System.out.print("alamat: ");
            String alamat = input.readLine().trim();

            String sql = "UPDATE mahasiswa SET nama='%s', alamat='%s' WHERE id =%d";
            sql = String.format(sql, nama, alamat, id);

            stmt.execute(sql);
            
        } catch (IOException | NumberFormatException | SQLException e) {
        }
    }

    static void deleteData() {
        try {
            
            System.out.print("ID yang mau dihapus: ");
            int id = Integer.parseInt(input.readLine());
            
            String sql;
            sql = String.format("DELETE FROM mahasiswa WHERE id=%d", id);

            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
        } catch (IOException | NumberFormatException | SQLException e) {
        }
    }
}