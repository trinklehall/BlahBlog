//BLAHBLOG LOGIN CLASS
//Creates login object to log user in
//Last updated 12/03/2012 10:21AM

import java.util.Scanner;
import java.io.*;

public class login{

	public login(){}
	public String loginUser(String username, String password){
		Scanner input = new Scanner(System.in);
		String s = username + " " + password;


		try{
			//selects file login.txt to be read
			FileReader file1 = new FileReader(new File("users.blah"));
			//reads file
			BufferedReader f = new BufferedReader(file1);
			//creates temp string file to save each line read
			String temp;
			Boolean found = false;

			while((temp=f.readLine())!=null && found == false)
			{
				if(temp.equals(s))
				{

					found=true;
					return username;
					}
			}
		}
		catch(Exception e)
		{System.out.println("Error Reading Login file");}
		return "";
	}
	//return username;
}
