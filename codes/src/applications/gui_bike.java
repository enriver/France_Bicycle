package applications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.border.*;
import javax.swing.table.*;

public class gui_bike{
	
	static String header[] = null;
	static String content[][] = null;
	static JTable table;
	static boolean my_switch;
	static JScrollPane scrollpane;
	
	public static void main(String[] args) { 
		
		JFrame frm= new JFrame("Available Bicycle");		
		JLabel label=new JLabel();
		JLabel label2=new JLabel();
		JLabel label3=new JLabel();
		JLabel label4=new JLabel();
		JLabel label5=new JLabel();
		
		label.setText("Bicycle Program");
		label.setFont(new Font("", Font.BOLD,15).deriveFont(30.0f));
		label.setForeground(Color.GRAY);
		label.setBounds(30,20,350,35);
		
		label2.setText("<html>In this program, you can find bicycle stations<br/>with information in the city you like</html>");
		label2.setFont(label2.getFont().deriveFont(15.0f));
		label2.setBounds(30,60,400,40);
		
		label3.setText("City name");
		label3.setFont(label3.getFont().deriveFont(15.0f));
		label3.setBounds(540,0,200,35);
		
		// Image
		label4.setBounds(540,25,125,125);
		
		label5.setText("JenaEngine output");
		label5.setFont(label5.getFont().deriveFont(15.0f));
		label5.setBounds(770,20,200,35);
		
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setText("Here comes Jena Output");
		editorPane.setBounds(770,50,1000,1000);
		editorPane.setFont(new Font("Consolas", 0, 11));
		editorPane.setEditable(false);
		
		JScrollPane scrollpaneEdit = new JScrollPane(editorPane);
		scrollpaneEdit.setBounds(770,50,380,370);
		frm.getContentPane().add(scrollpaneEdit);
		
		
		// Read filename 
		File folder = new File("data/cities/");
		File[] listOfFiles = folder.listFiles();
		//System.out.println(listOfFiles[0]);
		
		File[] cities= listOfFiles;
		JComboBox strCombo = new JComboBox(cities);
		strCombo.setBounds(30,120,200,20);
		
		

		
		Main get_data = new Main();
		my_switch = false;
		
		JButton btn = new JButton("Confirm");
		btn.setBounds(240,120,90,20);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Change city name
				String cityname = strCombo.getSelectedItem().toString().split("_bik")[0].split("\\\\")[2];
				label3.setText(cityname.substring(0, 1).toUpperCase() + cityname.substring(1));
				
				// Set image
				Image img = Toolkit.getDefaultToolkit().getImage("data/images/"+label3.getText()+".png");
				label4.setIcon(new ImageIcon(img.getScaledInstance(label4.getWidth(), label4.getHeight(), Image.SCALE_AREA_AVERAGING)));

				
				// Here it will be actived
				if(my_switch) {
					System.out.println("Removes all");
					scrollpane.removeAll();
				}
				
				String selected = strCombo.getSelectedItem().toString();
				Object[] data = get_data.main(selected);
				
				String[] head_data = (String[]) data[0];
				String[][] body_data = (String[][]) data[1];
				
				String header[]= head_data ;
				String content[][] = body_data;
								

				table = new JTable(content,header);
				JTableHeader tableheader=table.getTableHeader();
				tableheader.setFont(new Font("Verdana",Font.BOLD, 13));
				
				DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
				celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcm=table.getColumnModel();
				
				for(int i=0; i<tcm.getColumnCount(); i++) {
					tcm.getColumn(i).setCellRenderer(celAlignCenter);
				}
				
				scrollpane = new JScrollPane(table);
				scrollpane.setBounds(30, 160, 720, 260);
				frm.getContentPane().add(scrollpane);
				
				// Jena Output
				editorPane.setText(data[2].toString());
				
				my_switch = true;
				
				
			}
		});
		
		JButton btn_open_query = new JButton("Open Query");
		btn_open_query.setBounds(340,120,120,20);
		btn_open_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fil = new File("data/query.txt");
				
				Desktop desktop = Desktop.getDesktop();
				if(fil.exists()) {
					try {
						desktop.open(fil);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		// Get data from JENA!
		
		
		
		frm.add(strCombo);
		frm.add(btn);
		frm.add(btn_open_query);
		frm.add(label);
		frm.add(label2);
		frm.add(label3);
		frm.add(label4);
		frm.add(label5);
		//frm.add(editorPane);
        frm.setSize(1200,500);
        frm.getContentPane().setLayout(null);
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frm.getContentPane().setBackground(Color.white);	
		
		
		
	}
}
