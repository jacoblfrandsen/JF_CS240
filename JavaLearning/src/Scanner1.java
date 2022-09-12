import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Scanner1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner1 scan = new Scanner1();
        if (args.length ==1){
            scan.processFile(args[0]);
        }
        else {
            scan.usage();
        }
    }
    public void processFile(String filePath) throws FileNotFoundException{
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()){
            String str = scanner.next();
            System.out.println(str);
        }
    }
    private void usage(){
        System.out.println("\nUSAGE :java Scanner Example <input-file>");
    }

}
