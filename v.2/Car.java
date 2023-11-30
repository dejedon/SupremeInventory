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
        

        //[Loan Function] Added the loan file creation
        try{
        File loanFile = new File(folder,"Loans.txt");
        if (loanFile.createNewFile()){System.out.println("Created new loan file for car: " + make + "_" + model + "_" + id);}
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
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
    
    /**
     * [Loan Function] This fucntion will get the loan for car from a record file.
     * The benfit of this is that you can change the information stored in the file and the retreval when needed.
     * @return path to loan.txt
     */
    public String getLoanFile(){
        return path + "/" + "LoanData.txt";
    }
    
    /**
     * [Loan Function] This fucntion reads all the loans under the cars loan 
     * @return an ArrayList<string> of all the loans under the car
     */
    public ArrayList<String> LoadLoans(){
        File loanFile = new File(getLoanFile());
        ArrayList<String> Loans = new ArrayList<>();
        try{
            Scanner loanScanner = new Scanner(loanFile);
            while(loanScanner.hasNext()){
                Loans.add(loanScanner.nextLine());
            }
            loanScanner.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return Loans;
    }

    /**
     * [Loan Function] This function Adds a loan to the Car by entering it in the loan.txt.
     * @param bank
     * @param job
     * @param date
     */
    public void AddLoan(String bank, String job, String[] date){
        File loanFile = new File(this.getLoanFile());
        try{
            FileWriter loanFw = new FileWriter(loanFile,true);
            loanFw.append(bank + "_" + job + "_" + date[0] + "_" + date[1]+ "_" + date[2]);
            loanFw.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * [Loan Function] This function Adds a loan to the Car by entering it in the loan.txt.
     * @param bank
     * @param job
     * @param day
     * @param month
     * @param year
     */
    public void AddLoan(String bank, String job, String day, String month, String year){
        File loanFile = new File(this.getLoanFile());
        try{
            FileWriter loanFw = new FileWriter(loanFile,true);
            loanFw.append(bank + "_" + job + "_" + day + "_" + month+ "_" + year);
            loanFw.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /***
     * [Loan Function] This function removes a loan from loan.txt
     * @param bank
     * @param job
     * @param date
     */
    public void RemoveLoan(String bank, String job, String[] date){
        ArrayList<String> loanList = LoadLoans();
        loanList.remove(bank + "_" + job + "_" + date[0] + "_" + date[1] + "_" + date[2]);
        try{
            FileWriter fw = new FileWriter(getFile());
            for (String string : loanList) {
                fw.write(string);
            }
            fw.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /***
     * [Loan Function] This function removes a loan from loan.txt
     * @param bank
     * @param job
     * @param day
     * @param month
     * @param year
     */
    public void RemoveLoan(String bank, String job, String day, String month, String year){
        ArrayList<String> loanList = LoadLoans();
        loanList.remove(bank + "_" + job + "_" + day + "_" + month + "_" + year);
        try{
            FileWriter fw = new FileWriter(getFile());
            for (String string : loanList) {
                fw.write(string);
            }
            fw.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * [Loan Function] This function checks to see if the car has any loans in loan.txt.
     * @return True if the car has a loan in progress.
     */
    public Boolean HasLoan(){
        return LoadLoans().size() > 0;
    }

    /**
     * [Loan Function] This function get the amount of loans under the cars loan.txt.
     * @return an integer with the number of loans
     */
    public int CountLoans(){
        return LoadLoans().size();
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

