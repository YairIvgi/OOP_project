package util;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

public class ExportData {
	private JMenuItem m_item;

	public ExportData(JMenuItem item){
		m_item = item;
	}
	
	public String  WriteToFolder(){
		String folderPath;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("WiFi_App- select Folder");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fc.showOpenDialog(m_item) == JFileChooser.APPROVE_OPTION){
			folderPath = fc.getSelectedFile().getAbsolutePath();
			return folderPath;
		}
		return null;
	}
}
