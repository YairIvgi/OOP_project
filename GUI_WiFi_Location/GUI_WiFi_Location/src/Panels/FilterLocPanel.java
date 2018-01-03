package Panels;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class FilterLocPanel extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterLocPanel frame = new FilterLocPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FilterLocPanel() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 299, 578, 45);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		JCheckBox checkBox = new JCheckBox("Not");
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkBox.setBounds(439, 76, 139, 29);
		contentPane.add(checkBox);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(145, 44, 188, 26);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(145, 125, 188, 26);
		contentPane.add(textPane_1);
		
		JLabel lblCenterPoint = new JLabel("longitude ");
		lblCenterPoint.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCenterPoint.setBounds(15, 44, 134, 26);
		contentPane.add(lblCenterPoint);
		
		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRadius.setBounds(15, 131, 93, 20);
		contentPane.add(lblRadius);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(145, 86, 188, 26);
		contentPane.add(textPane_2);
		
		JLabel lblLatitude = new JLabel("Latitude ");
		lblLatitude.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLatitude.setBounds(15, 89, 134, 20);
		contentPane.add(lblLatitude);
	}

}
