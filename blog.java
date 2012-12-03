//BLAHBLOG BLOG CLASS
//Contains all the methods of the blog class and supporting methods they may require.
//Last updated 12/03/2012 10:21AM

import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Scanner;
import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.lang.String;


public class blog{

	public blog(){}
//View dashboard
	public void viewDashboard(String username){
	
      Scanner input = new Scanner(System.in);
		String dashSelection;

		System.out.println();
//Checks for public or private messages
		System.out.println(detectDirected(username));
		checkPM(username);

		System.out.println("--------------------");
		System.out.println("1: View your blog");
		System.out.println("2: View posts from blog you are subscribed to");
		System.out.println("3: Search tags");
		System.out.println("4: Add post");
		System.out.println("5: Delete post");
		System.out.println("6: Subscribe to user");
		System.out.println("7: Unsubscribe to user");
		System.out.println("8: Exit");
		System.out.println("9: Private Message");
		System.out.println("10: Public Message");

		dashSelection = input.nextLine();
//If statements for whatever the user chooses.
		if(dashSelection.equals("1")){
			String site = (username + ".html");

			BareBonesBrowserLaunch.openURL(site);
		}
		if(dashSelection.equals("3")){
			System.out.println("What would you like to search for?");
			String searchFor = input.nextLine();
			searchResults(searchFor);
			System.out.println("\n(Press enter to continue)");
		input.nextLine();
		}
		if(dashSelection.equals("4")){
			boolean imageDec = false;
			String imageURL = null;
			System.out.println("Would you like to add an image to that post?");
			String imageDecision = input.nextLine();
			if(imageDecision.toLowerCase().equals("n")){
				imageDec = false;
				imageURL = "";
			}
			if(imageDecision.toLowerCase().equals("y")){
				imageDec = true;
				System.out.println("Type URL of image.");
				imageURL = input.nextLine();

			}
			System.out.println("Type post:");
			String post = input.nextLine();
			add(post, username, imageDec, imageURL);
			System.out.println("Post added. \n\n(Press enter to continue)");
			input.nextLine();
		}
		if(dashSelection.equals("8")){
			System.exit(0);
		}
		if(dashSelection.equals("5")){
		
			String postNum = "0";
			System.out.println("Would you like to delete all posts?");
			String imageDecision = input.nextLine();
			if(imageDecision.toLowerCase().equals("y")){
				postDelete(username, postNum);
				System.out.println("Posts deleted. \n\n(Press enter to continue)");
				input.nextLine();
			}
			if(imageDecision.toLowerCase().equals("n")){
				System.out.println("Which post would you like to delete?");
				postNum = input.nextLine();
				postDelete(username, postNum);
				System.out.println("Post deleted. \n\n(Press enter to continue)");
				input.nextLine();
			}
		}
		if(dashSelection.equals("6")){
			System.out.println("Type the username you wish to subscribe to");
			String subscribeTo = input.nextLine();
			addSubscription(username, subscribeTo);
			System.out.println("Now subscribed to "+subscribeTo);
			System.out.println("\n(Press enter to continue)");
			input.nextLine();
			
		}
		if(dashSelection.equals("7")){
			System.out.println("Type the username you wish to unsubscribe to");
			String subscribeTo = input.nextLine();
			removeSubscription(username, subscribeTo);
			System.out.println(subscribeTo + " removed from subscriptions");
			System.out.println("\n(Press enter to continue)");
			input.nextLine();

		}
		if(dashSelection.equals("9")){
			System.out.println("Type your message:");
			String msg = input.nextLine();
			System.out.println("Recipient:");
			String rec = input.nextLine();
			privateMsg(username, rec, msg);
		
		}
		if(dashSelection.equals("10")){
		boolean imageDec = false;
			String imageURL = null;
			System.out.println("Would you like to add an image to the message?");
			String imageDecision = input.nextLine();
			if(imageDecision.toLowerCase().equals("n")){
				imageDec = false;
				imageURL = "";
			}
			if(imageDecision.toLowerCase().equals("y")){
				imageDec = true;
				System.out.println("Type URL of image.");
				imageURL = input.nextLine();

			}
			System.out.println("Type message:");
			String message = input.nextLine();
			System.out.println("Recipient:");
			String recip = input.nextLine();
			add(("@"+recip+" "+message), username, imageDec, imageURL);
			System.out.println("Post added. \n\n(Press enter to continue)");
			input.nextLine();
		}

		
		
		viewDashboard(username);

	}
//End Dashboard method.

//OPEN BROWSER METHOD
//Do not touch
	public void openBrowser(String url){
	  String osName = System.getProperty("os.name");
                try {
                        if (osName.startsWith("Windows"))
                                Runtime.getRuntime().exec(
                                                "rundll32 url.dll,FileProtocolHandler " + url);
                        else {
                                String[] browsers = { "firefox", "opera", "konqueror",
                                                "epiphany", "mozilla", "netscape", "safari" };
                                String browser = null;
                                for (int count = 0; count < browsers.length && browser == null; count++)
                                        if (Runtime.getRuntime().exec(
                                                        new String[] { "which", browsers[count] })
                                                        .waitFor() == 0)
                                                browser = browsers[count];
                                Runtime.getRuntime().exec(new String[] { browser, url });
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error in opening browser"
                                        + ":\n" + e.getLocalizedMessage());
                }

	}
//END OPEN BROWSER METHOD	


//ADD POST METHOD
	public static void add(String post,String user, boolean image, String imageURL){
		//Collects the html file as is, into a string
		String userhtml = user + ".html";
		String blogContents = fileToString(userhtml);

		int postCount = countPosts(userhtml);
		//System.out.println(postCount);
		//postCountAdd(postCount, userhtml);

		//Adds the new blog post and image into that string (if the person gave an image)
		if(image == true){
			blogContents = blogContents.replace("<!-- START BLOG POSTS -->", "<!-- START BLOG POSTS --><tr><td>"+"<img src=\"" + imageURL + "\" width=\"550\"><br />"+post+"</td></tr><tr><td>Post " + postCount + "</td></tr>");
		}
		else{
			blogContents = blogContents.replace("<!-- START BLOG POSTS -->", "<!-- START BLOG POSTS --><tr><td>"+post+"</td></tr><tr><td>Post " + postCount + "</td></tr>");	 
		}
		//postCountAdd(postCount, userhtml);
		//Overwrites the html file with a html containing that string. Resulting in an updated html file.
		try {
			Writer output = null;
			File file = new File(userhtml);
			output = new BufferedWriter(new FileWriter(file));

			//System.out.println("its working");


			output.write(blogContents);
			//postCountAdd(postCount, userhtml);

			output.close();
		} catch (IOException e) {}
		postCountAdd(postCount, userhtml);
	}

