package Panels;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.csv.CSVRecord;

import readAndWrite.DataBaseIO;
import util.Sql;

public class SqlPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPort;
	private JTextField textUser;
	private JTextField textPassword;
	private JTextField textFolder;
	private JTextField textTable;
	private boolean selected = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SqlPanel frame = new SqlPanel(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param main 
	 */
	public SqlPanel(Main main) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtIP.setBounds(113, 16, 146, 26);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPort.setColumns(10);
		txtPort.setBounds(113, 58, 146, 26);
		contentPane.add(txtPort);
		
		textUser = new JTextField();
		textUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textUser.setColumns(10);
		textUser.setBounds(113, 100, 146, 26);
		contentPane.add(textUser);
		
		textPassword = new JTextField();
		textPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPassword.setColumns(10);
		textPassword.setBounds(417, 14, 146, 26);
		contentPane.add(textPassword);
		
		textFolder = new JTextField();
		textFolder.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFolder.setColumns(10);
		textFolder.setBounds(417, 56, 146, 26);
		contentPane.add(textFolder);
		
		textTable = new JTextField();
		textTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textTable.setColumns(10);
		textTable.setBounds(417, 98, 146, 26);
		contentPane.add(textTable);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblIp.setBounds(43, 19, 69, 20);
		contentPane.add(lblIp);
		
		JLabel lblPort = new JLabel("PORT");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPort.setBounds(43, 61, 69, 20);
		contentPane.add(lblPort);
		
		JLabel lblUser = new JLabel("USER");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUser.setBounds(43, 103, 69, 20);
		contentPane.add(lblUser);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(294, 19, 108, 20);
		contentPane.add(lblPassword);
		
		JLabel lblFolder = new JLabel("FOLDER");
		lblFolder.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFolder.setBounds(294, 61, 97, 20);
		contentPane.add(lblFolder);
		
		JLabel lblTable = new JLabel("TABLE");
		lblTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTable.setBounds(294, 103, 77, 20);
		contentPane.add(lblTable);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 149, 578, 45);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = true;
				String ip = txtIP.getText();
				String port = txtPort.getText();
				String user = textUser.getText();
				String password = textPassword.getText();
				String folder = textFolder.getText();
				String table =textTable.getText();
				main.sqlSelections = new Sql(ip, port, folder, user, password, table);
				try {
					main.sqlSelections.getSqlTable();
				} catch (SQLException e1) {
					String message = "There was a problem reching to the SQL server ";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					String message = "There was a problem reading the data";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
				main.stopCheckFileThread();
				List<CSVRecord> records = main.selections.getRecords();
				records.clear();
				main.SqlLogList = new ArrayList<Sql>();
				main.SqlLogList.add(main.sqlSelections);
				String filePath = System.getProperty("user.dir");
				filePath += "\\sqlData.csv";
				DataBaseIO db =new DataBaseIO();
				if(filePath !=null){
					try {
						records.addAll(db.readData(filePath));
					} catch (Exception e1) {
						String message = "There was a problem reading the selected file";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
				main.updateDataNumOfMacLabel();
				main.startCheckFileThread();
				
				
				
				dispose();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setActionCommand("OK");
		panel.add(button);
		
		JButton button_1 = new JButton("Cancel");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button_1.setActionCommand("Cancel");
		panel.add(button_1);
	}

	public boolean isSelected() {
		return selected;
	}	
}
