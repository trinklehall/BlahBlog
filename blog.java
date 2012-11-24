import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import java.lang.reflect.Method;

public class blog{

	public blog(){}
	
	public void viewDashboard(String username){
	
      Scanner input = new Scanner(System.in);
		String dashSelection;
		System.out.println("Welcome, " + username);
		System.out.println("--------------------");
		System.out.println("1: View your blog");
		System.out.println("2: View posts from blog you are subscribed to");
		System.out.println("3: Add post");
		System.out.println("4: Delete post");
		System.out.println("5: Subscribe to user");
		dashSelection = input.nextLine();
		if(dashSelection.equals("1")){
			String site = (username + ".html");
			openBrowser(site);
		}
	}
	public void openBrowser(String url){
	  String osName = System.getProperty("os.name");
                try {
                        if (osName.startsWith("Windows"))
                                Runtime.getRuntime().exec(
                                                "rundll32 url.dll,FileProtocolHandler " + url);
                        else {
                                String[] browsers = { "firefox", "opera", "konqueror",
                                                "epiphany", "mozilla", "netscape" };
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



}