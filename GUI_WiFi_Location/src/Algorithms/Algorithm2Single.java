package Algorithms;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import genral.FindLocByMac;
import javax.swing.JLabel;

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
		
		txtEnterRowFrom = new JTextField();
		txtEnterRowFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		txtEnterRowFrom.setText("Enter row from no gps csv");
		txtEnterRowFrom.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindLocByMac alg2=new FindLocByMac("C:\\Users\\user\\Desktop\\try\\newData\\BM3.csv", 4);
				try {
					alg2.estimatedLoc_FromString(txtEnterRowFrom.getText());
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
						lblResult.setText("My place is "+record.get("Lat")+", "+record.get("Lon")+", "+record.get("Alt"));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		lblResult = new JLabel("result");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(txtEnterRowFrom, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(107)
					.addComponent(btnOk, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
					.addGap(173))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblResult, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(txtEnterRowFrom, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnOk))
					.addGap(44)
					.addComponent(lblResult, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
}
