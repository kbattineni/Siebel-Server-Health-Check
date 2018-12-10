// #######################################################################################################
//
//	Objective : Generate the Final Report which will be sent as the content of Health Check Email
//	Author    : Hitesh Dhanwani
//	Date      : 20/10/2015
//	Date	  : 28/10/2015 	Hitesh Dhanwani	The program has been modified to generate a full Health Check Report
// 	Input File: HealthCheckStatus.txt
//	OutputFile: HealthCheckFinal.html
//
// #######################################################################################################

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.util.Arrays;
import java.lang.*;
 
public class ReportGen {
	public static void main(String[] args) throws IOException {
		File dir = new File(".");
 
		String source = dir.getCanonicalPath() + File.separator + "listserver.txt";
		String dest = dir.getCanonicalPath() + File.separator + "HealthCheckFinal.html";
		String destErr = dir.getCanonicalPath() + File.separator + "Error.html";  
 
		File fin = new File(source);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
 
		FileWriter fstream = new FileWriter(dest, true);
		BufferedWriter out = new BufferedWriter(fstream);
 
		String aLine = null;
		String server[] = new String[10];
		int errorFlag = 0;
		int totalServers = 0;
		out.newLine();
		out.write("<body bgcolor=\"tan\"><p> Dear User, </p>");
		out.newLine();
		out.newLine();
		out.write("<p></p><p>Please find Below The Detailed Status Of Health Check of Siebel Server</p><p></p>");
		out.newLine();
		out.newLine();
		out.write("<p></p><p><b>List and status of Servers on Environment</b></p><p></p>");
		out.newLine();
		out.write("<table border=\"1\" cellspacing =\"0\">");
		out.write("<tr><td><b>Siebel Server</b></td><td><b>Host/Component Name</b></td><td><b>Display State</b></td><td><b>Component Status</b></td></tr>");
		
		while ((aLine = in.readLine()) != null) 
		{
			if (aLine.startsWith("SS_"))
			{
				String m[]=(aLine.split(","));
				server[totalServers] = m[0];
				out.write("<tr><td>"+server[totalServers].trim()+"</td><td>"+m[2]+"</td>");
				totalServers++;
				if((m[5].trim()).equals("Running") || (m[5].trim()).equals("Online") )
				{
					out.write("<td><b><Font color=\"Green\">"+m[5].trim()+"</Font></b></td>");
				}
				else if((m[5].trim()).equals("Not available") || (m[5].trim()).equals("Offline") || m[5].equals("Shutdown"))
				{
					out.write("<td><b><Font color=\"Red\">"+m[5].trim()+"</Font></b></td>");
					errorFlag++;				
				}
				else
				{
					out.write("<td>"+m[5].trim()+"</td>");
				}


				if((m[6].trim()).equals("Running") || (m[6].trim()).equals("Online") )
				{
					out.write("<td><b><Font color=\"Green\">"+m[6].trim()+"</Font></b></td>");
				}
				else if((m[6].trim()).equals("Not available") || (m[6].trim()).equals("Offline") || m[6].equals("Shutdown"))
				{
					out.write("<td><b><Font color=\"Red\">"+m[6].trim()+"</Font></b></td>");
					errorFlag++;
				}
				else
				{
					out.write("<td><b>"+m[6].trim()+"</b></td>");
				}
				out.write("</tr>");
			}
		}
		out.write("</table>");
		in.close();
		if(errorFlag>0)
		{
		FileWriter fstreamErr = new FileWriter(destErr, true);
		BufferedWriter outErr = new BufferedWriter(fstreamErr);
		outErr.newLine();
		outErr.write("<p> There is some error !! Some of the Servers are not Running</p>");
		out.write("<p></p>");
		out.write("<p><b><font color=\"Red\">WARNING : The Pipeline was broken because some of the servers were not online/running .</font></b></p>");
		out.write("<p></p>");

		outErr.close();
		}

		out.newLine();
		out.write("<p></p>");
		out.write("<p></p>");


		for(int x=0;x<totalServers;x++)
		{
			String source1 = dir.getCanonicalPath() + File.separator + "listcomp.txt";
			File fin1 = new File(source1);
			FileInputStream fis1 = new FileInputStream(fin1);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(fis1));
			aLine = null;
			out.write("<p><b>List and Status of all the components on "+server[x].trim()+" server</b></p><p></p>");
			out.write("<table border=\"1\" cellspacing =\"0\">");
			out.write("<tr><td><b>Siebel Server</b></td><td><b>Component Name</b></td><td><b>DISP_STATE</b></td><td><b>SBLSRVR_STATE</b></td><td><b>Running Tasks</b></td><td><b>Max Tasks</b></td></tr>");
			while ((aLine = in1.readLine()) != null) 
			{	
				if (aLine.startsWith(server[x].trim()))
				{
					String m[]=(aLine.split(","));
					if((m[6].trim()).equals("Not available") || (m[6].trim()).equals("Offline") || (m[6].trim()).equals("Shutdown") || (m[6].trim()).equals("Not Online"))					
					{
						out.write("<tr><td>"+m[0].trim()+"</td><td>"+m[2]+"</td><td>"+m[5].trim()+"</td><td><b><Font color=\"Red\">"+m[6].trim()+"</Font></b></td><td>"+m[8].trim()+"</td><td>"+m[9].trim()+"</td></tr>");
					}
				}
			}
			in1.close();				

			File fin1a = new File(source1);
			FileInputStream fis1a = new FileInputStream(fin1a);
			BufferedReader in1a = new BufferedReader(new InputStreamReader(fis1a));
			aLine = null;
			while ((aLine = in1a.readLine()) != null) 
			{	
				if (aLine.startsWith(server[x].trim()))
				{
					String m[]=(aLine.split(","));
					if(!(m[6].trim()).equals("Not available") && !(m[6].trim()).equals("Offline") && !(m[6].trim()).equals("Shutdown") && !(m[6].trim()).equals("Not Online"))										
					{
						out.write("<tr><td>"+m[0].trim()+"</td><td>"+m[2]+"</td><td>"+m[5].trim()+"</td>");
						if((m[6].trim()).equals("Running") || (m[6].trim()).equals("Online") )
						{
							out.write("<td><b><Font color=\"Green\">"+m[6].trim()+"</Font></b></td>");
						}
						else
						{
							out.write("<td>"+m[6].trim()+"</td>");
						}
						out.write("<td>"+m[8].trim()+"</td><td>"+m[9].trim()+"</td></tr>");
					}
				}
			}
			out.write("</table>");
			in1.close();				
		
		}
		out.write("<p></p><p></p>");

