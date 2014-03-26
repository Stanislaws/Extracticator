/*
 *  Extracticator - extracts email addresses from mailing lists
 *  Copyright © 2014 Jan Zajaczkowski
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package extracticator;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;

/**
 * extracts email addresses from mailing lists
 * @author Jan Zajaczkowski
 */
public class Extracticator extends JFrame{
    private final JFileChooser fc;
    private final JMenu File, Edit, Process, Help;
    private final JMenuBar Bar;
    private final JMenuItem New, Open, Save, Exit, Cut, Copy, Paste, 
            SelectAll, About, Extract;
    private final JScrollPane oscroll;
    private final JTextArea output, aoutput;
    
    private final JLabel statusbar = new JLabel("Ready");
    
    private final JPanel jp = new JPanel();
    
    private boolean SavedBeforeExit = false;
    
    /**
     * Sets up the GUI and actionable events
     */
    Extracticator(){
        fc = new JFileChooser();
        
        statusbar.setOpaque(true);
        statusbar.setBackground(Color.white);
                
        jp.setLayout(new BorderLayout());
        Bar = new JMenuBar();
        
        File = new JMenu("File");
        File.setMnemonic(KeyEvent.VK_F);
        
        New = new JMenuItem("New");
        New.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        New.setMnemonic(KeyEvent.VK_N);
        File.add(New);
        
        Open = new JMenuItem("Open");
        Open.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        Open.setMnemonic(KeyEvent.VK_O);
        File.add(Open);
        
        Save = new JMenuItem("Save");
        Save.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        Save.setMnemonic(KeyEvent.VK_S);
        File.add(Save);
        
        File.addSeparator();
        
        Exit = new JMenuItem("Exit");
        Exit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.ALT_MASK));
        Exit.setMnemonic(KeyEvent.VK_X);
        File.add(Exit);
        
        Bar.add(File);
        
        Edit = new JMenu("Edit");
        Edit.setMnemonic(KeyEvent.VK_E);
        
        SelectAll = new JMenuItem("Select All");
        SelectAll.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        SelectAll.setMnemonic(KeyEvent.VK_A);
        Edit.add(SelectAll);
        
        Edit.addSeparator();
        
        Cut = new JMenuItem(new DefaultEditorKit.CutAction());
        Cut.setText("Cut");
        Cut.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        Cut.setMnemonic(KeyEvent.VK_U);
        Edit.add(Cut);
        
        Copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        Copy.setText("Copy");
        Copy.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        Copy.setMnemonic(KeyEvent.VK_C);
        Edit.add(Copy);
        
        Paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        Paste.setText("Paste");
        Paste.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        Paste.setMnemonic(KeyEvent.VK_P);
        Edit.add(Paste);
        
        Bar.add(Edit);
        
        Process = new JMenu("Process");
        Process.setMnemonic(KeyEvent.VK_P);
        
        Extract = new JMenuItem("Extract");
        Extract.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        Extract.setMnemonic(KeyEvent.VK_E);
        
        Process.add(Extract);
        
        Bar.add(Process);
        
        Help = new JMenu("Help");
        Help.setMnemonic(KeyEvent.VK_H);
        
        About = new JMenuItem("About");
        About.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        About.setMnemonic(KeyEvent.VK_A);
        Help.add(About);
        
        Bar.add(Help);
        
        aoutput = new JTextArea(10,5);
        
        output = new JTextArea(25,50);
        //output.setLineWrap(true);
        
        oscroll = new JScrollPane(output);
        jp.add(oscroll, BorderLayout.CENTER);
        setJMenuBar(Bar);
        
        
        
        jp.add(statusbar, BorderLayout.PAGE_END);
        
        add(jp);
              
        New.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                newfile();
            }
        });
        
        Open.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        
        Save.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
            
        });
        
        Exit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        }
        );
        
        SelectAll.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                selectall();
            }
            
        });
        
        Extract.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                extract();
            }
            
        });
        
        About.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                about();
            }
            
        });
        
        output.getDocument().addDocumentListener(new DocumentListener(){
            //flag as unsaved if you insert into the text area
            @Override
            public void insertUpdate(DocumentEvent e) {
                SavedBeforeExit = false;
            }
            //flag as unsaved if you remove from the text area
            @Override
            public void removeUpdate(DocumentEvent e) {
                SavedBeforeExit = false;
            }
            //flag as unsaved if you change the contents of the text area
            @Override
            public void changedUpdate(DocumentEvent e) {
                SavedBeforeExit = false;
            }
            
        });
    }
    
    /**
     * Shows information about the author and the license
     */
    public void about(){
        
        aoutput.setEditable(false);
        aoutput.setText("Extracticator - extracts email addresses from "
                        + "mailing lists\n" +
                    "Copyright © 2013 Jan Zajaczkowski\n" +
                    "\n" +
                    "This program is free software: you can "
                        + "redistribute it and/or modify\n" +
                    "it under the terms of the GNU General Public "
                        + "License as published by\n" +
                    "the Free Software Foundation, either version 3 "
                        + "of the License, or\n" +
                    "(at your option) any later version.\n" +
                    "\n" +
                    "This program is distributed in the hope that it "
                        + "will be useful,\n" +
                    "but WITHOUT ANY WARRANTY; without even the "
                        + "implied warranty of\n" +
                    "MERCHANTABILITY or FITNESS FOR A PARTICULAR "
                        + "PURPOSE.  See the\n" +
                    "GNU General Public License for more details.\n" +
                    "\n" +
                    "You should have received a copy of the GNU "
                        + "General Public License\n" +
                    "along with this program.  If not, see "
                        + "<http://www.gnu.org/licenses/>.");
        JOptionPane.showMessageDialog(jp,aoutput,"About",
                JOptionPane.PLAIN_MESSAGE);        
    }
    
    /**
     * Implements the main purpose of the program
     */
    public void extract(){
        String text = output.getText();
        
        if(text.equals("")){
            if(statusbar.getText().equals("Text area is blank!")){
                JOptionPane.showMessageDialog(jp, "Text area is blank!",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                statusbar.setText("Text area is blank!");
            }
        } 
        
        else if(text.indexOf("<")==-1||text.indexOf(">")==-1) {
            if(statusbar.getText().equals("< or > not found in text!")){
                JOptionPane.showMessageDialog(jp, "< or > not found in text!",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                statusbar.setText("< or > not found in text!");
            }
            
        }
        
        else {
            output.setText("");
            String emails[] = null;
            
            //create array of emails for between every ; or new line
            if(text.lastIndexOf(";")==-1){
                emails = text.split("\n");
            }else{
                emails = text.split(";");
            }
            
            
            int badcount = 0;
            
            
            for (String email : emails) {
                String line = "";
                String first = "";
                String last = "";
                
                //skip if the segment doesn't have an email
                if(email.indexOf("<")!=-1&&email.indexOf(">")!=-1){
                    line = email.substring(email.indexOf("<") 
                        + 1, email.indexOf(">"));
                    first = line.substring(0, line.indexOf("."));
                    last = line.substring(line.indexOf(".")+1,line.indexOf("@"));
                    
                    //append the contents between < and >
                    output.append(last+","+first+","+line+"\n");
                }
                else {
                    //add to badcount if line doesn't have an email
                    badcount += 1;
                }
            }
            
            statusbar.setText("Found " + (emails.length - badcount)
                    + " email addresses.");
            
        }
    }
    
    /**
     * Exits the program if the user clicks no, opens save if user clicks yes
     */
    public void exit(){
        if(!"".equals(output.getText())){
            if(!SavedBeforeExit){
                int n = JOptionPane.showConfirmDialog(
                        jp,
                        "Would you like to save your work before exiting?",
                        "Exit?",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (n==0){
                    //YES OPTION
                    SavedBeforeExit=true;
                    save();
                } 
                else
                if (n==1){
                    //NO OPTION
                    System.exit(0);
                } 
                else {
                    //CANCEL OPTION
                } 
            }
            else {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
    
    /**
     * Clears the text area after confirmation
     */
    public void newfile(){
        if(!"".equals(output.getText())){
            int n = JOptionPane.showConfirmDialog(
                    jp,
                    "Are you sure you want to start a new file?"
                            + "\n(all unsaved work will be lost)",
                    "New?",
                    JOptionPane.YES_NO_OPTION);
            if(n==0){
                //Yes
                output.setText("");
                ready();
            }
            if(n==1){
                //No
            }
        }else {
            ready();
        }
    }
    
    /**
     * Indicates the program is ready in the status bar
     */
    public void ready(){
        statusbar.setText("Ready.");
    }
    
    /**
     * Opens a file using a JFileChooser and a unbuffered stream
     */
    public void open() {
        int returnVal = fc.showOpenDialog(Extracticator.this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            Path file = f.toPath();
            try (InputStream in = Files.newInputStream(file);
                BufferedReader reader =
                  new BufferedReader(new InputStreamReader(in))) {
                String line;
                output.setText("");
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }               
            } catch (IOException x) {
                JOptionPane.showMessageDialog(jp,x,"Error!"
                        ,JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Saves the current contents of the text area
     */
    public void save(){
        fc.showSaveDialog(Extracticator.this);
        try(FileWriter fw = new FileWriter(fc.getSelectedFile())){
            fw.write(output.getText().toString());
            fw.flush();
            fw.close();
            SavedBeforeExit = true;
        } catch (IOException ex) {
            statusbar.setText(ex.toString());
        }
    }
    
    /**
     * Selects all the contents of the text area
     */
    public void selectall(){
        output.selectAll();
        statusbar.setText("Everything is selected!");
    }
    
    /**
     * Creates a new instance of the GUI and sets some options for it
     * @param args the command line arguments, none implemented
     */
    public static void main(String[] args) {
        final Extracticator ex = new Extracticator();
        ex.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ex.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                ex.exit();
            }
        });
        ex.setTitle("Extracticator");
        ex.pack();
        ex.setLocationRelativeTo(null);
        ex.setVisible(true);
        ex.setResizable(false);
    }
    
}
