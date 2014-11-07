package com.danielbchapman.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Connector
{
	public static void main(String[] args)
	{
		File file = new File("out.txt");
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		try
		{
			writer.write("Usage: java HTTPSClient host\r\n");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		for(int i = 0; i < 50; i++)
		{
			int port = 443; // default https port
			String host = "red001.mail.microsoftonline.com";
			System.out.println("Starting Connection");
			try
			{
				SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

				SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
				socket.setSoTimeout(30000);
				// read response
				System.out.println("reading stream");
				long start = System.currentTimeMillis();
				writer.write("Connection ");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				int c;
				while((c = in.read()) != -1)
				{
					System.out.write(c);
				}
				System.out.println("Ending Stream...");
				in.close();
				socket.close();
				writer.write(" Successful elapsed time is");
				writer.write("" + (System.currentTimeMillis() - start) / 1000);
				writer.write("\r\n");
			}
			catch(IOException e)
			{
				try
				{
					writer.write("Connection Failed: " + e.getMessage());
					writer.write("\r\n");
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				System.err.println(e);
			}

		}

		try
		{
			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Completed");

		System.exit(0);
	}

}
