package TeamRocketPower;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
/**
MailClient: Used to send an email out on the mail.gsu.edu server.
No authentication required.
*/
public class MailClient {

	static String msgString = "Test Text.";
	static String from = "cdrury2@student.gsu.edu";
	static String to = "cdrury2@student.gsu.edu";
	static String mailHost = "mail.gsu.edu";
	static int mailPort = 25;
	static BufferedReader in;
	static PrintWriter out;

	public static void main(String args[]) 
	{
		try
		{
			send(msgString, from, to);
		}
		catch(IOException e)
		{
			System.out.println("Error: " + e);
		}
			

	}
	/**
	Sends a string from a person to another person via the mail.gsu.edu server
 */
	public static void send(String msgString, String from, String to) throws IOException 
	{
		Socket mailSocket;
		InputStream inputStream;
		OutputStream outputStream;
		mailSocket = new Socket(mailHost, mailPort);
		
		inputStream = mailSocket.getInputStream();
		outputStream = mailSocket.getOutputStream();
		
		in = new BufferedReader(new InputStreamReader(inputStream));
		out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		
		String reply = in.readLine();
		System.out.println(reply);

		out.println("HELO Client");
		reply = in.readLine();
		System.out.println(reply);
		
		out.println("MAIL From:<" + from + ">");
		reply = in.readLine();
		System.out.println(reply);
				
		out.println("RCPT TO:<" + to + ">");
		reply = in.readLine();
		System.out.println(reply);

		out.println("DATA");
		out.println(msgString);
		out.println(".");
		
		reply = in.readLine();
		System.out.println(reply);

		out.println("QUIT");
	}
}