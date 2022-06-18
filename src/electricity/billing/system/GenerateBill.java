package electricity.billing.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class GenerateBill extends JFrame implements ActionListener{
    JLabel l1, l2;
    JTextArea t1;
    JButton b1;
    Choice c2;
    JPanel p1;
    String meter;
    GenerateBill(String meter){
        this.meter = meter;

        setSize(500,700);
        setLayout(new BorderLayout());
        
        p1 = new JPanel();
        
        l1 = new JLabel("Generiraj račun");
        
        l2 = new JLabel(meter);
        c2 = new Choice();
        
        c2.add("Siječanj");
        c2.add("Veljača");
        c2.add("Ožujak");
        c2.add("Travanj");
        c2.add("Svibanj");
        c2.add("Lipanj");
        c2.add("Srpanj");
        c2.add("Kolovoz");
        c2.add("Rujan");
        c2.add("Listopad");
        c2.add("Studeni");
        c2.add("Prosinac");

        
        t1 = new JTextArea(50,15);
        t1.setText("\n\n\t------- klikni na  -------\n\tGeneriranje računa da biste dobili \n\tračun za odabrani mjesec\n\n");
        JScrollPane jsp = new JScrollPane(t1);
        t1.setFont(new Font("Senserif",Font.ITALIC,18));
        
        b1 = new JButton("Generate Bill");
        
        p1.add(l1);
        p1.add(l2);
        p1.add(c2);
        add(p1,"North");
        
        add(jsp,"Center");
        add(b1,"South");
        
        b1.addActionListener(this);
        
        setLocation(550,50);
    }
    public void actionPerformed(ActionEvent ae){
        try{
            Conn c = new Conn();
   
            String month = c2.getSelectedItem();
            t1.setText("\tReliance Power Limited\nELECTRICITY BILL FOR THE MONTH OF "+month+" ,2021\n\n\n");
            
            ResultSet rs = c.s.executeQuery("select * from customer where meter="+meter);
            
            if(rs.next()){
                t1.append("\n    Ime potrošača:"+rs.getString("name"));
                t1.append("\n    ID brojila:  "+rs.getString("meter"));
                t1.append("\n    Adresa Stanovanja:            "+rs.getString("address"));
                t1.append("\n    Država:                 "+rs.getString("state"));
                t1.append("\n    Grad:                   "+rs.getString("city"));
                t1.append("\n    Email adresa:                "+rs.getString("email"));
                t1.append("\n    Broj telefona:  "+rs.getString("phone"));
                t1.append("\n-------------------------------------------------------------");
                t1.append("\n");
            }
            
            rs = c.s.executeQuery("select * from meter_info where meter_number = " + meter);
            
            if(rs.next()){
                t1.append("\n    Lokacija brojila:"+rs.getString("meter_location"));
                t1.append("\n    Tip brojila:      "+rs.getString("meter_type"));
                t1.append("\n    Šifra osnovice:    "+rs.getString("phase_code"));
                t1.append("\n    Vrsta računa:         "+rs.getString("bill_type"));
                t1.append("\n    Broj dana:               "+rs.getString("days"));
                t1.append("\n");
            }
            rs = c.s.executeQuery("select * from tax");
            if(rs.next()){
                t1.append("---------------------------------------------------------------");
                t1.append("\n\n");
                t1.append("\n Cijena po jedinici:             Rs "+rs.getString("cost_per_unit"));
                t1.append("\n Marža:                Rs "+rs.getString("meter_rent"));
                t1.append("\n Naplata po usluzi:            Rs "+rs.getString("service_charge"));
                t1.append("\n Porez usluge:               Rs "+rs.getString("service_tax"));
                t1.append("\n Prirez:        Rs "+rs.getString("swacch_bharat_cess"));
                t1.append("\n Porez na račun:                 Rs "+rs.getString("fixed_tax"));
                t1.append("\n");
                
            }
            
            rs = c.s.executeQuery("select * from bill where meter="+meter+" AND month = '"+c2.getSelectedItem()+"'");
            
            if(rs.next()){
                t1.append("\n    Tekući mjesec :\t"+rs.getString("month"));
                t1.append("\n    Ukupna potrošnja po jedinici:\t"+rs.getString("units"));
                t1.append("\n    Ukupna naplata :\t"+rs.getString("total_bill"));
                t1.append("\n---------------------------------------------------------------");
                t1.append("\n    UKUPAN RAČUN :\t"+rs.getString("total_bill"));
            }
            
            
            
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new GenerateBill("").setVisible(true);
    }
}
