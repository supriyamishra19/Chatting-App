
package chatting.application;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server  implements ActionListener{
     JTextField text;
     JPanel a1;
      static  Box vertical = Box.createVerticalBox(); //its help to send message in vertical box like ek k niche ek.
      static JFrame f = new JFrame();
      static DataOutputStream dout;
    Server(){
        
        f.setLayout(null);//we ask swing that we use our own layout thats why i use null.
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84)); //here making color class object and rcb color no.
        p1.setBounds(0,0,450,65);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel (i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
           back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){ // use to click mouse for the exit
               System.exit(0);  //here we close our whole project
             }
          });
           
         ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT); //use for insert icon to the places
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel (i6);
        profile.setBounds(40,10,50,60);
        p1.add(profile);
        
        
        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png")); //use video icon
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel (i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        
        ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png")); //use phone icon
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel (i12);
        phone.setBounds(360,20,25,25);
        p1.add(phone);
        
        
        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png")); //use morevert icon
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel (i15);
        morevert.setBounds(420,20,10,25);
        p1.add(morevert);
        
        JLabel name = new JLabel("Supriya");
        name.setBounds(110,15,100,21);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        JLabel status= new JLabel("Online");
        status.setBounds(110,40,100,19);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        a1=new JPanel ();   
        a1.setBounds(5,55,440,570);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5,630,310,40);
        text.setFont(new Font("SAN_SARIF",Font.PLAIN,16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320,630,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SARIF",Font.PLAIN,16));
        f.add(send);
        
        
        f.setSize(450,700); //inside in the frame make the size of frame.     
        f. setLocation(100,0);//give the frame location to change its place.
        f.setUndecorated(true);//removing header space 
        f.getContentPane().setBackground(Color.WHITE);//give the color to the frame using of getcontentpane().
        
         f.setVisible(true);//for visibilty the frame we use setvisible and  make true for see the frame.
        }
    
    public void  actionPerformed(ActionEvent ae){//override for the action listener
        try{
         String out = text.getText();
      
         JPanel p2 = FormatLabel(out);
         
         
         a1.setLayout(new BorderLayout());//with the help of borderlayout we send message on line end
         
         JPanel right = new JPanel(new BorderLayout());
         right.add(p2, BorderLayout.LINE_END);
         vertical.add(right);//ek k niche ek message show krne k liye vertical ko add kiya .
         vertical.add(Box.createVerticalStrut(15));//give space 15 between the message.
         
         a1.add(vertical,BorderLayout.PAGE_START); //here we alian message  vertical .
         dout.writeUTF(out);
         
         text.setText(""); // use for empty text message.
         
         f.repaint();//its call the frame so we use repaint function.
         f.invalidate();//its also repaint function.
         f.validate();//calling function.
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
     public static JPanel FormatLabel(String out){
       JPanel panel = new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       
       JLabel output = new JLabel(out);
       output.setFont(new Font("Tahoma",Font.PLAIN,16));
       output.setBackground(new Color(37,211,102));
       output.setOpaque(true);//opaque is boolean function 
       output.setBorder(new EmptyBorder(15,15,15,50));
       
 
       panel.add(output);
       
       Calendar cal = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
       JLabel time = new JLabel();
       time.setText(sdf.format(cal.getTime()));
       panel.add(time);
       
       return panel;
       
     }
    public static void main(String[] args){
        new Server();
        
                try {
           ServerSocket skt = new ServerSocket(6001); 
           while(true){
              Socket s =  skt.accept();
              DataInputStream din = new DataInputStream(s.getInputStream());
               dout = new DataOutputStream(s.getOutputStream());
              
              while(true){
                  String msg =  din.readUTF();
                  JPanel panel = FormatLabel(msg);
                  
                  JPanel left = new JPanel(new BorderLayout());
                  left.add(panel,BorderLayout.LINE_START);
                  vertical.add(left);
                  f.validate();
              }
           }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    
}
