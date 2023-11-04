import java_cup.runtime.Symbol;
import lex_analyzer.Lexer;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        String fileName = "/home/pedro/IdeaProjects/LengAutom1/src/test/code.txt";

        try {
            FileReader fileReader = new FileReader(fileName);
            Lexer lexer = new Lexer(new BufferedReader(fileReader));

            Symbol token;
            do {
                token = lexer.next_token();
                int tokenId = token.sym;
                String tokenText = token.value != null ? token.value.toString() : "";
                int line = token.left + 1; // Compensar la línea base 0
                int column = token.right;

                System.out.println("Token ID: " + tokenId);
                if (!tokenText.isEmpty())
                    System.out.println("Token Text: " + tokenText);
                System.out.println("Line: " + line);
                System.out.println("Column: " + column);
                System.out.println();
            } while (token.sym != Lexer.YYEOF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
