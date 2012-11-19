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

	}
}