		String source3 = dir.getCanonicalPath() + File.separator + "rcrspool.txt";
		File fin3 = new File(source3);
		FileInputStream fis3 = new FileInputStream(fin3);
		BufferedReader in3 = new BufferedReader(new InputStreamReader(fis3));
		aLine = null;
		out.write("<p><b>List and Status of all the Running RCRs on SVP Environment</b></p><p></p>");
		out.write("<table border=\"1\" cellspacing =\"0\">");
		out.write("<tr><td><b>RCR Id</b></td><td><b>RCR Status</b></td><td><b>RCR Name</b></td><td><b>Task Id</b></td><td><b>Task Status</b></td><td><b>Task Scheduled Start</b></td><td><b>Task Actual Start</b></td><td><b>Task End</b></td></tr>");
		while ((aLine = in3.readLine()) != null) 
		{	
			String m[]=(aLine.split(","));

			if(m[0].trim().equals("Job"))
			{
				out.write("<tr>");			
				for(int y=1;y<4;y++)
				{
					out.write("<td>"+m[y].trim()+"</td>");
				}
			}
			else
			{
				out.write("<tr><td></td><td></td><td></td>");

				for(int y=1;y<6;y++)
				{
					out.write("<td>"+m[y].trim()+"</td>");
				}
			}
			out.write("</tr>");			

		}
		out.write("</table>");
		in3.close();				

		String source4 = dir.getCanonicalPath() + File.separator + "queuedmailspool.txt";
		File fin4 = new File(source4);
		FileInputStream fis4 = new FileInputStream(fin4);
		BufferedReader in4 = new BufferedReader(new InputStreamReader(fis4));
		aLine = null;
		out.write("<p><b>List of all the Queued Mails on SVP Environment</b></p><p></p>");
		out.write("<table border=\"1\" cellspacing =\"0\">");
		out.write("<tr><td><b>Activity Id</b></td><td><b>Status</b></td><td><b>Created</b></td><td><b>Subject</b></td><td><b>To</b></td><td><b>From</b></td></tr>");
		while ((aLine = in4.readLine()) != null) 
		{	
			String m[]=(aLine.split(","));
			out.write("<tr>");
			for(int z=0;z<6;z++)
			{
				out.write("<td>"+m[z].trim()+"</td>");
			}
			out.write("</tr>");
		}
		out.write("</table>");
		in4.close();	



		String source5 = dir.getCanonicalPath() + File.separator + "tablespace.txt";
		File fin5 = new File(source5);
		FileInputStream fis5 = new FileInputStream(fin5);
		BufferedReader in5 = new BufferedReader(new InputStreamReader(fis5));
		aLine = null;
		out.write("<p><b>Table Space on SVP Environment</b></p><p></p>");
		out.write("<table border=\"1\" cellspacing =\"0\">");
		out.write("<tr><td><b>Table Space</b></td><td><b>Total Space (MB)</b></td><td><b>Used Space (MB)</b></td><td><b>Free Space (MB)</b></td><td><b>Percentage Free</b></td></tr>");
		while ((aLine = in5.readLine()) != null) 
		{	
			String m[]=(aLine.split(","));
			out.write("<tr>");
			for(int z=0;z<5;z++)
			{
				out.write("<td>"+m[z].trim()+"</td>");
			}
			out.write("</tr>");
		}
		out.write("</table>");
		in5.close();				

