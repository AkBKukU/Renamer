import java.io.*;
import java.util.Arrays;


public class StringListContainer {
    

    //--Field Declarations
    public static final int NUM_OF_ACTIONS = 10;
    String[] modActions = new String[NUM_OF_ACTIONS];
    String folderPath;
    String stringList[];
    String hardList[];
    String extensionList[];
    
    public StringListContainer(){

        //--Set default action values
        for(int c = 0; c < NUM_OF_ACTIONS; c++){
            modActions[c] = "none";
        }
        
        
        setFolder("noFolder");
    }
    
    public void setFolder(String newFolder){
        
        folderPath = newFolder;
        System.out.println("Folder Selected: " + folderPath);

        String[] rawStrings = {
                "the quick brown fox jumps over the lazy dog.mp3",
                "abcdefghijklmnopqrstuvwxyz.DOCX",
                "lorem ipsum dolor sit amet.gif"
            };
        
        int STRING_COUNT = 3;
        if(newFolder != "noFolder"){
            STRING_COUNT = 0;
            File folderFile = new File(newFolder);
            File[] files = folderFile.listFiles();

            int FILE_COUNT = rawStrings.length;
            FILE_COUNT = files.length;
            
            String[] directoryDump = new String[FILE_COUNT];
            
            for(int c = 0; c < FILE_COUNT; c++){
                System.out.println("File: " + files[c].getPath() + " is " + files[c].isFile());
                if( files[c].isFile() ){
                    directoryDump[STRING_COUNT] = files[c].getName();
                    STRING_COUNT++;
                }
            }
            
            if(STRING_COUNT < 0){STRING_COUNT = 0;}
            
            rawStrings = new String[STRING_COUNT];
            for(int c = 0; c < STRING_COUNT; c++){
                rawStrings[c] = directoryDump[c];
                    
                
            }
        }
        
        Arrays.sort(rawStrings, String.CASE_INSENSITIVE_ORDER);
        
        
        extensionList = new String[STRING_COUNT];
        stringList = new String[STRING_COUNT];
        hardList = new String[STRING_COUNT];

        
        //--Get filenames and extensions
        for(int c = 0; c < STRING_COUNT; c++){
            
            if( rawStrings[c].contains(".") ){
                extensionList[c] = "." + rawStrings[c].split("\\.")[ rawStrings[c].split("\\.").length-1 ];
                stringList[c] = (String) rawStrings[c].subSequence(0, rawStrings[c].length() - extensionList[c].length() );
                hardList[c] = stringList[c];
                
            }else{
                extensionList[c] = "";
                stringList[c] = rawStrings[c];
                hardList[c] = stringList[c];
                
            }
            
            

        }
        
        
    }
    
    /* actString
     * 
     * Adds a string as a prefix
     * 
     * ID: 0
     * 
     */
    private String[] actString(String[] input, String modValues){
        
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = input[c] + modValues.replace("/", "");
        }
        
