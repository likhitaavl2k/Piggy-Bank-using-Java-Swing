package atm_managing;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.text.*;
import java.util.Scanner;

public class BankAccount extends javax.swing.JFrame implements Serializable 
{
	// inputs
	JFrame frame = new JFrame("PiggyPal");
	JPanel up, outer, contentPane1,  contentPane2, mainPane, contentPane11;
	JPanel buttonsPanel1;
	JLabel amtJLabel;
	JTextField amtJTextField;
	JFileChooser fc;
	// outputs
	JLabel statusJLabel;
	JTextField statusJTextField;
	JLabel balJLabel;
	JTextField balJTextField;
	JLabel availbalJLabel;
	JTextField availbalJTextField;
	// controls
	JButton depositJButton, withdrawJButton, clearJButton, clearAllJButton, closeJButton;

	// variables
	double amt, bal, check;
	String status;

	// object classes	
	DecimalFormat decimalFormat;
	Account account;
	CardLayout co;

	private String sLogin;
	private char[] sPass;
	private char[] svPass;
	double availbalance;

	public BankAccount() 
	{
		co = new CardLayout();
		outer = new JPanel();
		mainPane = new JPanel();
		contentPane1 = new JPanel();
		contentPane11 = new JPanel();
		buttonsPanel1 = new JPanel();
		contentPane2 = new JPanel();
		up = new JPanel();

		setLayout(new BorderLayout());
		outer.setLayout(co);
		initComponents();
		createUserInterface();

		outer.add(mainPane);
		mainPane.add(buttonsPanel1);
		outer.add(contentPane1);
		outer.add(contentPane11);
		outer.add(contentPane2);

		add(outer, BorderLayout.CENTER);
		setSize(500, 500);
		setVisible(true);		
	}
	private void initComponents() 
	{
		setLayout(new BorderLayout());
		final JLabel label = new JLabel("Welcome to Piggybank",SwingConstants.CENTER); 
		label.setBounds(20,150, 200,50);

		final JTextField text = new JTextField();
		text.setBounds(50,20, 100,30);

		label.setFont(new Font("Papyrus", Font.ITALIC, 50));
		label.setText("Welcome To PiggyPal - The Best Ever MoneySaver");   

		mainPane.add(label);
		
		JButton b1 = new JButton("Login"); 
		b1.setBounds(50,300, 80,30);
		JButton b2 = new JButton("Sign up"); 	 
		b2.setBounds(160,240, 80,30);

		mainPane.setBackground(Color.PINK);

		try 
		{
			BufferedImage image = ImageIO.read(new File("F:\\piggy1.jpg"));
			JLabel label1 = new JLabel(new ImageIcon(image));
			mainPane.add(label1);
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		}

		buttonsPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel1.setBackground(Color.PINK);
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.YELLOW);
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.YELLOW);
		buttonsPanel1.add(b1);
		buttonsPanel1.add(b2);
		add(buttonsPanel1, BorderLayout.SOUTH);

		b1.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {       
				login();
				co.next(outer);
			}  
		});   
		b2.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {       

				register();
				co.next(outer);
				co.next(outer);
			}  
		}); 
	}

	public void login() {
		final JLabel label = new JLabel();            
		label.setBounds(500,150, 200,50); 

		final JPasswordField value = new JPasswordField();   
		value.setBounds(600,75,100,30); 

		JLabel l1=new JLabel("Username:");    
		l1.setBounds(520,20, 80,30);  

		JLabel l2=new JLabel("Pin:");    
		l2.setBounds(520,75, 80,30);

		JButton b1 = new JButton("Login"); 
		b1.setBounds(550,120, 80,30);
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.YELLOW);

		JButton b2 = new JButton("Back"); 	 
		b2.setBounds(660,120, 80,30);
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.YELLOW);

		final JTextField text = new JTextField();  
		text.setBounds(600,20, 100,30);   

		contentPane1.add(value); 
		contentPane1.add(l1); 
		contentPane1.add(label); 
		contentPane1.add(l2); 
		contentPane1.add(b1);
		contentPane1.add(b2);
		contentPane1.add(text);

		b1.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {       
				sLogin = text.getText();
				sPass = value.getPassword();

				String username = null;
				char[] pinNumber = null;


				String line;
				try 
				{
					FileInputStream file = new FileInputStream("F:\\PiggyPal.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(file));
					line = br.readLine();
					while(line!=null) {
						boolean isPresent = line.indexOf(sLogin) != -1 ? true : false;
						if(isPresent)
						{
							int index1=line.indexOf(sLogin);
							username = line.substring(index1, index1+4);
							pinNumber = (line.substring(index1+4, index1+8)).toCharArray();
							availbalance = Double.parseDouble(line.substring(index1+8,index1+13));

						}
						line=br.readLine();
					}
				} catch (FileNotFoundException e2) 
				{
					e2.printStackTrace();
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				int var1 = sLogin.compareTo(username);

				if(var1!=0 || (sPass.equals(pinNumber)))
					label.setText("Invalid login credentials"); 

				else if(sLogin.equals("") || sPass.equals("") || sPass.length<4 || sLogin.length()!=4)
				{
					label.setText("Invalid input"); 

					if(sLogin.length()!=4)
						JOptionPane.showMessageDialog(frame,"Username length:4 characters only!!","Warning",JOptionPane.WARNING_MESSAGE);
					if(sPass.length<4)
						JOptionPane.showMessageDialog(frame,"Minimum pin length:4","Warning",JOptionPane.WARNING_MESSAGE);
				}

				else {
					co.next(outer);
					co.next(outer);

				}
			}  
		}); 

		b2.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) { 
				co.previous(outer);
			}
		});
		contentPane1.setSize(1000,1000);    
		contentPane1.setLayout(null);    
		contentPane1.setVisible(true);

	}
	public void register() {
		final JLabel label = new JLabel();            
		label.setBounds(520,400,200,50); 

		final JPasswordField value = new JPasswordField();   
		value.setBounds(600,75,100,30); 


		JLabel l1=new JLabel("Username:");    
		l1.setBounds(520,20, 80,30);  

		JLabel l2=new JLabel("Pin:");    
		l2.setBounds(520,75, 80,30);

		JLabel l3=new JLabel("Confirm Pin:");    
		l3.setBounds(520,130, 80,30);

		final JPasswordField value1 = new JPasswordField();   
		value1.setBounds(600,130,100,30); 

		JButton b1 = new JButton("SignUp"); 
		b1.setBounds(550,160, 80,30);
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.YELLOW);

		JButton b2 = new JButton("Back"); 	 
		b2.setBounds(660,160, 80,30);
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.YELLOW);
		final JTextField text = new JTextField();  
		text.setBounds(600,20, 100,30);   

		contentPane11.add(value); 
		contentPane11.add(l1); 
		contentPane11.add(label); 
		contentPane11.add(l2);
		contentPane11.add(value1); 
		contentPane11.add(l3);
		contentPane11.add(b1);
		contentPane11.add(b2);
		contentPane11.add(text);

		b1.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e){       
				sLogin= text.getText();
				sPass =value.getPassword();
				svPass=value1.getPassword();

				availbalance = 0.00;

				if(sLogin.equals("") || sPass.equals("") || sPass.length<4 || (svPass.equals(sPass)) || sLogin.length()!=4) 
				{
					label.setText("Invalid input"); 
					if(sLogin.length()!=4)
						JOptionPane.showMessageDialog(frame,"Username length:4 characters only!!","Warning",JOptionPane.WARNING_MESSAGE);
					if(sPass.length<4)
						JOptionPane.showMessageDialog(frame,"Minimum pin length:4!!","Warning",JOptionPane.WARNING_MESSAGE);
					if((sPass.equals(svPass))==true)
						JOptionPane.showMessageDialog(frame,"Confirm pin properly!!","Warning",JOptionPane.WARNING_MESSAGE);
				}

				else 
				{
					try 
					{
						FileWriter fw = new FileWriter("F:\\PiggyPal.txt",true);
						fw.write(sLogin);
						fw.write(sPass);
						fw.close();
					} catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					co.next(outer);
				}

			}  
		});   

		b2.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) { 
				clearAllJButtonActionPerformed(e);
				co.previous(outer);
				co.previous(outer);
			}
		});
		
		contentPane11.setSize(500,500);    
		contentPane11.setLayout(null);    
		contentPane11.setVisible(true);
	}
	public void createUserInterface()
	{
		contentPane2.setBackground(Color.white);
		contentPane2.setLayout(null);
		//initialize
		// inputs
		amtJLabel = new JLabel();
		amtJLabel.setBounds(501, 1, 120, 120);
		amtJLabel.setFont(new Font("Default", Font.PLAIN, 12));
		amtJLabel.setText("Enter the amount:");
		amtJLabel.setHorizontalAlignment(JLabel.RIGHT);
		contentPane2.add(amtJLabel);

		amtJTextField = new JTextField();
		amtJTextField.setBounds(700, 50, 80, 20);
		amtJTextField.setFont(new Font("Default", Font.PLAIN, 12));
		amtJTextField.setHorizontalAlignment(JTextField.CENTER);
		amtJTextField.setEditable(true);
		contentPane2.add(amtJTextField);

		// outputs
		statusJLabel = new JLabel();
		statusJLabel.setBounds(550, 150, 120, 20);
		statusJLabel.setFont(new Font("Default", Font.PLAIN, 12));
		statusJLabel.setText("Account status:");
		statusJLabel.setHorizontalAlignment(JLabel.RIGHT);
		contentPane2.add(statusJLabel);

		statusJTextField = new JTextField();
		statusJTextField.setBounds(700, 150, 80, 20);
		statusJTextField.setFont(new Font("Default", Font.PLAIN, 12));
		statusJTextField.setHorizontalAlignment(JTextField.CENTER);
		statusJTextField.setEditable(false);
		contentPane2.add(statusJTextField);

		balJLabel = new JLabel();
		balJLabel.setBounds(550, 180, 120, 20);
		balJLabel.setFont(new Font("Default", Font.PLAIN, 12));
		balJLabel.setText("Current bal:");
		balJLabel.setHorizontalAlignment(JLabel.RIGHT);
		contentPane2.add(balJLabel);

		balJTextField = new JTextField();
		balJTextField.setBounds(700, 180, 80, 20);
		balJTextField.setFont(new Font("Default", Font.PLAIN, 12));
		balJTextField.setHorizontalAlignment(JTextField.CENTER);
		balJTextField.setEditable(false);
		contentPane2.add(balJTextField);

		availbalJLabel = new JLabel();
		availbalJLabel.setBounds(550, 400, 120, 20);
		availbalJLabel.setFont(new Font("Default", Font.PLAIN, 12));
		availbalJLabel.setText("Prev. bal:");
		availbalJLabel.setHorizontalAlignment(JLabel.RIGHT);
		contentPane2.add(availbalJLabel);

		availbalJTextField = new JTextField();
		availbalJTextField.setBounds(700, 400, 80, 20);
		availbalJTextField.setFont(new Font("Default", Font.PLAIN, 12));
		availbalJTextField.setHorizontalAlignment(JTextField.CENTER);
		availbalJTextField.setEditable(false);
		contentPane2.add(availbalJTextField);

		// controls
		clearJButton = new JButton();
		clearJButton.setBounds(520, 300, 100, 20);
		clearJButton.setFont(new Font("Default", Font.PLAIN, 12));
		clearJButton.setText("Clear");
		clearJButton.setBackground(Color.LIGHT_GRAY);
		contentPane2.add(clearJButton);
		clearJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				clearJButtonActionPerformed(event);
			}
		}
				);

		clearAllJButton = new JButton();
		clearAllJButton.setBounds(650, 300, 100, 20);
		clearAllJButton.setFont(new Font("Default", Font.PLAIN, 12));
		clearAllJButton.setText("Clear All");
		clearAllJButton.setBackground(Color.GRAY);
		contentPane2.add(clearAllJButton);
		clearAllJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				clearAllJButtonActionPerformed(event);
			}
		}
				);

		closeJButton = new JButton();
		closeJButton.setBounds(780, 300, 100, 20);
		closeJButton.setFont(new Font("Default", Font.PLAIN, 12));
		closeJButton.setText("Close");
		closeJButton.setBackground(Color.RED);
		contentPane2.add(closeJButton);
		closeJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				closeJButtonActionPerformed(event);
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("F:\\PiggyPal.txt", true)));
					out.print(decimalFormat.format(bal));
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
				);

		depositJButton = new JButton();
		depositJButton.setBounds(580, 95, 90, 20);
		depositJButton.setFont(new Font("Default", Font.PLAIN, 12));
		depositJButton.setText("Deposit");
		depositJButton.setBackground(Color.CYAN);
		contentPane2.add(depositJButton);
		depositJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				depositJButtonActionPerformed(event);
			}
		}
				);

		withdrawJButton = new JButton();
		withdrawJButton.setBounds(690, 95, 90, 20);
		withdrawJButton.setFont(new Font("Default", Font.PLAIN, 12));
		withdrawJButton.setText("Withdraw");
		withdrawJButton.setBackground(Color.CYAN);
		contentPane2.add(withdrawJButton);
		withdrawJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				withdrawJButtonActionPerformed(event);
			}
		}
				);

		account = new Account();
		setTitle("BankAccount");
		setSize(1000, 1000);
		setVisible(true);
	}
	// main method
	public static void main(String[] args)
	{
		BankAccount application = new BankAccount();		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void depositJButtonActionPerformed(ActionEvent event)
	{
		getamt();
		depositamt();
	}
	public void withdrawJButtonActionPerformed(ActionEvent event)
	{
		getamt();
		withdrawamt();
	}
	public void getamt()
	{
		try
		{
			amt = Double.parseDouble(amtJTextField.getText());
		}
		catch(NumberFormatException exception)
		{
			JOptionPane.showMessageDialog(this, "Please enter the amt!", "Number Format Error", JOptionPane.ERROR_MESSAGE);
			amtJTextField.setText("");
			amtJTextField.requestFocusInWindow();
		}
	}
	public void depositamt()
	{
		account.getCurrentbal(bal);
		account.deposit(amt);
		account.setCurrentbal();
		bal = account.setCurrentbal();
		displayResults();
	}
	public void withdrawamt()
	{
		account.getCurrentbal(bal);
		account.withdraw(amt);
		account.setCurrentbal();

		bal = account.setCurrentbal();

		check = account.setnoCurrentbal();

		displayResults();
	}
	public void displayResults()
	{
		decimalFormat = new DecimalFormat("0.00");
		determineStatus();
		statusJTextField.setText(status);

		balJTextField.setText("" + decimalFormat.format(bal+availbalance));
		availbalJTextField.setText("" + decimalFormat.format(availbalance));

		amtJTextField.setText("");
		amtJTextField.requestFocusInWindow();
	}
	public void determineStatus()
	{
		if( check == -1)
		{
			status = "Low balance!!";
		}
		else
		{
			status = "OK";
		}
	}
	public void clearJButtonActionPerformed(ActionEvent event)
	{
		amtJTextField.setText("");
		statusJTextField.setText("");
		amtJTextField.requestFocusInWindow();
	}
	public void clearAllJButtonActionPerformed(ActionEvent event)
	{
		amtJTextField.setText("");
		statusJTextField.setText("");
		balJTextField.setText("");
		amtJTextField.requestFocusInWindow();
	}
	public void closeJButtonActionPerformed(ActionEvent event)
	{
		BankAccount.this.dispose();
	}
}
class Account
{
	double currentbal;
	double amtNumber;
	String status;
	BankAccount bankAccount;
	public void getCurrentbal(double balValue)
	{
		currentbal = balValue;
	}
	public void deposit(double amtValue)
	{
		amtNumber = amtValue;
		currentbal = currentbal + amtNumber;
		setCurrentbal();
	}
	public void withdraw(double amtValue)
	{
		amtNumber = amtValue;
		if(currentbal-amtNumber>=0) 
		{
			currentbal = currentbal - amtNumber;
			setCurrentbal();
		}
		else 
		{
			setCurrentbal();
			setnoCurrentbal();
		}

	}
	public double setCurrentbal()
	{
		return currentbal;

	}
	public double setnoCurrentbal()
	{
		return -1;

	}
}