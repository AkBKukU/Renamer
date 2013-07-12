import java.awt.*;
import java.awt.Color;
import javax.swing.*;


public class AboutWindow extends JFrame {

    //--Field Declarations
    int pageID = 0; //--0 general about / 1 language about
    
    //--Panels
    public JPanel mainPanel;
    public JPanel sideMenu;
    public JPanel contentArea;

    /**AboutWindow
     * 
     * Defines and displays the window
     * 
     */
    public AboutWindow(){
        
        
        //--Set title
        super("Renamer About");

        //--Load text
        
        //--Field Declarations
        final int   WINX = 325,
                    WINY = 250;
        
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
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        //--Build sub panels
        buildSideMenuPanel();
        buildContentAreaPanel();
        
        //--Define layouts for items
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        mainPanel.add(sideMenu,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.weightx = 0.2;
        c.gridy = 0;
        c.gridwidth = 1;
        mainPanel.add(contentArea,c);
        
    }
    
    
    /**buildSideMenuPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildSideMenuPanel(){
        
        sideMenu = new JPanel();
        //--Add padding
        sideMenu.setBorder(BorderFactory.createEmptyBorder(0,0,0,0) );
        sideMenu.setBackground(Color.GRAY);
        
    }
    
    
    /**buildSideMenuPanel
     * 
     * Creates the contents of the window
     * 
     */
    private void buildContentAreaPanel(){

        contentArea = new JPanel();
        
        //--Determine Content of panel
        switch(pageID){
            case 0:
                
                break;
            case 1:

                break;
        }
    }
    
}
