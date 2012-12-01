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
	
	public void viewDashboard(String username){
	
      Scanner input = new Scanner(System.in);
		String dashSelection;
		System.out.println("Welcome, " + username);
		System.out.println();
		System.out.println(detectDirected(username));
		System.out.println("--------------------");
		System.out.println("1: View your blog");
		System.out.println("2: View posts from blog you are subscribed to");
		System.out.println("3: Add post");
		System.out.println("4: Delete post");
		System.out.println("5: Subscribe to user");
		System.out.println("6: Exit");
		dashSelection = input.nextLine();
		if(dashSelection.equals("1")){
			String site = (username + ".html");
//			openBrowser(site);
			BareBonesBrowserLaunch.openURL(site);
		}
		if(dashSelection.equals("3")){
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
		}
		if(dashSelection.equals("6")){
			System.exit(0);
		}
		viewDashboard(username);
	}
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



//ADD METHOD
	 public static void add(String post,String user, boolean image, String imageURL){

//Collects the html file as is, into a string
	 String blogContents = fileToString(user + ".html");
//Adds the new blog post and image into that string (if the person gave an image)
	 if(image == true){
	 blogContents = blogContents.replace("<!-- START BLOG POSTS -->", "<!-- START BLOG POSTS --><tr><td>"+"<img src=\"" + imageURL + "\" width=\"550\"><br />"+post+"</td></tr>");
	 }
	 else{
	 blogContents = blogContents.replace("<!-- START BLOG POSTS -->", "<!-- START BLOG POSTS --><tr><td>"+post+"</td></tr>");	 
	 }
//Overwrites the html file with a html containing that string. Resulting in an updated html file.
	try {
  Writer output = null;
	  String userhtml = (user + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {}
	 }
//END ADD METHOD	 
	
//REMOVE METHOD// 
	 public static void remove(String user){
    Scanner input = new Scanner(System.in);
//Collects contents of html into string
	 String currentPost;
	 String yesNo;
	 String blogContents = fileToString(user + ".html");
//Parses through lines, if there is <tr><td>(post text)</td></tr>, store it in currentPost
//System.out.println("Would you like to delete\n" + currentPost + "? (Y/N)");
//input.nextLine = yesNo;
//if(yesNo.toLowerCase.equals("y"){
//blogContents.delete(that exact segment of code)
//}


//Replaces the old blog with the post with the new blog, without the post
	 blogContents = blogContents.replace("<!-- START BLOG POSTS -->", "<!-- START BLOG POSTS -->");

	 
	 }
	 
 //END REMOVE METHOD
	 
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

	public static void addSubscription(String username, String subscribeTo){
	//Collects the html file as is, into a string
	 String blogContents = fileToString(username + ".html");
//Adds the new blog post and image into that string (if the person gave an image)
	//Overwrites the html file with a html containing that string. Resulting in an updated html file.
	
	
	
	
	 blogContents = blogContents.replace("<!--SUBSCRIPTIONS-->", "<!--SUBSCRIPTIONS-->"+subscribeTo);	 

	try {
  Writer output = null;
	  String userhtml = (username + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {}	
	}


public static void removeSubscription(String username, String unsubscribeTo){
	//Collects the html file as is, into a string
	 String blogContents = fileToString(username + ".html");
//Adds the new blog post and image into that string (if the person gave an image)
	//Overwrites the html file with a html containing that string. Resulting in an updated html file.
	
	
	
	
	 blogContents = blogContents.replace(unsubscribeTo, "");	 

	try {
  Writer output = null;
	  String userhtml = (username + ".html");
	  File file = new File(userhtml);
	  output = new BufferedWriter(new FileWriter(file));
  

    	  output.write(blogContents);
	 
    output.close();
} catch (IOException e) {}	
	}

public static String detectDirected(String username){

	

		try{
			//selects file login.txt to be read
			FileReader file1 = new FileReader(new File("users.blah"));
			//reads file
			BufferedReader f = new BufferedReader(file1);
			//creates temp string file to save each line read
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

}