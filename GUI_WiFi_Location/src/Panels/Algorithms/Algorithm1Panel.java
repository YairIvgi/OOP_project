package Panels.Algorithms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import Panels.WiFi_App;
import genral.FindMacLoc;

public class Algorithm1Panel extends JFrame {
	
	private JTextField txtEnterMac;
	private JLabel lblNewLabel;
	public Algorithm1Panel() {
		setTitle("Algorithm 1");
		setBounds(100, 100, 600, 400);
		txtEnterMac = new JTextField();
		txtEnterMac.setBounds(132, 26, 275, 47);
		txtEnterMac.setBackground(new Color(192, 192, 192));
		txtEnterMac.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEnterMac.setColumns(10);
		
		JButton btnOk = new JButton("Locate");
		btnOk.setBounds(200, 110, 113, 35);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FindMacLoc alg1=new FindMacLoc(WiFi_App.selections.getRecords(), 4);
				try {
					alg1.locateMac_FromExistingMac(txtEnterMac.getText());
				} catch (Exception e1) {
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				String folder = System.getProperty("user.dir");
				String output=folder+"\\Algorithm1.csv";
				File file = new File(output);
				try {
					Reader in =new FileReader(file);
					Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
					boolean existMac =false;
					for(CSVRecord record: records) {
						existMac=true;
						lblNewLabel.setText("Mac Location: "+record.get("Lat")+", "+record.get("Lon")+", "+record.get("Alt"));
					}
					if(!existMac) {
						lblNewLabel.setText("No such MAC in the Data base");
					}
				} catch (IOException e) {
					String message = "Error "+e.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		JLabel label = new JLabel("");
		label.setBounds(176, 286, 45, 0);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(15, 202, 563, 69);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		getContentPane().setLayout(null);
		getContentPane().add(btnOk);
		getContentPane().add(label);
		getContentPane().add(txtEnterMac);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter mac");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(15, 26, 113, 47);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Mac Location:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(15, 155, 146, 60);
		getContentPane().add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 286, 578, 51);
		getContentPane().add(panel);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(201, 0, 113, 35);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.setLayout(null);
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnReturn.setActionCommand("Cancel");
		panel.add(btnReturn);
	}
}
