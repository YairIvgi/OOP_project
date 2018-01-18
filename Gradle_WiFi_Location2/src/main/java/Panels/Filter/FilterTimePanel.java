package Panels.Filter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import guiFilter.FilterSelectionTime;
import guiFilter.FilterType;
import guiFilter.IFiltersSelect;
import Panels.Main;

/**
 * @Description The is the GUI panel of filter by Time.
 * @author Yair Ivgi
 */

public class FilterTimePanel extends JFrame {
	private static final long serialVersionUID = 1L;
	private  JPanel contentPanel;
	private JTextPane textPaneFrom;
	private JTextPane textPaneTo;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		try {
			FilterTimePanel dialog = new FilterTimePanel(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FilterTimePanel(IFiltersSelect iSelect) {

		setTitle("Filter By Time");
		setBounds(100, 100, 600, 250);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textPaneFrom = new JTextPane();
		textPaneFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPaneFrom.setBounds(81, 47, 340, 26);
		contentPanel.add(textPaneFrom);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(15, 47, 59, 20);
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lblFrom);

		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(15, 84, 49, 20);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lblTo);

		textPaneTo = new JTextPane();
		textPaneTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPaneTo.setBounds(81, 84, 340, 26);
		contentPanel.add(textPaneTo);

		JCheckBox checkBoxNot = new JCheckBox("Not");
		checkBoxNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.selections.getM_time().setNot(checkBoxNot.isSelected());
			}
		});
		checkBoxNot.setBounds(477, 43, 90, 29);
		checkBoxNot.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(checkBoxNot);

		JLabel lblNewLabel = new JLabel("yyyy-mm-dd   hh:mm:ss");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblNewLabel.setBounds(80, 11, 341, 20);
		contentPanel.add(lblNewLabel);

		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Main.selections.getM_time().setNot(checkBoxNot.isSelected());		
				FilterSelectionTime time= Main.selections.getM_time();
				time.setType(FilterType.ByTime);
				time.setFrom(textPaneFrom.getText());
				time.setTo(textPaneTo.getText());
				time.setNot(checkBoxNot.isSelected());	

				if(iSelect.getType1() == null){
					Main.selections.setM_type1(FilterType.ByTime);
					iSelect.setType1(FilterType.ByTime);
					iSelect.setFilter1(time.toString());
				}else{
					Main.selections.setM_type2(FilterType.ByTime);
					iSelect.setType2(FilterType.ByTime);
					iSelect.setFilter2(time.toString());
				}
				dispose();
			}
		});
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);		
	}
}
