import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Note extends JFrame{

	ImageIcon notepadimg; //The window icon variable
	String location = "C:/Users/" + System.getProperty("user.name") + "/Documents/Writer Files"; //The location on the computer where files will be stored
	File file = new File(location); //An instance of the file class which points to the location on the computer where the folder storing files for this program is


	public Note(){
		notepadimg = new ImageIcon(getClass().getResource("Notepad image.png")); //This sets the window image to the specified image
		setIconImage(notepadimg.getImage()); //Set's the icon image to he above variable

		NoteGUI nGUI = new NoteGUI(); //Creates an instance of the panel.

		nGUI.sf.setSaved(false); //Setting the state of the file so that when the program starts it is not saved

		if(!file.exists()){ //Checks to see if the file directory exists
			 file.mkdir();
		 }

		add(nGUI); //Setting the graphic layout of the program
	}

	public static void main(String[] args) {
		//Initallize's the class\

		Note note = new Note();

		//Set's the title
		note.setTitle("JNote");

		//Set's whether the application will be visible
		note.setVisible(true);

		//Set's the size of the screen to fit according to the contents
		note.pack();

		//Set's the screen to terminate on closing
		note.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Set's the screen location in the center.
		note.setLocationRelativeTo(null);


	}

}
