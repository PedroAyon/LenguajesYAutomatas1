package lex_analyzer;
import java_cup.runtime.Symbol;

%%

%public
%class Lexer
%line
%column
%unicode
%ignorecase
%cup
%char

%init{
    yyline = 1;
%init}

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

%eofval{
{return new Symbol(Token.EOF, null);}
%eofval}

DecIntegerLiteral = 0 | [1-9][0-9]*
DecimalLiteral = {DecIntegerLiteral}("."[0-9]+)?
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
Identifier = [A-Za-z] [A-Za-z0-9]*

%state STRING


%%
<YYINITIAL> {
    // Literals
    \" { string.setLength(0); yybegin(STRING); }
    {DecIntegerLiteral} { return symbol(Token.INTEGER_LITERAL); }
    {DecimalLiteral} { return symbol(Token.DECIMAL_LITERAL); }
}

// Delimitadores
";" { return symbol(Token.SEMICOLON); }
":" { return symbol(Token.COLON); }
"," { return symbol(Token.COMA); }
"." { return symbol(Token.DOT); }
"{" { return symbol(Token.OBRACKET); }
"}" { return symbol(Token.CBRACKET); }
"(" { return symbol(Token.OPARENTHESIS); }
")" { return symbol(Token.CPARENTHESIS); }

// Operadores Aritméticos
"*" { return symbol(Token.MUL); }
"**" { return symbol(Token.POW); }
"+" { return symbol(Token.SUM); }
"-" { return symbol(Token.SUB); }
"/" { return symbol(Token.DIV); }
"//" { return symbol(Token.INT_DIV); }
"%" { return symbol(Token.MOD); }

// Operadores Rango
".." { return symbol(Token.RANGE); }

// Operadores Incremento
"++" { return symbol(Token.INC); }
"--" { return symbol(Token.DEC); }

// Operadores Lógicos
"and" { return symbol(Token.AND); }
"not" { return symbol(Token.NOT); }
"or" { return symbol(Token.OR); }

// Operadores Relacionales
"<" { return symbol(Token.LESS_THAN); }
">" { return symbol(Token.MORE_THAN); }
"<=" { return symbol(Token.LESS_OR_EQ); }
">=" { return symbol(Token.MORE_OR_EQ); }
"==" { return symbol(Token.EQUALS); }
"!>" { return symbol(Token.DIFFERENT_THAN); }

// Operadores Generales
"->" { return symbol(Token.RETURNS); }
"=" { return symbol(Token.ASSIGNMENT); }

// Tipos de dato
"int" | "double" | "string" | "char" | "bool" { return symbol(Token.DATA_TYPE, yytext()); }

// Estructuras de control
"for" { return symbol(Token.FOR); }
"while" { return symbol(Token.WHILE); }
"if" { return symbol(Token.IF); }
"else" { return symbol(Token.ELSE); }

// Manejo de funciones
"fn" { return symbol(Token.FUNCTION); }
"return" { return symbol(Token.RETURN); }

// Gestión de librerías
"import" { return symbol(Token.IMPORT); }

// I/O
"input" { return symbol(Token.INPUT); }
"print" { return symbol(Token.PRINT); }

{Identifier} { return symbol(Token.IDENTIFIER, yytext()); }

// Strings
<STRING> {
    \"                             { yybegin(YYINITIAL);
                                   return symbol(Token.STRING_LITERAL, string.toString()); }
    [^\n\r\"\\]+                   { string.append(yytext() ); }
    \\t                            { string.append('\t'); }
    \\n                            { string.append('\n'); }
    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\                             { string.append('\\'); }
}

// Comentarios
{Comment} { /* ignore */ }
// Espacios en blanco
{WhiteSpace} { /* ignore */ }

/* error fallback */
[^] { throw new Error("Illegal character <"+ yytext()+">"); }