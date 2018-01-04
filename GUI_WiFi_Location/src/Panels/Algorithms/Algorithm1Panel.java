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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import Panels.WiFi_App;
import genral.FindMacLoc;
import java.awt.Font;
import java.awt.Color;

public class Algorithm1Panel extends JFrame {
	
	private JTextField txtEnterMac;
	private JLabel lblNewLabel;
	public Algorithm1Panel() {
		setTitle("Algorithm 1");
		setBounds(100, 100, 600, 400);
		txtEnterMac = new JTextField();
		txtEnterMac.setBackground(new Color(192, 192, 192));
		txtEnterMac.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEnterMac.setText("Enter mac");
		txtEnterMac.setColumns(10);
		
		JButton btnOk = new JButton("OK");
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
						lblNewLabel.setText("Mac place is "+record.get("Lat")+", "+record.get("Lon")+", "+record.get("Alt"));
					}
					if(!existMac) {
						lblNewLabel.setText("no macs like this in the database");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel label = new JLabel("");
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(152)
					.addComponent(btnOk, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
					.addGap(160))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(176)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(357, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtEnterMac, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(288, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(218, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(txtEnterMac, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnCancel))
					.addGap(44)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(label)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}


	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Algorithm1Panel frame = new Algorithm1Panel();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setBounds(100, 100, 773, 501);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
