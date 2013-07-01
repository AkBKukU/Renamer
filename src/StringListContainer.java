
public class StringListContainer {
    

    //--Field Declarations
    String[] modActions = {"none","none","none","none","none"};
    String stringList[];
    
    public StringListContainer(String[] rawStrings){
        stringList = rawStrings;
    }
    
    /* actPrefix
     * 
     * Adds a string as a prefix
     * 
     * ID: 0
     * 
     */
    private String[] actPrefix(String[] input, String modValues){
        
        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = modValues + input[c];
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
        
        boolean isValid = true;
        int start = 0;
        
        
        try { 
            start = Integer.parseInt(modValues); 
        } catch(NumberFormatException e) { 
            isValid = false;
        }
        
        if(isValid){
            for(int c = 0; c < STRING_COUNT; c++){
                output[c] = input[c] + modValues;
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

        final int STRING_COUNT = input.length;
        String[] output = new String[STRING_COUNT];
        
        boolean isValid = true;
        String[] counterLimits= modValues.split(":");
        int maxLength = counterLimits[0].length();
        int start = 0;
        int end = 100;
        
        
        try { 
            start = Integer.parseInt(counterLimits[0]); 
        } catch(NumberFormatException e) { 
            isValid = false;
        }
        try { 
            end = Integer.parseInt(counterLimits[1]); 
        } catch(NumberFormatException e) { 
            isValid = false;
        }
        
        
        for(int c = 0; c < STRING_COUNT; c++){
            output[c] = input[c] + modValues;
        }
        
        return output;
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
        
        String[] output = this.stringList;
        
        
        for(int c = 0; c < 5; c++){
            switch(modActions[c].charAt(0)){
            
                case '0':
                    output = this.actPrefix(output, modActions[c].substring(1));
                    break;

                case '1':
                    output = this.actSuffix(output, modActions[c].substring(1));
                    break;
                    
                default:
                    break;
            }
            
        }
        
        return output;
    }
}
