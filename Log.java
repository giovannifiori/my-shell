/*
 *
 *   @Author: Giovanni Fiori <gcf@icomp.ufam.edu.br>
 *
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Log {

    private LinkedList<String> log;

    public Log(){
        this.log = new LinkedList<>();
    }

    //retorna timestamp atual
    private String now(){
        return LocalDateTime.now().toString();
    }

    //insere no log
    public void insert(String command, String user, String host){
        this.log.add(now() + " " + user + "@" + host + ": " + command);
    }

    //salva os dados do log em um arquivo .txt
    public void save(String path){
        Path p = Paths.get(path);

        try{
            Files.write(p, this.log, Charset.forName("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
