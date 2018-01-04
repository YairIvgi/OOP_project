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
	private JLabel lblResult;

	public Algorithm2Single() {
		setTitle("Algorithm 2 single scan");
		setBounds(100, 100, 1024, 768);
		txtEnterRowFrom = new JTextField();
		txtEnterRowFrom.setBounds(0, 64, 987, 43);
		txtEnterRowFrom.setBackground(Color.LIGHT_GRAY);
		txtEnterRowFrom.setForeground(Color.BLACK);
		txtEnterRowFrom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEnterRowFrom.setColumns(10);
		
		JButton btnOk = new JButton("Locate");
		btnOk.setBounds(190, 123, 137, 33);
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
				String path=folder+"\\Algorithm2.csv";
				File file = new File(path);
				Reader in;
				try {
					in = new FileReader(file);
					Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
					for(CSVRecord record: records) {
						lblResult.setText("Estimated position "+record.get("Lat")+", "+record.get("Lon")+", "+record.get("Alt"));
					}
				} catch (IOException e1) {
					String message = "Error "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblResult = new JLabel("");
		lblResult.setBounds(96, 175, 467, 113);
		lblResult.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getContentPane().setLayout(null);
		getContentPane().add(txtEnterRowFrom);
		getContentPane().add(btnOk);
		getContentPane().add(lblResult);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 293, 578, 51);
		getContentPane().add(panel);
		
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setActionCommand("Cancel");
		button.setBounds(201, 0, 113, 35);
		panel.add(button);
		
		JLabel lblNewLabel = new JLabel("result");
		lblNewLabel.setBounds(15, 223, 69, 20);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter row from NO GPS CSV file:");
		lblNewLabel_1.setBounds(0, 16, 266, 32);
		getContentPane().add(lblNewLabel_1);
	}
}
