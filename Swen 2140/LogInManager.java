import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
//import javafx.geometry.Insets;
import java.awt.event.*;
import java.io.*;


//Login Manager consists of mostly logic code that deals with the retrival of files and passes them to the editors
public class LogInManager {

    public String getLogin(){
        return "Login.txt";
    }

    public boolean login(String username, String password){
        ArrayList<String> logindata = readFile();
        if(logindata.contains(username+ ":" + password)){
            return true;
        }
        return false;
    }

    private ArrayList<String> readFile()
    {
        ArrayList<String> logindata = new ArrayList<String>();
        try
        {
            Scanner fscan = new Scanner(new File(getLogin()));
            
            while (fscan.hasNext()){
                    logindata.add(fscan.next());
            }
            fscan.close();
            //System.out.println(fldata);
        }
        catch(IOException e){}
        return logindata;
    }
//modifier, adds new logins to the resivour
    public boolean addlogin(String username, String password) throws FileNotFoundException{
        ArrayList<String> logindata = readFile();
        if(logindata.contains(username+ ":" + password)){
            return false;
        }else{
            PrintStream loglist=new PrintStream(new FileOutputStream("Login.txt"));
            for(String login : logindata){
                loglist.println(login);
            }
            loglist.println(username+ ":" + password);
            loglist.close();
        }
        return false;
    }

    public boolean isOwner(String username){
        if(username.equals("Owner")){
            return true;
        }
        return false;
    }

    public boolean isParent(String username){
        if(username.equals("guest")){
            return true;
        }else{return false;}
    }


    public JPanel newAccount() {
        JLabel labelUsername = new JLabel("Enter username: ");
        JLabel labelPassword = new JLabel("Enter password: ");
        JLabel labelPassword2 = new JLabel("Re-Enter password: ");
        JTextField textUsername = new JTextField(20);
        JTextField fieldPassword = new JTextField(20);
        JTextField fieldPassword2 = new JTextField(20);
        JButton buttonLogin = new JButton("Login");
        JFrame here = new JFrame();
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        buttonLogin.addActionListener(new ActionListener(){@Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String user = textUsername.getText();
            String passW = fieldPassword.getText();
            String passW2 = fieldPassword2.getText();

            if(passW == passW2){
                try {
                    addlogin(user, passW);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }}
        }});
         
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        newPanel.add(labelUsername, constraints);
 
        constraints.gridx = 1;
        newPanel.add(textUsername, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(labelPassword, constraints);
         
        constraints.gridx = 1;
        newPanel.add(fieldPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(labelPassword2, constraints);
         
        constraints.gridx = 1;
        newPanel.add(fieldPassword2, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.EAST;
        //newPanel.add(buttonLogin, constraints);
         
        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));
         
        // add the panel to this frame
         
        
        return newPanel;
    }
}
