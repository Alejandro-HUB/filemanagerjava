package WordCount;

import java.net.*;
import java.io.*;
import java.util.*;


/*
    Alejandro Lopez
    Operating Systems Project 3
    Nova Southeastern University
 */

public class Client {
    public static void main(String[] args) throws IOException {
        final String HOST = "127.0.0.1"; //Local Host
        final int PORT = 3307; //Opened port number in the gateway

        System.out.println("Client started."); //Display each time a new client is started

        try (
                Socket socket = new Socket(HOST, PORT); //Instantiate a socket using the 127.0.0.1:3307
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //To display in the command line
                Scanner in = new Scanner(socket.getInputStream()); //Read the lines in the file
                Scanner s = new Scanner(System.in); //Get input from the user
        ) {
            String fromServer; //Lines that come from the server
            String fromUser; //Lines typed by the user
            //Menu to display options
            System.out.println("--Options--\n"
                    +"1. Create  (create a new file). \n" +
                    "2. Update  (write into a file). \n" +
                    "3. Remove (delete a file and its contents) " +
                    "\n" +
                    "4. Store (copy a file and its contents)\n" +
                    "5. List  (display all files that were created " +
                    "). \n" +
                    "6. Read (read the contents of the file and count the lines, chars, and words in it " +
                    ")" + "\n" +
                    "7. Exit (exit client) ");

            //If the server is saying something
            while ((fromServer = in.nextLine()) != null) {

                System.out.println("Server: " + fromServer); //Display what the server has to say
                if (fromServer.equals("exit")) //If the server says exit then exit client
                {
                    System.exit(0);
                }

                fromUser = s.nextLine(); //Get input from the user
                if (fromUser != null) { //If the user said something
                    System.out.println("Client: " + fromUser); //Display what the user said
                    out.println(fromUser);
                }
            }
        }
    }
}
