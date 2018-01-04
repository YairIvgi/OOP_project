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
import java.awt.Scrollbar;

public class Algorithm2Single extends JFrame {
	
	private JTextField txtEnterRowFrom;
	private JLabel lblResult;
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Algorithm2Single frame = new Algorithm2Single();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setBounds(100, 100, 773, 501);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Algorithm2Single() {
		setTitle("Algorithm 2 single scan");
		setBounds(100, 100, 600, 400);
		txtEnterRowFrom = new JTextField();
		txtEnterRowFrom.setBounds(0, 27, 578, 43);
		txtEnterRowFrom.setBackground(Color.LIGHT_GRAY);
		txtEnterRowFrom.setForeground(Color.BLACK);
		txtEnterRowFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEnterRowFrom.setText("Enter row from NO GPS CSV file");
		txtEnterRowFrom.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(107, 98, 137, 33);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindLocByMac alg2=new FindLocByMac(WiFi_App.selections.getRecords(), 4);
				try {
					alg2.estimatedLoc_FromString(txtEnterRowFrom.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				String folder = System.getProperty("user.dir");
				String output=folder+"\\OneStringForAlgorithm2.csv";
				File file = new File(output);
				Reader in;
				try {
					in = new FileReader(file);
					Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
					for(CSVRecord record: records) {
						lblResult.setText("My place is "+record.get("Lat")+", "+record.get("Lon")+", "+record.get("Alt"));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(259, 98, 146, 33);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		lblResult = new JLabel("result");
		lblResult.setBounds(15, 175, 548, 169);
		lblResult.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getContentPane().setLayout(null);
		getContentPane().add(txtEnterRowFrom);
		getContentPane().add(btnOk);
		getContentPane().add(btnCancel);
		getContentPane().add(lblResult);
	}
}
