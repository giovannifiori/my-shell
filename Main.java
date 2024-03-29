/*
 *
 *   @Author: Giovanni Fiori <gcf@icomp.ufam.edu.br>
 *
 */

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Shell shell = new Shell();
        Scanner in = new Scanner(System.in);
        String uniqueID = UUID.randomUUID().toString(); //id unico para salvar arquivo de log
        String logDir = shell.getHomeDirectory();       //diretorio onde serão salvos os arquivos de log da sessao
        Log log = new Log();
        String input = "";


        System.out.println("Welcome! Type `info` for help");
        do {
            System.out.print(shell.lineHeader());
            input = in.nextLine();
            log.insert(input, shell.getUser(), shell.getHostName());    //insere a entrada no log
            String[] input_array = input.split(" ");

            switch (input_array[0].toLowerCase()) {
                case "info":
                    shell.info();
                    break;
                case "pwd":
                    System.out.println(shell.pwd());
                    break;
                case "whoami":
                    System.out.println(shell.whoami());
                    break;
                case "ls":
                    if(input_array.length > 1)
                        shell.ls(input_array[1]);
                    else
                        shell.ls();
                    break;
                case "cd":
                    if(input_array.length > 1)
                        shell.cd(input_array[1]);
                    else
                        shell.cd("");
                    break;
                case "rm":
                    if(input_array.length > 1)
                        shell.rm(input_array[1]);
                    else
                        shell.rm("");
                    break;
                case "rmdir":
                    if(input_array.length > 1)
                        shell.rmdir(input_array[1]);
                    else
                        shell.rmdir("");
                    break;
                case "mkdir":
                    if(input_array.length > 1)
                        shell.mkdir(input_array[1]);
                    else
                        shell.mkdir("");
                    break;
                case "touch":
                    if(input_array.length > 1)
                        shell.touch(input_array[1]);
                    else
                        shell.touch("");
                    break;
                case "mv":
                    if(input_array.length > 2)
                        shell.mv(input_array[1], input_array[2]);
                    break;
                case "clear":
                    shell.clear();
                    break;
                case "exec":
                    if(input_array.length > 1){
                        String[] params = new String[input_array.length - 1];
                        System.arraycopy(input_array, 1, params, 0, input_array.length-1);
                        shell.exec(params);
                    }
                    break;
                case "exit":
                    log.save(logDir + "/log_" + uniqueID+".txt");
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        } while(!input.equals("exit"));
    }
}
