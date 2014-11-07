package com.danielbchapman.utility;

import javax.swing.JFrame;

public class HashCollector extends JFrame
{
	// private HashMap<String, String> names = new HashMap<String, String>();
	// private JButton process = new JButton();
	// private static String fileName = "hashCollection.txt";
	//
	// public static void main(String[] args)
	// {
	//
	// HashCollector hc = new HashCollector();
	// hc.setLocation(50, 50);
	// hc.setSize(300, 300);
	// hc.setVisible(true);
	// }
	//
	// public HashCollector()
	// {
	// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//
	// this.addWindowListener(new WindowListener()
	// {
	// @Override
	// public void windowActivated(WindowEvent e)
	// {
	// }
	//
	// @Override
	// public void windowClosed(WindowEvent e)
	// {
	// writeFile();
	// }
	//
	// @Override
	// public void windowClosing(WindowEvent e)
	// {
	// }
	//
	// @Override
	// public void windowDeactivated(WindowEvent e)
	// {
	// }
	//
	// @Override
	// public void windowDeiconified(WindowEvent e)
	// {
	// }
	//
	// @Override
	// public void windowIconified(WindowEvent e)
	// {
	// }
	//
	// @Override
	// public void windowOpened(WindowEvent e)
	// {
	// }
	//
	// });
	//
	// init();
	// }
	//
	// public void init()
	// {
	// readFile();
	// this.setTitle("Hash Collector");
	// this.setLayout(new GridLayout());
	// this.add(input);
	// this.add(add);
	// add.addActionListener(
	// new ActionListener()
	// {
	//
	// @Override
	// public void actionPerformed(ActionEvent e)
	// {
	// String name
	//
	// }
	//
	// }
	// );
	// }
	//
	// public void readFile()
	// {
	// try
	// {
	// BufferedReader reader = new BufferedReader(new FileReader(fileName));
	// String line;
	// while((line = reader.readLine()) != null)
	// if(line.trim().length() > 0)
	// hash.add(line);
	//
	// }
	// catch(FileNotFoundException e)
	// {
	// e.printStackTrace();
	// System.exit(-1);
	// }
	// catch(IOException e)
	// {
	// e.printStackTrace();
	// System.exit(-1);
	// }
	// }
	//
	// public void writeFile()
	// {
	// try
	// {
	// BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	// for(String s : hash)
	// {
	// writer.write(s);
	// writer.write("\r\n");
	// }
	// writer.flush();
	// writer.close();
	// }
	// catch(IOException e)
	// {
	// e.printStackTrace();
	// }
	// }
}
