import java.io.*;

import javax.swing.JOptionPane;
public class FileOperations{

	NoteGUI nGUI;
	BufferedReader reader;
	private boolean saved;
	private String text;
	private String location = "C:/Users/" + System.getProperty("user.name") + "/Documents/Writer Files/"; //The location on the computer where files will be stored


	public boolean isSaved(){
		return saved;
	}

	public void setSaved(boolean saved){
		this.saved = saved;
	}

	public FileOperations(NoteGUI nGUI){
		this.nGUI = nGUI;
	}

	public void save(){
		int verify = JOptionPane.showConfirmDialog(null, "Are you sure you wanna save the file?", "Confirm?", JOptionPane.OK_CANCEL_OPTION);
		if(verify == JOptionPane.OK_OPTION){
			String text = JOptionPane.showInputDialog("Input File Name.", JOptionPane.OK_OPTION);
			saveAs(text);
		}else{
			saved = false;
		}
	}

	public void saveAs(String fileName){
		try (FileWriter fileWriter = new FileWriter(location + fileName + ".txt");){
			File file = new File(location + fileName + ".txt");
			if(file.exists()){
		        fileWriter.write(nGUI.getTxt());
		        saved = true;
			}else{
				file.createNewFile();
				fileWriter.write(nGUI.getTxt());
		        saved = true;
			}



		}catch(IOException e){
			System.out.println("You have an error @$$hole. Fix me: "+ e.getMessage());
		}
	}

	public void open(String file){
		try{
			FileInputStream open = new FileInputStream(location + file + ".txt");
			InputStreamReader buffOpen = new InputStreamReader(open, "UTF8");

			reader = new BufferedReader(buffOpen);
			nGUI.setTxt("");
			saved = false;
			if(nGUI.getTxt() != null || nGUI.getTxt() != ""){
				while((text = reader.readLine()) != null){
					 nGUI.setTxt(nGUI.getTxt()+"\n" + text);
					 
				 }
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
		}finally{
			try{
				if(text == null){
					reader.close();
				}
			}catch(IOException e){
				System.out.println("Error " + e.getMessage());
			}

		}
	}


}
