package WordCount;

import java.net.*;
import java.io.*;


/*
    Alejandro Lopez
    Operating Systems Project 3
    Nova Southeastern University
 */

public class Server
{
    public static void main(String[] args) throws IOException {
        int PORT = 3307; //Port opened in the gateway
        ServerSocket serverSocket = new ServerSocket(PORT); //New instance of a server socket

        //Display when the server starts
        System.out.println("Server started...");
        System.out.println("Wating for clients...");

        //Create a new instance of the cache to save the contents of a file
        Cache caching = new Cache();

        //While still running
        while (true) {
            Socket clientSocket = serverSocket.accept(); //Connection from the client to the server
            Thread t = new Thread() {
                public void run() { //New thread begins to run
                    try (
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // To display in the server
                            BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream())); //Receive from the user
                    ) {

                        String input, output; //Variables to hold the displayed information and the input from the user


                        Protocol connect = new Protocol(); //New protocol instance to process the user input
                        output = connect.processInput(null, caching); //Display from server what comes from the protocol
                        out.println(output);

                        while ((input = in.readLine()) != null) { //While there is still input
                            output = connect.processInput(input.toLowerCase(), caching); //Display the cache information, turn all the input to lower case to check for user input
                            out.println(output);
                        }
                    } catch (IOException e) { }
                }
            };
            t.start();
        }
    }
}

