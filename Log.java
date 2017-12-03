import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Log {

    LinkedList<String> log;

    public Log(){
        this.log = new LinkedList<>();
    }

    private String now(){
        return LocalDateTime.now().toString();
    }

    public void insert(String command, String user, String host){
        this.log.add(now() + " " + user + "@" + host + ": " + command);
    }

    public void save(String path){
        Path p = Paths.get(path);

        try{
            Files.write(p, this.log, Charset.forName("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