        return output;
    }

    
    /* actReplace
     * 
     * Adds a string as a suffix
     * 
     * ID: 1
     * 
     */
    private String[] actReplace(String[] input, String modValues){
        
        
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        String[] splitValues = modValues.split("/");

        if(splitValues.length > 1){
            for(int c = 0; c < STRING_COUNT; c++){
                output[c] = input[c].replace(splitValues[0], splitValues[1]);
            }
        }else{

            for(int c = 0; c < STRING_COUNT; c++){
                output[c] = input[c].replace(splitValues[0], "");
            }
        }
        
        return output;
    }

    
    /* actCounter
     * 
     * Adds a counter as a suffix
     * 
     * ID: 2
     * 
     */
    private String[] actCounter(String[] input, String modValues){

        boolean isValid = true;
        int digits = 0;
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];

        String[] splitValues = modValues.split("/");
        if(splitValues.length > 0){
            digits = splitValues[0].length();
        }else{
            isValid = false;
            
        }
        
        int start = 1;
        

        if(splitValues.length > 1){
            try { 
                start = Integer.parseInt(splitValues[1]); 
                
            } catch(NumberFormatException e) { 
                isValid = false;
                
            }
        }else{
            isValid = false;
            
        }
        String startOut = "";
        
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                startOut = start + "";
                if( startOut.length() < digits){
                    int difference = digits - startOut.length() ;
                    
                    for( int d = 0; d < difference; d++){
                        startOut = "0" + startOut;
                    }
                    
                }
                
                
                output[c] = input[c] + startOut;
                start++;
            }
        }
        
        return output;
    }

    
    /* actSubstring
     * 
     * Adds a counter as a suffix
     * 
     * ID: 3
     * 
     */
    private String[] actSubstring(String[] input, String modValues){

        final int STRING_COUNT = this.stringList.length;
        String[] localOutput = new String[STRING_COUNT];
        
        boolean isValid = true;
        String[] stringLimits= modValues.split("/");
        int start = 0;
        int end = 100;
        

        if(stringLimits.length > 0){
            try { 
                start = Integer.parseInt(stringLimits[0]); 
            } catch(NumberFormatException e) { 
                isValid = false;
            }
        }
        if(stringLimits.length > 1){
            try { 
                end = Integer.parseInt(stringLimits[1]); 
            } catch(NumberFormatException e) { 
                isValid = false;
            }
        }
        
        int tempEnd = end;
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                tempEnd = end;
                if(end > stringList[c].length() ){
                    tempEnd = stringList[c].length();
                }
                
                localOutput[c] = input[c] + this.stringList[c].substring(start,tempEnd);
            }
        }
        return localOutput;
    }


    
    /* addAction
     * 
     * Adds an action to the string modifier list
     * 
     */
    public void addAction(int actionID, String actionValue){

        boolean notAdded = true;
        for(int c = 0; c < NUM_OF_ACTIONS; c++){
            
            if(notAdded){
                if(modActions[c] == "none"){

                    modActions[c] = actionID + actionValue;
                    notAdded = false;
                }
            }
            
        }
        
    }

    
    /* setAction
     * 
     * Sets an action to the string modifier list
     * 
     */
    public void setAction(int actionListID, int actionID, String actionValue){

        boolean isValid = true;
        if( !(actionListID < NUM_OF_ACTIONS) ){ isValid = false; }
        String[] stringLimits = actionValue.split("/");
        if(stringLimits.length>0){
            if( stringLimits[0].trim() == "" ){ isValid = false; }
        }
        if(stringLimits.length>1){
            if( stringLimits[1].trim() == "" ){ isValid = false; }
        }
        
        if(isValid){
                
                modActions[actionListID] = actionID + actionValue;
        }
        
    }
    /* getModStringList
     * 
     * Returns list of moddified Strings
     * 
     */
    public String[] getModStringList(){

        final int STRING_COUNT = stringList.length;
        
        String[] output = getModStringListPlain();

        //--Build Final output
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = output[c] + extensionList[c];
        }
        return output;
    }


    
    /* getModStringList
     * 
     * Returns list of moddified Strings
     * 
     */
    public String[] getModStringListPlain(){

        final int STRING_COUNT = stringList.length;
        
        String[] output = new String[STRING_COUNT];

        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = "";
        }
        
        for(int c = 0; c < NUM_OF_ACTIONS; c++){
            switch(modActions[c].charAt(0)){
            
                case '0':
                    output = this.actString(output, modActions[c].substring(1));
                    break;

                case '1':
                    output = this.actReplace(output, modActions[c].substring(1));
                    break;

                case '2':
                    output = this.actCounter(output, modActions[c].substring(1));
                    break;

                case '3':
                    output = this.actSubstring(output, modActions[c].substring(1));
                    break;
                    
                default:
                    break;
            }
            
        }
        return output;
    }
    
    
    /* getStringList
     * 
     * Returns list of original Strings
     * 
     */
    public String[] getStringList(){
        
        final int STRING_COUNT = stringList.length;

        String[] output = new String[STRING_COUNT];

        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = stringList[c] + extensionList[c];
        }
        return output;
    }
    
    
    /* commitNames
     * 
     * Commits the modded strings to the files
     * 
     */
    public void commitNames(){
        
        final int STRING_COUNT = stringList.length;
        
        //--Check what file delimiter based on the OS 
        String OS = System.getProperty("os.name");
        String fileDelim = "/";
        if( OS.startsWith("Windows") ){
            fileDelim = "\\";
        }

        String[] originalNames = getStringList();
        String[] modNames = getModStringListPlain();
        String[] newNames = getModStringList();

        for(int c = 0; c < STRING_COUNT; c++){
            originalNames[c] = folderPath + fileDelim + originalNames[c];
            newNames[c] = folderPath + fileDelim + newNames[c];
            // File (or directory) with old name
            File file = new File(originalNames[c]);

            // File (or directory) with new name
            File file2 = new File(newNames[c]);
            
            
            if(file2.exists()){
                System.out.println("file exists");
            }
            
            if(file.exists()){
                System.out.println("Original file exists");
            }else{

                System.out.println("Not a file: " + originalNames[c]);
            }

            // Rename file (or directory)
            boolean success = file.renameTo(file2);
            if (!success) {
                // File was not successfully renamed
            }else{
                stringList[c] = modNames[c];
            }
            
            
        }
    }
    /* revertNames
     * 
     * Returns all the files to their original names
     * 
     */
    public void revertNames(){
        
        final int STRING_COUNT = stringList.length;
        
        //--Check what file delimiter based on the OS 
        String OS = System.getProperty("os.name");
        String fileDelim = "/";
        if( OS.startsWith("Windows") ){
            fileDelim = "\\";
        }

        String[] originalNames = getStringList();
        String[] newNames = new String[STRING_COUNT];

        for(int c = 0; c < STRING_COUNT; c++){
            originalNames[c] = folderPath + fileDelim + originalNames[c];
            newNames[c] = folderPath + fileDelim + hardList[c] + extensionList[c];

            // File (or directory) with old name
            File file = new File(originalNames[c]);

            // File (or directory) with new name
            File file2 = new File(newNames[c]);
            
            
            if(file2.exists()){
                System.out.println("file exists");
            }
            
            if(file.exists()){
                System.out.println("Original file exists");
            }else{

                System.out.println("Not a file: " + originalNames[c]);
            }

            // Rename file (or directory)
            boolean success = file.renameTo(file2);
            if (!success) {
                // File was not successfully renamed
            }else{
                stringList[c] = hardList[c];
            }
            
            
        }
    }
}
