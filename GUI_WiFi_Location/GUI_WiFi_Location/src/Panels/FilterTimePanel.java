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

import util.FilterSelection;
import util.FilterType;


public class FilterTimePanel extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private FilterSelection m_filter;

//	private IWiFi_AppHandler m_handler;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FilterTimePanel dialog = new FilterTimePanel();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FilterTimePanel() {
	//	m_handler = handler;
		JTextPane textPaneFrom;
		JTextPane textPaneTo;
		
		setTitle("Filter By Time");
		setBounds(100, 100, 600, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
		
			textPaneFrom = new JTextPane();
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
		textPaneTo.setBounds(81, 47, 188, 26);
		contentPanel.add(textPaneTo);
		
		JCheckBox chckbxNon = new JCheckBox("Not");
		chckbxNon.setBounds(351, 13, 139, 29);
		chckbxNon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPanel.add(chckbxNon);
		
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						m_filter = new FilterSelection();
						m_filter.setType(FilterType.ByTime);
						m_filter.setFrom(textPaneFrom.getText());
						m_filter.setTo(textPaneTo.getText());
					//	m_handler.setFilter1(true);
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
