package Panels.Filter;

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

import Filter.FilterSelectionLocation;
import Filter.FilterType;
import Filter.IFiltersSelect;
import Panels.main;

/**
 * @Description The is the GUI panel of filter by Location.
 * @author Yair Ivgi
 */

public class FilterLocPanel extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextPane textPaneLongtiude;
	private JTextPane textPaneLatitude;
	private JTextPane textPaneRadius;
	private JCheckBox checkBoxNot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterLocPanel frame = new FilterLocPanel(null);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	public FilterLocPanel(IFiltersSelect iSelect){
		
		setBounds(100, 100, 600, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		checkBoxNot = new JCheckBox("Not");
		checkBoxNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.selections.getM_location().setNot(checkBoxNot.isSelected());
			}
		});
		checkBoxNot.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkBoxNot.setBounds(439, 47, 139, 29);
		contentPane.add(checkBoxNot);
		
		textPaneLongtiude = new JTextPane();
		textPaneLongtiude.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPaneLongtiude.setBounds(145, 5, 188, 26);
		contentPane.add(textPaneLongtiude);
		
		textPaneRadius = new JTextPane();
		textPaneRadius.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPaneRadius.setBounds(145, 101, 188, 26);
		contentPane.add(textPaneRadius);
		
		JLabel lblCenterPoint = new JLabel("Longitude ");
		lblCenterPoint.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCenterPoint.setBounds(37, 5, 103, 26);
		contentPane.add(lblCenterPoint);
		
		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRadius.setBounds(37, 107, 93, 20);
		contentPane.add(lblRadius);
		
		textPaneLatitude = new JTextPane();
		textPaneLatitude.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPaneLatitude.setBounds(145, 50, 188, 26);
		contentPane.add(textPaneLatitude);
		
		JLabel lblLatitude = new JLabel("Latitude ");
		lblLatitude.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLatitude.setBounds(37, 56, 93, 20);
		contentPane.add(lblLatitude);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 143, 578, 51);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterSelectionLocation location= main.selections.getM_location();
				location.setType(FilterType.ByLocation);
				location.setRadius(textPaneRadius.getText());
				location.setLon(textPaneLongtiude.getText());
				location.setLat(textPaneLatitude.getText());
				location.setNot(checkBoxNot.isSelected());
				
				if(iSelect.getType1() == null){
					main.selections.setM_type1(FilterType.ByLocation);
					iSelect.setType1(FilterType.ByLocation);
					iSelect.setFilter1(location.toString());
				}else{
					main.selections.setM_type2(FilterType.ByLocation);
					iSelect.setType2(FilterType.ByLocation);
					iSelect.setFilter2(location.toString());
				}				
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
	

}
