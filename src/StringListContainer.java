
public class StringListContainer {
    

    //--Field Declarations
    public static final int NUM_OF_ACTIONS = 10;
    String[] modActions = new String[NUM_OF_ACTIONS];
    String stringList[];
    String extensionList[];
    
    public StringListContainer(){

        //--Set default action values
        for(int c = 0; c < NUM_OF_ACTIONS; c++){
            modActions[c] = "none";
        }
        
        String[] emptyFolder = {
                "the quick brown fox jumps over the lazy dog.mp3",
                "abcdefghijklmnopqrstuvwxyz.DOCX",
                "lorem ipsum dolor sit amet.gif"
            };
        setFolder(emptyFolder);
    }
    
    public void setFolder(String[] rawStrings){
        

        final int STRING_COUNT = rawStrings.length;
        extensionList = new String[STRING_COUNT];
        stringList = new String[STRING_COUNT];

        
        //--Get filenames and extensions
        for(int c = 0; c < STRING_COUNT; c++){
            
            if( rawStrings[c].contains(".") ){
                extensionList[c] = "." + rawStrings[c].split("\\.")[ rawStrings[c].split("\\.").length-1 ];
                stringList[c] = (String) rawStrings[c].subSequence(0, rawStrings[c].length() - extensionList[c].length()-1 );
            }else{
                extensionList[c] = "";
                stringList[c] = rawStrings[c];
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
        
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = input[c].replace(splitValues[0], splitValues[1]);
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

        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];

        String[] splitValues = modValues.split("/");
        int digits = splitValues[0].length();
        String startRaw = splitValues[1];
        
        boolean isValid = true;
        int start = 0;
        
        
        try { 
            start = Integer.parseInt(startRaw); 
            
        } catch(NumberFormatException e) { 
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
        
        
        try { 
            start = Integer.parseInt(stringLimits[0]); 
        } catch(NumberFormatException e) { 
            isValid = false;
        }
        try { 
            end = Integer.parseInt(stringLimits[1]); 
        } catch(NumberFormatException e) { 
            isValid = false;
        }
        
        int tempEnd = end;
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                tempEnd = end;
                if(end > stringList[c].length() ){tempEnd = stringList[c].length();}
                
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
        String[] stringLimits= actionValue.split("/");
        if( stringLimits[0].trim() == "" ){ isValid = false; }
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
        
        //--Build Final output
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = output[c] + extensionList[c];
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
}
