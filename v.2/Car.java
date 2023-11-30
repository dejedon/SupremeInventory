import java.io.*;
import java.nio.file.Files;
import java.util.*;

import javafx.scene.image.Image;

// no file not found implimented 
public class Car {
    String path;
    String make;
    String model;
    String vin;
    String price;
    String des;
    String ids;
    PrintStream car;
    ArrayList<String> subjects = new ArrayList<String>();
    private int id;

    public Car(String make, String model, String vin, String price, String des, String image) throws FileNotFoundException{
        id = ObjectCounter.incrementObjectCount();
        path = make+ "_" + model + "_" + id;
        this.make = make;
        this.model = model;
        ids = String.valueOf(id);
        File folder = new File(path);
        folder.mkdir();
        saveImage(image, folder);
        car =new PrintStream(new FileOutputStream(new File(folder, make+ "_" + model + ".txt")));
        car.println(make+"_"+ model+"_"+ vin +"_" +price +"_" + des); 
        addcar(path);
        
    }

    public Car(String data){// throws FileNotFoundException{
        String[] car = data.split("_");
        this.make = car[0];
        this.model = car[1];
        ids = car[2];
        path = make+ "_" + model + "_" + car[2];
    }

    public String getCarName(){
        return make + " " + model + " ID#" + ids;
    }

    public String getPath(){
        return path;
    }

    public String getCarImage(){
        return path + "/" + make+ model + ".png";
    }

    private String getFile(){
        return path + "/" + make+ "_" + model + ".txt";
    }
    
    public ArrayList<String> readFile()
    {
        ArrayList<String> sublist = new ArrayList<>();
        try
        {
            Scanner fscan = new Scanner(new File(getFile()));
            
            while (fscan.hasNext()){
                    sublist.add(fscan.next());
            }
            fscan.close();
            //System.out.println(fldata);
        }
        catch(IOException e){}
        return sublist;
    }

    public ArrayList<String> updateFile(String subject, String text){
        System.out.println(subject);
        System.out.println(text);
        ArrayList<String> subs = readFile();
        System.out.println(subs);
        subjects.clear();
        for(String sub : subs){
            System.out.println(sub + ";");
            Object[] data = sub.split("_");
                    System.out.println(data[0]);
                    System.out.println(data[1]);
                    if(subject.equals(data[0])){
                        data[1] = text;
                        
                    }
            subjects.add(data[0]+"_"+data[1]);
        }
        
        try{    
            car =new PrintStream(new FileOutputStream(getFile()));
            for(String results : subjects){
                car.println(results);
            }
        }
        catch(IOException ioe){}
        return subjects;
    }

    //cahange to boolean
    private void copyFileUsingStream(String dest) throws IOException {
        PrintStream os;
        String destination = "Archive/" +dest+".txt";
        try {
            Scanner fscan = new Scanner(new File(getFile()));
            os = new PrintStream(new FileOutputStream(new File(destination)));
            while (fscan.hasNext()) {
                os.println(fscan.next());
            }
            fscan.close();
            os.close();
        } finally {
            
        }
    }
    public void archive_delete() throws IOException{
        copyFileUsingStream(getPath());
        try{
            File file = new File(getFile());
            file.delete();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void addcar(String name){
            ArrayList<String> carlist = new ArrayList<>();
            try
            {
                //fldata.clear();
                Scanner sscan = new Scanner(new File("car.txt"));
                
                while (sscan.hasNext()){
                        carlist.add(sscan.next());
                }
                sscan.close();
                //System.out.println(fldata);
            }
            catch(IOException e){}

            carlist.add(name);
            try{    
                PrintStream file =new PrintStream(new FileOutputStream("car.txt"));
                for(String car: carlist){
                    file.println(car);
                }
                file.close();
            }
            catch(IOException ioe){}
            
    
        }

    public void saveImage(String sourceFilePath, File targetFolder) {
        String[] parts = sourceFilePath.split("\\.");
        File sourceImageFile = new File(sourceFilePath);
        

        if (sourceImageFile.exists() && targetFolder.exists()) {
            File targetImageFile = new File(targetFolder, make+model+"."+parts[parts.length -1]);

            try {
                Files.copy(sourceImageFile.toPath(), targetImageFile.toPath());
                System.out.println("Image saved to_ " + targetImageFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Source image file or target folder not found.");
        }
    }

        

        

        
    

}

