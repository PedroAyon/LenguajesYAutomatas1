import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //generar();
        String file = "C:\\Users\\pedro\\IdeaProjects\\LenguajesYAutomatas1\\src\\test\\code.txt";
        Analyzer analyzer = new Analyzer(file);
        printSymbolTable(analyzer.getSymbolTable());
    }


    public static void printSymbolTable(ArrayList<SymTableRow> symbolTable) {
        for (SymTableRow row : symbolTable) {
            System.out.println("Name: " + row.getName());
            System.out.println("Data Type: " + row.getDataType());
            System.out.println("Scope: " + row.getScope());
            System.out.println("Visibility: " + row.getVisibility());
            System.out.println("Size: " + row.getSize());
            System.out.println("Row: " + row.getRow());
            System.out.println("Column: " + row.getColumn());
            System.out.println("Role: " + row.getRole());
            System.out.println();
        }
    }

    public static void generar() throws Exception {
        String path = "C:/Users/pedro/IdeaProjects/LenguajesYAutomatas1/";
        String srcPath = path + "src/";

        String[] argsFlex = {srcPath + "LexerCup.flex"};
        jflex.Main.main(argsFlex);
        String[] argsCup = {"-parser", "Syntax", srcPath + "Syntax.cup"};
        java_cup.Main.main(argsCup);

        Path rutaSymbols = Paths.get(srcPath + "sym.java");
        if (Files.exists(rutaSymbols)) {
            Files.delete(rutaSymbols);
        }
        Files.move(
                Paths.get(path + "sym.java"),
                rutaSymbols
        );
        Path rutaSintactico = Paths.get(srcPath + "Syntax.java");
        if (Files.exists(rutaSintactico)) {
            Files.delete(rutaSintactico);
        }
        Files.move(
                Paths.get(path + "Syntax.java"),
                rutaSintactico
        );
    }
}
