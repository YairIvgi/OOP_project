package util;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @Description This class opens a file directory and allows the user to choose a file or folder path.
 * @author Yair Ivgi
 */

public class SelectFileDirectory {

	private JMenuItem m_item;

	public SelectFileDirectory(JMenuItem item){
		m_item = item;
	}
	public String  FromFolder(boolean addData){
		String folderPath;
		JFileChooser fc = new JFileChooser();
		if(addData){
			fc.setDialogTitle("WiFi_App- Add Folder");
		}else{
			fc.setDialogTitle("WiFi_App- select Folder");
		}
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fc.showOpenDialog(m_item) == JFileChooser.APPROVE_OPTION){
			folderPath = fc.getSelectedFile().getAbsolutePath();
			return folderPath;
		}
		return null;
	}

	public String  addFromFile(){
		String folderPath;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("WiFi_App- Add File");
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV only", "csv");
		fc.addChoosableFileFilter(filter);
		if (fc.showOpenDialog(m_item) == JFileChooser.APPROVE_OPTION) {
			folderPath = fc.getSelectedFile().getAbsolutePath();
			return folderPath;
		}
		return null;
	}
}