	//END ADD METHOD	 
	 
//POST DELETE METHOD
private static void postDelete(String username, String postNum) {

		//name of user's html file
		String userhtml = username + ".html";
		//convert string to interger
		int postNumInt = Integer.parseInt(postNum);

		//deletes all posts
		if (postNumInt == 0)
		{
			String blogContents = fileToString(userhtml);

			String keyword = "START BLOG POSTS";
			int indexStart = blogContents.indexOf(keyword) + 20;
			String keyword2 = "END BLOG POSTS";
			int indexEnd = blogContents.indexOf(keyword2) -10;

			deleted(userhtml, blogContents, indexStart, indexEnd);
			resetNumPosts(userhtml);

		}


		if(postNumInt > 1){

			String blogContents = fileToString(userhtml);


			String keyword = "Post " + (postNumInt + 1);
			int indexStart = blogContents.indexOf(keyword) + 17;
			//if post trying to delete is most recent post
			if (indexStart == 16){
				keyword = "START BLOG POSTS";
				indexStart = blogContents.indexOf(keyword) + 22;
				//System.out.println("whoa: " + indexStart);
			}

			String keyword2 = "Post " + postNumInt;
			int indexEnd = blogContents.indexOf(keyword2) + 17;

			deleted(userhtml, blogContents, indexStart, indexEnd);
		}


	}


//END DELETE METHOD.
//DELETED METHOD

	private static void deleted(String userhtml, String blogContents, int indexStart, int indexEnd) {

		char[] characters = blogContents.toCharArray();

		StringBuilder sb = new StringBuilder();
		sb.append(characters);
		sb.replace(indexStart, indexEnd, "");
		String bg = sb.toString();

		try {
			Writer output = null;
			File file = new File(userhtml);
			output = new BufferedWriter(new FileWriter(file));

			output.write(bg);

			output.close();
		} catch (IOException e) {}

	}
	
//END DELETED METHOD

//POST COUNT ADD METHOD

