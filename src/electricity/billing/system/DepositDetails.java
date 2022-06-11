package electricity.billing.system;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class DepositDetails extends JFrame implements ActionListener{
    
    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;
    
    DepositDetails(){
        super("Podatci o uplati");
        
        setSize(700, 600);
        setLocation(300, 60);
        
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        JLabel lblmeternumber = new JLabel("Pretraži po ID brojila");
        lblmeternumber.setBounds(20, 20, 150, 20);
        add(lblmeternumber);
        
        meternumber = new Choice();
        meternumber.setBounds(180, 20,150,20);
        add(meternumber);
        
        
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            while(rs.next()){
                meternumber.add(rs.getString("meter_no"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        JLabel lblmonth = new JLabel("Pretraži po Mjesecu");
        lblmonth.setBounds(380, 20, 120, 20);
        add(lblmonth);
        
        cmonth = new Choice();
        cmonth.setBounds(520, 20, 150,20);
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
        add(cmonth);
        
        
        table = new JTable();
        
        try{
        
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bill");
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 100, 700, 600);
        add(sp);
        
        search = new JButton("Pretraži");
        search.setBounds(450, 70, 100, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Isprintaj");
        print.setBounds(570, 70, 100, 20);
        print.addActionListener(this);
        add(print);
        
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == search){
            String query = "select * from bill where meter_no = '"+meternumber.getSelectedItem()+"' and month = '"+cmonth.getSelectedItem()+"'";
            
            try{
            
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }catch(Exception e){
                e.printStackTrace();
            }
        
        
        }else {
            try{
                table.print();
            }catch(Exception e){
            
            e.printStackTrace();
            }
        }
        
        
    }
    
    public static void main(String[] args){
        new DepositDetails();
    }
}
