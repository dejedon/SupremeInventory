import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class Employee extends JFrame{
    private static final int BORDER = 12;  
    private static final int GAP    = 5;  
    GBHelper pos = new GBHelper(); 

    JButton x = new JButton();
    JButton logout = new JButton("Logout");
    JTextField searchtf = new JTextField();
    private static ArrayList<String> edata = new ArrayList<String>();
    JFrame employ = new JFrame(); // D/n: this might be the frame displayed for the employees 
                                  // if so it should be named 'employee_MainScreen'
    JFrame addAcc = new JFrame(); // D/n: might be the screen for making a new account. I don't know why
                                  // you need a whole new frame for that instead of just changing the panels
    ImageDragAndDropApp pic = new ImageDragAndDropApp(); // D/n: this is what i mean. instead of 'pic' just name
                                                         // it drag and drop.
    

        

    public void employee() throws FileNotFoundException{  
        listEmployees();
        employ = menu();
        employ.setContentPane(employeemenu());
        employ.pack();
        employ.setSize(300,400);
        employ.setLocationRelativeTo(this);
        employ.setVisible(true);
        employ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // D/n: Change Priority High
        
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ev){
            Object employee = ev.getSource();
            String name = employee.toString();
            String[] data = name.split("text");
            name = data[1];
            data = name.split(",");
            name = data[0];
            data = name.split("=");
            name = data[1];
            data = name.split(" ");
            data[2] = data[2].replaceAll("ID#", "");
            name = data[0]+":"+ data[1]+ ":" + data[2];

            System.out.println(name);
            
            employ.setContentPane(employeemenu(name));
            employ.setSize(800,800);
            employ.repaint();
            //h.setVisible(true);

        }
    }
    
    public JPanel employeemenu() throws FileNotFoundException{
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        JPanel listJPanel= new JPanel(new GridBagLayout());
        listJPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        JScrollPane asp = new JScrollPane(listJPanel);
        
        ButtonListener buttonlistener = new ButtonListener();

        searchtf.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
                System.out.println("This is triggered");
                String name = searchtf.getText().toLowerCase();
                String[] data = name.split(" ");
                edata = employeeSearch(data[0], data[1]);
                try {
                    employ.setContentPane(employeemenu());
                    employ.setVisible(true);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                
        }});

        for(String employee: edata){
            Car vechicle = new Car(employee);
            x = new JButton(new ImageIcon(vechicle.getCarImage()));
            x.setText(vechicle.getCarName());
            x.addActionListener(buttonlistener);
            //x.setPreferredSize(new Dimension(100,100));
            listJPanel.add(x, pos.nextRow().expandW());
        }

        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        asp.setSize(200, 70);

        String [] list = {" ","Add car", "Logout", "Add Account"};
        JComboBox<String> cb = new JComboBox<>(list);
        cb.setSize(3,3);

        asp.add(listJPanel);
        JLabel search = new JLabel("Search");
        
            content.add(search, pos.nextCol());
            content.add(searchtf, pos.nextCol().expandW().expandW());
            content.add(new Gap(GAP), pos.nextCol());
            //content.add(cb, pos.nextCol());
            content.add(listJPanel, pos.nextRow().width());
            content.setSize (400, 130);

            
        return content;
    }

    //=================================================================== accessor 
    private String getEmployeeList(){
        return "car.txt"; 
    }


    private void listEmployees()
    {
        try
        {
            edata.clear();
            Scanner sscan = new Scanner(new File(getEmployeeList()));
            
            while (sscan.hasNext()){
                    edata.add(sscan.next());
            }
            sscan.close();
        }
        catch(IOException e){}

    }

    public ArrayList<String> employeeSearch(Object first, Object last){
        ArrayList<String> employeelist = new ArrayList<String>();
        if((first == null) && (last == null)){
            listEmployees();
            return edata;
        }  

        if(first == null){ 
             for(String employee: edata){
                    Object[] data = employee.split(":");
                    if(last.equals(data[1])){
                        employeelist.add(employee);
                    }
                }
            return employeelist;
        }else if(last == null){
            for(String employee: edata){
                Object[] data = employee.split(":");
                if(first.equals(data[0])){
                   employeelist.add(employee);
                }
            }
            return employeelist;
        }else{
            for(String employee: edata){
                Object[] data = employee.split(":");
                if((first.equals(data[0])) &&(last.equals(data[1]))){
                   employeelist.add(employee);
                }
            }
            return employeelist;
        }
    } 

    public JFrame menu() throws FileNotFoundException{
        JMenu menu; 
          JMenuItem i1, i2, i3;  
          JFrame f= new JFrame("Supreme Wheels");  
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("Menu");    
          i1=new JMenuItem("Add Car");  
          i1.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                    LogInManager helper = new LogInManager();

                    JFrame frame = new JFrame();
                    JPanel panel = new JPanel(new BorderLayout(5, 5));
                    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                    label.add(new JLabel("Username", SwingConstants.RIGHT));
                    label.add(new JLabel("Password", SwingConstants.RIGHT));
                    panel.add(label, BorderLayout.WEST);
                
                    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                    JTextField username = new JTextField();
                    controls.add(username);
                    JPasswordField password = new JPasswordField();
                    controls.add(password);
                    panel.add(controls, BorderLayout.CENTER);
                
                    JOptionPane.showMessageDialog(frame, panel, "login", JOptionPane.QUESTION_MESSAGE);
                
                    String user = username.getText();
                    String passW = new String(password.getPassword());
                    if(helper.login(user, passW)){
                        if(helper.isOwner(user)){
                            employ.setContentPane(addCar());
                            employ.repaint();
                        }
                    }else{ JOptionPane.showMessageDialog(frame, "User not permitted. Try Again", 
                    "Error Message", JOptionPane.ERROR_MESSAGE);}
                    
                
                }});
          i2=new JMenuItem("Logout"); 
          i2.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                employ.dispose();
                BackgroundImageJFrame test = new BackgroundImageJFrame();
            }});  
            i3 = new JMenuItem("Add Account");
            i3.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                    LogInManager helper = new LogInManager();
                    JPanel panel = new JPanel(new BorderLayout(5, 5));
                    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                    label.add(new JLabel("Username", SwingConstants.RIGHT));
                    label.add(new JLabel("Password", SwingConstants.RIGHT));
                    panel.add(label, BorderLayout.WEST);
                
                    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                    JTextField username = new JTextField();
                    controls.add(username);
                    JPasswordField password = new JPasswordField();
                    controls.add(password);
                    panel.add(controls, BorderLayout.CENTER);
                
                    JOptionPane.showMessageDialog(addAcc, panel, "login", JOptionPane.PLAIN_MESSAGE);
                
                    String user = username.getText();
                    String passW = new String(password.getPassword());
                    if(helper.login(user, passW)){
                        if(helper.isOwner(user)){
                            JFrame frame = new JFrame();
                            JLabel labelUsername = new JLabel("Enter username: ");
                            JLabel labelPassword = new JLabel("Enter password: ");
                            JLabel labelPassword2 = new JLabel("Re-Enter password: ");
                            JTextField textUsername = new JTextField(20);
                            JTextField fieldPassword = new JTextField(20);
                            JTextField fieldPassword2 = new JTextField(20);
                            
                            JFrame here = new JFrame();
                            // create a new panel with GridBagLayout manager
                            JPanel newPanel = new JPanel(new GridBagLayout());
 
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
        
         
                            // set border for the panel
                            newPanel.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createEtchedBorder(), "Login Panel"));
         
                            // add the panel to this frame
                            JOptionPane.showMessageDialog(frame , newPanel, "Add account", JOptionPane.QUESTION_MESSAGE);

                            String userx = textUsername.getText();
                            String passWx = fieldPassword.getText();
                            String passW2 = fieldPassword2.getText();

                            if(passWx == passW2){
                                System.out.println(passW2);
                                System.out.println(passWx);
                            try {
                                helper.addlogin(userx, passWx);
                                } catch (FileNotFoundException e1) {
                                     // TODO Auto-generated catch block
                                    e1.printStackTrace();
                             }}
                            
                        }
                    }else{ JOptionPane.showMessageDialog(addAcc, "User not permitted. Try Again", 
                    "Error Message", JOptionPane.ERROR_MESSAGE);}
                    
                
                }});
            
          menu.add(i1); menu.add(i2); menu.add(i3);
          mb.add(menu);  
          f.setJMenuBar(mb);  
          f.setSize(400,400);  
          f.setLayout(null);    
        return f;
    }


    


    private JPanel employeemenu(String name){
        Car car = new Car(name);
        //JFrame e = new JFrame(car.getcarName());
        JComboBox<String> subselect = new JComboBox<>();
        JPanel carPanel = new JPanel(new GridBagLayout());
        carPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        JTextArea area = new JTextArea("Type here");
        ArrayList<String> subjects = car.readFile();
        String[] col = {"", ""};
        JComboBox<String> choise =  new JComboBox<>();
        JButton submit = new JButton("SUBMIT");

        submit.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent ev){
            String comment = area.getText();
            Object sub = choise.getSelectedItem();
            car.updateFile(sub.toString(), comment);
            employ.setContentPane(employeemenu(name));
            employ.setSize(800,800);
            employ.setVisible(true);
            }
        });

        JPanel subjectPanel = new JPanel();
        subjectPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(col);
        for(String subject: subjects){
            String[] data = subject.split(":");
            tableModel.addRow(data);
            choise.addItem(data[0]);
        }
        JTable table = new JTable (tableModel);
        table.setRowHeight ( 2 * table.getRowHeight()); // set the heights of the rows 
        table.setRowMargin (10); // sets spaces between the rows 
        JScrollPane jsp = new JScrollPane (table);
        
    

        carPanel.add(jsp, pos.width());
        carPanel.add(new Gap(GAP*10), pos.nextRow());
        carPanel.add(choise, pos.nextRow());
        carPanel.add(new Gap(GAP), pos.nextCol());
        carPanel.add(area, pos.nextCol());
        carPanel.add(new Gap(GAP), pos.nextRow());
        carPanel.add(submit, pos.nextCol().expandW());





        //e.setContentPane(carPanel);
        //e.setSize(800,800);
        //e.setVisible(true);Object JPanel
        return carPanel;
    }

    public JPanel addCar(){
        //JFrame pic = new ImageDragAndDropApp().ImageDragAndDropApp2();
        JLabel l1 =new JLabel("Vehicle make ");
        JLabel l2 =new JLabel("Vehicle model ");
        JLabel l3 =new JLabel("VIN # ");
        JLabel l4 =new JLabel("Vehicle Price ");
        JLabel l5 =new JLabel("Discription ");
        JTextField make = new JTextField();
        JTextField model = new JTextField();
        JTextField vin = new JTextField();
        JTextField price = new JTextField();
        JTextArea des = new JTextArea();
        JButton add = new JButton("ADD");
        add.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
        String test = pic.getLocation();
        String vhmake = make.getText();
        String vhmodel = model.getText();
        String vhvin = vin.getText();
        String vhprice = price.getText();
        String vhdes = des.getText();
        try {
            Car car = new Car(vhmake, vhmodel, vhvin, vhprice, vhdes, test);
            //employ.setContentPane(employeemenu(car.getPath()));
            //employ.repaint();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }});
        JButton upload = new JButton("UPLOAD PHOTO");
        upload.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
            pic.ImageDragAndDropApp2();
        //try {
            //car car = new car(fname, lname);
            //employ.setContentPane(employeemenu(car.getPath()));
            //employ.repaint();
        //} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        //}
        
    }});
    
        JPanel compose = new JPanel(new GridBagLayout());
        compose.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
        compose.add(l1, pos.nextRow());
        compose.add(make, pos.nextRow().expandW());
        compose.add(new Gap(GAP*5), pos.nextRow());
        compose.add(l2, pos.nextRow());
        compose.add(model, pos.nextRow().expandW());
        compose.add(new Gap(GAP*5), pos.nextRow());
        compose.add(l3, pos.nextRow());
        compose.add(vin, pos.nextRow().expandW());
        compose.add(new Gap(GAP*5), pos.nextRow());
        compose.add(l4, pos.nextRow());
        compose.add(price, pos.nextRow().expandW());
        compose.add(new Gap(GAP*5), pos.nextRow().nextRow());
        compose.add(l5, pos.nextRow());
        compose.add(des, pos.nextRow().expandW().expandH());
        compose.add(upload, pos.nextRow());
        compose.add(add, pos.nextRow().nextCol());
        
        return compose;

    }
}