	private static void postCountAdd(int postCount, String userhtml) {
		// TODO Auto-generated method stub
		String blogContents = fileToString(userhtml);
		//System.out.println(blogContents.length());
		String postCnt = "numposts";
		String numPosts = Integer.toString(postCount + 1);
		System.out.println(numPosts);
		int index = blogContents.indexOf(postCnt) + 8;
		System.out.println(index);
		int index2 = blogContents.indexOf(postCnt) + 9;
		System.out.println(index2);
//Adds posts added together
		char[] characters = blogContents.toCharArray();
		char temp = characters[index];

		System.out.print(temp);
		StringBuilder sb = new StringBuilder();
		sb.append(characters);
		sb.replace(index, index2, numPosts);
		String bg = sb.toString();

		try {
			Writer output = null;
			File file = new File(userhtml);
			output = new BufferedWriter(new FileWriter(file));

			output.write(bg);

			output.close();
		} catch (IOException e) {}
	}
//END POSTCOUNTADD METHOD

//COUNTPOSTSMETHOD
	public static int countPosts(String userhtml){
//Counts number of posts in a specific blog

		String blogContents = fileToString(userhtml);
		//System.out.println(blogContents.length());
		String postCount = "numposts";
		int index = blogContents.indexOf(postCount) + 8;
		//System.out.println(index);
		char[] chars = blogContents.toCharArray();
		char temp = chars[index];
		System.out.println(temp);
		int postCnt = Character.getNumericValue(temp);
		return postCnt;
	}
//END COUNTPOSTS METHOD

//ADJUSTPOSTCOUNT METHOD
	private static void adjustPostCount(int postCount, int postNumInt) {
		// TODO Auto-generated method stub
		if (postCount == postNumInt){
			//do nothing
		}
		if (postNumInt < postCount){
			//do something
		}
	}


//END ADJUSTPOSTCOUNTMETHOD

//RESET NUMPOSTS METHOD
//Used for when user deletes all posts
	private static void resetNumPosts(String userhtml) {
		String blogContents = fileToString(userhtml);
		String s = "numposts";
		int indexStart = blogContents.indexOf(s) + 8;
		int indexEnd = blogContents.indexOf(s) + 10;
		char[] characters = blogContents.toCharArray();

		StringBuilder sb = new StringBuilder();
		sb.append(characters);
		sb.replace(indexStart, indexEnd, "0");
		String bg = sb.toString();

		try {
			Writer output = null;
			File file = new File(userhtml);
			output = new BufferedWriter(new FileWriter(file));

			output.write(bg);

			output.close();
		} catch (IOException e) {}

	}
//END RESETPOSTCOUNT METHOD


//FILETOSTRING METHOD (do not touch)
//Converts text file to string for manipulation
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
//END FILETOSTRING METHOD

//ADD SUBSCRIPTION

