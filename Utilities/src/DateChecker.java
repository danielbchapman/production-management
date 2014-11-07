import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class DateChecker extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String ... args)
	{
		new DateChecker().setVisible(true);
	}
	
	public DateChecker()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("Date Checker");
		
		setLayout(new FlowLayout());
		final JTextField input = new JTextField("input a number");
		JButton convert = new JButton("Convert");
		final JLabel output = new JLabel("Enter a number and convert");
		
		add(input);
		add(output);
		add(convert);
		
		convert.addActionListener(new ActionListener()
		{

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Date d = new Date(Long.valueOf(input.getText()));
					output.setText(d.toGMTString());
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
				
			}
			
		});
		
		pack();
	}

}
