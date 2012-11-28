import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;

public class Blahblog{

        public static void main(String[] args) {
		  String registerOrNo;
		  blog theBlog = new blog();
		  Scanner input = new Scanner(System.in);
		  System.out.println("Welcome. Do you have an account? (Y/N)");
		  registerOrNo = input.nextLine();
		  if(registerOrNo.toLowerCase().equals("n")){
				
				System.out.println("Do you want to be a guest? (Y/N)");
				String guestOrNo = input.nextLine();
					if(guestOrNo.toLowerCase().equals("y")){
					}
					else{
				
					String username;
					String password;  	
			  		System.out.println("What username do you want?");
					username = input.nextLine();
					System.out.println("What password do you want?");
					password = input.nextLine();
					register Reg = new register();
					Reg.newUser(username, password);
					login Log = new login();
					username = "-1";
					while(!username.equals(Log.loginUser(username,password))){
						System.out.println("What's your username?");
						username = input.nextLine();
						System.out.println("What's your password?");
						password = input.nextLine();
					}
					theBlog.viewDashboard(username);

					}
			}
			else{
				login Log = new login();
						String username = "-1";
						String password = "";
						while(!username.equals(Log.loginUser(username,password))){
							System.out.println("What's your username?");
							username = input.nextLine();
							System.out.println("What's your password?");
							password = input.nextLine();
					
						}
						theBlog.viewDashboard(username);

			}
			}
	}