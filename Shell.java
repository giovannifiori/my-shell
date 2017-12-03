/*
 *
 *   @Author: Giovanni Fiori <gcf@icomp.ufam.edu.br>
 *
 */

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Map;

public class Shell {

    private String hostName;
    private String user;
    private String homeDirectory;
    private String currentDirectory;


    //Inicia um novo objeto com os valores padrão
    public Shell(){
        this.homeDirectory = System.getProperty("user.home");
        //o terminal inicia no diretório home do usuário
        this.currentDirectory = this.homeDirectory;
        this.user = System.getProperty ("user.name");

        try {
            //Classe InetAddress representa um endereço IP, neste caso, do localhost
            this.hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void info(){
        System.out.println("Commands available: ");
        System.out.println("    - ls");
        System.out.println("    - pwd");
        System.out.println("    - cd");
        System.out.println("    - mkdir");
        System.out.println("    - touch");
        System.out.println("    - mv");
        System.out.println("    - rm");
        System.out.println("    - rmdir");
        System.out.println("    - whoami");
        System.out.println("    - clear");
        System.out.println("    - exec");
    }

    public String getHostName() {
        return this.hostName;
    }

    public String getUser() {
        return this.user;
    }

    public String getHomeDirectory() {
        return this.homeDirectory;
    }

    public String getCurrentDirectory() {
        return this.currentDirectory;
    }

    //user@host:currDir$
    public String lineHeader(){
        String home = this.currentDirectory.equals(this.homeDirectory) ? "~" : this.getCurrentDirectory();
        home = home.replace(this.homeDirectory, "~");
        return this.getUser() + '@' + this.getHostName() + ':' + home + "$ ";
    }

    //retorna o caminho absoluto pra dado arquivo/diretorio
    private String getAbsPath(String path){
        String returnString = "";
        try {
            File thisDir = new File(this.getCurrentDirectory());
            File dir = new File(thisDir, path);
            returnString = dir.getCanonicalPath();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

    /*------------- COMMANDS ------------------*/

    //retorna o diretório de trabalho atual
    public String pwd() {
        return this.currentDirectory;
    }

    public String whoami() {
        return this.getUser();
    }

    //mudar de diretório
    public void cd(String goTo) {
        if (goTo == null || goTo.isEmpty()){
            goTo = System.getProperty("user.home");
        }

            if(goTo.startsWith("/")){
                this.currentDirectory = goTo;
            }else{
                File thisDir = new File(this.getCurrentDirectory());
                File dir = new File(thisDir, goTo);

                goTo = getAbsPath(goTo);
                if(dir.exists()){
                    this.currentDirectory = goTo;
                }
            }
    }

    //lista diretorios e arquivos dentro do diretorio atual
    public void ls() {
        //Classe File representa os paths de arquivos e diretorios com um pathname abstrato
        File files = new File(this.currentDirectory);
        //list() retorna um array de string com todos os arquivos e diretorios que estao no caminho passado na nova instancia de File
        String ls[] = files.list();

        for (int i = 0; i < ls.length; i++) {
            //ignora arquivos .~
            if (!ls[i].startsWith(".")) {
                System.out.println(ls[i]);
            }
        }
    }

    //cria um novo diretório
    public void mkdir(String newDirName){
        if (newDirName == null || newDirName.isEmpty()) {
            System.out.println("invalid param");
            return;
        }

        newDirName = getAbsPath(newDirName);
        File newDir = new File(newDirName);
        newDir.mkdirs();
    }

    //cria um novo arquivo
    public void touch(String path) {
        if (path == null || path.isEmpty()) {
            System.out.println("invalid param");
            return;
        }

        path = getAbsPath(path);
        File newFile = new File(path);

        if (newFile.exists()) {
            System.out.println("File already exists!");
            return;
        }

        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //move um arquivo
    public void mv(String from, String to) {
        if (to == null || to.isEmpty()) {
            System.out.println("invalid param");
            return ;
        }

        from = getAbsPath(from);
        to = getAbsPath(to);

        File movingFile = new File(from);
        // diretorio de destino
        File movingDir = new File(to);
        // move o arquivo para o novo diretorio
        movingFile.renameTo(new File(movingDir, movingFile.getName()));
    }

    //deleta arquivo / diretorio
    public void rm(String path) {
        if (path == null || path.isEmpty()) {
            System.out.println("invalid param");
            return;
        }

        path = getAbsPath(path);
        File file = new File(path);
        if(!file.isDirectory())
            file.delete();
        else
            System.out.println("param is not a file");
    }

    public void rmdir(String path) {
        if (path == null || path.isEmpty()) {
            System.out.println("invalid param");
            return;
        }

        path = getAbsPath(path);
        File folder = new File(path);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File f : files) {
                f.delete();
            }
            folder.delete();
        }else{
            System.out.println("param is not a directory");
        }

    }

    public void clear() {
        for (int i = 0; i < 30; i++) {
            System.out.println("\n");
        }
    }

    public void exec(String[] params) {
        ProcessBuilder pb = new ProcessBuilder(params);
        Map<String, String> env = pb.environment();
        env.put("VAR1", "myValue");
        env.remove("OTHERVAR");
        env.put("VAR2", env.get("VAR1") + "suffix");
        File dir = new File(getCurrentDirectory());
        pb.directory(dir);
        try {
            Process p = pb.start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
