
public class Renamer {


    //--Field Declarations
    public static int runMode = 0; // 0 = Normal/GUI 1 = CLI 2 = Demo Mode
    public static RenamerGui window;
    public static StringListContainer stringListContainer;
    public static String folderPath;
    public static String demoEntries[] = {
        "the quick brown fox jumps over the lazy dog.mp3",
        "abcdefghijklmnopqrstuvwxyz.DOCX",
        "lorem ipsum dolor sit amet.gif"
    };
    
    
    public static void main(String[] args){

        //--Field Declarations
        parseArgs(args);
        
        //--Object Declarations
        
        switch(Renamer.runMode){
            
            case 0:

                stringListContainer = new StringListContainer();
                
                //--Create window
                window = new RenamerGui();
                break;
            
            case 2:
                String[] moddedNames;
                int numNames = demoEntries.length;
                System.out.println(
                    "--------------------------------------------------------------------------" + "\n" + 
                    "Renamer Demo Mode" + "\n" +
                    "--------------------------------------------------------------------------" + "\n"
                );

                stringListContainer = new StringListContainer();
                
                moddedNames = stringListContainer.getModStringList();
                System.out.println( "\n" +  "\n");

                stringListContainer.addAction(0, "IMG");
                moddedNames = stringListContainer.getModStringList();

                System.out.println("Added Strings:" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }

                stringListContainer.addAction(2, "0000/1");
                moddedNames = stringListContainer.getModStringList();

                System.out.println( "\n" +  "\n");
                System.out.println("Added Counter:" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }

                stringListContainer.addAction(0, " - ");
                moddedNames = stringListContainer.getModStringList();

                System.out.println( "\n" +  "\n");
                System.out.println("Added Strings:" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }
                
                System.out.println( "\n" +  "\n");

                stringListContainer.addAction(3, "0/10");
                moddedNames = stringListContainer.getModStringList();

                System.out.println( "\n" +  "\n");
                System.out.println("Added Substrings:" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }

                stringListContainer.addAction(1, "e/[Best Letter Here]");
                moddedNames = stringListContainer.getModStringList();

                System.out.println( "\n" +  "\n");
                System.out.println("Replaced the letter 'e':" + "\n");
                for(int c = 0; c < numNames; c++){

                    System.out.println(moddedNames[c]);
                }
                
                break;
                
            default:
                break;
        }
        
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
