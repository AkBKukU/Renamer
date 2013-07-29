
/**=============================================================*\
 *                                                              *
 *                   StringListContainer Class                  *
 *                                                              *
 *                      by: Shelby Jueden                       *
 *                                                              *
 *    ______________________________________________________    *
 *   |                 StringListContainer                  |   *
 *   |------------------------------------------------------|   *
 *   | + NUM_OF_ACTIONS:                    int             |   *
 *   | + modActions:                        String[]        |   *
 *   | + folderPath:                        String          |   *
 *   | + stringList:                        String[]        |   *
 *   | + hardList:                          String[]        |   *
 *   | + extensionList:                     String[]        |   *
 *   |------------------------------------------------------|   *
 *   | + setFolder(String):                 void            |   *
 *   |                                                      |   * 
 *   | - actString(String[], String)        String[]        |   *
 *   | - actReplace(String[], String)       String[]        |   *
 *   | - actCounter(String[], String)       String[]        |   *
 *   | - actSubstring(String[], String)     String[]        |   *
 *   |                                                      |   * 
 *   | + addAction(int, String)                             |   *
 *   | + setAction(int, int, String)                        |   *
 *   |                                                      |   * 
 *   | + getModStringList()                 String[]        |   *
 *   | + getModStringListPlain()            String[]        |   *
 *   | + getStringList()                    String[]        |   *
 *   |                                                      |   * 
 *   | + commitNames()                      void            |   *
 *   | + revertNames()                      void            |   *
 *   |                                                      |   * 
 *   |______________________________________________________|   *
 *                                                              *
 *                                                              *
\*==============================================================*/


/**
 * Imports
 */
import java.io.*;
import java.util.Arrays;


public class StringListContainer {
    

    //--Field Declarations
    public static final int NUM_OF_ACTIONS = 10; //--10 is arbitrary. This value can be modified to change how many actions are allowed
    public String[] modActions = new String[NUM_OF_ACTIONS];
    public static String folderPath;
    public static  String stringList[];
    public static String[] errors;
    public String hardList[];
    public String extensionList[];

    
    /* No-Arg Constructor
     * 
     * Starts at the current directory
     */
    public StringListContainer(){

        //--Set default action values
        for(int c = 0; c < NUM_OF_ACTIONS; c++){
            modActions[c] = "none";
        }
        
        
        setFolder(System.getProperty("user.dir"));
    }

