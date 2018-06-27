package data;

import javafx.scene.control.Alert;

import java.io.File;

public class DataUtils {
   /* Author @Conrad defines all useful data things that either write or read would need

    */

    static final String FILE ="File:";
    static final String BACKSLASH = "\\";
    static final String USER_DIR = "user.dir";
    static final String FRONTSLASH = "/";
    static final String ERROR ="Error";
    static final String FAIL_MESSAGE ="File could not be loaded";
    static final String SAVE_PATH = "saves/";
    static final String GAME_PATH = "games/";
    static final String WRITE_ERROR = "Could not write file";
    protected static String gameName= "";


    public static File findInDirectory(File directory, String target){
//        System.out.println("Directory is " + directory.getAbsolutePath() + "   looking for"+ target);
        if(directory.isDirectory()){
            for(File subDir : directory.listFiles()){
                File found = findInDirectory(subDir, target);
                if(found!=null){
                    return found;
                }
            }
        }
        if(directory.getName().equals(target)){
//            System.out.println("Directory is foun " + directory.getName());

            return directory;
        }
        else return null;
    }

    public static File loadFile(String path) {
        String filePath = path.replace(BACKSLASH,FRONTSLASH);
        File file = new File(filePath);
       // System.out.println("TRYING TO FIND "+filePath);
        if(!file.exists()) {
            //System.out.println("TRYING TO FIND " + FILE + filePath);
            file = new File(FILE + filePath);
        }
        if (!file.exists()) {
            try {
                //System.out.println("TRYING TO FIND "+ filePath + " with resourceloader");
                file = new File(DataRead.class.getClassLoader().getResource(filePath).getFile());
                if (!file.exists())
                    throw new NullPointerException();
            } catch (NullPointerException e) {
                try {
                    //System.out.println("TRYING TO FIND "+ filePath + " with resourceloader and /");
                    file = new File(DataRead.class.getClassLoader().getResource(FRONTSLASH + filePath).getFile());
                    if (!file.exists())
                        throw new NullPointerException();
                } catch (NullPointerException f) {
                    try {
                        //System.out.println("File Finder ");
                        //System.out.print(filePath);
                        String fileName = filePath.substring(filePath.lastIndexOf(FRONTSLASH )+1);
                        //System.out.println("");
                        //System.out.println("Finding " + fileName + "   in  " + getProjectDir().getAbsolutePath());
                        file = findInDirectory(getProjectDir(), fileName);
                    }
                    catch(NullPointerException g){
                        ErrorStatement(FAIL_MESSAGE);
                    }
                }
            }
        }
        return file;
    }

    public static File getProjectDir(){
        String superDir = System.getProperty(USER_DIR).replace(BACKSLASH,FRONTSLASH);
        return new File(superDir);
    }

    public static void ErrorStatement(String error)  {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ERROR);
            alert.setContentText(error);
            alert.showAndWait();
        }
        catch (Error el )
        {
            System.out.print("Application not defined");
        }
    }

    public static void setGame(String name){
         gameName = name;
    }
    
    public static String getGame() {
    		return gameName;
    }

}