	public static void addSubscription(String username, String subscribeTo){
	//Collects the html file as is, into a string
	 String blogContents = fileToString(username + ".html");

	
	 blogContents = blogContents.replace("<!-- SUBSCRIPTIONS ", "<!-- SUBSCRIPTIONS "+subscribeTo+", " );	 
	 blogContents = blogContents.replace("<h3>Subscribed to:</h3>", "<h3>Subscribed to:</h3><h4><a href=\""+subscribeTo+".html\" target=\"_blank\">" + subscribeTo + "</a></h4>");
	try {
  Writer output = null;
	  String userhtml = (username + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {
	System.out.println(e);
}	
	}
//END ADDSUBSCRIPTION

//REMOVESUBSCRIPTION

public static void removeSubscription(String username, String unsubscribeTo){
	//Collects the html file as is, into a string
	 String blogContents = fileToString(username + ".html");

	
	
	
	 blogContents = blogContents.replace(unsubscribeTo+", ", "");	 
	 	 blogContents = blogContents.replace("<h4><a href=\""+unsubscribeTo+".html\" target=\"_blank\">" + unsubscribeTo + "</a></h4>", "");	 

	try {
  Writer output = null;
	  String userhtml = (username + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {}	
	}

//END REMOVESUBSCRIPTION


//DETECTDIRECTED METHOD
//Detects public messages directed towards user's account.
public static String detectDirected(String username){

	

		try{
			//selects file login.txt to be read
			FileReader file1 = new FileReader(new File("users.blah"));
			//reads file
			BufferedReader f = new BufferedReader(file1);
			//creates temp string to save each line read
			String temp;

			
			while((temp=f.readLine())!=null)
			{
				int spacePos = temp.indexOf(" ");
				String firstWord = temp.substring(0,spacePos);
				String blogContents = fileToString(firstWord + ".html");
				String msg = "";
				if(blogContents.contains("@"+username)){
					
					int atSymbolPos = blogContents.indexOf("@"+username);

					int endOfBlogPos = blogContents.length();

					String blogContentsonward = blogContents.substring(atSymbolPos, endOfBlogPos);

					
					int endOfMsgPos = blogContentsonward.indexOf("</td></tr>") + atSymbolPos;

					msg = msg + "\nMessage from "+ firstWord + ": " + blogContents.substring(atSymbolPos, endOfMsgPos);
					
					return msg;
				}
				
		
			}
		}
		catch(Exception e)
		{System.out.println(e);}

			return "";
}
//END DETECTDIRECTED

//SEARCHRESULTS METHOD
//Searches all account for a certain tag
public static void searchResults(String searchTag){
		
		Scanner input = new Scanner(System.in);
		try{
			//selects file login.txt to be read
			FileReader file1 = new FileReader(new File("users.blah"));
			//reads file
			BufferedReader f = new BufferedReader(file1);
			//creates temp string to save each line read
			String temp;

			System.out.println("Search results:");
			while((temp=f.readLine())!=null)
			{

				int spacePos = temp.indexOf(" ");
				String firstWord = temp.substring(0,spacePos);
				String blogContents = fileToString(firstWord + ".html");
				String msg = "";

				if(blogContents.contains("#"+searchTag)){
					int atHashSymbolPos = blogContents.indexOf("#"+searchTag);
	
					String atHashAfter = blogContents.substring(atHashSymbolPos, blogContents.length());

					int endOfPostPos = (atHashAfter.indexOf("</td></tr>"))+atHashSymbolPos;


					int begOfPostPos = blogContents.lastIndexOf("<td>", atHashSymbolPos)+4;

					String post = blogContents.substring(begOfPostPos, endOfPostPos);
					System.out.println("from user " + firstWord + ":\n" + post);

				}
				
		
			}
		}
		catch(Exception e)
		{System.out.println(e);}
		


}
//END SEARCHRESULTS METHOD

//PRIVATEMSG METHOD
//Sends a private message to a certain user so only they can read it.

public static void privateMsg(String fromUser, String toUser, String msg){
	

	 String blogContents = fileToString(toUser + ".html");

	
	 blogContents = blogContents.replace("<!-- PM:", "<!-- PM:"+fromUser+","+msg+";" );	 

	try {
  Writer output = null;
	  String userhtml = (toUser + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {
	System.out.println(e);
}	}

//END PRIVATEMSG METHOD

//CHECKPM METHOD
//Checks if the user has any new private messages. Displays, then erases.
public static void checkPM(String username){
	 Scanner input = new Scanner(System.in);
	 String blogContents = fileToString(username + ".html");
	 int PMstart = blogContents.indexOf("<!-- PM:")+8;
	 int PMend = blogContents.indexOf(" -->", PMstart);

	 String PMs = blogContents.substring(PMstart,PMend);
	 while(PMstart!=PMend){
	 	int indexOfComma = PMs.indexOf(',');
		String fromPerson = PMs.substring(0,indexOfComma);
		int indexOfSemi = PMs.indexOf(';');
		String message = PMs.substring(indexOfComma+1,indexOfSemi);
		System.out.println("Private message from " + fromPerson + ": \n"+message);

		System.out.println("Press enter to continue");
		input.nextLine();
      blogContents = blogContents.replace(fromPerson+","+message+";", "" );	 
		PMstart = blogContents.indexOf("<!-- PM:")+8;
	 	PMend = blogContents.indexOf(" -->", PMstart);
		try {
  Writer output = null;
	  String userhtml = (username + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {
	System.out.println(e);
}
		
	 }
//END CHECKPM METHOD


}

	
}