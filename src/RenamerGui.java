

/** Imports **/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;


public class RenamerGui extends JFrame{
    
    //--Field Declarations
    
    //--Panels
    private JPanel masterPanel;
    private JPanel statusPanel;
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel originalPanel;
    private JPanel previewPanel;
    
    //--Menu Components
    private JMenuBar mainMenu;
    private JMenu mainMenuFile; //--File Menu
    private JMenuItem mainMenuFileOpen;
    private JMenuItem mainMenuFileRevert;
    private JMenuItem mainMenuFileExit;
    private JMenu mainMenuAction; //--Action Menu
    private JMenuItem mainMenuActionRename;
    private JMenu mainMenuSettings; //--Settings Menu
    private JMenu mainMenuLanguages;
    private JRadioButtonMenuItem[] languagesButtons;
    private ButtonGroup languagesButtonsGroup;
    private JMenuItem mainMenuSettingsAbout;
    
    //--Primary Inputs
    private JComboBox[]  actionID;
    private JTextField[] actionInput1;
    private JTextField[] actionInput2;
    
    //--Text Areas
    private JLabel originalNameLabel;
    private JScrollPane originalNameScrollPane;
    private JList originalNameList;
    private JLabel newNameLabel;
    private JScrollPane newNameScrollPane;
    private JList newNameList;
    private JTextField statusFolder;
    private JTextField statusItemCount;
    

    
    //--Gui Language items
    private LanguageHandler langHandler;
    
    private String userLanguage;
    
    
    /**RenamerGui
     * 
     * Defines and displays the window
     * 
     */
    public RenamerGui(){
        
        
        //--Set title
        super("Renamer");

        //--Load text
        loadLanguage();
        
        //--Field Declarations
        final int   WINX = 700,
                    WINY = 400;
        
        //--Set Values
        setSize(WINX,WINY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //--Add content
        buildMasterPanel();
        add(masterPanel);
        
        //--Show Window
        setVisible(true);
        
        
    }
    
    /**buildMasterPanel
     * 
     * Creates the contents of the main area and the status area
     * 
     */
    private void buildMasterPanel(){
        
        masterPanel = new JPanel();
        //--Add padding
        masterPanel.setLayout( new BorderLayout() );
        //--Set rows based on how many actions are allowed

        //--Add content
        buildMainPanel();
        masterPanel.add(mainPanel,BorderLayout.CENTER);
        
        buildStatusPanel();
        masterPanel.add(statusPanel,BorderLayout.SOUTH);
    }
    
    /**buildStatusPanel
     * 
     * Creates the contents of the status area
     * 
     */
    private void buildStatusPanel(){
        
        statusPanel = new JPanel();
        //--Add padding
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder() );
        statusPanel.setLayout( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        
        //--Define layouts for items
        statusFolder = new JTextField(Renamer.stringListContainer.folderPath);
        statusFolder.setEditable(false);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        statusPanel.add(statusFolder,c);
        
        
        statusItemCount = new JTextField( Renamer.stringListContainer.stringList.length + " " + langHandler.getText("statusBarItems") );
        statusItemCount.setEditable(false);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        statusPanel.add(statusItemCount);
        //--Add content
               
    }
    
    /**buildMainPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildMainPanel(){
        
        //--Build Menu
        mainMenu = new JMenuBar();
        
        //--File Area
        mainMenuFile = new JMenu( langHandler.getText("mainMenuFileText") );
        //--Open
        mainMenuFileOpen = new JMenuItem( langHandler.getText("mainMenuFileOpen") );
        mainMenuFileOpen.addActionListener(new OpenListener());
        mainMenuFile.add(mainMenuFileOpen);
        //--Revert
        mainMenuFileRevert = new JMenuItem( langHandler.getText("mainMenuFileRevert") );
        mainMenuFileRevert.addActionListener(new RevertListener());
        mainMenuFile.add(mainMenuFileRevert);
        //--Exit
        mainMenuFileExit = new JMenuItem( langHandler.getText("mainMenuFileExit") );
        mainMenuFileExit.addActionListener(new ExitListener());
        mainMenuFile.addSeparator();
        mainMenuFile.add(mainMenuFileExit);
        
        mainMenu.add(mainMenuFile);

        //--Action Area
        mainMenuAction = new JMenu( langHandler.getText("mainMenuActionText") );
        //--Rename
        mainMenuActionRename = new JMenuItem( langHandler.getText("mainMenuActionRename") );
        mainMenuActionRename.addActionListener(new RenameListener());
        
        mainMenuAction.add(mainMenuActionRename);

        //--Settings Area
        mainMenuSettings = new JMenu( langHandler.getText("mainMenuSettingsText") );
        //--Language
        mainMenuLanguages = new JMenu( langHandler.getText("mainMenuSettingsLanguagesText") );
        
        //--Get available languages and put them in the menu
        languagesButtons = new JRadioButtonMenuItem[langHandler.getNumFiles()];
        languagesButtonsGroup = new ButtonGroup();
        for(int c = 0;c < langHandler.getNumFiles(); c++){
            languagesButtons[c] = new JRadioButtonMenuItem( langHandler.getText("name",c) );
            languagesButtons[c].addActionListener(new LanguageListener());
            //--Select current language
            if( langHandler.checkUserIndex(c) ){
                languagesButtons[c].setSelected(true);
            }
            languagesButtonsGroup.add(languagesButtons[c]);
            mainMenuLanguages.add(languagesButtons[c]);
        }
        mainMenuSettings.add(mainMenuLanguages);
        //--About
        mainMenuSettingsAbout = new JMenuItem( langHandler.getText("mainMenuSettingsAboutText") );
        mainMenuSettingsAbout.addActionListener(new AboutListener());
        mainMenuSettings.add(mainMenuSettingsAbout);
        
        //--Add all menus areas
        mainMenu.add(mainMenuFile);
        mainMenu.add(mainMenuAction);
        mainMenu.add(mainMenuSettings);
        
        //--Add menu
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
    
    /**buildInputPanel
     * 
     * Creates the contents of the input area
     * 
     */
    private void buildInputPanel(){
        
        inputPanel = new JPanel();
        //--Add padding
        inputPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3) );
        //--Set rows based on how many actions are allowed
        inputPanel.setLayout( new GridLayout(StringListContainer.NUM_OF_ACTIONS,3) );
        int numActionList =  langHandler.getText("actionList").split(",").length;
        
