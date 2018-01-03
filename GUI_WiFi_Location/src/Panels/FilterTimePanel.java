package Panels;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import Filter.FilterSelectionID;
import Filter.FilterSelectionTime;
import Filter.FilterType;
import Filter.IFiltersSelect;


public class FilterTimePanel extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextPane textPaneFrom;
	private JTextPane textPaneTo;

//	private IWiFi_AppHandler m_handler;
	
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
		setBounds(100, 100, 600, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
		
			textPaneFrom = new JTextPane();
			textPaneFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
			textPaneFrom.setBounds(81, 16, 188, 26);
			contentPanel.add(textPaneFrom);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(25, 16, 69, 20);
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lblFrom);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(25, 56, 69, 20);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(lblTo);
		
		textPaneTo = new JTextPane();
		textPaneTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPaneTo.setBounds(81, 47, 188, 26);
		contentPanel.add(textPaneTo);
		
		JCheckBox checkBoxNot = new JCheckBox("Not");
		checkBoxNot.setBounds(351, 13, 139, 29);
		checkBoxNot.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(checkBoxNot);
		
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {	
						WiFi_App.selections.getM_time().setNot(checkBoxNot.isSelected());		
						FilterSelectionTime time= WiFi_App.selections.getM_time();
						time.setType(FilterType.ByTime);
						time.setFrom(textPaneFrom.getText());
						time.setTo(textPaneTo.getText());
						time.setNot(checkBoxNot.isSelected());		
						
						if(iSelect.getType1() == null){
							WiFi_App.selections.setM_type1(FilterType.ByTime);
							iSelect.setType1(FilterType.ByTime);
							iSelect.setFilter1(time.toString());
						}else{
							WiFi_App.selections.setM_type2(FilterType.ByTime);
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
