import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Window.Type;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.DropMode;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import edu.smu.tspell.wordnet.Synset;

public class search {

	private JFrame frmWebQueryClassification;
	private JTextField textField;
	private JLabel lblNewLabel; 
	private JTextPane textPane;
	private JList<String> list;
	
	;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					search window = new search();
					window.frmWebQueryClassification.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public search() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		
		frmWebQueryClassification = new JFrame();
		frmWebQueryClassification.setType(Type.UTILITY);
		frmWebQueryClassification.setResizable(false);
		frmWebQueryClassification.setTitle("Web Query Classification");
		frmWebQueryClassification.setBounds(100, 100, 679, 487);
		frmWebQueryClassification.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWebQueryClassification.getContentPane().setLayout(null);
		frmWebQueryClassification.getContentPane().setBackground(Color.orange);
	    
		textField = new JTextField();
		textField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textField.setCaretPosition(0);
		textField.setToolTipText("type your query and Press Search e.g. Apple");
		textField.setBounds(100, 11, 394, 20);
		frmWebQueryClassification.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch .setBackground(Color.MAGENTA);
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				callSearch(arg0);
			}
		});
		
		btnSearch.setBounds(504, 10, 80, 23);
		frmWebQueryClassification.getContentPane().add(btnSearch);
		
		JLabel lblSearchQuery = new JLabel("Search Query");
		lblSearchQuery.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSearchQuery.setBounds(10, 14, 80, 14);
		frmWebQueryClassification.getContentPane().add(lblSearchQuery);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(20, 44, 633, 386);
		frmWebQueryClassification.getContentPane().add(panel);
		panel.setLayout(null);
		
		list = new JList();
		list.setBounds(10, 16, 158, 353);
		panel.add(list);
		list.setBorder(null);
		
		
		textPane = new JTextPane();

		textPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		textPane.setEditable(false);
		textPane.setBounds(178, 16, 445, 353);
		panel.add(textPane);
		textPane.setBackground(Color.lightGray);
		
		lblNewLabel = new JLabel("Classification : ");
		lblNewLabel.setBounds(30, 435, 593, 14);
		frmWebQueryClassification.getContentPane().add(lblNewLabel);
	
	}//end initialize
	
	public void callSearch(ActionEvent arg){
		String SearchString =textField.getText();
		System.out.println("Search string="+SearchString);
	getTextPane().setText(" ");
		if(!(SearchString.length()==0))
		{ 
			System.out.print("welcome");
			///Directory Search
			DirectorySearchMap dsm = new DirectorySearchMap();
			ArrayList<String> categories = dsm.directorySearch(SearchString);
			DefaultListModel<String> listmodel = new DefaultListModel<String>();
			for (int i = 0; i<categories.size();i++)
				listmodel.addElement(categories.get(i));
			list.setModel(listmodel);
			testJAWS WSearch = new testJAWS();	   
			// String searchStrings = WSearch.getWordFormat(SearchString);
			 //System.out.print("in main........."+searchStrings);
			 
			Synset[] synsets = WSearch.WordnetSearchSyn(SearchString);
			 System.out.print("in main........."+synsets.toString());
			String[] wordForms=null;
			StringBuffer buffer = new StringBuffer();
		    buffer.append(getTextPane().getText());
		    System.out.println("ssdafsfs=="+getTextPane().getText().toString());
		   buffer.append('\n');
			if (synsets.length > 0)
			{
				System.out.println("The Wordnet contain '" +
						SearchString + "' or a possible base form " +
						"of that text:");
				for (int i = 0; i < synsets.length; i++)
				{
					System.out.println("");
					String[] wordForms1 = synsets[i].getWordForms();
					for (int j = 0; j < wordForms1.length; j++)
					{
						System.out.print((j > 0 ? ", " : "") +
								wordForms1[j]);
					}
					System.out.println(": " + synsets[i].getDefinition());
				}
				for (int i = 0; i < synsets.length; i++)
				{
					System.out.println("");
					wordForms = synsets[i].getWordForms();
					for (int j = 0; j < wordForms.length; j++)
					{
						buffer.append((j > 0 ? '\n' : "") +wordForms[j]);
						
					}
					
					//listmodel.addElement(wordForms);
					buffer.append(": " + synsets[i].getDefinition());
					
				}
				buffer.append('\n');
				JOptionPane.showMessageDialog(frmWebQueryClassification,buffer);
			}
			else
			{
				System.err.println("No Wordnet information exist that contain " +
						"the word form '" + SearchString + "'");
				String b="No Wordnet information exist that contain " +
				"the word form '" + SearchString + "'";
				JOptionPane.showMessageDialog(frmWebQueryClassification,b);
			}
			
			
			
			
			/*String[] wordForms = WSearch.WordnetSearch(SearchString);
		    
		    for (int j = 0; j < wordForms.length; j++)
			{
		    	buffer.append((j > 0 ? "\n " : "") + wordForms[j]);
				//System.out.print((j > 0 ? ", " : "") + wordForms[j]);
			}*/
			
		    //getTextPane().setText(buffer.toString());
		    JLabel l1=new JLabel();
		    JLabel l2=new JLabel();
		    JLabel l3=new JLabel();
		    JLabel l4=new JLabel();
		    JLabel l5=new JLabel();
		    JLabel l6=new JLabel();
		    JLabel l7=new JLabel();
		    JLabel l8=new JLabel();
		    JLabel l9=new JLabel();
		    JLabel l10=new JLabel();
		    JLabel l11=new JLabel();
		    JLabel l12=new JLabel();
		    JLabel l13=new JLabel();
		    JLabel l14=new JLabel();
		    JLabel l15=new JLabel();
		    JLabel l16=new JLabel();
		    
		    textPane.add(l1);
		    textPane.add(l2);
		    textPane.add(l3);
		    textPane.add(l4);
		    textPane.add(l5);
		    textPane.add(l6);
		    textPane.add(l7);
		    textPane.add(l8);
		    textPane.add(l9);
		    textPane.add(l10);
		    textPane.add(l11);
		    textPane.add(l12);
		    textPane.add(l13);
		    textPane.add(l14);
		    textPane.add(l15);
		    textPane.add(l16);
		    
		 	l1.setBounds(10,5, 593, 14);
		 	l2.setBounds(10,20, 593, 14);
		 	l3.setBounds(10,35, 593, 14);
		 	l4.setBounds(10,50, 593, 14);
			l5.setBounds(10,65, 593, 14);
		 	l6.setBounds(10,80, 593, 14);
		 	l7.setBounds(10,95, 593, 14);
		 	l8.setBounds(10,110, 593, 14);
			l9.setBounds(10,125, 593, 14);
		 	l10.setBounds(10,140, 593, 14);
		 	l11.setBounds(10,155, 593, 14);
		 	l12.setBounds(10,170, 593, 14);
		 	l13.setBounds(10,185, 593, 14);
		 	l14.setBounds(10,200, 593, 14);
		 	l15.setBounds(10,215, 593, 14);
		 	l16.setBounds(10,230, 593, 14);
		     //String jlabel[]=new String[16];
		  String[] u=dsm.getUrls(); //new String[17];
		    //frmWebQueryClassification.getContentPane().add(l);
		    //final  String[]str=new String[16]; 
		    final  String str1=u[0];
		    final  String str2=u[1];
		    final  String str3=u[2];
		    final  String str4=u[3];
		    final  String str5=u[4];
		    final  String str6=u[5];
		    final  String str7=u[6];
		    final  String str8=u[7];
		    final  String str9=u[8];
		    final  String str10=u[9];
		    final  String str11=u[10];
		    final  String str12=u[11];
		    final  String str13=u[12];
		    final  String str14=u[13];
		    final  String str15=u[14];
		    final  String str16=u[15];
		   // u[i]=dsm.getUrls().toString();
		   
		    l1.setText(u[0]);
		    l2.setText(u[1]);
		    l3.setText(u[2]);
		    l4.setText(u[3]);
		    
		    l5.setText(u[4]);
		    l6.setText(u[5]);
		    l7.setText(u[6]);
		    l8.setText(u[7]);
		    l9.setText(u[8]);
		    l10.setText(u[9]);
		    l11.setText(u[10]);
		    l12.setText(u[11]);
		    l13.setText(u[12]);
		    l14.setText(u[13]);
		    l15.setText(u[14]);
		    l16.setText(u[15]);
		    
		    
		    
		   
		   /****************
		     * 1
		     */
		  
		    l1.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str1);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 2
		     */
		  
		    l2.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str2);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 3
		     */
		  
		    l3.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str3);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 4
		     */
		  
		    l4.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 5
		     */
		  
		    l5.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 6
		     */
		  
		    l6.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 7
		     */
		  
		    l7.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 8
		     */
		  
		    l8.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 9
		     */
		  
		    l9.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 10
		     */
		  
		    l10.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 11
		     */
		  
		    l11.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 12
		     */
		  
		    l12.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 13
		     */
		  
		    l13.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 14
		     */
		  
		    l14.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    /****************
		     * 15
		     */
		  
		    l15.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    
		    /****************
		     * 16
		     */
		  
		    l16.addMouseListener(new MouseAdapter() {
		    	   public void mouseClicked(MouseEvent e) {
		    	      if (e.getClickCount() > 0) {
		    	          if (Desktop.isDesktopSupported()) {
		    	                Desktop desktop = Desktop.getDesktop();
		    	                try {
		    	                    URI uri = new URI(str4);
		    	                    desktop.browse(uri);
		    	                } catch (IOException ex) {
		    	                    ex.printStackTrace();
		    	                } catch (URISyntaxException ex) {
		    	                    ex.printStackTrace();
		    	                }
		    	        }
		    	      }
		    	   }
		    	});
		    
		    
		    
		    
		    
		  //  getTextPane().setText(dsm.getUrls());
		    System.out.println("end="+dsm.getUrls().toString());
		    Classifier c = new Classifier();
		    
		    lblNewLabel.setText("Classification : "+c.classify(SearchString));
		}
		else
		{
			//textField.selectAll();
		}
	}

	public JTextPane getTextPane() {
		return textPane;
	}//callsearch
	}//main class