		String source6 = dir.getCanonicalPath() + File.separator + "diskspace.txt";
		File fin6 = new File(source6);
		FileInputStream fis6 = new FileInputStream(fin6);
		BufferedReader in6 = new BufferedReader(new InputStreamReader(fis6));
		aLine = null;
		out.write("<p><b>Disk Space on SVP Servers</b></p><p></p>");
		out.write("<table border=\"1\" cellspacing =\"0\">");
		out.write("<tr><td><b>Server Name</b></td><td><b>Drive</b></td><td><b>Disk Size (G.B.)</b></td><td><b>Disk Free Space (G.B.)</b></td><td><b>% Free</b></td></tr>");
		while ((aLine = in6.readLine()) != null) 
		{	
			String m[]=(aLine.split("EOL"));
			int records = m.length;
			float value; 
			int z;
			for (int x=0;x<records;x++)
			{
				String d[]=(m[x].split(","));
				out.write("<tr>");
				for(z=0;z<d.length;z++)
				{

					if(z==4)
					{

						value = Float.valueOf(d[z]);

						if(value>15.00)
							out.write("<td><font color=\"Green\">"+value+"</font></td>");							
						else
							out.write("<td><font color=\"Red\">"+value+"</font></td>");							

					}
					else
						out.write("<td>"+d[z].trim()+"</td>");						
				}
				out.write("</tr>");

			
			}
			
		}
		out.write("</table>");
		in6.close();	
		out.write("<p></p>");

		for(int x=0;x<totalServers;x++)
		{
			String source2 = dir.getCanonicalPath() + File.separator + "listtask.txt";
			File fin2 = new File(source2);
			FileInputStream fis2 = new FileInputStream(fin2);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(fis2));
			aLine = null;
			out.write("<p><b>List and Status of all the tasks on "+server[x].trim()+" server</b></p><p></p>");
			out.write("<table border=\"1\" cellspacing =\"0\">");
			out.write("<tr><td><b>Siebel Server</b></td><td><b>Comp Name</b></td><td><b>Task Id</b></td><td><b>Task Status</b></td><td><b>Task Display State</b></td><td><b>Task Start</b></td><td><b>Task End</b></td></tr>");
			while ((aLine = in2.readLine()) != null) 
			{	
				if (aLine.startsWith(server[x].trim()))
				{
					String m[]=(aLine.split(","));
					if((m[4].trim()).equals("Not available") || (m[4].trim()).equals("Offline") || (m[4].trim()).equals("Shutdown") || (m[4].trim()).equals("Exited with error"))					
					{
						out.write("<tr><td>"+m[0].trim()+"</td><td>"+m[1].trim()+"</td><td>"+m[2]+"</td><td><b><Font color=\"Red\">"+m[4].trim()+"</Font></b></td><td>"+m[5].trim()+"</td><td>"+m[6]+"</td><td>"+m[7]+"</td></tr>");
					}
				}	
			}
			in2.close();				
			
			File fin2a = new File(source2);
			FileInputStream fis2a = new FileInputStream(fin2a);
			BufferedReader in2a = new BufferedReader(new InputStreamReader(fis2a));
			aLine = null;
			while ((aLine = in2a.readLine()) != null) 
			{	
				if (aLine.startsWith(server[x].trim()))
				{
					String m[]=(aLine.split(","));
					if(!(m[4].trim()).equals("Not available") && !(m[4].trim()).equals("Offline") && !(m[4].trim()).equals("Shutdown") && !(m[4].trim()).equals("Exited with error"))					
					{
						out.write("<tr><td>"+m[0].trim()+"</td><td>"+m[1].trim()+"</td><td>"+m[2]+"</td>");
						if((m[4].trim()).equals("Running") || (m[4].trim()).equals("Online") || (m[4].trim()).equals("Completed") )
						{
							out.write("<td><b><Font color=\"Green\">"+m[4].trim()+"</Font></b></td>");
						}
						else
						{
							out.write("<td>"+m[4].trim()+"</td>");
						}
						out.write("<td>"+m[5].trim()+"</td><td>"+m[6]+"</td><td>"+m[7]+"</td></tr>");
					}
				}	
			}
			out.write("</table>");
			in2a.close();				
			
			
			
		}
		out.write("<p></p><p></p>");
		
		out.write("<p>Note : For Detailed information check The Log Files Attached with This Mail</p>");
		out.newLine();
		out.newLine();
		out.write("<p></p>");
		out.write("<p></p>");
		out.write("<p></p>");
		out.write("<p></p>");
		out.newLine();
		out.write("Regards,");
		out.newLine();
		out.write("<p></p>");
		out.write("Siebel Admin");
		out.close();
	}
}