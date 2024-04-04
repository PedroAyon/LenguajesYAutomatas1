import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Analyzer {
    private final ArrayList<SymTableRow> symbolTable = new ArrayList<>();
    private final String file;

    public Analyzer(String file) {
        this.file = file;
        try {
            lexical();
            System.out.println("Lexical analysis completed successfully\n");
            syntax();
            System.out.println("Syntax analysis completed successfully\n");
            createSymbolTable();
        } catch (FileNotFoundException | AnalysisException e) {
            throw new RuntimeException(e);
        }
    }

    private void lexical() throws AnalysisException, FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        LexerCup lexer = new LexerCup(fileReader);
        Symbol symbol = null;
        do {
            try {
                symbol = lexer.next_token();
                // printSymbol(symbol);
            } catch (Exception e) {
                String message = e.getMessage();
                if (symbol != null) {
                    message += " at line " + (symbol.left + 1) + ", column " + symbol.right;
                }
                throw new AnalysisException(message);
            }
        } while (symbol.sym - 1 != LexerCup.YYEOF);
    }

    private void printSymbol(Symbol symbol) {
        int tokenId = symbol.sym;
        String tokenText = symbol.value != null ? symbol.value.toString() : "";
        int line = symbol.left;
        int column = symbol.right;

        System.out.println("Token: " + sym.terminalNames[tokenId]);
        System.out.println("TokenID: " + tokenId);
        if (!tokenText.isEmpty()) System.out.println("Token Text: " + tokenText);
        System.out.print("Line: " + line);
        System.out.println(", Column: " + column);
        System.out.println();
    }

    private void syntax() throws FileNotFoundException, AnalysisException {
        FileReader fileReader = new FileReader(file);
        LexerCup lexer = new LexerCup(fileReader);
        Syntax syntaxAnalyzer = new Syntax(lexer);
        try {
            syntaxAnalyzer.parse();
        } catch (Exception e) {
            throw new AnalysisException("Error during syntax analysis: " + e.getMessage());
        }
    }

    private ArrayList<SymTableRow> createSymbolTable() throws AnalysisException, FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        LexerCup lexer = new LexerCup(fileReader);
        symbolTable.clear();
        String scope = "global";
        String lastDatatype = "";
        int bracketCount = 0;
        ArrayList<Integer> symbolHistory = new ArrayList<>();

        Symbol symbol = null;
        do {
            try {
                symbol = lexer.next_token();
            } catch (Exception e) {
                String message = e.getMessage();
                if (symbol != null) {
                    message += " at line " + (symbol.left + 1) + ", column " + symbol.right;
                }
                throw new AnalysisException(message);
            }
            String value = symbol.value != null ? symbol.value.toString() : "";
            int line = symbol.left;
            int column = symbol.right;

            SymTableRow symTableRow = new SymTableRow();

            switch (symbol.sym) {
                case sym.DATA_TYPE -> lastDatatype = value;
                case sym.OBRACKET -> bracketCount++;
                case sym.SEMICOLON -> lastDatatype = "";
                case sym.CBRACKET -> {
                    bracketCount--;
                    if (bracketCount == 0) {
                        scope = "global";
                    }
                }
                case sym.IDENTIFIER -> {
                    if (identifierAlreadyExists(value, scope) || lastDatatype.isEmpty()) {
                        continue;
                    }

                    symTableRow.setName(value);
                    symTableRow.setDataType(lastDatatype);
                    symTableRow.setVisibility(determineVisibility(value));
                    symTableRow.setRow(line);
                    symTableRow.setColumn(column);
                    symTableRow.setScope(scope);
                    symTableRow.setRole(isUpperCase(value) ? Role.CONSTANT : Role.VARIABLE);

                    if (symbolHistory.get(symbolHistory.size() - 2) == sym.FUNCTION) {
                        symTableRow.setRole(Role.METHOD);
                        scope = value;
                    }
                    symbolTable.add(symTableRow);
                }
            }
            symbolHistory.add(symbol.sym);
        } while (symbol.sym - 1 != LexerCup.YYEOF);
        calculateSizes();
        return symbolTable;
    }

    // the size of a symbol equals to the number of symbols under its scope
    private void calculateSizes() {
        for (SymTableRow symbol : symbolTable) {
            int size = 0;
            for (SymTableRow s : symbolTable) {
                if (s.getScope().equals(symbol.getName())) {
                    size++;
                }
            }
            symbol.setSize(size);
        }
    }

    private boolean identifierAlreadyExists(String value, String scope) {
        for (SymTableRow symTableRow : symbolTable) {
            if (symTableRow.getName().equals(value)
                    && (symTableRow.getScope().equals(scope) || symTableRow.getScope().equals("global"))) {
                return true;
            }
        }
        return false;
    }

    private boolean isUpperCase(String value) {
        return value.equals(value.toUpperCase());
    }

    private Visibility determineVisibility(String identifier) {
        if (identifier.startsWith("_")) {
            return Visibility.PRIVATE;
        }
        return Visibility.PUBLIC;
    }

    public ArrayList<SymTableRow> getSymbolTable() {
        return symbolTable;
    }
}
