import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
 
class Owner extends Employee {
  private static final int BORDER = 12;  
    private static final int GAP    = 5;
    private static ArrayList<String> cardata = new ArrayList<String>();


    public void ownermenu() throws FileNotFoundException{
          listCars();
          JPanel prinpanel = employeemenu();
          JMenu menu;
          JMenuItem i1, i2, i3;  
          JFrame f= new JFrame(" ");  
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("Menu");    
          i1=new JMenuItem("Add Car");  
          i1.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                //f.setContentPane(addCar());
                f.repaint();
              }});
          i2=new JMenuItem("Logout"); 
          i2.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
            /* log out gui call */}});   
          i3= new JMenuItem("Archive/ Delete");
          i3.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ev){
                try {
                  f.setContentPane(archivemenu());
                  f.repaint();
                } catch (FileNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }}});  
          menu.add(i1); menu.add(i2); menu.add(i3);
          mb.add(menu);  
          f.setJMenuBar(mb);  
          f.setSize(400,400);  
          f.setLayout(null);
          f.setContentPane(employeemenu());
          f.setVisible(true);


        //return f;

    }


    private class ButtonListener implements ActionListener
    {
     @Override
      public void actionPerformed(ActionEvent ev){
            Object car = ev.getSource();
            String name = car.toString();
            String[] data = name.split("text");
            name = data[1];
            data = name.split(",");
            name = data[0];
            data = name.split("=");
            name = data[1];
            data = name.split(" ");
            data[2] = data[2].replaceAll("ID#", "");
            name = data[0]+":"+ data[1]+ ":" + data[2];
            
            Car car2 = new Car(name);
            try {
              JPanel frame = new JPanel();
              JButton delete = new JButton ("delete");
              frame.add(delete);
              JOptionPane.showMessageDialog(frame, "delete", name, JOptionPane.QUESTION_MESSAGE);
              car2.archive_delete();
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            //employ.setContentPane(carmenu(name));
            employ.setSize(800,800);
            employ.repaint();
            //h.setVisible(true);

        }
    }
    
    public JPanel archivemenu() throws FileNotFoundException{
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
        BORDER, BORDER));
        JPanel listJPanel= new JPanel(new GridBagLayout());
        listJPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        JScrollPane asp = new JScrollPane(listJPanel);
        listCars();
        
        ButtonListener buttonlistener = new ButtonListener();

        for(String car: cardata){
            Car vehicle = new Car(car);
            x = new JButton(new ImageIcon(vehicle.getCarImage()));
            x.setText(vehicle.getCarName());
            x.addActionListener(buttonlistener);
            //x.setPreferredSize(new Dimension(100,100));
            listJPanel.add(x, pos.nextRow().expandW());
        }

        content.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 
                    BORDER, BORDER));

        asp.setSize(200, 70);

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

    private String getCarList(){
      return "car.txt"; 
  }


  private void listCars()
  {
      try
      {
          
          Scanner sscan = new Scanner(new File(getCarList()));
          
          while (sscan.hasNext()){
                  cardata.add(sscan.next());
          }
          sscan.close();
          //System.out.println(cardata);
      }
      catch(IOException e){}
  }

}
