package Panels.Algorithms;

import java.awt.Color;
import java.awt.Font;
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

import Panels.Main;
import genral.FindMacLoc;

/**
 * @Description The is the GUI panel of algorithm 1. 
 * @author Idan Hollander and Yair Ivgi
 */

public class Algorithm1Panel extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtEnterMac;
	private JLabel lblResultLat;
	private JLabel lblResultLon;
	private JLabel lblResultAlt;
	
	public Algorithm1Panel() {
		setTitle("Algorithm 1");
		setBounds(100, 100, 600, 400);
		txtEnterMac = new JTextField();
		txtEnterMac.setBackground(new Color(192, 192, 192));
		txtEnterMac.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEnterMac.setText("Enter mac");
		txtEnterMac.setColumns(10);

		JButton btnLocate = new JButton("Locate");
		btnLocate.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnLocate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FindMacLoc alg1=new FindMacLoc(Main.selections.getRecords(), 4);
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
						lblResultLat.setText(record.get("Lat"));
						lblResultLon.setText(record.get("Lon"));
						lblResultAlt.setText(record.get("Alt"));
					}
					if(!existMac) {
						lblResultLat.setText("no match");
						lblResultLon.setText("no match");
						lblResultAlt.setText("no match");
					}
					in.close();
				} catch (IOException e) {
					String message = "oops error: "+e.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);

				}
			}
		});


		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JLabel label = new JLabel("");

		lblResultLat = new JLabel("");
		lblResultLat.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblMacLocation = new JLabel("MAC Location:");
		lblMacLocation.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblLat = new JLabel("Lat:");
		lblLat.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblLon = new JLabel("Lon:");
		lblLon.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblResultLon = new JLabel("");
		lblResultLon.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblAlt = new JLabel("Alt:");
		lblAlt.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblResultAlt = new JLabel("");
		lblResultAlt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(txtEnterMac, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
						.addGap(82)
						.addComponent(btnLocate, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addGap(84))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblMacLocation, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(385, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblAlt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLat, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblResultLat, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblResultLon, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addGap(127))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblResultAlt, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
										.addContainerGap())))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(146)
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(307, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(24)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtEnterMac, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLocate))
						.addGap(40)
						.addComponent(lblMacLocation, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblLat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblResultLat, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblLon)
								.addComponent(lblResultLon, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAlt, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblResultAlt, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGap(55)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(label)
								.addComponent(btnReturn))
						.addGap(41))
				);
		getContentPane().setLayout(groupLayout);
	}


}
