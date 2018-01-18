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

import guiFilter.FilterSelectionID;
import guiFilter.FilterType;
import guiFilter.IFiltersSelect;
import Panels.main;

/**
 * @Description The is the GUI panel of filter by ID.
 * @author Yair Ivgi
 */

public class FilterIDPanel extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextPane textPaneID;
	private JButton buttonOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterIDPanel frame = new FilterIDPanel(null);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FilterIDPanel(IFiltersSelect iSelect) {
		setTitle("Filter By ID");
		setBounds(100, 100, 600, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPaneID = new JTextPane();
		textPaneID.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textPaneID.setBounds(151, 36, 297, 26);
		contentPane.add(textPaneID);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblId.setBounds(67, 36, 69, 20);
		contentPane.add(lblId);
		
		JCheckBox checkBoxNot = new JCheckBox("Not");
		checkBoxNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.selections.getM_id().setNot(checkBoxNot.isSelected());
			}
		});
		checkBoxNot.setFont(new Font("Tahoma", Font.PLAIN, 22));
		checkBoxNot.setBounds(480, 36, 69, 29);
		contentPane.add(checkBoxNot);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 149, 578, 45);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterSelectionID id= main.selections.getM_id();
				id.setType(FilterType.ById);
				id.setNot(checkBoxNot.isSelected());		
				id.setId(textPaneID.getText());
				
				if(iSelect.getType1() == null){
					main.selections.setM_type1(FilterType.ById);
					iSelect.setType1(FilterType.ById);
					iSelect.setFilter1(id.toString());
				}else{
					main.selections.setM_type2(FilterType.ById);
					iSelect.setType2(FilterType.ById);
					iSelect.setFilter2(id.toString());
				}
				dispose();
			}
		});
		buttonOk.setFont(new Font("Tahoma", Font.PLAIN, 22));
		buttonOk.setActionCommand("OK");
		panel.add(buttonOk);
		
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonCancel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		buttonCancel.setActionCommand("Cancel");
		panel.add(buttonCancel);
	}

}
