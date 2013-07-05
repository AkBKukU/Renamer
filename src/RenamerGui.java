import java.awt.*;

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
    private JMenuItem mainMenuFileExit;

    private JComboBox[]  actionID;
    private JTextField[] actionInput1;
    private JTextField[] actionInput2;

    private JLabel originalNameLabel;
    private JList originalNameList;
    private JLabel newNameLabel;
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
        mainMenuFileExit = new JMenuItem("Exit");
        
        mainMenuFile.add(mainMenuFileOpen);
        mainMenuFile.add(mainMenuFileExit);

        mainMenu.add(mainMenuFile);
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
        inputPanel.setLayout( new GridLayout(StringListContainer.NUM_OF_ACTIONS,3) );
        int numActionList = actionList.length;
        
        actionID =    new JComboBox[StringListContainer.NUM_OF_ACTIONS];
        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            
            for(int d = 0;d < numActionList; d++){
                actionID[c] = new JComboBox(actionList);
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
        
        originalNameLabel =    new JLabel("Orginal Names");
        originalNameList = new JList(Renamer.stringListContainer.getStringList());
        
        originalPanel.add(originalNameLabel);
        originalPanel.add(originalNameList);
    }
    
    /**buildMainPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildPreviewPanel(){
        previewPanel = new JPanel();

        newNameLabel =    new JLabel("New Names");
        newNameList = new JList(Renamer.stringListContainer.getModStringList());
        
        previewPanel.add(newNameLabel);
        previewPanel.add(newNameList);
        
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
}
