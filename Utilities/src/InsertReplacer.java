import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class InsertReplacer
{
	public static void main(String ... args) throws IOException
	{
		String line = null;
		BufferedReader in = new BufferedReader(new FileReader(new File("insert.sql")));
		PrintWriter out = new PrintWriter(new FileWriter(new File("out.sql")));
		while((line = in.readLine()) != null)
		{
			System.out.println("processing: " + line);
			String regex = line
					.replaceAll(",0,", ",false,")
					.replaceAll(",1,", ",true,")
					.replaceAll(",0,", ",false,")//Capturing Groups
					.replaceAll(",1,", ",true,");
			out.println(regex);
		}
		
		out.flush();
			
	}
}
