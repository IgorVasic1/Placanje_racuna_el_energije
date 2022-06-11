package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;


/**
 *
 * @author Igor
 */
public class CalculateBill extends JFrame implements ActionListener {
    JTextField tfname, tfstate, tfaddress, tfunits, tfemail, tfphone;
    JButton next, cancel;
    JLabel lblmeter, labeladdress;
    Choice meternumber, cmonth;
    CalculateBill(){
        setSize(700, 500);
        setLocation(300, 100);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(Color.WHITE);
        add(p);
        
        JLabel heading = new JLabel("Izračun potrošnje");
        heading.setBounds(100, 10, 200, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
       
        
        JLabel lblmeternumber = new JLabel("ID brojila");
        lblmeternumber.setBounds(100, 120, 100, 20);
        p.add(lblmeternumber);
       
        
        
        meternumber = new Choice();
        meternumber.setBounds(260, 120, 200, 20);
        p.add(meternumber);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            while(rs.next()){
                meternumber.add(rs.getString("meter_no"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        JLabel lblname = new JLabel("Ime");
        lblname.setBounds(260, 160, 150, 20);
        p.add(lblname);
        
        JLabel labelname = new JLabel("Ime");
        labelname.setBounds(100, 160, 150, 20);
        p.add(labelname);
        
        
        JLabel labeladdress = new JLabel("Adresa stanovanja");
        labeladdress.setBounds(260, 200, 150, 20);
        p.add(labeladdress);
        
         JLabel lbladdress = new JLabel("Adresa");
        lbladdress.setBounds(100, 200, 150, 20);
        p.add(lbladdress);
        
        
        
        
          try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meternumber.getSelectedItem()+"'");
            while(rs.next()){
                lblname.setText(rs.getString("name"));
                labeladdress.setText(rs.getString("address"));

            }
        } catch(Exception e){
            e.printStackTrace();
        }
          
        meternumber.addItemListener(new ItemListener(){
              public void itemStateChanged(ItemEvent ie){
              try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meternumber.getSelectedItem()+"'");
                while(rs.next()){
                    lblname.setText(rs.getString("name"));
                    labeladdress.setText(rs.getString("address"));

                }
               } catch(Exception e){
                    e.printStackTrace();
                }
              }
          });
        
        JLabel lblcity = new JLabel("Jedinična potrošnja");
        lblcity.setBounds(100, 240, 150, 20);
        p.add(lblcity);
        
        tfunits = new JTextField();
        tfunits.setBounds(260, 240, 200, 20);
        p.add(tfunits);
        
        JLabel lblstate = new JLabel("Mjesec");
        lblstate.setBounds(100, 280, 150, 20);
        p.add(lblstate);
        
        cmonth = new Choice();
        cmonth.setBounds(260, 280, 200, 20);
        cmonth.add("Siječanj");
        cmonth.add("Veljača");
        cmonth.add("Ožujak");
        cmonth.add("Travanj");
        cmonth.add("Svibanj");
        cmonth.add("Lipanj");
        cmonth.add("Srpanj");
        cmonth.add("Kolovoz");
        cmonth.add("Rujan");
        cmonth.add("Listopad");
        cmonth.add("Studeni");
        cmonth.add("Prosinac");
        p.add(cmonth);
        
        
        
        
        next = new JButton("Nastavi");
        next.setBounds(100, 350, 160, 20);
        next.addActionListener(this);
        p.add(next);
        
        
        cancel = new JButton("Otkaži");
        cancel.setBounds(300, 350, 160, 20);
        cancel.addActionListener(this);
        p.add(cancel);
        
        
        setLayout(new BorderLayout());
        add(p, "Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(190, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");
        
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae){
           if(ae.getSource() == next){
               String meter = meternumber.getSelectedItem();
               String units = tfunits.getText();
               String month = cmonth.getSelectedItem();
               
               int totalbill = 0;
               int unit_consumed = Integer.parseInt(units);
               
              
               String query = "select * from tax";
               
               try{
               
                   Conn c = new Conn();
                   ResultSet rs = c.s.executeQuery(query);
                   
                   
                   while(rs.next()){
                       totalbill += unit_consumed * Integer.parseInt(rs.getString("cost_per_unit"));
                       totalbill += Integer.parseInt(rs.getString("meter_rent"));
                       totalbill += Integer.parseInt(rs.getString("service_tax"));
                       totalbill += Integer.parseInt(rs.getString("swacch_bhart_cess"));
                       totalbill += Integer.parseInt(rs.getString("fixed_tax"));
                   }
               }catch(Exception e){
                   e.printStackTrace();
                   
               }
               String query2 = "insert into bill values('"+meter+"', '"+month+"', '"+units+"', '"+totalbill+"', 'Not Paid')";
               
               
               try{
                   Conn c = new Conn();
                   c.s.executeUpdate(query2);
                   
                   JOptionPane.showMessageDialog(null, "Račun uspješno izmjenjen");
                   setVisible(false);
               }catch(Exception e){
               e.printStackTrace();
               
               }
           }else{
               setVisible(false);
           }
    }
    
    public static void main(String[] args){
        new CalculateBill();
    }
    
}
