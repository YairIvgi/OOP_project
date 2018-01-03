package Panels;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
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

import Algorithms.Algorithm1Panel;
import Filter.FilterOperation;
import Filter.FilterType;
import Filter.FiltersSelections;
import Filter.IFiltersSelect;
import filter.FilterById;
import filter.FilterByLocation;
import filter.FilterByTime;
import filter.IFilter;
import readAndWrite.DataBaseIO;
import readAndWrite.RawCsvReader;
import readAndWrite.UnionRecords;
import util.AddData;
import util.ExportData;

public class WiFi_App implements IFiltersSelect{

	private JFrame frame;
	private JLabel labelFilter1;
	private JLabel labelFilter2;
	JRadioButton radioButtonNone;
	JRadioButton radioButtonAnd;
	JRadioButton radioButtonOr;
	
	private FilterType type1;
	private FilterType type2;

	private List<CSVRecord> m_records = new ArrayList<CSVRecord>();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public static FiltersSelections selections = new FiltersSelections();
	
	
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

		labelFilter1 = new JLabel("Filter 1");
		labelFilter1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelFilter1.setEnabled(false);
		labelFilter1.setBounds(38, 49, 504, 40);
		frame.getContentPane().add(labelFilter1);

		labelFilter2 = new JLabel("Filter 2");
		labelFilter2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelFilter2.setEnabled(false);
		labelFilter2.setBounds(38, 157, 504, 40);
		frame.getContentPane().add(labelFilter2);

