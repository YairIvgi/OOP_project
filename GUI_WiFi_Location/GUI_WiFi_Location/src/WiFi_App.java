
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import org.apache.commons.csv.CSVRecord;
import org.boehn.kmlframework.kml.KmlException;

import Panels.FilterIDPanel;
import Panels.FilterLocPanel;
import Panels.FilterTimePanel;
import readAndWrite.DataBaseIO;
import readAndWrite.RawCsvReader;
import readAndWrite.UnionRecords;
import util.AddData;
import util.ExportData;
import javax.swing.ButtonGroup;

public class WiFi_App implements IWiFi_AppHandler{

	private JFrame frame;
	private JLabel lblNewLabelFilter1;
	private JLabel lblNewLabelFilter2;

	private List<CSVRecord> m_records = new ArrayList<CSVRecord>();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WiFi_App window = new WiFi_App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WiFi_App() {
		try {
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 773, 501);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblNewLabelFilter1 = new JLabel("Filter 1");
		lblNewLabelFilter1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabelFilter1.setEnabled(false);
		lblNewLabelFilter1.setBounds(28, 314, 138, 20);
		frame.getContentPane().add(lblNewLabelFilter1);

		lblNewLabelFilter2 = new JLabel("Filter 2");
		lblNewLabelFilter2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabelFilter2.setEnabled(false);
		lblNewLabelFilter2.setBounds(28, 350, 138, 20);
		frame.getContentPane().add(lblNewLabelFilter2);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("None");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(11, 189, 155, 29);
		frame.getContentPane().add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("And");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnNewRadioButton_1.setBounds(11, 221, 155, 29);
		frame.getContentPane().add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Or");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnNewRadioButton_2.setBounds(11, 258, 155, 29);
		frame.getContentPane().add(rdbtnNewRadioButton_2);

		JButton btnNewButtonExecute = new JButton("Execute Filter");
		btnNewButtonExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
               FilterIDPanel app=new FilterIDPanel();
               app.main(null);
			}
		});

		btnNewButtonExecute.setBounds(213, 326, 168, 29);
		frame.getContentPane().add(btnNewButtonExecute);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		menuBar.add(mnFile);

		JMenu mnNew = new JMenu("New Location Project");
		mnNew.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFile.add(mnNew);

		///add data from file- menu button
		Image file = new ImageIcon(this.getClass().getResource("/add document.png")).getImage();
		JMenuItem mntmAddFile1 = new JMenuItem("Add File");
		mntmAddFile1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFile1.setIcon(new ImageIcon(file));
		mnNew.add(mntmAddFile1);
		mntmAddFile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddData ad = new AddData(mntmAddFile1);
				String filePath = ad.addFromFile();
				DataBaseIO db =new DataBaseIO();
				if(filePath !=null){
					try {
						m_records.addAll(db.readData(filePath));
					} catch (Exception e1) {
						String message = "There was a problem reading the selected file";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		///add data from folder- menu button
		Image folder = new ImageIcon(this.getClass().getResource("/folder.png")).getImage();
		JMenuItem mntmAddFolder1 = new JMenuItem("Add Folder");
		mntmAddFolder1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFolder1.setIcon(new ImageIcon(folder));
		mnNew.add(mntmAddFolder1);
		mntmAddFolder1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddData ad = new AddData(mntmAddFolder1);
				String folderPath = ad.addFromFolder();
				if(folderPath !=null){
					RawCsvReader folder=new RawCsvReader();
					DataBaseIO db =new DataBaseIO();
					try {
						folder.readFolder(folderPath);				
					} catch (Exception e1) {
						String message = "There was a problem reading the selected folder";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
					try {
						m_records.addAll(db.readData(folder.getOutputFile()));
					} catch (Exception e1) {
						String message = "An error occurred";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JMenu mnAddData = new JMenu("Add Data");
		mnAddData.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFile.add(mnAddData);

		///Add to data from file -menu button
		JMenuItem mntmAddFile2 = new JMenuItem(" From File");
		mntmAddFile2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFile2.setIcon(new ImageIcon(file));
		mnAddData.add(mntmAddFile2);
		mntmAddFile2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddData ad = new AddData(mntmAddFile2);
				String filePath = ad.addFromFile();
				DataBaseIO db =new DataBaseIO();
				if(filePath !=null){
					UnionRecords ur =new UnionRecords(m_records);
					try {
						ur.addDataFromFile(filePath);
						m_records = ur.get_records();
					} catch (IOException e1) {
						String message = "There was a problem reading the selected file";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		///Add to data from folder -menu button
		JMenuItem mntmAddFolder2 = new JMenuItem("From Folder");
		mntmAddFolder2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFolder2.setIcon(new ImageIcon(folder));
		mnAddData.add(mntmAddFolder2);
		mntmAddFolder2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddData ad = new AddData(mntmAddFolder2);
				String folderPath = ad.addFromFolder();
				if(folderPath !=null){
					UnionRecords ur =new UnionRecords(m_records);
					try {
						ur.addDataFromFolder(folderPath);
						m_records = ur.get_records();
					} catch (Exception e1) {
						String message = "There was a problem reading the selected folder";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});


		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenu mnExportData = new JMenu("Export Data");
		mnExportData.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFile.add(mnExportData);

		///write to csv -button
		Image csv = new ImageIcon(this.getClass().getResource("/csv-file.png")).getImage();
		JMenuItem mntmCsvFormat = new JMenuItem("CSV Format");
		mntmCsvFormat.setIcon(new ImageIcon(csv));
		mnExportData.add(mntmCsvFormat);
		mntmCsvFormat.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmCsvFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportData dv = new ExportData(mntmCsvFormat);
				String FolderPath = dv.WriteToFolder();
				if(FolderPath !=null){
					try {
						DataBaseIO io = new DataBaseIO();
						io.writeCSV(FolderPath, m_records);
					} catch (Exception e1) {
						String message = "There was a problem writing to CSV format";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		///write to kml -button
		Image kml = new ImageIcon(this.getClass().getResource("/kml-file.png")).getImage();
		JMenuItem mntmKmlFormat = new JMenuItem("KML Format");
		mntmKmlFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportData dv = new ExportData(mntmKmlFormat);
				String FolderPath = dv.WriteToFolder();
				if(FolderPath !=null){
					DataBaseIO io = new DataBaseIO();
					try {
						io.writeKML(FolderPath, m_records);				
					} catch (KmlException | IOException e1) {
						String message = "There was a problem writing to KML format";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		mntmKmlFormat.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmKmlFormat.setIcon(new ImageIcon(kml));
		mnExportData.add(mntmKmlFormat);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		///delete data -button
		Image delete = new ImageIcon(this.getClass().getResource("/delete.png")).getImage();
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_records = null;
				lblNewLabelFilter1.setEnabled(false);
				lblNewLabelFilter2.setEnabled(false);
			}
		});

		///Retrieve Data -button
		JMenuItem mntmRetrieveData = new JMenuItem("Retrieve Data");
		mntmRetrieveData.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFile.add(mntmRetrieveData);
		mntmDelete.setIcon(new ImageIcon(delete));
		mnFile.add(mntmDelete);

		///close program -button
		Image close = new ImageIcon(this.getClass().getResource("/close.png")).getImage();
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});

		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		mntmClose.setIcon(new ImageIcon(close));
		mnFile.add(mntmClose);

		JMenu mnFilter = new JMenu("Filter");
		mnFilter.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		menuBar.add(mnFilter);

		JMenu mnFilters = new JMenu("Filters");
		mnFilters.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilter.add(mnFilters);

		///filter By time -button
		JMenuItem mntmByTime = new JMenuItem("By Time");
		mntmByTime.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmByTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterTimePanel filterTimePanel =new FilterTimePanel();
				filterTimePanel.show();
			}
		});
		mnFilters.add(mntmByTime);

		///filter By Location -button
		JMenuItem mntmByLocation = new JMenuItem("By Location");
		mntmByLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterLocPanel filterByLocation =new FilterLocPanel();
				filterByLocation.show();
			}
		});
		mntmByLocation.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmByLocation);

		///filter By ID -button
		JMenuItem mntmById = new JMenuItem("By ID");
		mntmById.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterIDPanel filterIDPanel = new FilterIDPanel();
				filterIDPanel.show();
			}
		});
		mntmById.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmById);

		JMenu mnAlgorithms = new JMenu("Algorithms");
		mnAlgorithms.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		menuBar.add(mnAlgorithms);

		JMenuItem mntmFirstAlgo = new JMenuItem("First Algo");
		mntmFirstAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnAlgorithms.add(mntmFirstAlgo);

		JMenu mnSecondAlgo = new JMenu("Second Algo");
		mnSecondAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnAlgorithms.add(mnSecondAlgo);

		JMenuItem mntmSingleScan = new JMenuItem("Single Scan");
		mntmSingleScan.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnSecondAlgo.add(mntmSingleScan);

		JMenuItem mntmMultiScan = new JMenuItem("Multi Scan");
		mntmMultiScan.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnSecondAlgo.add(mntmMultiScan);

	}

	@Override
	public void setFilter1(boolean enable) {
		lblNewLabelFilter1.setEnabled(enable);

	}

	@Override
	public void setFilter2(boolean enable) {
		lblNewLabelFilter2.setEnabled(enable);
	}
}
