package Panels;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

import Filter.FilterOperation;
import Filter.FilterType;
import Filter.FiltersSelections;
import Filter.IFiltersSelect;
import Panels.Algorithms.Algorithm1Panel;
import Panels.Algorithms.Algorithm2Multi;
import Panels.Algorithms.Algorithm2Single;
import Panels.Filter.FilterIDPanel;
import Panels.Filter.FilterLocPanel;
import Panels.Filter.FilterTimePanel;
import filter.AndFilters;
import filter.FilterById;
import filter.FilterByLocation;
import filter.FilterByTime;
import filter.FilterData;
import filter.IFilter;
import filter.IOperationFilter;
import filter.OrFilters;
import readAndWrite.DataBaseIO;
import readAndWrite.RawCsvReader;
import readAndWrite.UnionRecords;
import util.AddData;
import util.ExportData;
import util.NumberOfDiffMac;

public class WiFi_App implements IFiltersSelect{
	/**
	 * @Description The is the main panel of the GUI of WiFi_Location. 
	 * @author Yair Ivgi
	 */

	private JFrame frame;
	private JLabel labelFilter1;
	private JLabel labelFilter2;
	private JLabel lblSampelsLabel;
	private JLabel lblDifferentMacSamples;

	JRadioButton radioButtonNone;
	JRadioButton radioButtonAnd;
	JRadioButton radioButtonOr;

	private FilterType type1;
	private FilterType type2;

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

