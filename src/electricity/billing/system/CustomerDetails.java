package electricity.billing.system;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class CustomerDetails extends JFrame implements ActionListener{
    
    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;
    
    CustomerDetails(){
        super("Podatci o potrošačima");
        
        setSize(1200, 600);
        setLocation(0, 0);
        
       
        
        table = new JTable();
        
        try{
        
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        JScrollPane sp = new JScrollPane(table);
        add(sp);
        
      
        
        print = new JButton("Isprintaj");
        print.addActionListener(this);
        add(print, "South");
        
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
   
            try{
                table.print();
            }catch(Exception e){
            
            e.printStackTrace();
            }
        }
        
        
    
    
    public static void main(String[] args){
        new CustomerDetails();
    }
}
