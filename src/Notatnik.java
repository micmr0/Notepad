import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Notatnik extends JFrame implements ActionListener 
{
	JMenuBar menuBar;
	JButton bSearch, bSetColor;

	JPopupMenu popup;
	JMenu menuFile, menuEdit, menuTheme, menuOptions, menuHelp;
	JMenuItem mOpen, mSave, mClose, 
				mCopy, mPaste, mAppend, mSelectAll,  
				mMetal, mNimbus, mWindows, 
				mpCopy, mpPaste, mpAppend, mpSelectAll, 
				mAbout;
	
	JTextField tSearched;
	JTextArea notepad;
	JScrollPane scrollPane;
	JComboBox colorCombo;
	String chosenText;
	
	public Notatnik()
	{
		setTitle("Notatnik");
		setSize(720, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		menuBar = new JMenuBar();
		
		menuFile = new JMenu("Plik");
		
			mOpen = new JMenuItem("Otwórz", 'O');
			mOpen.addActionListener(this);
			mOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
			mSave = new JMenuItem("Zapisz");
			mSave.addActionListener(this);
			mSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
			mClose = new JMenuItem("Wyjœcie");
			
			menuFile.add(mOpen);
			menuFile.add(mSave);
			menuFile.addSeparator();
			menuFile.add(mClose);
		
			mClose.addActionListener(this);
			mClose.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		
		// ----- Menu Edit ------ //
			
		menuEdit = new JMenu("Edycja");
		
			mCopy = new JMenuItem("Kopiuj");
			mCopy.addActionListener(this);
			mPaste = new JMenuItem("Wklej");
			mPaste.addActionListener(this);
			mAppend = new JMenuItem("Do³¹cz");
			mAppend.addActionListener(this);
			mSelectAll = new JMenuItem("Zaznacz wszystko");
			mSelectAll.addActionListener(this);
			
			menuEdit.add(mCopy);
			menuEdit.add(mPaste);
			menuEdit.add(mAppend);
			menuEdit.addSeparator();
			menuEdit.add(mSelectAll);

		// ----- Menu Design ------ //
			
		menuTheme = new JMenu("Wygl¹d");
			mMetal = new JMenuItem("Metal");
			mNimbus = new JMenuItem("Nimbus");
			mWindows = new JMenuItem("Windows");
			
			mMetal.addActionListener(this);
			mNimbus.addActionListener(this);
			mWindows.addActionListener(this);
			
			menuTheme.add(mMetal);
			menuTheme.add(mNimbus);
			menuTheme.add(mWindows);
		
		menuHelp = new JMenu("Pomoc");
			mAbout = new JMenuItem("O Programie");
			mAbout.addActionListener(this);
			menuHelp.add(mAbout);
		
		setJMenuBar(menuBar);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuTheme);
		menuBar.add(menuHelp);
		

		notepad = new JTextArea();
		scrollPane = new JScrollPane(notepad);
		scrollPane.setBounds(0, 0, 700, 600);
		add(scrollPane);
		
		// ----- Menu Popup -----
		
		popup = new JPopupMenu();
		mpCopy = new JMenuItem("Kopiuj");
		mpCopy.addActionListener(this);
		mpPaste = new JMenuItem("Wklej");
		mpPaste.addActionListener(this);
		mpAppend = new JMenuItem("Do³¹cz");
		mpAppend.addActionListener(this);
		mpSelectAll = new JMenuItem("Zaznacz Wszystko");
		mpSelectAll.addActionListener(this);
		
		popup.add(mpCopy);
		popup.add(mpPaste);
		popup.add(mpAppend);
		popup.addSeparator();
		popup.add(mpSelectAll);
		
		
		notepad.setComponentPopupMenu(popup);
		
		//----- searching -----
		
		tSearched = new JTextField();
		tSearched.setBounds(0, 600, 100, 20);
		add(tSearched);
		
		bSearch = new JButton("Szukaj");
		bSearch.setBounds(100, 600, 80, 20);
		add(bSearch);
		bSearch.addActionListener(this);
		
		// ------ chosing a color -------
		
				colorCombo = new JComboBox();
				colorCombo.setBounds(500, 600, 100, 20);
					colorCombo.addItem("czarny");
					colorCombo.addItem("czerwony");
					colorCombo.addItem("zielony");
					colorCombo.addItem("niebieski");
					colorCombo.addItem("ró¿owy");
				add(colorCombo);
				colorCombo.addActionListener(this);
				
				bSetColor = new JButton("Inny kolor...");
				bSetColor.setBounds(600, 600, 100, 20);
				add(bSetColor);
				bSetColor.addActionListener(this);	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// -----checking what option chosen---- //
		
		Object z = e.getSource();
		if(z == mOpen)
		{
			JFileChooser fc = new JFileChooser();
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				File plik = fc.getSelectedFile();
				try
				{
					Scanner scanner = new Scanner(plik);
					while(scanner.hasNext())
						notepad.append(scanner.nextLine() + "\n");
					scanner.close();
				} 
				catch (FileNotFoundException e1)
				{
					
					e1.printStackTrace();
				}
			}
		}
		else if(z == mSave)
		{
			JFileChooser fc = new JFileChooser();
			if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				File plik = fc.getSelectedFile();
				try
				{
					PrintWriter pw = new PrintWriter(plik);
					Scanner skaner = new Scanner(notepad.getText());
					while(skaner.hasNext())
					{
						pw.println(skaner.nextLine() + "\n");
					}
					pw.close();
					skaner.close();
				} 
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		else if(z == mClose) 
		{
			int odp = JOptionPane.showConfirmDialog(null, "Czy na pewno wyjœæ?", "Pytanie", JOptionPane.YES_NO_OPTION);
			
			if(odp == JOptionPane.YES_OPTION)
				dispose();
		}
		else if(z == mCopy)
		{
			chosenText = notepad.getSelectedText();
		}
		
		else if(z == mPaste)
		{
			notepad.insert(chosenText, notepad.getCaretPosition());
		}
		
		else if(z == mAppend)
			notepad.append("\n" + chosenText);
		
		else if(z == mSelectAll)
			notepad.selectAll();
		
		else if(z == mMetal)
		{
			try
			{
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1)
			{
				e1.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(this);
		}
		
		else if(z == mNimbus)
		{
			try
			{
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1)
			{
				e1.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(this);
		}
		
		else if(z == mWindows)
		{
			try
			{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1)
			{
				e1.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(this);
		}
		
		else if(z == mAbout)
			JOptionPane.showMessageDialog(null, "Notatnik Java \n Wersja 1.0", "O Programie", JOptionPane.INFORMATION_MESSAGE);

		else if(z == bSearch)
		{
			if(tSearched.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Podaj szukane slowo", "Szukaj" , JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				String tekst = notepad.getText();
				String szukane = tSearched.getText();
				String wyst¹pienia = "";
				int i = 0;
				int index;
				int startIndex = 0;
				while( (index = tekst.indexOf(szukane, startIndex)) != -1)
				{
					startIndex = index + szukane.length();
					i++;
					wyst¹pienia = wyst¹pienia + " " + index;
				}
				
				JOptionPane.showMessageDialog(null, "\""+szukane + "\" wyst¹pi³o " + i +" raz(y). \nIndeksy: " + wyst¹pienia, "Szukaj", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		else if(z == mpCopy)
			chosenText = notepad.getSelectedText();
		
		else if(z == mpPaste)
			notepad.insert(chosenText, notepad.getCaretPosition());
		
		else if(z == mpAppend)
			notepad.append("\n" + chosenText);
		
		else if(z == mpSelectAll)
			notepad.selectAll();
		
		else if(z == colorCombo)
		{
			String color = colorCombo.getSelectedItem().toString();
			if(color.equals("czerwony"))
				notepad.setForeground(Color.RED);
			if(color.equals("zielony"))
				notepad.setForeground(Color.GREEN);
			if(color.equals("niebieski"))
				notepad.setForeground(Color.BLUE);
			if(color.equals("ró¿owy"))
				notepad.setForeground(Color.PINK);
			if(color.equals("czarny"))
				notepad.setForeground(Color.BLACK);
		}
		else if(z == bSetColor)
		{
			Color wybranyKolor = JColorChooser.showDialog(null, "Wybór koloru", Color.BLACK);
			notepad.setForeground(wybranyKolor);
		}
	}

	public static void main(String[] args) 
	{
		Notatnik appMenu = new Notatnik();
		appMenu.setVisible(true);

	}

}
