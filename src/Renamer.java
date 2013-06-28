
public class Renamer {


    //--Field Declarations
    public static int runMode = 0; // 0 = Normal/GUI 1 = CLI 2 = Demo Mode
    public static String demoEntries[] = {
        "the quick brown fox jumps over the lazy dog",
        "abcdefghijklmnopqrstuvwxyz",
        "lorem ipsum dolor sit amet"
    };
    
    
    public static void main(String[] args){

        //--Field Declarations
        parseArgs(args);
        
        //--Object Declarations
        
        switch(Renamer.runMode){
            case 2:
                String[] moddedNames;
                int numNames = demoEntries.length;
                System.out.println(
                    "--------------------------------------------------------------------------" + "\n" + 
                    "Renamer Demo Mode" + "\n" +
                    "--------------------------------------------------------------------------" + "\n"
                );
                
                StringListContainer stringList = new StringListContainer(demoEntries);
                moddedNames = stringList.getModStringList();
                System.out.println("Input Strings:" + "\n");
                
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }
                System.out.println( "\n" +  "\n");

                stringList.addAction(0, "Prefixing rules! ");
                moddedNames = stringList.getModStringList();

                System.out.println("Prefixed Strings:" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }
                
                System.out.println( "\n" +  "\n");

                stringList.addAction(1, " Sufixing rules!");
                moddedNames = stringList.getModStringList();

                System.out.println("Suffixed Strings:" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }
                
                break;
                
            default:
                break;
        }
        
        
        System.exit(0);
    }
    
    
    
    /**
     * Checks to see what arguments have been passed
     * 
     * @param args
     */
    private static void parseArgs(String[] args){

        //--Field Declarations
        int numArgs = args.length;
        
        //--Run through all arguments
        for(int c = 0;c < numArgs; c++){
            
            switch(args[c]){
                
                //--Check for demo mode and get string input
                case "-d":
                case "--demo":
                    Renamer.runMode = 2;
                    break;
                    
                //--Display help and exit
                case "--help":
                    System.out.println(
                            "Usage: java Renamer [OPTIONS]" + "\n" +
                            "Batch file renaming utility" + "\n" +
                            "  -d, --demo                 Run program in demo mode. Shows many functions" + "\n" + 
                            "                             it can do in console" + "\n" +
                            "  --help                     Display this help sheet"
                    );
                    System.exit(0);
                
                    
                   
            }
            
            
        }
        
    }
}