		radioButtonNone = new JRadioButton("None");
		radioButtonNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WiFi_App.selections.setM_operation(FilterOperation.none);
				labelFilter2.setText("Filter 2");
				labelFilter2.setEnabled(false);
			}
		});
		buttonGroup.add(radioButtonNone);
		radioButtonNone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		radioButtonNone.setSelected(true);
		radioButtonNone.setBounds(28, 105, 96, 29);
		frame.getContentPane().add(radioButtonNone);

		radioButtonAnd = new JRadioButton("And");
		radioButtonAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WiFi_App.selections.setM_operation(FilterOperation.and);
			}
		});
		buttonGroup.add(radioButtonAnd);
		radioButtonAnd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		radioButtonAnd.setBounds(165, 105, 77, 29);
		frame.getContentPane().add(radioButtonAnd);

		radioButtonOr = new JRadioButton("Or");
		radioButtonOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WiFi_App.selections.setM_operation(FilterOperation.or);
			}
		});
		buttonGroup.add(radioButtonOr);
		radioButtonOr.setFont(new Font("Tahoma", Font.PLAIN, 20));
		radioButtonOr.setBounds(290, 105, 77, 29);
		frame.getContentPane().add(radioButtonOr);

		JButton btnNewButtonExecute = new JButton("Execute Filter");
		btnNewButtonExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IFilter filter1 = null;
				IFilter filter2 = null;
				switch (WiFi_App.selections.getM_type1()){
				case ById:
					filter1 = new FilterById("Lenovo PB2-690Y");
					break;
				case ByLocation:
					filter1 = new FilterByLocation(34.806, 32.165, 0.022);
					break;
				case ByTime:
					try {
						filter1 = new FilterByTime("2016-12-1  10:43:00", "2017-12-1  20:50:14");
					} catch (Exception e2) {
						String message = "oops error: "+e2.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}
					break;
				}
				switch (WiFi_App.selections.getM_type2()){
				case ById:
					filter2 = new FilterById("Lenovo PB2-690Y");
					break;
				case ByLocation:
					filter2 = new FilterByLocation(34.806, 32.165, 0.022);
					break;
				case ByTime:
					try {
						filter2 = new FilterByTime("2016-12-1  10:43:00", "2017-12-1  20:50:14");
					} catch (Exception e1) {
						String message = "oops error: "+e1.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}
					break;
				}
				System.out.println(WiFi_App.selections.getM_time().getType().toString()+"  "+WiFi_App.selections.getM_time().getFrom());
				System.out.println(WiFi_App.selections.getM_id().getType().toString()+"  "+WiFi_App.selections.getM_id().getId());
				System.out.println(WiFi_App.selections.getM_location().getType().toString()+"  "+WiFi_App.selections.getM_location().getLat());
			}
		});

		btnNewButtonExecute.setBounds(261, 364, 168, 29);
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
				String folderPath = dv.WriteToFolder();
				WiFi_App.selections.setM_folderPath(folderPath);
				if(folderPath !=null){
					try {
						DataBaseIO io = new DataBaseIO();
						io.writeCSV(folderPath, m_records);
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
				labelFilter1.setEnabled(false);
				labelFilter2.setEnabled(false);
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
				FilterTimePanel filterTimePanel =new FilterTimePanel(WiFi_App.this);
				filterTimePanel.setVisible(true);
			}
		});
		mnFilters.add(mntmByTime);

		///filter By Location -button
		JMenuItem mntmByLocation = new JMenuItem("By Location");
		mntmByLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterLocPanel filterByLocation =new FilterLocPanel(WiFi_App.this);
				filterByLocation.setVisible(true);
			}
		});
		mntmByLocation.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmByLocation);

		///filter By ID -button
		JMenuItem mntmById = new JMenuItem("By ID");
		mntmById.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterIDPanel filterIDPanel = new FilterIDPanel(WiFi_App.this);
				filterIDPanel.setVisible(true);				
			}
		});
		
		mntmById.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmById);
		
		JSeparator separator_3 = new JSeparator();
		mnFilters.add(separator_3);
		
		JMenuItem mntmResetFilter = new JMenuItem("Reset Filter 1");
		mntmResetFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFilter1();
			}
		});
		mntmResetFilter.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmResetFilter);
		
		JMenuItem mntmResetFilter_1 = new JMenuItem("Reset Filter 2");
		mntmResetFilter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFilter2();
			}
		});
		mntmResetFilter_1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmResetFilter_1);
		
		JSeparator separator_4 = new JSeparator();
		mnFilters.add(separator_4);
		
		JMenuItem mntmSaveFilters = new JMenuItem("Save Filters ");
		mntmSaveFilters.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmSaveFilters);

		JMenu mnAlgorithms = new JMenu("Algorithms");
		mnAlgorithms.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		menuBar.add(mnAlgorithms);

		JMenuItem mntmFirstAlgo = new JMenuItem("First Algo");
		mntmFirstAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Algorithm1Panel algorithm1panel= new Algorithm1Panel();
				algorithm1panel.setVisible(true);
			}
		});
		mntmFirstAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnAlgorithms.add(mntmFirstAlgo);

		JMenu mnSecondAlgo = new JMenu("Second Algo");
		mnSecondAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnAlgorithms.add(mnSecondAlgo);

		JMenuItem mntmSingleScan = new JMenuItem("Single Scan");
		mntmSingleScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mntmSingleScan.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnSecondAlgo.add(mntmSingleScan);

		JMenuItem mntmMultiScan = new JMenuItem("Multi Scan");
		mntmMultiScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mntmMultiScan.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnSecondAlgo.add(mntmMultiScan);

	}

	@Override
	public String getFilter1() {
		return labelFilter1.getText();
	}

	@Override
	public String getFilter2() {
		return labelFilter2.getText();
	}

	@Override
	public void setFilter1(String filter1) {
		labelFilter1.setEnabled(true);
		labelFilter1.setText(filter1);			
	}

	@Override
	public void setFilter2(String filter2) {
		if(radioButtonAnd.isSelected() || radioButtonOr.isSelected()){
		labelFilter2.setEnabled(true);
		labelFilter2.setText(filter2);	
		}
	}

	@Override
	public FilterType getType1() {
		return type1;
	}

	@Override
	public FilterType getType2() {
		return type2;
	}

	@Override
	public void setType1(FilterType type1) {
		this.type1 = type1;
	}

	@Override
	public void setType2(FilterType type2) {
		this.type2 = type2;
	}

	@Override
	public void resetFilter1() {
		type1 = null;
		setFilter1("Filter 1");
		labelFilter1.setEnabled(false);
		radioButtonNone.setSelected(true);
	}

	@Override
	public void resetFilter2() {
		type2 = null;
		setFilter2("Filter 2");
		labelFilter2.setEnabled(false);
		radioButtonNone.setSelected(true);
	}

	@Override
	public void serializeFilter() {
		// TODO Auto-generated method stub
		
	}

}
