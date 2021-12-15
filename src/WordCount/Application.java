package WordCount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

/*
    Alejandro Lopez
    Operating Systems Project 3
    Nova Southeastern University
 */

//This class deals with all the operations related to files
public class Application {

    public static File create(String fileName) {
        File file = new File(fileName); //Creates a new file instance
        if(!file.exists()){ //Check if the file name does not exist
            try { //Generate an exception for the new file
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace(); //Reach the exception
            }
        }

        return file; //Returns the newly created file
    }

    //Updates/Writes the contents of the file
    public static void write(String fileName, String text) {
        File file = new File(fileName); //A new file instance

        try { //Generate an exception if there is an error reading the file
            file = create(fileName); //Creates the new file

            PrintWriter out = new PrintWriter(file.getAbsoluteFile()); //Generates a new instance of a print writer

            try {
                out.print(text); //Writes into the newly created file
            } finally {
                out.close(); //Closes the print writer
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Reads the contents inside a file
    public static String read(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder(); //New instance of a string builder
        File file = new File(fileName); //New instance of a file
        exists(fileName); //Check if the file already exists

        try { // Create an exception if there is an error reading the file
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile())); //Instantiate a buffer reader to read the contents of the file
            try { //Try to read the contents of the file
                String s; //Variable used for the buffered reader
                while ((s = in.readLine()) != null) { //Loop while there are more lines to read in the file
                    sb.append(s); //Add the contents to the string s
                    sb.append("\n"); //Add the new line to the string s
                }
            } finally {
                in.close(); //Close the buffered reader
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString(); //Return what was added to the string
    }

    //Deletes an existing file
    public static void delete(String nameFile) throws FileNotFoundException {
        exists(nameFile); //Checks if the file exists
        new File(nameFile).delete(); //Deletes the found file
    }

    //Checks if a file exists
    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName); //Generates an instance of a file
        if (!file.exists()){ //If the file does not exist
            throw new FileNotFoundException(file.getName()); //Throw an exception
        }
    }

    //Check if a file is in the output folder to either display it or check if it exists
    public static boolean inList(String fileName) {
        File file = new File(fileName); //New instance of a file
        boolean result = file.exists(); // Boolean to check if it exists
        if(result && file.isFile()) //If the file exsists and the file name provided matches
        {
           return true; //The file exists!
        }
        //to check whether it is directory
        else if(file.isDirectory())
        {
            return true;
        }
        else
            return false; //The file does not exist
    }

    //Copies the contents of one file to another
    public static void copyContent(File a, File b)
            throws Exception
    {
        FileInputStream in = new FileInputStream(a);
        FileOutputStream out = new FileOutputStream(b);

        try {

            int n;

            // read() function to read the
            // byte of data
            while ((n = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(n);
            }
        }
        finally {
            if (in != null) {

                // close() function to close the
                // stream
                in.close();
            }
            // close() function to close
            // the stream
            if (out != null) {
                out.close();
            }
        }
        System.out.println("File Copied");
    }
}
