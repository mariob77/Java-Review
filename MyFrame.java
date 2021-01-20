/*Name: Michael Evans
 Course: CNT 4714- Spring 2021
 Assignment title: Project 1 - Event-driven-Enterprise Simulation
 Date: Sunday January 31, 2021
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener{

	// Fields required for main gui frame
	JButton button1;  // must be global or else only the constructor would have access to it
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	JButton button6;
	JTextField textbox1;
	JTextField textbox2;
	JTextField textbox3;
	JTextField textbox4;
	JTextField textbox5;
	String ordernumber = "0";  // current order, how many items have been successfully processed
	String totalnumofitems; // total amount of items in order
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	
	
	// Fields for other prompts
	JFrame notinfileframe;
	JFrame outofstockframe;
	JFrame itemaddedframe;
	JFrame vieworderframe;
	JButton okbutton;
	JButton okbutton2;
	JButton itemaddedok;
	JButton vieworderokbutton;
	
	//Global variables for description box
	String description;
	String quantity;
	String price;
	String PercentDisc; 
	String currentSubtotal = "0";
	String grandtotal = currentSubtotal;
	
	
	// Receipt and file output fields
	String [] receipt = new String [100];
	
	
	public MyFrame() throws FileNotFoundException{
		
		button1 = new JButton();
		button1.addActionListener(this); // labda expression that reacts to button input
		button1.setText("Process Item #1");  // actually gives the button a label
		button1.setFocusable(false);  //gets rid of ugly inner button lining
		
		button2 = new JButton();
		button2.addActionListener(this); 
		button2.setText("Confirm Item #1");  
		button2.setFocusable(false);  
		
		button3 = new JButton();
		button3.addActionListener(this); 
		button3.setText("View Order");  
		button3.setFocusable(false);  
		
		button4 = new JButton();
		button4.addActionListener(this); 
		button4.setText("Finish Order");  
		button4.setFocusable(false);  
		
		button5 = new JButton();
		button5.addActionListener(this); 
		button5.setText("New Order"); 
		button5.setFocusable(false);  
		
		button6 = new JButton();
		button6.addActionListener(this);
		button6.setText("Exit"); 
		button6.setFocusable(false);  
		
		textbox1 = new JTextField();
		textbox2 = new JTextField();
		textbox3 = new JTextField();
		textbox4 = new JTextField();
		textbox5 = new JTextField();
		textbox5 = new JTextField();
		// textbox5.setPreferredSize(new Dimension(250,40));
		
		JLabel label1 = new JLabel();
		label1.setText("Enter number of items in this order:");
		label2 = new JLabel();
		label2.setText("Enter item ID for Item #1:");
		label3 = new JLabel();
		label3.setText("Enter quantity for Item #1:");
	    label4 = new JLabel();
		label4.setText("Item #1 info");
	    label5 = new JLabel();
		label5.setText("Order subtotal for 0 item(s):");
		
		// Panels and other artistic parts of UI
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.blue);
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.green);
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.red);
		
		this.setTitle("Nile Dot Com - Spring 2021");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(650,250);
		this.setResizable(true);
		
		
		JPanel bottomarea = new JPanel();
		JPanel centerarea = new JPanel();
		centerarea.setLayout(new GridLayout(5,2));
		bottomarea.setLayout(new FlowLayout());
		bottomarea.setBackground(Color.blue);
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2,BorderLayout.WEST);
		this.add(panel3,BorderLayout.EAST);
		this.add(bottomarea, BorderLayout.SOUTH);
		this.add(centerarea, BorderLayout.CENTER);
		centerarea.add(label1);
		centerarea.add(textbox1);
		centerarea.add(label2);
		centerarea.add(textbox2);
		centerarea.add(label3);
		centerarea.add(textbox3);
		centerarea.add(label4);
		centerarea.add(textbox4);
		centerarea.add(label5);
		centerarea.add(textbox5);
		
		bottomarea.add(button1);
     	bottomarea.add(button2);
		bottomarea.add(button3);
		bottomarea.add(button4);
		bottomarea.add(button5);
		bottomarea.add(button6);
		
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);

		this.setVisible(true);
	}
	
	public static void main(String [] args) throws FileNotFoundException
	{
		new MyFrame();
	}
	
	@Override  //Overrides element delared in superclass, helps prevent errors
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button1)
		{
		  int code = 0;  
		  try 
		  {
			 code = checkInventory();
		  } 
		  catch (FileNotFoundException e1)
		  {
			e1.printStackTrace();
		  }

		  if(code == 2 || code == 1)
		  {
			  try 
			  {
				getitemDescription();
			   } 
			  catch (FileNotFoundException e1)
			 {
				e1.printStackTrace();
			 }
		  }
		  totalnumofitems = textbox1.getText();
		  label5.setText("Order subtotal for " + textbox1.getText() + " item(s):");
		  button1.setEnabled(false);
		  button2.setEnabled(true);
		  if(code != 0)
		  {
			  incrementerpart1();
		  }
		}
		if(e.getSource()==button2)
		{
			int code = 0;
			try 
			{
				code = checkInventory();
			} 
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			if(code == 0 || code == 1)
			{
				if(code == 0) notinfile();
				if(code == 1) outofstock();
				button1.setEnabled(true);
				button2.setEnabled(false);
			}
			
			if(code == 2)
			{
				incrementerpart2();  
				itemaddedframe();
				button1.setEnabled(true);
				button2.setEnabled(false);
				button3.setEnabled(true);
				incrementerpart1();
				grandtotalcalculandset();
				additemtoreceipt();
				if(totalnumofitems.equals(ordernumber)) nomoreitems();
			}
		}
		if(e.getSource()==button3)
		{
			vieworderframe();  
		}
		if(e.getSource()==button4)
		{
			button4.setEnabled(false);  
		}
		if(e.getSource()==button5)
		{
			newOrder();
		}
		if(e.getSource()==button6)
		{  
			this.dispose();
		}
		if(e.getSource()==okbutton)
		{
			notinfileframe.dispose();
		}
		if(e.getSource()==okbutton2)
		{
			outofstockframe.dispose();
		}
		if(e.getSource()==itemaddedok)
		{
			itemaddedframe.dispose();
		}
		if(e.getSource()==vieworderokbutton)
		{
			vieworderframe.dispose();
		}
	}
	
	public int checkInventory() throws FileNotFoundException
	{
		File inventory = new File("inventory.txt");
	    Scanner file = new Scanner(inventory);
		file.useDelimiter(", *");
		int code = 0;
		
		 while(file.hasNext())
		 {			
			 if(file.next().equals(textbox2.getText()))
			{
				code = 1;
				file.next();
				if(file.next().equals("true")) code = 2;
				return code;
			}
			file.nextLine();
		}
		 return code;
	}
	
	public void getitemDescription() throws FileNotFoundException
	{
		File inventory = new File("inventory.txt");
		Scanner file = new Scanner(inventory);
		file.useDelimiter(", *");
		String itemName = textbox2.getText();
		
		while(file.hasNext())
		{
			if(file.next().equals(itemName))
			{
			    description = file.next();
				file.next();
				file.useDelimiter(",|\\n");
				price = file.next().trim();
				quantity = textbox3.getText();
				PercentDisc = determineDiscount(quantity);
				currentSubtotal = subtotal(quantity,price,PercentDisc);
			    textbox4.setText(itemName + " " + description + " $" + price + " " + quantity + " " + PercentDisc + "% $" + currentSubtotal);
				break;
			}
			file.nextLine();
		}
		
	}
	public String determineDiscount(String textbox3)
	{
		String nodiscount = "0";
		String tendiscount = "10";
		String fifdiscount= "15";
		String twentdiscount = "20";
		
		if (textbox3.equals("1") || textbox3.equals("2") || textbox3.equals("3") || textbox3.equals("4")) return nodiscount;
		if (textbox3.equals("5") || textbox3.equals("6") || textbox3.equals("7") || textbox3.equals("8") || textbox3.equals("9")) return tendiscount;
		if (textbox3.equals("10") || textbox3.equals("11") || textbox3.equals("12") || textbox3.equals("13") || textbox3.equals("14")) return fifdiscount;
		
		
		return twentdiscount;
	}
	
	public  String subtotal(String quantity, String price, String PercentDisc)
	{
		double localquantity = Double.parseDouble(quantity);
		double localprice = Double.parseDouble(price);
		double Percentdisc = Double.parseDouble(PercentDisc);
		if (Percentdisc == 0) return String.valueOf(localprice*localquantity);
		double subtotal = localquantity*(localprice - (localprice*(Percentdisc/100)));
		return String.valueOf(subtotal);
	}
	
	public void incrementerpart1()
	{
		int intfornumber = Integer.parseInt(ordernumber);
		intfornumber++;
		String preordernumber = String.valueOf(intfornumber);
		label2.setText("Enter item ID for Item #" + preordernumber + ":");
		label3.setText("Enter quantity for Item #" + preordernumber + ":");
	}
	
	public void incrementerpart2()
	{
		int intfornumber = Integer.parseInt(ordernumber);
		intfornumber++;
		ordernumber = String.valueOf(intfornumber);
		label4.setText("Item #" + ordernumber + " info:");
		intfornumber++;
		button1.setText("Process Item #" + String.valueOf(intfornumber));
		button2.setText("Confirm Item #" + String.valueOf(intfornumber));
	}
	
	public void grandtotalcalculandset()
	{
		double grand = Double.parseDouble(grandtotal);
		double current = Double.parseDouble(currentSubtotal);
		grandtotal = String.valueOf(grand+current);
		textbox5.setText(grandtotal);
	}
	
	public void additemtoreceipt()
	{
		int ordernumber = Integer.parseInt(this.ordernumber);
		receipt[ordernumber-1] = ordernumber + ". " + textbox2.getText() + " " + description + " $" + price + " " + quantity + " " + PercentDisc + "% $" + currentSubtotal;
	}
	
	public void nomoreitems()
	{
		label2.setText("");
		label3.setText("");
		textbox2.setText("");
		textbox3.setText("");
		button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(true);
		button4.setEnabled(true);
		button5.setEnabled(true);
		button6.setEnabled(true);
		/*for(int i = 0; i < 50; i++)
		{
			System.out.println(receipt[i]);
		}*/
	}
	public void vieworderframe()
	{
		vieworderframe = new JFrame();
		vieworderframe.setLayout(new BorderLayout());
		
		JList locallist = new JList(receipt);
		vieworderframe.setTitle("Message");
		vieworderframe.add(locallist, BorderLayout.CENTER);
		System.out.println(Integer.parseInt(ordernumber));
		 vieworderframe.setSize(450,200);
		vieworderframe.setResizable(true);
		vieworderframe.setVisible(true);
		
		JPanel panelforbutton = new JPanel();
		panelforbutton.setLayout(new FlowLayout());
		panelforbutton.setBackground(Color.blue);
		vieworderokbutton = new JButton();
	    vieworderokbutton.addActionListener(this);
		vieworderokbutton.setText("Ok");
		panelforbutton.add(vieworderokbutton);
		vieworderframe.add(panelforbutton, BorderLayout.SOUTH);
	}
	
	public void newOrder()
	{
		textbox1.setText("");
		textbox2.setText("");
		textbox3.setText("");
		textbox4.setText("");
		textbox5.setText("");
		label2.setText("Enter item ID for Item #1:");
		label3.setText("Enter quantity for Item #1:");
		label4.setText("Item #1 info");
		label5.setText("Order subtotal for 0 item(s):");
		button1.setEnabled(true);
		button1.setText("Process Item #1");
		button2.setEnabled(false);
		button2.setText("Process Item #1");
		button3.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(true);
		button6.setEnabled(true);
		currentSubtotal = "0";
		grandtotal = "0";
		for(int i = 0; i < Integer.parseInt(this.ordernumber); i++)
		{
			receipt[i] = null;
		}
		ordernumber = "0";
	}
	
	public void notinfile()
	{
		notinfileframe = new JFrame();
		JLabel locallabel = new JLabel();
		okbutton = new JButton();
		okbutton.addActionListener(this);
		notinfileframe.setTitle("Message");
		notinfileframe.setLayout(new GridLayout(2,1));
		notinfileframe.setSize(300,300);
		notinfileframe.setResizable(true);
		locallabel.setText("Item ID " + textbox2.getText() + " not in file.");
		okbutton.setText("OK");
		notinfileframe.add(locallabel);
		notinfileframe.add(okbutton);
		notinfileframe.setVisible(true);
	}
	
	public void outofstock()
	{
		outofstockframe = new JFrame();
		JLabel locallabel = new JLabel();
		okbutton2 = new JButton();
		okbutton2.addActionListener(this);
		outofstockframe.setTitle("Message");
		outofstockframe.setLayout(new GridLayout(2,1));
		outofstockframe.setSize(300,300);
		outofstockframe.setResizable(true);
		locallabel.setText("Sorry...that item is out of stock please try another item.");
		okbutton2.setText("OK");
		outofstockframe.add(locallabel);
		outofstockframe.add(okbutton2);
		outofstockframe.setVisible(true);
	}
	
	public void itemaddedframe()
	{
		itemaddedframe = new JFrame();
		JLabel locallabel = new JLabel();
		itemaddedok = new JButton();
		itemaddedok.addActionListener(this);
		itemaddedframe.setTitle("Message");
		itemaddedframe.setLayout(new GridLayout(2,1));
		itemaddedframe.setSize(300,300);
		itemaddedframe.setResizable(true);
		locallabel.setText("Item #" + ordernumber + " Added.");
		itemaddedok.setText("OK");
		itemaddedframe.add(locallabel);
		itemaddedframe.add(itemaddedok);
		itemaddedframe.setVisible(true);
	}
	
}
