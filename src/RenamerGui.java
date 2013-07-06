import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;




public class RenamerGui extends JFrame{
    
    //--Field Declarations
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel originalPanel;
    private JPanel previewPanel;

    private JMenuBar mainMenu;
    private JMenu mainMenuFile;
    private JMenuItem mainMenuFileOpen;
    private JMenuItem mainMenuFileRevert;
    private JMenuItem mainMenuFileExit;
    private JMenu mainMenuAction;
    private JMenuItem mainMenuActionRename;

    private JComboBox[]  actionID;
    private JTextField[] actionInput1;
    private JTextField[] actionInput2;

    private JLabel originalNameLabel;
    private JScrollPane originalNameScrollPane;
    private JList originalNameList;
    private JLabel newNameLabel;
    private JScrollPane newNameScrollPane;
    private JList newNameList;
    
    private String[] actionList = {
                            "None",
                            "Add String",
                            "Replace",
                            "Counter",
                            "Substring"
                        };
    
    /**CalcWindow
     * 
     * Defines and displays the window
     * 
     */
    public RenamerGui(){
        
        //--Set title
        super("Renamer");

        //--Field Declarations
        final int   WINX = 700,
                    WINY = 400;
        
        //--Set Values
        setSize(WINX,WINY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //--Add content
        buildMainPanel();
        add(mainPanel);
        
        //--Show Window
        setVisible(true);
        
        
    }
    
    /**buildMainPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildMainPanel(){
        
        //--Build Menu
        mainMenu = new JMenuBar();
        mainMenuFile = new JMenu("File");
        mainMenuFileOpen = new JMenuItem("Open Folder");
        mainMenuFileRevert = new JMenuItem("Revert");
        mainMenuFileExit = new JMenuItem("Exit");
        mainMenuFileOpen.addActionListener(new OpenListener());
        mainMenuFileRevert.addActionListener(new RevertListener());
        mainMenuFileExit.addActionListener(new ExitListener());
        
        mainMenuFile.add(mainMenuFileOpen);
        mainMenuFile.add(mainMenuFileRevert);
        mainMenuFile.addSeparator();
        mainMenuFile.add(mainMenuFileExit);
        
        mainMenu.add(mainMenuFile);

        mainMenuAction = new JMenu("Action");
        mainMenuActionRename = new JMenuItem("Rename");
        mainMenuActionRename.addActionListener(new RenameListener());
        
        mainMenuAction.add(mainMenuActionRename);
        
        mainMenu.add(mainMenuFile);
        mainMenu.add(mainMenuAction);
        
        
        setJMenuBar(mainMenu);
        
        //--Create Panels
        mainPanel = new JPanel();
        mainPanel.setLayout( new GridLayout(1,3) );
        
        buildInputPanel();
        mainPanel.add(inputPanel);
        
        buildOriginalPanel();
        mainPanel.add(originalPanel);
        
        buildPreviewPanel();
        mainPanel.add(previewPanel);
        
    }
    
    /**buildMainPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5) );
        inputPanel.setLayout( new GridLayout(StringListContainer.NUM_OF_ACTIONS,3) );
        int numActionList = actionList.length;
        
        actionID =    new JComboBox[StringListContainer.NUM_OF_ACTIONS];
        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            
            for(int d = 0;d < numActionList; d++){
                actionID[c] = new JComboBox(actionList);
                actionID[c].addActionListener( new ActionChangeListener() );
            }
        }
        
        actionInput1 = new JTextField[StringListContainer.NUM_OF_ACTIONS];
        actionInput2 = new JTextField[StringListContainer.NUM_OF_ACTIONS];
        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            actionInput1[c] = new JTextField();
            actionInput1[c].getDocument().addDocumentListener( new InputListener() );
            
            actionInput2[c] = new JTextField();
            actionInput2[c].getDocument().addDocumentListener( new InputListener() );
        }

        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            inputPanel.add(actionID[c]);
            inputPanel.add(actionInput1[c]);
            inputPanel.add(actionInput2[c]);
        }
               
    }
    
    /**buildOriginalPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildOriginalPanel(){
        originalPanel = new JPanel();
        originalPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0) );
        originalPanel.setLayout( new BorderLayout() );
        
        originalNameLabel =    new JLabel("Orginal Names");
        originalNameList = new JList(Renamer.stringListContainer.getStringList());

        originalNameScrollPane = new JScrollPane(originalNameList);
        originalNameScrollPane.setBorder(BorderFactory.createLoweredBevelBorder() );
        originalPanel.add(originalNameLabel,BorderLayout.NORTH);
        originalPanel.add(originalNameScrollPane,BorderLayout.CENTER);
    }
    
    /**buildMainPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildPreviewPanel(){
        previewPanel = new JPanel();
        previewPanel.setLayout( new BorderLayout() );

        newNameLabel =    new JLabel("New Names");
        newNameList = new JList(Renamer.stringListContainer.getModStringList());

        newNameScrollPane = new JScrollPane(newNameList);
        newNameScrollPane.setBorder(BorderFactory.createLoweredBevelBorder() );
        previewPanel.add(newNameLabel,BorderLayout.NORTH);
        previewPanel.add(newNameScrollPane,BorderLayout.CENTER);
        
    }
    
    
    /**InputListener
     * 
     * Changes text if there are values entered
     * 
     */
    private class InputListener implements DocumentListener{

        /**removeUpdate
         * 
         * Checks if text was removed and updates field
         */
        public void removeUpdate(DocumentEvent e){
            updateActions();
        }
        /**insertUpdate
         * 
         * Checks if text was added and updates field
         */
        public void insertUpdate(DocumentEvent e){
            updateActions();
        }
        public void changedUpdate(DocumentEvent e) {} //--Unused but needed

        /**updateActions
         * 
         * Sends the actions to the StringListContainer object
         */
        private void updateActions(){
            
            //--Field Declarations
            String[] actionValues = new String[StringListContainer.NUM_OF_ACTIONS];
            
            //--Collect Values
            for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
                if(actionID[c].getSelectedIndex() != 0){
                    actionValues[c] = actionInput1[c].getText() + "/" + actionInput2[c].getText();
                }
            }
            
            //--Set Actions
            for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
                if(actionID[c].getSelectedIndex() != 0){
                    Renamer.stringListContainer.setAction(c, actionID[c].getSelectedIndex()-1, actionValues[c]);
                }
            }
            
            newNameList.setListData(Renamer.stringListContainer.getModStringList());
            
        }
    }
    
    /**ActionChangeListener
     * 
     * Updates lists if the action changes
     * 
     */
    private class ActionChangeListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            //--Field Declarations
            String[] actionValues = new String[StringListContainer.NUM_OF_ACTIONS];
            
            //--Collect Values
            for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
                if(actionID[c].getSelectedIndex() != 0){
                    actionValues[c] = actionInput1[c].getText() + "/" + actionInput2[c].getText();
                }
            }
            
            //--Set Actions
            for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
                if(actionID[c].getSelectedIndex() != 0){
                    Renamer.stringListContainer.setAction(c, actionID[c].getSelectedIndex()-1, actionValues[c]);
                }
            }
            
            originalNameList.setListData(Renamer.stringListContainer.getStringList());
            newNameList.setListData(Renamer.stringListContainer.getModStringList());
        }
    }
    
    
    /**ExitListener
     * 
     * Exits Application
     * 
     */
    private class ExitListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    
    
    /**OpenListener
     * 
     * Opens folder and sends it to the StringListContatainer
     * 
     */
    private class OpenListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            JFileChooser openFolder = new JFileChooser();
            openFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int status = openFolder.showOpenDialog(null);
            if(status == openFolder.APPROVE_OPTION){
                File newfolder = openFolder.getSelectedFile();
                Renamer.stringListContainer.setFolder(newfolder.getPath());
                
                originalNameList.setListData(Renamer.stringListContainer.getStringList());
                newNameList.setListData(Renamer.stringListContainer.getModStringList());
            }
        }
    }
    
    
    /**RenameListener
     * 
     * Opens folder and sends it to the StringListContatainer
     * 
     */
    private class RenameListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            
            Renamer.stringListContainer.commitNames();

            originalNameList.setListData(Renamer.stringListContainer.getStringList());
            newNameList.setListData(Renamer.stringListContainer.getModStringList());
        }
    }
    
    
    /**RevertListener
     * 
     * Opens folder and sends it to the StringListContatainer
     * 
     */
    private class RevertListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            
            Renamer.stringListContainer.revertNames();

            originalNameList.setListData(Renamer.stringListContainer.getStringList());
            newNameList.setListData(Renamer.stringListContainer.getModStringList());
        }
    }
}