    /* setFolder
     * 
     * Sets the folder to load files from and gets list of files
     */
    public void setFolder(String newFolder){
        
        folderPath = newFolder;

        String[] rawStrings = {
                "the quick brown fox jumps over the lazy dog.mp3",
                "abcdefghijklmnopqrstuvwxyz.DOCX",
                "lorem ipsum dolor sit amet.gif"
            };
        
        //--Open folder and get list of items
        File folderFile = new File(newFolder);
        File[] files = folderFile.listFiles();

        int FILE_COUNT = rawStrings.length;
        FILE_COUNT = files.length;
        
        String[] directoryDump = new String[FILE_COUNT];

        //--Filter out folders
        int STRING_COUNT = 0;
        for(int c = 0; c < FILE_COUNT; c++){
            if( files[c].isFile() ){
                directoryDump[STRING_COUNT] = files[c].getName();
                STRING_COUNT++;
            }
        }
        
        //--Convert to usable string
        if(STRING_COUNT < 0){STRING_COUNT = 0;}
        rawStrings = new String[STRING_COUNT];
        for(int c = 0; c < STRING_COUNT; c++){
            
            rawStrings[c] = directoryDump[c];
                
        }
        
        //--Put in alphabetical order
        Arrays.sort(rawStrings, String.CASE_INSENSITIVE_ORDER);
        
        //--Initialize global variables
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
     * Adds a string
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

        //--Get the two values
        String[] splitValues = modValues.split("/");
        
        //--Check if second value is set and use nothing if not
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
        
        //--Declare local variables 
        boolean isValid = true;
        int digits = 0;
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        
        //--Get the two values
        String[] splitValues = modValues.split("/");
        
        //--Check if set then get number of digits
        if(splitValues.length > 0){
            digits = splitValues[0].length();
        }else{
            isValid = false;
            
        }
        
        int start = 1;
        
        //--Make sure the user input a valid int
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
        
        //--Final validity check and write counter
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                startOut = start + "";
                
                //--Check if current number has enough digits and add them if not
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
        
        //--Get the two values
        String[] stringLimits= modValues.split("/");
        int start = 0;
        int end = 100;
        

        //--Make sure the user input valid ints
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

        
        //--Final validity check and write substring
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                
                //--Check if end from user is longer than string and correct
                tempEnd = end;
                if(end > stringList[c].length() ){
                    tempEnd = stringList[c].length();
                }
                
                localOutput[c] = input[c] + this.stringList[c].substring(start,tempEnd);
            }
        }
        return localOutput;
    }

    
    /* actUppercase
     * 
     * Uppercases the whole or part of the text
     * 
     * ID: 4
     * 
     */
    private String[] actUppercase(String[] input, String modValues){

        final int STRING_COUNT = this.stringList.length;
        String[] localOutput = new String[STRING_COUNT];
        
        boolean isValid = true;
        
        //--Get the two values
        String[] stringLimits= modValues.split("/");
        int start = 0;
        int end = 100;
        

        //--Make sure the user input valid ints
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

        
        //--Final validity check and write substring
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                
                //--Check if end from user is longer than string and correct
                tempEnd = end;
                if(end > input[c].length() ){
                    tempEnd = input[c].length();
                }
                
                localOutput[c] = input[c].replace(input[c].substring(start,tempEnd), input[c].substring(start,tempEnd).toUpperCase()) ;
                
            }
        }
        return localOutput;
    }

    
    /* actLowercase
     * 
     * Lowercases the whole or part of the text
     * 
     * ID: 5
     * 
     */
    private String[] actLowercase(String[] input, String modValues){

        final int STRING_COUNT = this.stringList.length;
        String[] localOutput = new String[STRING_COUNT];
        
        boolean isValid = true;
        
        //--Get the two values
        String[] stringLimits= modValues.split("/");
        int start = 0;
        int end = 100;
        

        //--Make sure the user input valid ints
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

        
        //--Final validity check and write substring
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                
                //--Check if end from user is longer than string and correct
                tempEnd = end;
                if(end > input[c].length() ){
                    tempEnd = input[c].length();
                }
                
                localOutput[c] = input[c].replace(input[c].substring(start,tempEnd), input[c].substring(start,tempEnd).toLowerCase()) ;
                
            }
        }
        return localOutput;
    }
    
    /* actSmartCase
     * 
     * Tries to make a pleasant looking string out of the text
     * 
     * ID: 6
     * 
     */
    private String[] actSmartCase(String[] input, String modValues){
        
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = input[c].toLowerCase();
        }
        
        for(int c = 0; c < STRING_COUNT; c++){
            
            //--Capitalize First Letter
            output[c] = input[c].replace( input[c], input[c].substring(0,1).toUpperCase() + input[c].substring(1) );
            
            int STRING_LENGTH = output[c].length();
            
            for(int d = 1; d < STRING_LENGTH-1; d++){
                
                //--Checks if there is a space and then a letter then a letter or a space and will capitalize the middle letter
                if(
                    output[c].charAt(d-1) == ' ' && 
                    Character.isLetter(output[c].charAt(d)) && 
                    Character.isLowerCase(output[c].charAt(d)) && 
                    (Character.isLetter(output[c].charAt(d+1)) || output[c].charAt(d-1) == ' ') 
                ){
                    output[c] = output[c].replace(output[c], output[c].substring(0,d) + output[c].substring(d,d+1).toUpperCase() + output[c].substring(d+1)) ;
                    d = 1;
                }
            }
        }
        
        String[] lowercaseWords = { "to", "a", "the", "at", "in", "with", "and", "but", "or"};
        final int NUMLOWWORDS = lowercaseWords.length;
        for(int c = 0; c < STRING_COUNT; c++){
            
            for(int d = 0; d < NUMLOWWORDS; d++){

                output[c] = output[c].replace( 
                        " " + lowercaseWords[d].substring(0,1).toUpperCase() + lowercaseWords[d].substring(1) + " ",
                        " " + lowercaseWords[d] + " "
                );
                
            }
            
            for(int d = 0; d < output[c].length(); d++){

                if(Character.isLetter(output[c].charAt(d))){
                    
                    output[c] = output[c].replace(output[c], output[c].substring(0,d) + output[c].substring(d,d+1).toUpperCase() + output[c].substring(d+1)) ;
                    
                    d = output[c].length();
                }
                
            }
        }
        
        return output;
    }


    
    /* addAction
     * 
     * Adds an action to the string modifier list in the next available slot
     * 
     */
    public void addAction(int actionID, String actionValue){

        boolean notAdded = true;
        for(int c = 0; c < NUM_OF_ACTIONS; c++){
            
            if(notAdded){
                
                //--Checks if slot is empty and writes if it is
                if(modActions[c] == "none"){

                    modActions[c] = actionID + actionValue;
                    notAdded = false;
                }
            }
            
        }
        
    }

    
    /* setAction
     * 
     * Sets an action to the string modifier list at a specific index
     * 
     */
    public void setAction(int actionListID, int actionID, String actionValue){
        
        boolean isValid = true;
        //--Makes sure is a valid index
        if( !(actionListID < NUM_OF_ACTIONS) ){ isValid = false; }
        
        //--Check for data
        String[] stringLimits = actionValue.split("/");
        if(stringLimits.length>0){
            if( stringLimits[0].trim() == "" ){ isValid = false; }
        }
        if(stringLimits.length>1){
            if( stringLimits[1].trim() == "" ){ isValid = false; }
        }
        
        if(isValid){
                
                modActions[actionListID] = actionID + actionValue;
        }else if(actionID == (int) -1){

            modActions[actionListID] = 0 + "";
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

        //--Add extension to final output
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = output[c] + extensionList[c];
        }
        return output;
    }


    
    /* getModStringList
     * 
     * Returns list of moddified Strings without extensions
     * 
     */
    public String[] getModStringListPlain(){

        final int STRING_COUNT = stringList.length;
        
        String[] output = new String[STRING_COUNT];
        
        //--Fill with blank data
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = "";
        }
        
        //--Apply stored actions to final output
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

                case '4':
                    output = this.actUppercase(output, modActions[c].substring(1));
                    break;

                case '5':
                    output = this.actLowercase(output, modActions[c].substring(1));
                    break;

                case '6':
                    output = this.actSmartCase(output, modActions[c].substring(1));
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
    public int commitNames(){
        return this.commitNames(1);
    }
    
    
    /* commitNames
     * 
     * Commits the modded strings to the files
     * Specifies the tolerance level for errors to pass at
     * 
     * 
     */
    public int commitNames(int errorTolerance){
        
        final int STRING_COUNT = stringList.length;
        

        String[] originalNames = getStringList();
        String[] modNames = getModStringListPlain();
        String[] newNames = getModStringList();
        errors = new String[STRING_COUNT];
        int errorLevel = 0;

        for(int c = 0; c < STRING_COUNT; c++){
            
            errors[c] = "none";

            for(int d = 0; d < STRING_COUNT; d++){
                
                if(c == d){
                    //--Do nothing on it's own name
                    
                }else if( newNames[c].equals(originalNames[d]) ){
                    errors[c] = "There was already a file called this";
                    if(errorLevel < 2)errorLevel = 2;
                    
                }else if( newNames[c].equalsIgnoreCase(originalNames[d]) ){
                    errors[c] = "There was already a file called this with a different case";
                    if(errorLevel < 2)errorLevel = 2;
                    
                }else if(newNames[c].equals(newNames[d]) ){
                    errors[c] = "There would be another file called this";
                    if(errorLevel < 2)errorLevel = 2;
                    
                }else if(newNames[c].equalsIgnoreCase(newNames[d]) ){
                    errors[c] = "There would be another file called this with a different case";
                    if(errorLevel < 2)errorLevel = 2;
                    
                }else if( newNames[c].contains("/") || newNames[c].contains("\0") ){
                    errors[c] = "Not a valid file Under UNIX or MS-DOS/Windows";
                    if(errorLevel < 2)errorLevel = 2;
                    
                }else if( 
                    newNames[c].contains("\\") || 
                    newNames[c].contains("/") || 
                    newNames[c].contains(":") || 
                    newNames[c].contains("*") || 
                    newNames[c].contains("?") || 
                    newNames[c].contains("\"") || 
                    newNames[c].contains("<") || 
                    newNames[c].contains(">") || 
                    newNames[c].contains("|")
                ){
                    errors[c] = "Not a valid file Under MS-DOS/Windows";
                    if(errorLevel < 1)errorLevel = 1;
                    
                }
            }
            
        }
        

        if(errorLevel < errorTolerance){
            for(int c = 0; c < STRING_COUNT; c++){
                originalNames[c] = folderPath + File.separator + originalNames[c];
                newNames[c] = folderPath + File.separator + newNames[c];
    
                File oldFile = new File(originalNames[c]);
                File newFile = new File(newNames[c]);
                                
                //--Perform change and check if succeeded and change field to reflect new name occurred 
                if ( oldFile.renameTo(newFile) ) {
                    stringList[c] = modNames[c];
                }else{
                    errors[c] = "A last minute error occurred. Check your values and the original names.";
                    if(errorLevel < 2)errorLevel = 2;
                }
                
                
            }
        }
        return errorLevel;
    }
    /* revertNames
     * 
     * Returns all the files to their original names
     * 
     */
    public void revertNames(){
        
        final int STRING_COUNT = stringList.length;
        

        String[] originalNames = getStringList();
        String[] newNames = new String[STRING_COUNT];

        for(int c = 0; c < STRING_COUNT; c++){
            originalNames[c] = folderPath + File.separator + originalNames[c];
            newNames[c] = folderPath + File.separator + hardList[c] + extensionList[c];


            File oldFile = new File(originalNames[c]);
            File newFile = new File(newNames[c]);
            
            //--Check if there is already a file by the name
            if(newFile.exists()){
                System.out.println("A " + newFile.getName() + " already exists!");
            }
            
            //--Perform change and check if succeeded and change field to reflect new name
            if ( oldFile.renameTo(newFile) ) {
                stringList[c] = hardList[c];
            }
            
            
        }
    }
}
