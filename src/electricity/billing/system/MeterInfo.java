package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;


/**
 *
 * @author Igor
 */
public class MeterInfo extends JFrame implements ActionListener {
    JTextField tfname, tfstate, tfaddress, tfcity, tfemail, tfphone;
    JButton next, cancel;
    JLabel lblmeter;
    Choice meterlocation, metertype, phasecode, billtype;
    String meternumber;
    MeterInfo(String meternumber){
        this.meternumber = meternumber;
        
        setSize(700, 500);
        setLocation(300, 100);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(Color.WHITE);
        add(p);
        
        JLabel heading = new JLabel("Informacije o brojilu");
        heading.setBounds(100, 10, 300, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
       
        
        JLabel lblname = new JLabel("ID brojila");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);
        
        JLabel lblmeternumber = new JLabel(meternumber);
        lblmeternumber.setBounds(240, 80, 100, 20);
        p.add(lblmeternumber);
        
        JLabel lblmeterno = new JLabel("Lokacija brojila");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);
        
        meterlocation = new Choice();
        meterlocation.setBounds(260, 120, 200, 20);
        meterlocation.add("Inozemstvo");
        meterlocation.add("Lokalno");
        p.add(meterlocation);
                
                
      
        
        JLabel lbladdress = new JLabel("Tip brojila");
        lbladdress.setBounds(100, 160, 150, 20);
        p.add(lbladdress);
        
        metertype = new Choice();
        metertype.setBounds(260, 160, 200, 20);
        metertype.add("Električno brojilo");
        metertype.add("Solarno brojilo");
        metertype.add("Pametno brojilo");
        p.add(metertype);
        
        
        
        JLabel lblcity = new JLabel("Izbor struje");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);
        
        phasecode = new Choice();
        phasecode.setBounds(260, 200, 200, 20);
        phasecode.add("011");
        phasecode.add("022");
        phasecode.add("033");
        phasecode.add("044");
        phasecode.add("055");
        phasecode.add("066");
        phasecode.add("077");
        phasecode.add("088");
        phasecode.add("099");
        p.add(phasecode);
        
        JLabel lblstate = new JLabel("Tip računa");
        lblstate.setBounds(100, 240, 150, 20);
        p.add(lblstate);
        
        billtype = new Choice();
        billtype.setBounds(260, 240, 200, 20);
        billtype.add("Normalna struja");
        billtype.add("Industrijska struja");
        p.add(billtype);
        
        JLabel lblemail = new JLabel("Dani u mjesecu");
        lblemail.setBounds(100, 280, 150, 20);
        p.add(lblemail);
        
        JLabel lblemails = new JLabel("30 dana");
        lblemails.setBounds(260, 280, 150, 20);
        p.add(lblemails);
        
        JLabel lblphone = new JLabel("Podsjetnik");
        lblphone.setBounds(100, 320, 150, 20);
        p.add(lblphone);
        
        JLabel lblphones = new JLabel("Račun se odnosi za period od 30 dana");
        lblphones.setBounds(260, 320, 350, 20);
        p.add(lblphones);
        
        
        next = new JButton("Potvrdi");
        next.setBounds(100, 390, 360, 25);
        next.addActionListener(this);
        p.add(next);
        
        
       
        
        
        setLayout(new BorderLayout());
        add(p, "Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(190, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");
        
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae){
           if(ae.getSource() == next){
               String meter = meternumber;
               String location = meterlocation.getSelectedItem();
               String type = metertype.getSelectedItem();
               String code = phasecode.getSelectedItem();
               String typebill = billtype.getSelectedItem();
               String days = "30";
               
               
               String query = "insert into meter_info values('"+meter+"', '"+location+"', '"+type+"', '"+code+"', '"+typebill+"', '"+days+"')";
              
               
               try{
               
                   Conn c = new Conn();
                   c.s.executeUpdate(query);
                   JOptionPane.showMessageDialog(null, "Podatci o brojilu dodani uspješno");
                   setVisible(false);
                   
                   //new frame
               
                   
               }catch(Exception e){
                   e.printStackTrace();
                   
               }
           
           }
           else{
               setVisible(false);
           }
    }
    
    public static void main(String[] args){
        new MeterInfo("");
    }
    
}
