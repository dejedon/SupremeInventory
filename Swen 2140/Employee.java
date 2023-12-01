import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;


public class Employee extends JFrame{
    private static final int BORDER = 12;  
    private static final int GAP = 5;  
    GBHelper pos = new GBHelper(); 
    JPanel listJPanel;

    JButton x = new JButton();
    JButton logout = new JButton("Logout");
    JTextField searchtf = new JTextField();
    private static ArrayList<String> sdata = new ArrayList<String>();
    private static ArrayList<String> edata = new ArrayList<String>();
    JFrame employ = new JFrame();
    JFrame addAcc = new JFrame();
    ImageDragAndDropApp pic = new ImageDragAndDropApp();

    

        

    public void employee() throws FileNotFoundException{  
        listEmployees();
        employ.setJMenuBar(menu());
        JScrollPane scrollPane = createCarButtonPanel();
        JPanel searchPanel = createSearchPanel();
        employ.setLayout(new BorderLayout());
        //Add the scrollPane, employeePanel, and searchPanel to the main frame
        employ.add(scrollPane, BorderLayout.CENTER);
        employ.add(searchPanel, BorderLayout.NORTH);
        //employ.setContentPane(employeemenu());
        employ.pack();
        employ.setSize(300,400);
        employ.setLocationRelativeTo(this);
        employ.setVisible(true);
        
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
            //employ.setSize(800,800);
            employ.pack();
            employ.repaint();
            //h.setVisible(true);

        }
    }

    public JScrollPane createCarButtonPanel() {
        listJPanel = new JPanel();
        listJPanel.setLayout(new BoxLayout(listJPanel, BoxLayout.Y_AXIS));

        // Add dynamically created buttons to the carButtonPanel
        ButtonListener buttonlistener = new ButtonListener();
        for(String employee: edata){
            Car vechicle = new Car(employee);
            x = new JButton(createResizedImageIcon(vechicle.getCarImage(),50,50));
            x.setText(vechicle.getCarName());
            x.addActionListener(buttonlistener);
            //x.setPreferredSize(new Dimension(100,100));
            x.setAlignmentX(Component.CENTER_ALIGNMENT);
            listJPanel.add(x);
        }

        JScrollPane scrollPane = new JScrollPane(listJPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        // Create a JTextField for searching
        JTextField searchTextField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the search text
                String searchText = searchTextField.getText().toLowerCase();

                // Filter and display only the matching button
                for (Component component : listJPanel.getComponents()) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        if (button.getText().toLowerCase().contains(searchText)) {
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                        }
                    }
                }
            }
        });

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        return searchPanel;
    }
    /*public JPanel employeemenu() throws FileNotFoundException{
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        JPanel listJPanel= new JPanel(new GridBagLayout());
        listJPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        
        ButtonListener buttonlistener = new ButtonListener();

        searchtf.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
                String name = searchtf.getText().toLowerCase();
                String[] data = name.split(" ");
                edata = employeeSearch(data[0], data[1], data[2]);
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


        JScrollPane asp = new JScrollPane(listJPanel);

        asp.setPreferredSize(new Dimension(300, 200));

        JLabel search = new JLabel("Search");
        
            //content.add(search, pos.nextCol());
            //content.add(searchtf, pos.nextCol().expandW().expandW());
            //content.add(new Gap(GAP), pos.nextCol());
            //content.add(cb, pos.nextCol());
            //content.add(asp, pos.nextRow().width());
            //content.setSize (400, 130);
            
        return content;
    }*/

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

    public ArrayList<String> employeeSearch(Object model, Object make, Object id){
        ArrayList<String> employeelist = new ArrayList<String>();
        for (String car : edata) {
            String[] carDetails = car.split(",");
            boolean match = (model == null || model.equals(carDetails[0])) &&
                            (make == null || make.equals(carDetails[1])) &&
                            (id == null || id.equals(carDetails[2]));

            if (match) {
                /*System.out.println("Car found: Model - " + carDetails[0] +
                        ", Make - " + carDetails[1] +
                        ", ID - " + carDetails[2]);*/
                        employeelist.add(car);
                
            }
            
        }
        return employeelist;
    }

    public JMenuBar menu() throws FileNotFoundException{
        JMenu menu; 
          JMenuItem i1, i2, i3, i4;  
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
                            try {
                                employ.setContentPane(addCar());
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
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
                            //JOptionPane.showMessageDialog(frame , newPanel, "Add account", JOptionPane.QUESTION_MESSAGE);
                            
                            int input = JOptionPane.showOptionDialog(frame, newPanel, "Add account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                            if(input == JOptionPane.OK_OPTION)
                            {
                                JFrame newframe = new JFrame();
                                String userx = textUsername.getText();
                                String passWx = fieldPassword.getText();
                                String passW2 = fieldPassword2.getText();
                                
                                if(passWx.equals(passW2)){
                                    boolean hasSC = passWx.matches(".*[^a-zA-Z0-9].*");
                                    boolean hasCL= passWx.matches(".*[A-Z].*");
    
                                    if(hasSC || hasCL){

                                        try {
                                            helper.addlogin(userx, passWx);
                                            } catch (FileNotFoundException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                        }
                                    }else{ JOptionPane.showMessageDialog(newframe , "Password contains no special characters. Try Again", 
                    "Error Message", JOptionPane.ERROR_MESSAGE);}
                                }else{ JOptionPane.showMessageDialog(newframe , "Password does not match. Try Again", 
                    "Error Message", JOptionPane.ERROR_MESSAGE);}
                            }
                        }
                    }else{ JOptionPane.showMessageDialog(addAcc, "User not permitted. Try Again", 
                    "Error Message", JOptionPane.ERROR_MESSAGE);}
                    
                
                }});
            i4=new JMenuItem("Generate Report"); 
            i4.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                genReport();
            }});
          menu.add(i1); menu.add(i2); menu.add(i3);menu.add(i4);
          mb.add(menu);  
        return mb;
    }

   


    


    private JPanel employeemenu(String name){
        Car car = new Car(name);
        JPanel mainPanel = new JPanel(new BorderLayout());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Resized Image, Text, and Large Text Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Load and resize the image from a local file
            ImageIcon icon = createResizedImageIcon(car.getCarImage(), 400, 400);

            // Create a JLabel to display the resized image
            JLabel imageLabel = new JLabel(icon);

            // Create a JPanel for the textLabel
            JTextArea textLabel = new JTextArea(car.getMake()+ "\n" + 
                                                car.getModel() + "\n" +
                                                car.getVin()+ "\n" +
                                                car.getID() + "\n" +
                                               car.getPrice());
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.add(textLabel);

            // Create a JTextArea for the large block of text
            JTextArea largeTextArea = new JTextArea(car.getDes());
            largeTextArea.setEditable(false); // Make the text area non-editable
            largeTextArea.setLineWrap(true); // Enable line wrapping

            // Create a JPanel to hold the resized image, text, and large text
            JPanel imageTextPanel = new JPanel(new BorderLayout());
            imageTextPanel.add(imageLabel, BorderLayout.WEST);
            imageTextPanel.add(textPanel, BorderLayout.CENTER);

            // Create a JComboBox for status
            JButton loanButton = new JButton("Loan Data");
            loanButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e){
                String loanAmountStr = car.getPrice();
                String outputPath = name +"_loan_payment.txt";
                Double annualInterestRate = 0.06;
                int loanTermYears = 5;
                double loanAmount = Double.parseDouble(loanAmountStr);
                double[] deposits = {0.10, 0.20, 0.30};
        
                try {
                    // Create a PrintWriter to write to the file
                    PrintWriter writer = new PrintWriter(new FileWriter(outputPath));
        
                    // Write the header
                    writer.println("Deposit\tTotal Amount Paid\tTotal Interest");
        
                    // Calculate and print loan payments for each deposit category
                    for (double deposit : deposits) {
                        double depositAmount = loanAmount * deposit;
                        double loanAmountAfterDeposit = loanAmount - depositAmount;
        
                        // Calculate monthly interest rate and number of payments
                        double monthlyInterestRate = annualInterestRate / 12;
                        int numberOfPayments = loanTermYears * 12;
        
                        // Calculate monthly payment using the formula for a fixed-rate mortgage
                        double monthlyPayment = (loanAmountAfterDeposit * monthlyInterestRate)
                                / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        
                        // Calculate total amount paid and total interest
                        double totalAmountPaid = monthlyPayment * numberOfPayments + depositAmount;
                        double totalInterest = totalAmountPaid - loanAmount;
        
                        // Print the results
                        writer.printf("%.0f%%\t$%.2f\t$%.2f\n", deposit * 100, totalAmountPaid, totalInterest);
                    }
        
                    // Close the writer
                    writer.close();
        
                    System.out.println("Results have been written to: " + outputPath);
        
                } catch (IOException error) {
                    error.printStackTrace();
                }
            

            }});

            // Create a JButton for "Sold"
            JButton soldButton = new JButton("Sold");

            soldButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent g){
                try {
                    car.archive_delete();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }     
            });
            

            // Create a JPanel to hold the JComboBox and JButton
            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            statusPanel.add(loanButton);

            // Create a JPanel to hold the JComboBox, JButton, and additional components
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(statusPanel, BorderLayout.WEST);
            bottomPanel.add(soldButton, BorderLayout.EAST);

            // Create a JPanel to hold the imageTextPanel and large block of text
            mainPanel.add(imageTextPanel, BorderLayout.NORTH);
            mainPanel.add(new JScrollPane(largeTextArea), BorderLayout.CENTER);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

            // Add the mainPanel to the frame
            frame.getContentPane().add(mainPanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
        return mainPanel;
    }

    private static ImageIcon createResizedImageIcon(String filePath, int width, int height) {
        try {
            File file = new File(filePath);
            Image originalImage = ImageIO.read(file);
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public JPanel addCar() throws FileNotFoundException{{}
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

    private String getSoldList(){
        return "sold.txt"; 
    }


    private void listSold()
    {
        try
        {
            sdata.clear();
            Scanner sscan = new Scanner(new File(getSoldList()));
            
            while (sscan.hasNext()){
                    sdata.add(sscan.next());
            }
            sscan.close();
        }
        catch(IOException e){}

    }

    public void genReport(){
        String filePath = "report.txt";
        System.out.println("NO FAILED HERE\n\n\n\n\n\n\n\n\n\n\n\n\n");
        listSold();
        System.out.println("NO FAILED HERE\n\n\n\n\n\n\n\n\n\n\n\n\n");

        try {
            // Create a BufferedWriter for the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            // Write header
            writer.write("Car Make\tCar Model\tCar VIN\tCar Price\tDate In\tDate Out\tDays Held");
            writer.newLine();

            System.out.println(sdata);

            // Write car information and calculate total profit
            double totalProfit = 0;
            for (String carInfo : sdata) {
                System.out.println("\n\n\n\n\n"+ carInfo+ "\n\n\n\n\n\n\n\n");
                System.out.println("NO FAILED HERE 1\n\n\n\n\n\n\n\n\n\n\n\n\n");
                Car car = new Car(carInfo);
                //String[] carParts = carInfo.split(":");
                System.out.println("\n\n\n\n\n"+ car.getMake() + "\n\n\n\n\n\n\n\n");
                
                String make = car.getMake();
                String model = car.getModel();
                String vin = car.getVin();
                System.out.println("\n\n\n\n\n"+ car.getPrice() + "\n\n\n\n\n\n\n\n");
                double price = Double.parseDouble(car.getPrice());
                System.out.println("NO FAILED HERE 2\n\n\n\n\n\n\n\n\n\n\n\n\n");
                // Assuming you want to write some placeholder values for date in, date out, and days held
                System.out.println("\n\n\n\n\n"+ car.getDatein() + "\n\n\n\n\n\n\n\n");
                String dateIn = car.getDatein();
                System.out.println("\n\n\n\n\n"+ car.getDateout() + "\n\n\n\n\n\n\n\n");
                String dateOut = car.getDateout();
                long daysHeld = car.getDaysBetween();
                
                
                // Write car information
                writer.write(make + "\t" + model + "\t" + vin + "\t$" + price + "\t" + dateIn + "\t" + dateOut + "\t" +
                        daysHeld);
                writer.newLine();
                System.out.println("NO FAILED HERE 3\n\n\n\n\n\n\n\n\n\n\n\n\n");

                totalProfit += price;
            }

            // Write total number of cars and total profit
            writer.newLine();
            writer.write("Total Cars: " + sdata.size());
            writer.newLine();
            writer.write("Total Profit: $" + totalProfit);

            // Close the BufferedWriter
            writer.close();

            System.out.println("Car information written to file successfully.");

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


}