/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp1dpbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sekar
 */
public class dbConnection {
    public static Connection conn;
    public static Statement stmt;
    
    public void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/tp1_dpbo";
            String user = "root";
            String pass = "";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
            System.out.println("koneksi berhasil");
        } catch (Exception e) {
            System.err.println("koneksi gagal " + e.getMessage());
        }
    }
    
    public DefaultTableModel readTable() {
        DefaultTableModel dataTabel = null;
        try {
            Object[] column = {"No", "Nama", "Jumlah Buku"};
            connect();
            dataTabel = new DefaultTableModel(null, column);
            String sql = "SELECT author_nama, author_jumlah_buku FROM author";
            ResultSet res = stmt.executeQuery(sql);
            
            int no = 1;
            while(res.next()) {
                Object[] hasil = new Object[3];
                hasil[0] = no;
                hasil[1] = res.getString("author_nama");
                hasil[2] = res.getString("author_jumlah_buku");
                no++;
                System.out.println(hasil[1]);
                dataTabel.addRow(hasil);
            }
        } catch (Exception e) {
            System.err.println("Read gagal " + e.getMessage());
        }
        
        return dataTabel;
    }
    
    public DefaultTableModel readTable(Object[] theads, Object[] columns, String q) {
        DefaultTableModel dataTabel = null;
        try {
            Object[] column = new Object[theads.length];
            for(int i = 0; i < theads.length; i++) {
                column[i] = theads[i].toString();
            }
            
            connect();
            dataTabel = new DefaultTableModel(null, column);
            String sql = q;
            ResultSet res = stmt.executeQuery(sql);
            
            int no = 1;
            while(res.next()) {
                Object[] hasil = new Object[theads.length];
                hasil[0] = no;
                for(int i = 1; i < columns.length; i++) {
                    hasil[i] = res.getString(columns[i].toString());
                }
                no++;
                System.out.println(hasil[1]);
                dataTabel.addRow(hasil);
            }
        } catch (Exception e) {
            System.err.println("Read gagal " + e.getMessage());
        }
        return dataTabel;
    }
    
    public void Query(String inputan) {
        try {
            connect();
            String sql = inputan;
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Read gagal " + e.getMessage());
        }
    }
    
    public ImageIcon showImage(String q, String column) {
        ImageIcon formate = null;
        try {
            connect();
            String sql = q;
            ResultSet res = stmt.executeQuery(sql);
            
            while(res.next()) {
                byte[] imageData = res.getBytes(column);
                formate = new ImageIcon(imageData);
                System.out.println(formate);
            }
        } catch (Exception e) {
            System.err.println("Read gagal " + e.getMessage());
        }
        return formate;
    }
}
