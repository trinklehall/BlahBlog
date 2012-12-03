import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;


public class register{
	public register(){
	}
	public void newUser(String username, String password){
	try {
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("users.blah", true)));
    out.println(username + " " + password);
    out.close();
} catch (IOException e) {
    //oh noes!
}



	try{
	  Writer output = null;
	  String userhtml = (username + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
	  String tempFileContents = fileToString("template.html");
	  

	  String result = tempFileContents.replace("User", username);

	
	  output.write(result);
	  

	  
	  output.close();
	   
  } catch (IOException e) {
    //oh noes!
}

	}
	
	 public static String fileToString(String file) {
        String result = null;
        DataInputStream in = null;

        try {
            File f = new File(file);
            byte[] buffer = new byte[(int) f.length()];
            in = new DataInputStream(new FileInputStream(f));
            in.readFully(buffer);
            result = new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException("IO problem in fileToString", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) { /* ignore it */
            }
        }
        return result;
    }
	 	
  }