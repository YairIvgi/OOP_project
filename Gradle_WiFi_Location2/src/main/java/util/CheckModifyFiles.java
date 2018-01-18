/**
 * 
 */
package util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVRecord;
import Panels.main;
import readAndWrite.UnionRecords;

/**
 * @Description This class checks if the source files or folders has changed and update the DataBase.
 * Runs on a different Thread then the main project.
 * @author Idan Hollander
 */

public class CheckModifyFiles implements Runnable {

	private volatile boolean running = true;
	
	private main m_ap;

	private List<String> m_fileNames;
	private List<String> m_folderNames;

	public CheckModifyFiles(List<String> fileNames,List<String> folderNames, main ap) {
		m_fileNames = new ArrayList<String>();
		if(fileNames!=null) {
			m_fileNames.addAll(fileNames);
		}
		m_folderNames = new ArrayList<String>();
		if(folderNames!=null)
			m_folderNames.addAll(folderNames);
		m_ap=ap;
	}
	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		long lastAddTime = System.currentTimeMillis();
		List<File> files;
		List<File> folders;

		while (running) {
			files=covertFiles();
			folders=coversFolders();
			boolean update = false;
			for(int i=0;i<files.size()&&!update;i++) {
				if(files.get(i).lastModified()>lastAddTime) {
					update=true;
				}
			}
			for(int i=0;i<folders.size()&&!update;i++) {
				try {
					if(folders.get(i).lastModified()>lastAddTime||ifModifiedFolder(folders.get(i), lastAddTime)) {
						update=true;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(update) {
				reloadFiles();
				lastAddTime = System.currentTimeMillis();
			}
			try {



				Thread.sleep((long) 1000);


			} catch (InterruptedException e) {

				running = false;
			}
		}

	}

	private List<File> covertFiles() {
		List<File> files=new ArrayList<File>();
		for(int i=0;i<m_fileNames.size();i++) {
			files.add(new File(m_fileNames.get(i)));
		}
		return files;
	}
	private List<File> coversFolders(){
		List<File> folders=new ArrayList<File>();
		for(int i=0;i<m_folderNames.size();i++) {
			folders.add(new File(m_folderNames.get(i)));
		}
		return folders;
	}
	private boolean ifModifiedFolder(File folder,long lastAddTime) throws Exception {
		File[] listOfFiles = folder.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String filename){
				return filename.endsWith(".csv"); 
			}
		});
		if( listOfFiles == null ){
			throw new Exception("The folder "+folder+" does not exist");
		}
		for(int i=0;i<listOfFiles.length;i++) {
			if(listOfFiles[i].lastModified()>lastAddTime) {
				return true;
			}
		}
		return false;
	}
	private void reloadFiles() {
		List<CSVRecord> records = main.selections.getRecords();
		//WiFi_App wa=new WiFi_App();
		records.clear();
		for(int i=0;i<m_fileNames.size();i++) {
			List<CSVRecord> result;
			if(m_fileNames.get(i) !=null){
				UnionRecords ur =new UnionRecords(records);
				try {
					ur.addDataFromFile(m_fileNames.get(i));
					result = ur.get_records();
					records.clear();
					records.addAll(result);
				} catch (IOException e1) {
					String message = "There was a problem reading the selected file";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		for(int i=0;i<m_folderNames.size();i++) {
			List<CSVRecord> result;
			
			if(m_folderNames.get(i) !=null){
				UnionRecords ur =new UnionRecords(records);
				try {
					ur.addDataFromFolder(m_folderNames.get(i));
					result = ur.get_records();
					records.clear();
					records.addAll(result);
				} catch (Exception e1) {
					String message = "There was a problem reading the selected folder";			
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
				}
			}

		}		
		main.selections.setRecords(records);
		m_ap.updateDataNumOfMacLabel();
	}

}
