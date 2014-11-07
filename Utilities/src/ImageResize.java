import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.DirectoryChooserBuilder;
import javafx.stage.Stage;

import javax.imageio.ImageIO;


public class ImageResize extends Application
{
	public static void main(String ... args)
	{
		Application.launch();
	}
	
	public static void resizeImage(File source, File target) throws IOException
	{
		BufferedImage sourceImage = ImageIO.read(source);
		
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		
		if(width > height)
		{
			Double ratio = Double.valueOf(800) / Double.valueOf(width);
			width = 800;
			Double newHeight = height * ratio;
			height = newHeight.intValue(); 
		}
		else
		{
			Double ratio = Double.valueOf(800) / Double.valueOf(height);
			height = 800;
			Double newWidth = width * ratio;
			width = newWidth.intValue();	
		}
		System.out.println("Resizing: " + sourceImage.getWidth() + ", " + sourceImage.getHeight() +  "] to " + width + ", " + height +"]");
		
	  BufferedImage resize = new BufferedImage(width, height, sourceImage.getType());
		Graphics2D g2d = resize.createGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(sourceImage, 0, 0, width, height, null);
		g2d.dispose();
		
		ImageIO.write(resize, "jpg", target);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		HBox root = new HBox();
		DirectoryChooser  fc = 
				DirectoryChooserBuilder
					.create()
					.title("Select a Folder")
					.build();
		
		Scene scene = new Scene(root);
		stage.show();
		File directory = fc.showDialog(stage);
		
		if(directory != null)
		{
			File[] files = directory.listFiles();
			
			File subDir = new File(directory.getAbsolutePath() + File.separator + "resized");
			subDir.mkdirs();
			
			for(File f : files)
				if(f != null && !f.isDirectory())
				{
					String name = f.getName();
					try
					{
						resizeImage(f, new File(subDir.getAbsolutePath() + File.separator + name));	
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
		}
		
		System.out.println("Exiting...");
		System.exit(0);
	}
	
}
