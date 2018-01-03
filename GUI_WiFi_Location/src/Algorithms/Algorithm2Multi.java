package Algorithms;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import genral.FindLocByMac;
import genral.FindMacLoc;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class Algorithm2Multi extends JFrame {
	private JTextField txtMac_1;
	private JTextField txtSignal_1;
	private JTextField txtMac_2;
	private JTextField txtSignal_2;
	private JTextField txtMac_3;
	private JTextField txtSignal_3;
	
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
		
		txtMac_1 = new JTextField();
		txtMac_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_1.setText("mac1");
		txtMac_1.setColumns(10);
		
		txtSignal_1 = new JTextField();
		txtSignal_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtSignal_1.setText("signal1");
		txtSignal_1.setColumns(10);
		
		txtMac_2 = new JTextField();
		txtMac_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_2.setText("mac2");
		txtMac_2.setColumns(10);
		
		txtSignal_2 = new JTextField();
		txtSignal_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtSignal_2.setText("signal2");
		txtSignal_2.setColumns(10);
		
		txtMac_3 = new JTextField();
		txtMac_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtMac_3.setText("mac3");
		txtMac_3.setColumns(10);
		
		txtSignal_3 = new JTextField();
		txtSignal_3.setText("signal3");
		txtSignal_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindLocByMac alg2 = new FindLocByMac("C:\\Users\\user\\Desktop\\try\\newData\\BM3.csv", 4);
				try {
					alg2.estimatedLoc_FromMacs(txtMac_1.getText(), txtSignal_1.getText(), txtMac_2.getText(), txtSignal_2.getText(), txtMac_3.getText(), txtSignal_3.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				File file = new File("C:\\Users\\user\\Desktop\\try\\newData\\MacsForAlgorithm2Estimated_Location.csv");
				Reader in;
				try {
					in = new FileReader(file);
					Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
					for(CSVRecord record: records) {
						System.out.println("My place is"+record.get("Lat")+","+record.get("Lon")+","+record.get("Alt"));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
				dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtMac_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
						.addComponent(txtMac_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
						.addComponent(txtMac_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtSignal_3, 0, 0, Short.MAX_VALUE)
						.addComponent(txtSignal_2, 0, 0, Short.MAX_VALUE)
						.addComponent(txtSignal_1, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
					.addGap(62))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(67)
					.addComponent(btnOk, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
					.addGap(153))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMac_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSignal_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMac_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSignal_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMac_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSignal_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnOk))
					.addContainerGap(106, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}

}
