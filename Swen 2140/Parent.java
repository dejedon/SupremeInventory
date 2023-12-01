import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

//Containts both logical and pesistent code, creates a parent object to acess the system
public class Parent {
    private static final int BORDER = 12;  
    private static final int GAP    = 5;  
    GBHelper pos = new GBHelper(); 

    JButton x = new JButton();
    JButton logout = new JButton("Logout");
    JTextField searchtf = new JTextField();
    private static ArrayList<String> studentdata = new ArrayList<String>();
    JFrame teach = new JFrame();

        

    public void parentMenu() throws FileNotFoundException{  
        teach = menu();
        teach.setContentPane(parentmenu());
        //fly.setTitle("Flights avaiable");
        teach.pack();
        teach.setSize(300,400);
        //teach.setLocation(this);
        teach.setVisible(true);
        
    }

    // when a button is pressed it checks and formats the data so the operation can be carried out
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ev){
            Object student = ev.getSource();
            String name = student.toString();
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
            
            teach.setContentPane(studentmenu(name));
            teach.setSize(800,800);
            teach.repaint();
            //h.setVisible(true);

        }
    }
    //builds parent gui
    public JPanel parentmenu() throws FileNotFoundException{
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        JPanel listJPanel= new JPanel(new GridBagLayout());
        listJPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        JScrollPane asp = new JScrollPane(listJPanel);
        listStudents();
        
        ButtonListener buttonlistener = new ButtonListener();

        for(String student: studentdata){
            Car stud = new Car(student);
            x = new JButton(new ImageIcon(stud.getStudentImage()));
            x.setText(stud.getStudentName());
            x.addActionListener(buttonlistener);
            //x.setPreferredSize(new Dimension(100,100));
            listJPanel.add(x, pos.nextRow().expandW());
        }

        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        asp.setSize(200, 70);

        String [] list = {" ","Add Student", "Logout"};
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
    private String getStudentList(){
        return "students.txt"; 
    }


    private void listStudents()
    {
        try
        {
            //fldata.clear();
            Scanner sscan = new Scanner(new File(getStudentList()));
            
            while (sscan.hasNext()){
                    studentdata.add(sscan.next());
            }
            sscan.close();
            //System.out.println(fldata);
        }
        catch(IOException e){}

    }

    public ArrayList<String> StudentSearch(Object first, Object last){
        ArrayList<String> studentlist = new ArrayList<String>();
        if((first == null) && (last == null)){
            //return fldata;
        }  
        if(first == null){ 
             for(String student: studentdata){
                    Object[] data = student.split(":");
                    if(last.equals(data[1])){
                        studentlist.add(student);
                    }
                }
            return studentlist;
        }else if(last == null){
            for(String student: studentdata){
                Object[] data = student.split(":");
                if(first.equals(data[0])){
                   studentlist.add(student);
                }
            }
            return studentlist;
        }else{
            for(String student: studentdata){
                Object[] data = student.split(":");
                if((first.equals(data[0])) &&(last.equals(data[1]))){
                   studentlist.add(student);
                }
            }
            return studentlist;
        }
    } 
    //builds parents menu options on the jframe
    public JFrame menu() throws FileNotFoundException{
        JMenu menu; 
          JMenuItem i2;  
          JFrame f= new JFrame("Shady Grove");  
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("Menu");    
          i2=new JMenuItem("Logout"); 
          i2.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                /* log out gui call */}});   
          menu.add(i2);
          mb.add(menu);  
          f.setJMenuBar(mb);  
          f.setSize(400,400);  
          f.setLayout(null);    
        return f;
    }
    // builds gui for student data
    private JPanel studentmenu(String name){
        Car student = new Car(name);
        //JFrame e = new JFrame(student.getStudentName());
        JPanel studentPanel = new JPanel(new GridBagLayout());
        studentPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        ArrayList<String> subjects = student.readFile();
        String[] col = {"Subject", "Comments"};
        JComboBox<String> choise =  new JComboBox<>();


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
        
    

        studentPanel.add(jsp, pos.width());
        return studentPanel;
    }
    
}
