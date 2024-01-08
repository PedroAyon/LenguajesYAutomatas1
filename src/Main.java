import java_cup.Lexer;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
//        generar();
        String fileName = "C:\\Users\\pedro\\Dev\\LenguajesYAutomatas1\\src\\test\\code.txt";

        try {
            FileReader fileReader = new FileReader(fileName);
            LexerCup lexer = new LexerCup(fileReader);
            Symbol token;
            do {
                token = lexer.next_token();
                int tokenId = token.sym;
                String tokenText = token.value != null ? token.value.toString() : "";
                int line = token.left + 1; // Compensar la línea base 0
                int column = token.right;

                System.out.println("Token ID: " + tokenId);
                System.out.println("Token Text: " + tokenText);
                System.out.println("Line: " + line);
                System.out.println("Column: " + column);
                System.out.println();
            } while (token.sym - 1 != LexerCup.YYEOF);
            fileReader = new FileReader(fileName);
            lexer = new LexerCup(fileReader);
            Syntax parser = new Syntax(lexer);
            parser.parse();
            System.out.println("Sintaxis correcta!");
        } catch (Exception e) {
            e.printStackTrace();
//            System.err.println("Error durante el análisis sintáctico: " + e.getMessage());
        }
    }

    public static void generar() throws Exception {
        String path = "C:/Users/pedro/Dev/LenguajesYAutomatas1/";
        String srcPath = path + "src/";

        String[] argsFlex = {srcPath + "LexerCup.flex"};
        jflex.Main.main(argsFlex);
        String[] argsCup = {"-parser", "Syntax", srcPath + "Syntax.cup"};
        java_cup.Main.main(argsCup);

        Path rutaSym = Paths.get(srcPath + "sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get(path + "sym.java"),
                Paths.get(srcPath + "sym.java")
        );
        Path rutaSin = Paths.get(srcPath + "Syntax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get(path + "Syntax.java"),
                Paths.get(srcPath + "Syntax.java")
        );
    }
}
