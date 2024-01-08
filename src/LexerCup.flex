import java_cup.runtime.Symbol;

%%

%public
%class LexerCup
%type java_cup.runtime.Symbol
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
{return new Symbol(sym.EOF, null);}
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
text = \".+\"


%state STRING


%%
<YYINITIAL> {
    // Literals
    \" { string.setLength(0); yybegin(STRING); }
    {DecIntegerLiteral} { return symbol(sym.INTEGER_LITERAL); }
    {DecimalLiteral} { return symbol(sym.DECIMAL_LITERAL); }
}

// Delimitadores
";" { return symbol(sym.SEMICOLON); }
":" { return symbol(sym.COLON); }
"," { return symbol(sym.COMMA); }
"." { return symbol(sym.DOT); }
"{" { return symbol(sym.OBRACKET); }
"}" { return symbol(sym.CBRACKET); }
"(" { return symbol(sym.OPARENTHESIS); }
")" { return symbol(sym.CPARENTHESIS); }

// Operadores Aritméticos
"*" { return symbol(sym.MUL); }
"**" { return symbol(sym.POW); }
"+" { return symbol(sym.SUM); }
"-" { return symbol(sym.SUB); }
"/" { return symbol(sym.DIV); }
"//" { return symbol(sym.INT_DIV); }
"%" { return symbol(sym.MOD); }

// Operadores Incremento
"++" { return symbol(sym.INC); }
"--" { return symbol(sym.DEC); }

// Operadores Lógicos
"and" { return symbol(sym.AND); }
"not" { return symbol(sym.NOT); }
"or" { return symbol(sym.OR); }
"true" | "false" { return symbol(sym.BOOL); }

// Operadores Relacionales
"<" { return symbol(sym.LESS_THAN); }
">" { return symbol(sym.MORE_THAN); }
"<=" { return symbol(sym.LESS_OR_EQ); }
">=" { return symbol(sym.MORE_OR_EQ); }
"==" { return symbol(sym.EQUALS); }
"!>" { return symbol(sym.DIFFERENT_THAN); }

// Operadores Generales
"=" { return symbol(sym.ASSIGNMENT); }

// Tipos de dato
"int" | "double" | "string" | "char" | "bool" { return symbol(sym.DATA_TYPE, yytext()); }

// Estructuras de control
"for" { return symbol(sym.FOR); }
"while" { return symbol(sym.WHILE); }
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }

// Manejo de funciones
"fn" { return symbol(sym.FUNCTION); }
"return" { return symbol(sym.RETURN); }
"return" { return symbol(sym.RETURN); }

// Gestión de librerías
"import" { return symbol(sym.IMPORT); }


{Identifier} { return symbol(sym.IDENTIFIER, yytext()); }

{text} {return new Symbol(sym.STRING_LITERAL, yyline, yycolumn, yytext());}

// Comentarios
{Comment} { /* ignore */ }
// Espacios en blanco
{WhiteSpace} { /* ignore */ }

/* error fallback */
[^] { throw new Error("Illegal character <"+ yytext()+">"); }