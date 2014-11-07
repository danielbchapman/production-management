import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompareTelevisions
{
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		double width = 1920;
		double height = 1080;
		double ratio = Double.valueOf(height / width);
		System.out.println("Input a value in inches (ratio)" + ratio);

		try
		{
			while((line = in.readLine()) != null)
			{
				if("exit".equals(line))
					System.exit(0);

				try
				{
					Double value = Double.valueOf(line);
					double tangent = height / width;
					double angle = Math.tanh(tangent);
					double sine = Math.sin(angle);
					double cosine = Math.cos(angle);
					double newWidth = Double.valueOf(cosine * value);
					double newHeight = Double.valueOf(sine * value);
					System.out.print("Width = ");
					System.out.print(newWidth);
					System.out.print(", Height = ");
					System.out.print(newHeight);
					System.out.println(", Ratio is: " + newHeight / newWidth);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}

}
