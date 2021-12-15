package WordCount;

import java.io.File;
import java.io.IOException;

/*
    Alejandro Lopez
    Operating Systems Project 3
    Nova Southeastern University
 */

public class Protocol {
    private static final int WAIT = 0; //This state means that the client is awaiting user input
    private static final int FILES = 1; //This state means that the client will perform a task

    private int state = WAIT; //The variable that determines the state
    private Boolean edit = false; //This variable checks whether a file's contents were edited to update the cache as well

    //                    ****              This is the path used for the program to create/store/update the files              ****

    private String path = "C:\\Users\\Alejandro Lopez\\IdeaProjects\\OSProject3\\src\\output\\"; //Update this path to the desired one on the user's computer


    private String fileName="";
    private String user = ""; //This is the user input for when they want to edit a file's contents

    public String processInput(String userInput, Cache caching) throws IOException {
        String display=""; //Variable to display on the client

        if (state==WAIT) {
            display = "What would you like to do? ";
            state=FILES;
        }
        //Update and Store work differently than the other tasks
        //List is a single display command and exit closes the client
        else if(state==FILES){
            String choice="";
            if((!userInput.contains("update")) && (!userInput.contains("store")) ) {
                if(userInput.contains("list"))
                {
                    choice = "list";
                }
                else if(userInput.contains("exit"))
                {
                    choice = "exit";
                }
                else if((!userInput.contains("create")) && (!userInput.contains("remove")) && (!userInput.contains("read")) && (!userInput.contains("list"))) //Check for user input
                {
                    display = "Error: Wrong user input";
                }
                else
                {
                    if(!userInput.contains(".txt")) //Checks the format of the file
                    {
                        display = "Error: Your file name does not contain the format: .txt";
                    }
                    else if(!userInput.contains(" "))
                    {
                        display = "Error: You are missing the second command or a space"; //Checks if the user put the second command which is the file name
                    }
                    else
                    {
                        String[] input = userInput.split(" "); //The user input is split into two parts
                        choice = input[0]; //This is the task that will be performed
                        fileName = input[1]; //This is the name of the file that will be worked on
                    }
                }
            }
            else {
                if( (userInput.contains("update")) || (userInput.contains("store"))) //Store and Update both split the input string into 3 parts
                {
                    if(!userInput.contains(".txt")) //Checking the format of the file
                    {
                        display = "Error: Your file name does not contain the format: .txt";
                    }
                    else if(!userInput.contains(" ")) //Checking for the second command
                    {
                        display = "Error: You are missing the second command or a space";
                    }
                    else
                    {
                        String[] input = userInput.split(" ", 3); //Split the string into 3 and only into 3
                        choice = input[0]; //The task
                        fileName = input[1]; //The file that will be worked on
                        if( (input.length <= 2)) //Third input cannot be empty
                        {
                            choice = "!@#^%";
                        }
                        else
                        {
                            user = input[2]; //Either the contents of the file (update) or the file to be copied (Store)
                        }
                    }
                }

                else
                {
                    display = "Error: Wrong user input";
                }
            }
            //Creates a new file if the file does not arleady exist
            if(choice.equalsIgnoreCase("create"))
            {
                if(Application.inList(path+fileName))
                {
                    display = "File with that name already exists";
                }
                else
                {
                    Application.create(path+fileName);
                    display = fileName + " Was created";
                }
            }
            //Updates the contents of the file if the file exists
            else if(choice.equalsIgnoreCase("update")){
                if(!Application.inList(path+fileName))
                {
                    display = "File does not exist";
                }
                else
                {
                    Application.create(path+fileName);
                    Application.write(path+fileName, user);
                    display = fileName + " Now has the value " + user;
                    edit = true;
                }

            }
            //Removes an existing file
            else if(choice.equalsIgnoreCase("remove")) {
                if(!Application.inList(path+fileName))
                {
                    display = "File does not exist";
                }
                else
                {
                    Application.delete(path+fileName);
                    display = fileName + " was removed";
                }

            }
            //Shows the list of existing files
            else if(choice.equalsIgnoreCase("list"))
            {
                File folder = new File(path);
                File[] listOfFiles = folder.listFiles();
                display="The files in the Datastore are: ";
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        display += " File:" + listOfFiles[i].getName() + " ";
                    }
                }
            }
            //Shows the contents of the file and counts the words, characters, and lines
            else if(choice.equalsIgnoreCase("read"))
            {
                if( (caching.get(fileName) != null)  && !edit ) //Check if the file was edited and whether it is in the cache already
                {
                    display = "From cache: " + caching.get(fileName);
                }
                else if(!Application.inList(path+fileName)) //Check iof the file exists
                {
                    display = "File does not exist";
                }
                else
                {
                    String all = new String();
                    all = Application.read(path+fileName); //Gets the raw data from the file
                    //Counts each character except space
                    String s = all.trim();

                    int cc = 0;
                    for(int i = 0; i < s.length(); i++) {
                        if(s.charAt(i) != ' ')
                            cc++;
                    }

                    //Counts each word
                    int wc=0;
                    char ch[]= new char[all.length()];      //Intialize the word Array
                    for(int i=0;i<all.length();i++)
                    {
                        ch[i]= all.charAt(i);
                        if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                            wc++;
                    }

                    //Counts each line
                    int lc = 0;
                    String[] lines = all.split("\r\n|\r|\n");
                    lc = lines.length;
                    s = s.replace("\n", " ,"); // , -> new line

                    display = //Displays the total number of characters present in the given string
                            "Characters: " + cc + " " +
                            //Displays number of words in the string
                            "Words: " + wc + " " +
                            //Display number of lines in the string
                            "Lines: " + lc + " " +
                            //Displays the contents of the file
                            "Contents: " + s + " ";
                    caching.put(fileName, display);
                    edit = false;
                }

            }
            else if(choice.equalsIgnoreCase("exit"))
            {
                display = "exit";
            }
            else if(choice.equalsIgnoreCase("store"))
            {
                if( (Application.inList(path+user)) && (Application.inList(path+fileName)) && (!fileName.equals(user)))
                {
                    // get the source file name
                    String a = (path+fileName);

                    // source file
                    File x = new File(a);

                    // get the destination file name
                    String b = (path+user);

                    // destination file
                    File y = new File(b);

                    // method called to copy the
                    // contents from x to y
                    try {
                        Application.copyContent(x, y);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    display = fileName + " was copied to " + user;
                    edit = true;
                }
                else if(!Application.inList(path+fileName))
                {
                    if(!fileName.contains(".txt"))
                    {
                        display = "first file input does not contain the format .txt";
                    }
                    else
                    {
                        display = "the file " + fileName + " does not exist please create it first to copy from it";
                    }
                }
                else if(fileName.equals(user))
                {
                    if(!fileName.contains(".txt"))
                    {
                        display = "first file input does not contain the format .txt";
                    }
                    else
                    {
                        display = "a file cannot be copied into itself";
                    }
                }
                else
                {
                    if(!user.contains(".txt"))
                    {
                        display = "second file input does not contain the format .txt";
                    }
                    else
                    {
                        display = "the file " + user + " does not exist please create it first to copy into it";
                    }
                }

            }
            else if(choice == "!@#^%")
            {
                display = "Error: Third input missing";
            }
        }


        return display;
    }
}