        //--Create action combo boxes based on how many allowed
        actionID =    new JComboBox[StringListContainer.NUM_OF_ACTIONS];
        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            
            for(int d = 0;d < numActionList; d++){
                actionID[c] = new JComboBox( langHandler.getText("actionList").split(",") );
                actionID[c].addActionListener( new ActionChangeListener() );
            }
        }

        //--Create action value fields based on how many allowed
        actionInput1 = new JTextField[StringListContainer.NUM_OF_ACTIONS];
        actionInput2 = new JTextField[StringListContainer.NUM_OF_ACTIONS];
        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            actionInput1[c] = new JTextField();
            actionInput1[c].getDocument().addDocumentListener( new InputListener() );
            
            actionInput2[c] = new JTextField();
            actionInput2[c].getDocument().addDocumentListener( new InputListener() );
        }
        
        //--Add inputs
        for(int c = 0;c<StringListContainer.NUM_OF_ACTIONS;c++){
            inputPanel.add(actionID[c]);
            inputPanel.add(actionInput1[c]);
            inputPanel.add(actionInput2[c]);
        }
               
    }
    
    /**buildOriginalPanel
     * 
     * Creates the contents of the current names area
     * 
     */
    private void buildOriginalPanel(){
        originalPanel = new JPanel();
        //--Add padding
        originalPanel.setBorder(BorderFactory.createEmptyBorder(3,0,3,0) );
        originalPanel.setLayout( new BorderLayout() );
        
        //--Add title
        originalNameLabel =    new JLabel( langHandler.getText("originalNameLabel") );
        originalNameLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
        originalNameLabel.setHorizontalAlignment( SwingConstants.CENTER );
        
        //--Get current names list and put it in a scroll pane
        originalNameList = new JList(Renamer.stringListContainer.getStringList());
        originalNameScrollPane = new JScrollPane(originalNameList);
        originalNameScrollPane.setBorder(BorderFactory.createLoweredBevelBorder() );
        originalPanel.add(originalNameLabel,BorderLayout.NORTH);
        originalPanel.add(originalNameScrollPane,BorderLayout.CENTER);
    }
    
    /**buildMainPanel
     * 
     * Creates the contents of the new names area
     * 
     */
    private void buildPreviewPanel(){
        previewPanel = new JPanel();
        //--Add padding
        previewPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3) );
        previewPanel.setLayout( new BorderLayout() );

        //--Add title
        newNameLabel =    new JLabel( langHandler.getText("newNameLabel") );
        newNameLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
        newNameLabel.setHorizontalAlignment( SwingConstants.CENTER );
        
        //--Get new names list and put it in a scroll pane
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
            
            //--Update Gui
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
                }else if(actionID[c].getSelectedIndex() == 0){
                    Renamer.stringListContainer.setAction(c, -1, "/");
                    
                }
            }

            //--Update Gui
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
            
            //--Get folder from dialog
            JFileChooser openFolder = new JFileChooser();
            openFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int status = openFolder.showOpenDialog(null);
            if(status == openFolder.APPROVE_OPTION){
                File newfolder = openFolder.getSelectedFile();
                
                //--Reload String list contents
                Renamer.stringListContainer.setFolder(newfolder.getPath());

                //--Update Gui
                originalNameList.setListData(Renamer.stringListContainer.getStringList());
                newNameList.setListData(Renamer.stringListContainer.getModStringList());
                statusItemCount.setText( Renamer.stringListContainer.stringList.length + " " + langHandler.getText("statusBarItems") );
                statusFolder.setText(Renamer.stringListContainer.folderPath);
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

            //--Update Gui
            originalNameList.setListData(Renamer.stringListContainer.getStringList());
            newNameList.setListData(Renamer.stringListContainer.getModStringList());
        }
    }
    
    
    /**AboutListener
     * 
     * Exits Application
     * 
     */
    private class AboutListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            new AboutWindow();
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

            //--Update Gui
            originalNameList.setListData(Renamer.stringListContainer.getStringList());
            newNameList.setListData(Renamer.stringListContainer.getModStringList());
        }
    }
    
    
    /**LanguageListener
     * 
     * Loads a new language and saves it to settings
     * 
     */
    private class LanguageListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e){
            
            int newLangIndex = 0;
            
            //--Get new language index
            for(int c = 0;c < langHandler.getNumFiles(); c++){
                if(languagesButtons[c].isSelected()){
                    newLangIndex = c;
                }
            }

            //--Get starting directory
            String stDir = System.getProperty("user.dir");

            //--Check settings file for preferred language
            File settingsFile = new File(stDir + File.separator + "settings.cfg");
            
            //--Save new language option
            try {
                PrintWriter saveLang = new PrintWriter(settingsFile.getPath());
                
                saveLang.println( "lang: " + langHandler.getText("id",newLangIndex).replace(".lang", "") );
                saveLang.close();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            //--Reload language text
            loadLanguage();


            //--Update Gui
            //NOTE--JComboBoxes cannot have their text modified
            mainMenuFile.setText( langHandler.getText("mainMenuFileText") );
            mainMenuFileOpen.setText( langHandler.getText("mainMenuFileOpen") );
            mainMenuFileRevert.setText( langHandler.getText("mainMenuFileRevert") );
            mainMenuFileExit.setText( langHandler.getText("mainMenuFileExit") );
            
            mainMenuAction.setText( langHandler.getText("mainMenuActionText") );
            mainMenuActionRename.setText( langHandler.getText("mainMenuActionRename") );
            
            originalNameLabel.setText( langHandler.getText("originalNameLabel") );
            newNameLabel.setText( langHandler.getText("newNameLabel") );

            mainMenuSettings.setText( langHandler.getText("mainMenuSettingsText") );
            
            
        }
    }
    
    
    
    /**loadLanguage
     * 
     * Loads the strings for the selected language
     * 
     */
    private void loadLanguage(){
        
        boolean settingsExist = false;
        
        //--Get starting directory
        String stDir = System.getProperty("user.dir");
        
        //--Check settings file for preferred language
        File settingsFile = new File(stDir + File.separator + "settings.cfg");
        ConfigHandler settingsConfig;
        
        //--Load Settings
        if(settingsFile.exists()){
            try {
                settingsConfig = new ConfigHandler(settingsFile.getPath());
                userLanguage = settingsConfig.getValueFor("lang");
                settingsExist = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
        //--Load Language handler
        langHandler = new LanguageHandler(stDir + File.separator + "lang", userLanguage);
        
    }
}
