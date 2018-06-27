package data.jarutil;

import data.DataUtils;

import java.io.*;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarZip
{
    List<String> fileList;
    List<String> addList;
    List<File> modules;
    Set<File> ignoreSet;
    private String sourceFolder;
    private String projectFolder;
    private Manifest manifest;
    private String outPath;
    private File main;
    private final String JAR=".jar";
    private final String SRC = "src/";
    private final String PERIOD = ".";
    private final String FRONTSLASH ="/";
    private final String BACKSLASH = "\\";
    private final String FAILED = "Failed to Make Jar";

    JarZip(){
        fileList = new ArrayList<>();
        addList = new ArrayList<>();
        ignoreSet = new HashSet<>();
    }

    public void zip(File source, List<File> add, List<File> ignore, List<File> modules, String projectFolder, File out, File main, String name){
        sourceFolder = source.getAbsolutePath().replace(BACKSLASH,FRONTSLASH);
        this.main = main;
        this.projectFolder = projectFolder.replace(BACKSLASH,FRONTSLASH);
        System.out.println(sourceFolder);
        System.out.println(projectFolder);
        this.modules = modules;
        outPath = (out.getAbsolutePath() + FRONTSLASH + name).replace(BACKSLASH,FRONTSLASH);
        if(outPath.indexOf(JAR)==-1)
            outPath = outPath + JAR;
        ignoreSet = new HashSet<>((ignore));
        this.projectFolder = projectFolder.replace(BACKSLASH,FRONTSLASH);
        for(File file : add){
           System.out.print(file.getAbsolutePath());
            addFile(file);
        }
        buildManifest();
        generateFileList(source);
        zipIt(outPath);

    }

    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){

        byte[] buffer = new byte[1024];

        try{
            zipFile.replace(BACKSLASH,FRONTSLASH);

            FileOutputStream fos = new FileOutputStream(zipFile);
            JarOutputStream jos = new JarOutputStream(fos,manifest);


            for(String file : this.fileList){

                JarEntry ze= new JarEntry(file.replace(BACKSLASH,FRONTSLASH));
                jos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(sourceFolder + FRONTSLASH + file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    jos.write(buffer, 0, len);
                }
                in.close();
            }

            for(String file : this.addList){

                JarEntry ze= new JarEntry(file.replace(BACKSLASH,FRONTSLASH));
                jos.putNextEntry(ze);
                System.out.println(projectFolder +FRONTSLASH + file);
                FileInputStream in = new FileInputStream(projectFolder + FRONTSLASH + file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    jos.write(buffer, 0, len);
                }
                in.close();
            }

            jos.closeEntry();
            //remember close it
            jos.close();

        }catch(IOException ex){
            DataUtils.ErrorStatement(FAILED);

        }
    }

    /**C:\Users\Conrad\IdeaProjects\voogasalad_oneclassonemethod\out\production\voogasalad_oneclassonemethod\xstream-1.4.10.jar
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public void generateFileList(File node){
        if(node.isFile() && !ignoreSet.contains(node)){
            fileList.add(generateZipEntry(node.getAbsolutePath(),sourceFolder));
        }
        if(node.isDirectory()&& !ignoreSet.contains(node)){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }

    public void addFile(File node){
        if(node.isFile() && !ignoreSet.contains(node)){
            addList.add(generateZipEntry(node.getAbsolutePath(),projectFolder));
        }        System.out.println(generateZipEntry(node.getAbsoluteFile().toString(),projectFolder));
        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                addFile(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file, String source){
        return file.substring(source.length()+1, file.length());
    }

    private void buildManifest(){
        manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        for(File module : modules) {
            manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH, module.getName());
            System.out.print(module.getName());
        }
        System.out.print(main.getAbsolutePath());
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, getMain(main));
    }


    public String getMain(File main){
        String mainclass = main.getAbsolutePath();
        System.out.println(mainclass+ "     " + projectFolder +FRONTSLASH + SRC);
        mainclass = mainclass.replace(BACKSLASH,FRONTSLASH);
        mainclass = mainclass.substring((projectFolder +FRONTSLASH + SRC).length());
        mainclass = mainclass.substring(0, mainclass.indexOf(PERIOD));
        mainclass= mainclass.replace(FRONTSLASH,PERIOD);
        System.out.println(mainclass);
        return mainclass;
    }
}