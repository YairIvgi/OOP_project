package Panels.Algorithms;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import Panels.WiFi_App;
import genral.FindLocByMac;
import java.awt.Font;
import java.awt.Color;

public class Algorithm2Multi extends JFrame {
	private JTextField txtMac_1;
	private JTextField txtSignal_1;
	private JTextField txtMac_2;
	private JTextField txtSignal_2;
	private JTextField txtMac_3;
	private JTextField txtSignal_3;
	private JLabel lblNewLabel;
	
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Algorithm2Multi frame = new Algorithm2Multi();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setBounds(100, 100, 773, 501);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Algorithm2Multi() {
		setTitle("Algorithm 2 multi scan");
		setBounds(100, 100, 600, 400);
		txtMac_1 = new JTextField();
		txtMac_1.setBackground(Color.LIGHT_GRAY);
		txtMac_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMac_1.setBounds(15, 26, 448, 39);
		txtMac_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_1.setText("mac1");
		txtMac_1.setColumns(10);
		
		txtSignal_1 = new JTextField();
		txtSignal_1.setBackground(Color.LIGHT_GRAY);
		txtSignal_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSignal_1.setBounds(472, 26, 91, 39);
		txtSignal_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtSignal_1.setText("signal1");
		txtSignal_1.setColumns(10);
		
		txtMac_2 = new JTextField();
		txtMac_2.setBackground(Color.LIGHT_GRAY);
		txtMac_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMac_2.setBounds(15, 75, 448, 39);
		txtMac_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_2.setText("mac2");
		txtMac_2.setColumns(10);
		
		txtSignal_2 = new JTextField();
		txtSignal_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSignal_2.setBackground(Color.LIGHT_GRAY);
		txtSignal_2.setBounds(472, 75, 91, 39);
		txtSignal_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtSignal_2.setText("signal2");
		txtSignal_2.setColumns(10);
		
		txtMac_3 = new JTextField();
		txtMac_3.setBackground(Color.LIGHT_GRAY);
		txtMac_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMac_3.setBounds(15, 123, 448, 39);
		txtMac_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_3.setText("mac3");
		txtMac_3.setColumns(10);
		
		txtSignal_3 = new JTextField();
		txtSignal_3.setBackground(Color.LIGHT_GRAY);
		txtSignal_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSignal_3.setBounds(472, 123, 91, 39);
		txtSignal_3.setText("signal3");
		txtSignal_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setBounds(253, 167, 172, 29);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnOk = new JButton("OK");
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOk.setBounds(67, 167, 171, 29);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindLocByMac alg2 = new FindLocByMac(WiFi_App.selections.getRecords(), 4);
				try {
					alg2.estimatedLoc_FromMacs(txtMac_1.getText(), txtSignal_1.getText(), txtMac_2.getText(), txtSignal_2.getText(), txtMac_3.getText(), txtSignal_3.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				String folder = System.getProperty("user.dir");
				String output=folder+"\\Algorithm2Estimated_Location.csv";
				File file = new File(output);
				Reader in;
				try {
					in = new FileReader(file);
					Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
					for(CSVRecord record: records) {
						lblNewLabel.setText("My place is "+record.get("Lat")+", "+record.get("Lon")+", "+record.get("Alt"));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Error reading file");
				}
			}
		});
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(15, 214, 548, 114);
		getContentPane().setLayout(null);
		getContentPane().add(txtMac_3);
		getContentPane().add(txtMac_2);
		getContentPane().add(txtMac_1);
		getContentPane().add(txtSignal_3);
		getContentPane().add(txtSignal_2);
		getContentPane().add(txtSignal_1);
		getContentPane().add(btnOk);
		getContentPane().add(btnCancel);
		getContentPane().add(lblNewLabel);
	}

}
