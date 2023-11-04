package lex_analyzer;

public interface Token {
    int EOF = -1;
    int SEMICOLON = 1;
    int COLON = 2;
    int COMA = 3;
    int OBRACKET = 4;
    int CBRACKET = 5;
    int OPARENTHESIS = 6;
    int CPARENTHESIS = 7;
    int MUL = 8;
    int POW = 9;
    int SUM = 10;
    int SUB = 11;
    int DIV = 12;
    int INT_DIV = 13;
    int MOD = 14;
    int RANGE = 15;
    int INC = 16;
    int DEC = 17;
    int AND = 18;
    int NOT = 19;
    int OR = 20;
    int LESS_THAN = 21;
    int MORE_THAN = 22;
    int LESS_OR_EQ = 23;
    int MORE_OR_EQ = 24;
    int EQUALS = 25;
    int DIFFERENT_THAN = 26;
    int RETURNS = 27;
    int ASSIGNMENT = 28;
    int DATA_TYPE = 29;
    int FOR = 30;
    int WHILE = 31;
    int IF = 32;
    int ELSE = 33;
    int FUNCTION = 34;
    int RETURN = 35;
    int IMPORT = 36;
    int INPUT = 37;
    int PRINT = 38;
    int STRING_LITERAL = 39;
    int INTEGER_LITERAL = 40;
    int DECIMAL_LITERAL = 41;
    int IDENTIFIER = 42;
    int DOT = 43;
}