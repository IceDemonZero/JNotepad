import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

@SuppressWarnings("serial")
public class NoteGUI extends JPanel {

  public FileOperations sf = new FileOperations(this);
  UndoManager manager = new UndoManager();
  JMenuBar menu;
  JMenu file;
  JMenu edit;
  JTextArea txtArea;
  JScrollPane scroll;

  boolean saved = sf.isSaved();
  String previous;

  public String getTxt(){return txtArea.getText();}

  public void setTxt(String text){txtArea.setText(text);}

  public NoteGUI(){
    setLayout(new BorderLayout());

    menu = new JMenuBar();
    txtArea = new JTextArea(20, 70);
    txtArea.setEditable(true);
    scroll = new JScrollPane(txtArea);

    scroll.setVerticalScrollBarPolicy(22);
    scroll.setHorizontalScrollBarPolicy(32);

    file = new JMenu("File");
    edit = new JMenu("Edit");

    menu.add(file);
    menu.add(edit);

    txtArea.setColumns(70);
    txtArea.getDocument().addUndoableEditListener(manager);

    Document doc = txtArea.getDocument();

    JMenuItem[] fileItems = new JMenuItem[5];
    fileItems[0] = new JMenuItem("New");
    fileItems[1] = new JMenuItem("Open");
    fileItems[2] = new JMenuItem("Save As");
    fileItems[3] = new JMenuItem("Save");
    fileItems[4] = new JMenuItem("Exit");

    JMenuItem[] editItems = new JMenuItem[2];
    editItems[0] = new JMenuItem("Undo");
    editItems[1] = new JMenuItem("Redo");

    doc.addUndoableEditListener(new UndoableEditListener(){

      public void undoableEditHappened(UndoableEditEvent e){
        manager.addEdit(e.getEdit());
      }

    });

    int fileElements = fileItems.length;
    for (int i = 0; i < fileElements; i++) {

    	file.add(fileItems[i]);

        fileItems[i].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JMenuItem getItem = (JMenuItem) e.getSource();
            String getItemTxt = getItem.getText();

            if (getItemTxt.equals("Exit")) {
              System.exit(0);
            } else if (getItemTxt.equals("Save As")){
              String fileName = JOptionPane.showInputDialog("What will be this file's name?");
              sf.saveAs(fileName);
              previous = txtArea.getText();
            } else if (getItemTxt.equals("New")) {
              if (saved) {
	                txtArea.setText("");
	                sf.setSaved(false);
              } else if (!saved){
            	  int q = JOptionPane.showConfirmDialog(null, "Do you want to save this file?", "File not saved.", JOptionPane.YES_NO_OPTION);

	                if (q == JOptionPane.YES_OPTION){
	                	String fileName = JOptionPane.showInputDialog("What will be this file's name?", JOptionPane.OK_OPTION);
		                  sf.saveAs(fileName);
		                  sf.setSaved(true);
	                } else{
	                	txtArea.setText("");
	                	sf.setSaved(false);
	                }
              }
            } else if (getItemTxt.equals("Save")) {
              if (sf.isSaved()) {
                JOptionPane.showMessageDialog(null, "File already exist!");
              } else if(!sf.isSaved()){
            	  sf.save();
            	  sf.setSaved(true);
            	  previous = txtArea.getText();
              }
            } else if (getItemTxt.equals("Open")) {
              String stuff = JOptionPane.showInputDialog(null, "Input File location");
              sf.open(stuff);
            }
          }
        });
    }

    int editElements = editItems.length;
    for (int i = 0; i < editElements; i++) {
    	edit.add(editItems[i]);

    	editItems[i].addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          JMenuItem getItem = (JMenuItem)e.getSource();
	          String getItemTxt = getItem.getText();

	          if (getItemTxt.equals("Undo")) {
	            try{
	             manager.undo();
	            }catch (CannotUndoException e1){
	              e1.printStackTrace();
	            }
	          } else if (getItemTxt.equals("Redo")) {
	            try{
	              manager.redo();
	            }catch (CannotUndoException e1){
	              e1.printStackTrace();
	            }
	          }
	        }
	   });
    }
    KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
    KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);

    this.txtArea.getInputMap(2).put(undoKeyStroke, "undoKeyStroke");
    this.txtArea.getActionMap().put("undoKeyStroke", new AbstractAction(){
      public void actionPerformed(ActionEvent e)
      {
        try{
        	manager.undo();
        }catch (CannotUndoException localCannotUndoException) {}
      }
    });

    this.txtArea.getInputMap(2).put(redoKeyStroke, "redoKeyStroke");
    this.txtArea.getActionMap().put("redoKeyStroke", new AbstractAction(){

      public void actionPerformed(ActionEvent e){
        try{
        	manager.redo();
        }catch (CannotRedoException localCannotRedoException) {}
      }

    });

    add(this.menu, "North");
    add(this.scroll, "Center");
  }
}
