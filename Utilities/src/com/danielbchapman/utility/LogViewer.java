package com.danielbchapman.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * A crap log viewer to replace the lack of use in IE.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Aug 15, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class LogViewer extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e)
		{
		}
		catch(InstantiationException e)
		{
		}
		catch(IllegalAccessException e)
		{
		}
		catch(UnsupportedLookAndFeelException e)
		{
		}
		new LogViewer();
	}

	private JPanel subPanel;
	private JTextPane text;
	private File file;

	public LogViewer()
	{
		initilize();
	}

	public void initilize()
	{
		setSize(600, 400);
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Properties props = new Properties();
		File lastSave = new File("lastSave.properties");
		if (lastSave.exists())
			try
			{
				props.load(new FileInputStream(lastSave));
				String fileName = (String) props.get("lastLog");
				if(fileName != null)
					file = new File(fileName);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(file);
		
		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			System.exit(-1);

		file = chooser.getSelectedFile();
		setTitle("Reading" + file.getName());
		props.setProperty("lastLog", file.getAbsolutePath());
		try
		{
			props.store(new FileOutputStream(lastSave), "File saved");
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		if (file == null)
			System.exit(-1);

		if (subPanel == null)
		{
			subPanel = new JPanel();
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.anchor = GridBagConstraints.PAGE_START;

			subPanel.setLayout(new GridBagLayout());
			subPanel.setBackground(Color.red);
			text = new JTextPane();
			try
			{				
				text.setFont(new java.awt.Font("monospaced", 0, 12));	
			}
			catch(Exception e)
			{//Don't really care
				e.printStackTrace();
			}
			
			JScrollPane pane = new JScrollPane(text);
			subPanel.add(pane, gbc);
			
			
			JButton refresh = new JButton("Refresh");
			
			refresh.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							refresh();
						}
					}

			);
			refresh();
			setLayout(new BorderLayout());
			add(refresh, BorderLayout.NORTH);
			add(subPanel, BorderLayout.CENTER);
		}
		this.setVisible(true);

	}

	public void refresh()
	{
		readLog();
	}

	private void readLog()
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String line;
			StringBuilder builder = new StringBuilder();
			for (; (line = reader.readLine()) != null;)
			{
				builder.append(line);
				builder.append("\n");
			}
			text.setText(builder.toString());
		}
		catch (FileNotFoundException e)
		{
			System.exit(-1);
		}
		catch (IOException e)
		{
			System.exit(-1);
		}
		finally
		{
			if (reader != null)
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();

				}
		}
	}
}
