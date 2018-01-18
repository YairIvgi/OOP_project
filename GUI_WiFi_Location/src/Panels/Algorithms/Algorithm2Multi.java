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

import Panels.Main;
import genral.FindLocByMac;

/**
 * @Description The is the GUI panel of algorithm 2 with 3 pairs of mac and signal. 
 * @author Idan Hollander and Yair Ivgi
 */

public class Algorithm2Multi extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtMac_1;
	private JTextField txtSignal_1;
	private JTextField txtMac_2;
	private JTextField txtSignal_2;
	private JTextField txtMac_3;
	private JTextField txtSignal_3;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel lblResultLat;
	private JLabel lblResultLon;
	private JLabel lblResultAlt;
	private JLabel lblEnterMacAnd;



	public Algorithm2Multi() {
		setTitle("Algorithm 2 multi scan");
		setBounds(100, 100, 800, 600);
		txtMac_1 = new JTextField();
		txtMac_1.setBackground(Color.LIGHT_GRAY);
		txtMac_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMac_1.setBounds(15, 64, 227, 39);
		txtMac_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_1.setText("mac1");
		txtMac_1.setColumns(10);

		txtSignal_1 = new JTextField();
		txtSignal_1.setBackground(Color.LIGHT_GRAY);
		txtSignal_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSignal_1.setBounds(275, 64, 91, 39);
		txtSignal_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtSignal_1.setText("signal1");
		txtSignal_1.setColumns(10);

		txtMac_2 = new JTextField();
		txtMac_2.setBounds(15, 119, 227, 39);
		txtMac_2.setBackground(Color.LIGHT_GRAY);
		txtMac_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMac_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_2.setText("mac2");
		txtMac_2.setColumns(10);

		txtSignal_2 = new JTextField();
		txtSignal_2.setBounds(275, 119, 91, 39);
		txtSignal_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSignal_2.setBackground(Color.LIGHT_GRAY);
		txtSignal_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtSignal_2.setText("signal2");
		txtSignal_2.setColumns(10);

		txtMac_3 = new JTextField();
		txtMac_3.setBounds(15, 174, 227, 39);
		txtMac_3.setBackground(Color.LIGHT_GRAY);
		txtMac_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMac_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_3.setText("mac3");
		txtMac_3.setColumns(10);

		txtSignal_3 = new JTextField();
		txtSignal_3.setBounds(275, 174, 91, 39);
		txtSignal_3.setBackground(Color.LIGHT_GRAY);
		txtSignal_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSignal_3.setText("signal3");
		txtSignal_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JButton btnOk = new JButton("Locate");
		btnOk.setBounds(443, 121, 171, 34);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindLocByMac alg2 = new FindLocByMac(Main.selections.getRecords(), 4);
				try {
					alg2.estimatedLoc_FromMacs(txtMac_1.getText(), txtSignal_1.getText(), txtMac_2.getText(), txtSignal_2.getText(), txtMac_3.getText(), txtSignal_3.getText());
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
						in.close();
				} catch (IOException e1) {
					String message = "Error "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(txtMac_3);
		getContentPane().add(txtMac_2);
		getContentPane().add(txtMac_1);
		getContentPane().add(txtSignal_3);
		getContentPane().add(txtSignal_2);
		getContentPane().add(txtSignal_1);
		getContentPane().add(btnOk);
		
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setActionCommand("Cancel");
		button.setBounds(443, 493, 171, 35);
		getContentPane().add(button);
		
		label = new JLabel("Estimated position");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(15, 246, 184, 20);
		getContentPane().add(label);
		
		label_1 = new JLabel("Lat");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_1.setBounds(15, 294, 69, 20);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("Lon");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_2.setBounds(15, 330, 69, 20);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("Alt");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_3.setBounds(15, 366, 69, 20);
		getContentPane().add(label_3);
		
		lblResultLat = new JLabel("");
		lblResultLat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblResultLat.setBounds(75, 289, 177, 25);
		getContentPane().add(lblResultLat);
		
		lblResultLon = new JLabel("");
		lblResultLon.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblResultLon.setBounds(75, 325, 177, 25);
		getContentPane().add(lblResultLon);
		
		lblResultAlt = new JLabel("");
		lblResultAlt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblResultAlt.setBounds(75, 361, 177, 25);
		getContentPane().add(lblResultAlt);
		
		lblEnterMacAnd = new JLabel("Enter MAC and Signal");
		lblEnterMacAnd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnterMacAnd.setBounds(15, 22, 296, 32);
		getContentPane().add(lblEnterMacAnd);
	}
}
