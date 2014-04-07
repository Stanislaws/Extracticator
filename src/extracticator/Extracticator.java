/*
 *  Extracticator - extracts email addresses from mailing lists
 *  Copyright © 2013 Jan Zajaczkowski
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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author Jan Zajaczkowski
 */
public class Extracticator extends JFrame{
    private JFileChooser filepick;
    private final JMenu File, Edit, Process, Tools, Help;
    private final JMenuBar Bar;
    private final JMenuItem New, Open, Save, SaveAs, Exit, Cut, Copy, Paste, 
            SelectAll, About, Extract, Options;
    private final JScrollPane oscroll;
    private JTextArea output, aoutput;
    
    private final JLabel statusbar = new JLabel("Ready");
    
    private final JPanel jp = new JPanel();
    
    Extracticator(){
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
        
        SaveAs = new JMenuItem("Save As");
        SaveAs.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
        SaveAs.setMnemonic(KeyEvent.VK_A);
        File.add(SaveAs);
        
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
        
        Tools = new JMenu("Tools");
        Tools.setMnemonic(KeyEvent.VK_T);
        
        Options = new JMenuItem("Options");
        Options.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        Options.setMnemonic(KeyEvent.VK_O);
        
        Tools.add(Options);
        
        Bar.add(Tools);
        
        Help = new JMenu("Help");
        Help.setMnemonic(KeyEvent.VK_H);
        
        About = new JMenuItem("About");
        About.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        About.setMnemonic(KeyEvent.VK_A);
        Help.add(About);
        
        Bar.add(Help);
        
        output = new JTextArea(25,50);
        output.setLineWrap(true);
        
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
        
        SaveAs.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                saveas();
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
        
        Options.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                options();
            }
            
        });
        
        About.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                about();
            }
            
        });
    }
    
    public void about(){
        aoutput = new JTextArea(10,5);
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
            
            //create array of emails for between every ;
            String emails[] = text.split(";");
            
            int badcount = 0;
            
            
            for (String email : emails) {
                //skip if the segment doesn't have an email
                if(email.indexOf("<")!=-1&&email.indexOf(">")!=-1){
                    //append the contents between < and >
                    output.append(email.substring(email.indexOf("<") 
                        + 1, email.indexOf(">")) + "\n");
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
    
    public void exit(){
        if(!"".equals(output.getText())){
            int n = JOptionPane.showConfirmDialog(
                    jp,
                    "Would you like to save your work before exiting?",
                    "Exit?",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (n==0){
                save();
            } 
            else
            if (n==1){
                System.exit(0);
            } 
            else {

            } 
        }
        else {
            System.exit(0);
        }
    }
    
    public void newfile(){
        if(!"".equals(output.getText())){
            int n = JOptionPane.showConfirmDialog(
                    jp,
                    "Are you sure you want to start a new file?"
                            + "\n(all unsaved work will be lost)",
                    "New?",
                    JOptionPane.YES_NO_OPTION);
            if(n==0){
                output.setText("");
                statusbar.setText("Ready");
            }         
        }
    }
    
    public void open() {
        nyi("open");
    }
    
    public void options() {
        nyi("options");
    }
    
    public void save(){
        nyi("save");
    }
    
    public void saveas(){
        nyi("saveas");
    }
    
    public void selectall(){
        output.selectAll();
        statusbar.setText("Everything is selected!");
    }
    
    public void nyi(String type){
        String sorry = "Sorry, no " + type + " currently implemented!";
        statusbar.setText(sorry);
        JOptionPane.showMessageDialog(jp, sorry,
                        "Error",JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Extracticator ex = new Extracticator();
        ex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ex.setTitle("Extracticator");
        ex.pack();
        ex.setLocationRelativeTo(null);
        ex.setVisible(true);
        ex.setResizable(false);
        UIManager.put("OptionPane.background",new ColorUIResource(255,255,255));
        UIManager.put("Panel.background",new ColorUIResource(255,255,255));
    }
    
}