	class Task1 implements Runnable{
		public void run(){
			//update filters
			{
				FiltersSelections obj = new FiltersSelections();

				String folder = System.getProperty("user.dir");
				File file = new File(folder,"dataSelections.obj");
				try {
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					obj = selections;
					oos.writeObject(obj);
					oos.close();
					fos.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			List<CSVRecord> records = selections.getRecords();
			if(records.size() ==0){			
				JOptionPane.showMessageDialog(new JFrame(),"Error- No Data\n"+"Please enter data", "Dialog",JOptionPane.ERROR_MESSAGE);
				return;
			}
			IFilter filter1 = null;
			IFilter filter2 = null;
			boolean isNot1 = false;
			boolean isNot2 = false;
			FilterData dataFilter = new FilterData();
			IOperationFilter oppFilter1 = null;
			if(labelFilter1.isEnabled()){			
				switch (WiFi_App.selections.getM_type1()){
				case ById:
					filter1 = new FilterById(selections.getM_id().getId());		
					isNot1 = selections.getM_id().isNot();
					break;
				case ByLocation:
					double lat,lon,radius;
					lat = Double.parseDouble(selections.getM_location().getLat());
					lon = Double.parseDouble(selections.getM_location().getLon());
					radius = Double.parseDouble(selections.getM_location().getRadius());
					filter1 = new FilterByLocation(lon,lat,radius);
					isNot1 = selections.getM_location().isNot();
					break;
				case ByTime:
					try {
						filter1 = new FilterByTime(selections.getM_time().getFrom(), selections.getM_time().getTo());
						isNot1 = selections.getM_time().isNot();
					} catch (Exception e2) {
						String message = "oops error: "+e2.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}
					break;
				}
			}
			if(labelFilter2.isEnabled()){
				switch (WiFi_App.selections.getM_type2()){
				case ById:
					filter2 = new FilterById(selections.getM_id().getId());
					isNot2 = selections.getM_id().isNot();
					break;
				case ByLocation:
					double lat,lon,radius;
					lat = Double.parseDouble(selections.getM_location().getLat());
					lon = Double.parseDouble(selections.getM_location().getLon());
					radius = Double.parseDouble(selections.getM_location().getRadius());
					filter2 = new FilterByLocation(lat,lon,radius);
					isNot2 = selections.getM_location().isNot();
					break;
				case ByTime:
					try {
						filter2 = new FilterByTime(selections.getM_time().getFrom(), selections.getM_time().getTo());
						isNot2 = selections.getM_time().isNot();
					} catch (Exception e1) {
						String message = "oops error: "+e1.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}			
					break;
				}
				if(selections.getM_operation() == FilterOperation.or){
					oppFilter1 = new OrFilters();

				}else{
					oppFilter1 = new AndFilters();
				}

			}
			List<CSVRecord> filterRecords;
			if(labelFilter1.isEnabled() && labelFilter2.isEnabled()){
				try {
					filterRecords = oppFilter1.getFiltered(records, filter1,isNot1, filter2,isNot2);
				} catch (Exception e1) {
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else if(labelFilter1.isEnabled() && !labelFilter2.isEnabled()){
				try {
					filterRecords = dataFilter.filterData(records, filter1,isNot1);
				} catch (Exception e1) {
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else{
				try {
					filterRecords = dataFilter.filterData(records, filter2,isNot2);
				} catch (Exception e1) {
					String message = "oops error: "+e1.getMessage();			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			records.clear();
			records.addAll(filterRecords);
			updateDataNumOfMacLabel();
		}
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
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.setBounds(100, 100, 800, 600);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		labelFilter1 = new JLabel("Filter 1");
		labelFilter1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelFilter1.setEnabled(true);
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
				labelFilter2.setEnabled(true);
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
				labelFilter2.setEnabled(true);
			}
		});
		buttonGroup.add(radioButtonOr);
		radioButtonOr.setFont(new Font("Tahoma", Font.PLAIN, 20));
		radioButtonOr.setBounds(290, 105, 77, 29);
		frame.getContentPane().add(radioButtonOr);

		JButton btnNewButtonExecute = new JButton("Execute Filter");
		btnNewButtonExecute.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnNewButtonExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					FiltersSelections obj = new FiltersSelections();

					String folder = System.getProperty("user.dir");
					File file = new File(folder,"dataSelections.obj");
					try {
						FileOutputStream fos = new FileOutputStream(file);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						obj.setRecords(selections.getRecords()); 
						obj.setDiffrentMac(selections.getDiffrentMac());
						oos.writeObject(obj);
						oos.close();
						fos.close();
					} catch (Exception e3) {
						JOptionPane.showMessageDialog(new JFrame(),"Error-"+e3.getMessage(), "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				List<CSVRecord> records = selections.getRecords();
				if(records.size() ==0){			
					JOptionPane.showMessageDialog(new JFrame(),"Error- No Data\n"+"Please enter data", "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				IFilter filter1 = null;
				IFilter filter2 = null;
				boolean isNot1 = false;
				boolean isNot2 = false;
				FilterData dataFilter = new FilterData();
				IOperationFilter oppFilter1 = null;
				if(labelFilter1.isEnabled()){			
					switch (WiFi_App.selections.getM_type1()){
					case ById:
						filter1 = new FilterById(selections.getM_id().getId());		
						isNot1 = selections.getM_id().isNot();
						break;
					case ByLocation:
						double lat,lon,radius;
						lat = Double.parseDouble(selections.getM_location().getLat());
						lon = Double.parseDouble(selections.getM_location().getLon());
						radius = Double.parseDouble(selections.getM_location().getRadius());
						filter1 = new FilterByLocation(lon,lat,radius);
						isNot1 = selections.getM_location().isNot();
						break;
					case ByTime:
						try {
							filter1 = new FilterByTime(selections.getM_time().getFrom(), selections.getM_time().getTo());
							isNot1 = selections.getM_time().isNot();
						} catch (Exception e2) {
							String message = "oops error: "+e2.getMessage();			
							JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
							return;
						}
						break;
					}
				}
				if(labelFilter2.isEnabled()){
					switch (WiFi_App.selections.getM_type2()){
					case ById:
						filter2 = new FilterById(selections.getM_id().getId());
						isNot2 = selections.getM_id().isNot();
						break;
					case ByLocation:
						double lat,lon,radius;
						lat = Double.parseDouble(selections.getM_location().getLat());
						lon = Double.parseDouble(selections.getM_location().getLon());
						radius = Double.parseDouble(selections.getM_location().getRadius());
						filter2 = new FilterByLocation(lat,lon,radius);
						isNot2 = selections.getM_location().isNot();
						break;
					case ByTime:
						try {
							filter2 = new FilterByTime(selections.getM_time().getFrom(), selections.getM_time().getTo());
							isNot2 = selections.getM_time().isNot();
						} catch (Exception e1) {
							String message = "oops error: "+e1.getMessage();			
							JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
							return;
						}			
						break;
					}
					if(selections.getM_operation() == FilterOperation.or){
						oppFilter1 = new OrFilters();

					}else{
						oppFilter1 = new AndFilters();
					}

				}
				List<CSVRecord> filterRecords;
				if(labelFilter1.isEnabled() && labelFilter2.isEnabled()){
					try {
						filterRecords = oppFilter1.getFiltered(records, filter1,isNot1, filter2,isNot2);
					} catch (Exception e1) {
						String message = "oops error: "+e1.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}else if(labelFilter1.isEnabled() && !labelFilter2.isEnabled()){
					try {
						filterRecords = dataFilter.filterData(records, filter1,isNot1);
					} catch (Exception e1) {
						String message = "oops error: "+e1.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}else{
					try {
						filterRecords = dataFilter.filterData(records, filter2,isNot2);
					} catch (Exception e1) {
						String message = "oops error: "+e1.getMessage();			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				records.clear();
				records.addAll(filterRecords);
				updateDataNumOfMacLabel();
			}
		});

		btnNewButtonExecute.setBounds(290, 367, 203, 60);
		frame.getContentPane().add(btnNewButtonExecute);

		lblSampelsLabel = new JLabel("0");
		lblSampelsLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblSampelsLabel.setBounds(488, 247, 173, 40);
		frame.getContentPane().add(lblSampelsLabel);

		lblDifferentMacSamples = new JLabel("0");
		lblDifferentMacSamples.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblDifferentMacSamples.setBounds(488, 287, 173, 40);
		frame.getContentPane().add(lblDifferentMacSamples);

		JLabel label = new JLabel("Combo Sampels: ");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(225, 245, 173, 40);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("Different Mac Samples:  ");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_1.setBounds(225, 285, 248, 40);
		frame.getContentPane().add(label_1);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		menuBar.add(mnFile);

		JMenu mnNew = new JMenu("New Location Project");
		mnNew.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFile.add(mnNew);

		///new program-  data from file- menu button
		Image file = new ImageIcon(this.getClass().getResource("/add document.png")).getImage();
		JMenuItem mntmAddFile1 = new JMenuItem("Add File");
		mntmAddFile1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFile1.setIcon(new ImageIcon(file));
		mnNew.add(mntmAddFile1);
		mntmAddFile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<CSVRecord> records = selections.getRecords();
				records.clear();
				AddData ad = new AddData(mntmAddFile1);
				String filePath = ad.addFromFile();
				DataBaseIO db =new DataBaseIO();
				if(filePath !=null){
					try {
						records.addAll(db.readData(filePath));
					} catch (Exception e1) {
						String message = "There was a problem reading the selected file";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
				updateDataNumOfMacLabel();
			}
		});

		///new program- data from folder- menu button
		Image folder = new ImageIcon(this.getClass().getResource("/folder.png")).getImage();
		JMenuItem mntmAddFolder1 = new JMenuItem("Add Folder");
		mntmAddFolder1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFolder1.setIcon(new ImageIcon(folder));
		mnNew.add(mntmAddFolder1);
		mntmAddFolder1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<CSVRecord> records = selections.getRecords();
				records.clear();
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
						records.addAll(db.readData(folder.getOutputFile()));
					} catch (Exception e1) {
						String message = "An error occurred";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
				updateDataNumOfMacLabel();
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
				List<CSVRecord> records = selections.getRecords();
				List<CSVRecord> result;
				if(records.size() ==0){			
					JOptionPane.showMessageDialog(new JFrame(),"Please start new project", "Dialog",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				AddData ad = new AddData(mntmAddFile2);
				String filePath = ad.addFromFile();
				if(filePath !=null){
					UnionRecords ur =new UnionRecords(records);
					try {
						ur.addDataFromFile(filePath);
						result = ur.get_records();
						records.clear();
						records.addAll(result);
						updateFilter();
					} catch (IOException e1) {
						String message = "There was a problem reading the selected file";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
				updateDataNumOfMacLabel();
			}
		});

		///Add to data from folder -menu button
		JMenuItem mntmAddFolder2 = new JMenuItem("From Folder");
		mntmAddFolder2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmAddFolder2.setIcon(new ImageIcon(folder));
		mnAddData.add(mntmAddFolder2);
		mntmAddFolder2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<CSVRecord> records = selections.getRecords();
				List<CSVRecord> result;
				if(records.size() ==0){			
					JOptionPane.showMessageDialog(new JFrame(),"Please start new project", "Dialog",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				AddData ad = new AddData(mntmAddFolder2);
				String folderPath = ad.addFromFolder();
				if(folderPath !=null){
					UnionRecords ur =new UnionRecords(records);
					try {
						ur.addDataFromFolder(folderPath);
						result = ur.get_records();
						records.clear();
						records.addAll(result);
						updateFilter();
					} catch (Exception e1) {
						String message = "There was a problem reading the selected folder";			
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					}
				}
				updateDataNumOfMacLabel();
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
				List<CSVRecord> records = selections.getRecords();
				if(records.size() ==0){			
					JOptionPane.showMessageDialog(new JFrame(),"Error- No Data\n"+"Please enter data", "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				ExportData dv = new ExportData(mntmCsvFormat);
				String folderPath = dv.WriteToFolder();
				WiFi_App.selections.setM_folderPath(folderPath);
				if(folderPath !=null){
					try {
						DataBaseIO io = new DataBaseIO();
						io.writeCSV(folderPath, records);
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
				List<CSVRecord> records = selections.getRecords();
				if(records.size() ==0){		
					JOptionPane.showMessageDialog(new JFrame(),"Error- No Data\n"+"Please enter data", "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				ExportData dv = new ExportData(mntmKmlFormat);
				String FolderPath = dv.WriteToFolder();
				if(FolderPath !=null){
					DataBaseIO io = new DataBaseIO();
					try {
						io.writeKML(FolderPath, records);				
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
		JMenuItem mntmDelete = new JMenuItem("Delete Data");
		mntmDelete.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selections = new FiltersSelections();
				List<CSVRecord> records = selections.getRecords();
				records.clear();
				resetFilter1();
				resetFilter2();
				updateDataNumOfMacLabel();
				updateFilter();
			}
		});

		///Retrieve Data -button
		JMenuItem mntmRetrieveData = new JMenuItem("Retrieve Data");
		mntmRetrieveData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FiltersSelections obj2 = null;
				String folder = System.getProperty("user.dir");
				try {
					File file1 = new File(folder,"dataSelections.obj");
					FileInputStream fis = new FileInputStream(file1);
					ObjectInputStream ois = new ObjectInputStream(fis);
					obj2 = (FiltersSelections)ois.readObject();
					ois.close();
					fis.close();
				} catch (Exception e2) {
					String message = "The Data is up to date";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
				selections.setRecords(obj2.getRecords());
				selections.setDiffrentMac(obj2.getDiffrentMac());
				updateDataNumOfMacLabel();
			}
		});
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

		JMenuItem mntmResetFilter = new JMenuItem("Reset Filters");
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
		mntmSaveFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				FiltersSelections obj = new FiltersSelections();	
//				String folder = System.getProperty("user.dir");
//				File file = new File(folder,"SaveFilters.obj");
//				try {
//					FileOutputStream fos = new FileOutputStream(file);
//					ObjectOutputStream oos = new ObjectOutputStream(fos);
//					obj.setM_id(selections.getM_id());
//					obj.setM_location(selections.getM_location());
//					obj.setM_time(selections.getM_time());
//					obj.setM_type1(selections.getM_type1());
//					obj.setM_type2(selections.getM_type2());
//					obj.setM_operation(selections.getM_operation());
//					oos.writeObject(obj);
//					oos.close();
//					fos.close();
//				} catch (Exception e3) {
//					String message = "Error can't save filters";			
//					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
//				}
			}
		});
		mntmSaveFilters.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(mntmSaveFilters);

		JMenuItem UploadSavedFilter = new JMenuItem("Upload Saved Filter");
		UploadSavedFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				FiltersSelections obj2 = null;
//				try {
//					String folder = System.getProperty("user.dir");
//					File file = new File(folder,"SaveFilters.obj");
//					FileInputStream fis = new FileInputStream(file);
//					ObjectInputStream ois = new ObjectInputStream(fis);
//					obj2 = (FiltersSelections)ois.readObject();
//					selections.setM_id(obj2.getM_id());
//					selections.setM_location(obj2.getM_location());
//					selections.setM_time(obj2.getM_time());
//					selections.setM_type1(obj2.getM_type1());
//					selections.setM_type2(obj2.getM_type2());
//					selections.setM_operation(obj2.getM_operation());
//					ois.close();
//					fis.close();
//					
//				} catch (Exception e4) {
//					String message = "Error can't upload filters";			
//					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
//				}
			}
		});
		UploadSavedFilter.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFilters.add(UploadSavedFilter);

		JMenu mnAlgorithms = new JMenu("Algorithms");
		mnAlgorithms.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		menuBar.add(mnAlgorithms);

		JMenuItem mntmFirstAlgo = new JMenuItem("First Algo");
		mntmFirstAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selections.getRecords().size()==0){
					String message = "Error NO DATA";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
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
			public void actionPerformed(ActionEvent e) {
				if(selections.getRecords().size()==0){
					String message = "Error NO DATA";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Algorithm2Single algorithm2Single= new Algorithm2Single();
				algorithm2Single.setVisible(true);
			}
		});
		mntmSingleScan.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnSecondAlgo.add(mntmSingleScan);

		JMenuItem mntmMultiScan = new JMenuItem("Multi Scan");
		mntmMultiScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selections.getRecords().size()==0){
					String message = "Error NO DATA";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Algorithm2Multi algorithm2Multi= new Algorithm2Multi();
				algorithm2Multi.setVisible(true);
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
		labelFilter1.setText("Filter 1");
		radioButtonNone.setSelected(true);
		resetFilter2() ;
	}

	@Override
	public void resetFilter2() {
		type2 = null;
		labelFilter2.setText("Filter 2");
		labelFilter2.setEnabled(false);
		radioButtonNone.setSelected(true);
	}
	public void updateDataNumOfMacLabel(){
		lblSampelsLabel.setText(String.valueOf((selections.getRecords().size())));
		NumberOfDiffMac nod = new NumberOfDiffMac(selections.getRecords());
		lblDifferentMacSamples.setText(String.valueOf(nod.getNum()));
	}

	@Override
	public void serializeFilter() {
		// TODO Auto-generated method stub

	}
	
	public void updateFilter() {
		Task1 bl = new Task1();
		Thread t = new Thread(bl);
		t.start();
	}
}
