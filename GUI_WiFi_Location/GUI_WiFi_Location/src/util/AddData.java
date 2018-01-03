package util;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddData {
	private JMenuItem m_item;
	public AddData(JMenuItem item){
		m_item = item;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public String  addFromFolder(){
		String folderPath;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("WiFi_App- Add Folder");
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