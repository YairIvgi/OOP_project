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
import javax.swing.JTextField;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import Panels.WiFi_App;
import genral.FindLocByMac;
import javax.swing.JPanel;

public class Algorithm2Single extends JFrame {
	
	private JTextField txtEnterRowFrom;
	private JLabel lblResultLat;
	private JLabel lblResultLon;
	private JLabel lblResultAlt;
	

	public Algorithm2Single() {
		setTitle("Algorithm 2 single scan");
		setBounds(100, 100, 800, 600);
		txtEnterRowFrom = new JTextField();
		txtEnterRowFrom.setText("CSV Line");
		txtEnterRowFrom.setBounds(10, 64, 977, 43);
		txtEnterRowFrom.setBackground(Color.LIGHT_GRAY);
		txtEnterRowFrom.setForeground(Color.BLACK);
		txtEnterRowFrom.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtEnterRowFrom.setColumns(10);
		
		JButton btnOk = new JButton("Locate");
		btnOk.setBounds(273, 123, 140, 33);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindLocByMac alg2=new FindLocByMac(WiFi_App.selections.getRecords(), 3);
				try {
					alg2.estimatedLoc_FromString(txtEnterRowFrom.getText());
				} catch (Exception e1) {
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
					CSVRecord record = records.iterator().next();
						lblResultLat.setText(record.get("Lat"));
						lblResultLon.setText(record.get("Lon"));
						lblResultAlt.setText(record.get("Alt"));		
				} catch (IOException e1) {
					String message = "Error "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblResultLat = new JLabel("");
		lblResultLat.setBounds(77, 256, 177, 25);
		lblResultLat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().setLayout(null);
		getContentPane().add(txtEnterRowFrom);
		getContentPane().add(btnOk);
		getContentPane().add(lblResultLat);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(77, 386, 578, 51);
		getContentPane().add(panel);
		
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setActionCommand("Cancel");
		button.setBounds(201, 0, 140, 35);
		panel.add(button);
		
		JLabel lblNewLabel = new JLabel("Estimated position");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(15, 220, 184, 20);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter row from NO GPS CSV file:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 16, 296, 32);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Lat");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(15, 256, 69, 20);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblLon = new JLabel("Lon");
		lblLon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLon.setBounds(15, 292, 69, 20);
		getContentPane().add(lblLon);
		
		JLabel lblAlt = new JLabel("Alt");
		lblAlt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAlt.setBounds(15, 328, 69, 20);
		getContentPane().add(lblAlt);
		
		lblResultLon = new JLabel("");
		lblResultLon.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblResultLon.setBounds(77, 291, 177, 25);
		getContentPane().add(lblResultLon);
		
		lblResultAlt = new JLabel("");
		lblResultAlt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblResultAlt.setBounds(77, 328, 177, 25);
		getContentPane().add(lblResultAlt);
	}
}
