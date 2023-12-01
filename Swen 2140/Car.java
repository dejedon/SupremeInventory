import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    String datein;
    String easier;
    String dateout;
    PrintStream car;
    ArrayList<String> subjects = new ArrayList<String>();
    private int id;

    public Car(String make, String model, String vin, String price, String des, String image) throws FileNotFoundException{
        id = ObjectCounter.incrementObjectCount();
        path = make+ ":" + model + ":" + id;
        this.make = make;
        this.model = model;
        this.price = price;
        this.vin = vin;
        ids = String.valueOf(id);
        File folder = new File(path);
        setDatein();
        easier = make+":"+ model+":"+ vin +":" +price;
        folder.mkdir();
        saveImage(image, folder);
        car =new PrintStream(new FileOutputStream(new File(folder, make+ ":" + model + ".txt")));
        car.println(make+":"+ model+":"+ vin +":" +price +":" + des+ ":" + datein); 
        addcar(path);
        setDatein();
        
    }

    public Car(String data){// throws FileNotFoundException{
        String[] car = data.split(":");
        this.make = car[0];
        this.model = car[1];
        ids = car[2];
        path = make+ ":" + model + ":" + car[2];

        try {
            // FileReader is used to read the character files
            FileReader fileReader = new FileReader(getFile());

            // BufferedReader provides efficient reading of characters, lines, etc.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            // Read lines until the end of the file
            while ((line = bufferedReader.readLine()) != null) {
                // Split each line by ":"
                String[] parts = line.split(":");
                
                // Process the parts as needed
                vin = parts[2];
                price = parts[3];
                des = parts[4];
                try{
                    datein = parts[5];
                    dateout = parts[6];
                }catch(IndexOutOfBoundsException e){}
                
            }

            // Close the resources to free up system resources
            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMake(){
        return make;
    }

    public String getModel(){
        return model;
    }

    public String getVin(){
        return vin;
    }
    public String getPrice(){
        return price;
    }
    
    public String getID(){
        return ids;
    }

    public String getDes(){
        return des;
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
        return path + "/" + make+ ":" + model + ".txt";
    }

    private String soldcar(){
        return make+":"+ model+ ":" +ids;
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
            Object[] data = sub.split(":");
                    System.out.println(data[0]);
                    System.out.println(data[1]);
                    if(subject.equals(data[0])){
                        data[1] = text;
                        
                    }
            subjects.add(data[0]+":"+data[1]);
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
        setDateout();
        String sourceFilePath = "car.txt";
        String destinationFilePath = "sold.txt";
        String stringToRemove = soldcar();

        try {
            // Read the content of the source file
            BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            reader.close();

            // Remove the specified string
            String modifiedContent = content.toString().replace(stringToRemove, "");

            // Write the modified content back to the source file
            BufferedWriter sourceFileWriter = new BufferedWriter(new FileWriter(sourceFilePath));
            sourceFileWriter.write(modifiedContent);
            sourceFileWriter.close();

            // Append the removed string to the destination file
            BufferedWriter destinationFileWriter = new BufferedWriter(new FileWriter(destinationFilePath, true));
            //destinationFileWriter.newLine();
            destinationFileWriter.write(soldcar());
            destinationFileWriter.close();

            System.out.println("String removed from source file and added to destination file successfully.");

        } catch (IOException e) {
            e.printStackTrace();
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
                System.out.println("Image saved to: " + targetImageFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Source image file or target folder not found.");
        }
    }

    public void setDatein(){
        LocalDate today = LocalDate.now();

        // Format the date if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datein = today.format(formatter);

    }

    public void setDateout(){
        LocalDate today = LocalDate.now();

        // Format the date if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateout = today.format(formatter);


        try {
            // Read the content of the file
            FileReader fileReader = new FileReader(getFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String existingContent = bufferedReader.readLine();

            // Close the resources after reading
            bufferedReader.close();
            fileReader.close();

            // Modify the content as needed
            String modifiedContent = existingContent + ":"+ dateout;

            // Write the modified content back to the same file
            FileWriter fileWriter = new FileWriter(getFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(modifiedContent);

            // Close the resources after writing
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("File content read, modified, and written back successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getDatein(){
        return datein;
    }

    public String getDateout(){
        return dateout;
    }

    public long getDaysBetween(){
        LocalDate date1 = LocalDate.parse(datein, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate date2 = LocalDate.parse(dateout, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Calculate the number of days between the two dates
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);

        return daysBetween;
    }



        

        

        
    

}

