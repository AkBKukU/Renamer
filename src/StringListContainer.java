
public class StringListContainer {
    

    //--Field Declarations
    String[] modActions = {"none","none","none","none","none"};
    String stringList[];
    
    public StringListContainer(String[] rawStrings){
        
        stringList = rawStrings;
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
            output[c] = input[c] + modValues;
        }
        
        return output;
    }

    
    /* actSufix
     * 
     * Adds a string as a suffix
     * 
     * ID: 1
     * 
     */
    private String[] actSuffix(String[] input, String modValues){
        
        
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = input[c] + modValues;
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

        String[] splitValues = modValues.split(":");
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
        String[] stringLimits= modValues.split(":");
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
        
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                localOutput[c] = input[c] + this.stringList[c].substring(start,end);
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
        for(int c = 0; c < 5; c++){
            
            if(notAdded){
                if(modActions[c] == "none"){

                    modActions[c] = actionID + actionValue;
                    notAdded = false;
                }
            }
            
        }
        
    }
    
    public String[] getModStringList(){

        final int STRING_COUNT = stringList.length;
        
        String[] output = new String[STRING_COUNT];

        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = "";
        }
        
        for(int c = 0; c < 5; c++){
            switch(modActions[c].charAt(0)){
            
                case '0':
                    output = this.actString(output, modActions[c].substring(1));
                    break;

                case '1':
                    output = this.actSuffix(output, modActions[c].substring(1));
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
}
