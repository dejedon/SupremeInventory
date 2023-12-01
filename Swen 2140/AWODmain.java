import java.io.*;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;

import java.awt.*;
import java.awt.event.*;


public class AWODmain extends JFrame{


    public static void main(String[] args) {
        //... Set Look and Feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception unused) {}
        
        //... Start up GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BackgroundImageJFrame();
        }
        });
    } 
}

class BackgroundImageJFrame extends JFrame{

        LogInManager helper = new LogInManager();
        int x = 0;
        private JLabel labelUsername = new JLabel("Enter username: ");
        private JLabel labelPassword = new JLabel("Enter password: ");
        private JTextField textUsername = new JTextField(20);
        private JTextField fieldPassword = new JTextField(20);
        private JButton buttonLogin = new JButton("Login");
        JFrame login = SwingJPanelDemo();

        public BackgroundImageJFrame(){
            BackgroundImageJFrame2();
        }

        public void BackgroundImageJFrame2() {

            if(x == 0){
                setSize(800,800);
            

                setLayout(new BorderLayout());

                JLabel background=new JLabel(new ImageIcon("/Users/garybowen/Desktop/car.jpeg"));

                add(background);
                setVisible(true);
           

                background.setLayout(new BorderLayout());
                login.setLocationRelativeTo(this);
                login.setVisible(true);
                return;
            }
            login.dispose();
            setVisible(false);
            
        }
     
        public JFrame SwingJPanelDemo() {
        JFrame here = new JFrame();
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        buttonLogin.addActionListener(new ActionListener(){@Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String user = textUsername.getText();
            String passW = fieldPassword.getText();

            checklogin(user, passW);
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
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        newPanel.add(buttonLogin, constraints);
         
        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));
         
        // add the panel to this frame
        here.add(newPanel);
         
        here.pack();
        here.setLocationRelativeTo(null);
        return here;
    }

    private void checklogin(String user, String passW){
        JPanel showAdmin = new JPanel();
        if(helper.login(user, passW) == true){
            System.out.println(user);
            if(helper.isOwner(user)){
                Owner owner = new Owner();
                try {
                    owner.ownermenu();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
                x=1;
                BackgroundImageJFrame2();
                return;
            }if(helper.isParent(user) == true){
                Parent parent = new Parent();
                try {
                    parent.parentMenu();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
                x=1;
                BackgroundImageJFrame2();
                return;
            }
            else{
                Employee teacher = new Employee();
                try {
                    teacher.employee();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                x=1;
                BackgroundImageJFrame2();
                return;
            }

        }else{JOptionPane.showMessageDialog(showAdmin, "Incorrct Login. Try Again", 
        "Error Message", JOptionPane.ERROR_MESSAGE);}
    }

}

    
    