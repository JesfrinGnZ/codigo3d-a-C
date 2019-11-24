//------------------>1era area<--------------------------

package gnz.backend.analizadores;
import java_cup.runtime.*;
import static gnz.backend.analizadores.sym.*;
%% //------------------>2da area<--------------------------

%public
%class AnalizadorLexicoCodigo
%cup
%cupdebug
%unicode
%line
%column

Salto = \r|\n|\r\n
Espacio = {Salto} | [ \t\f]
Identificador = [:jletter:] ( [:jletterdigit:] | [_] | [$] )*
Digitos = 0 | [1-9][0-9]*
%{



  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    System.out.println("Token:"+type+" Lexema:"+value);
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

 /*   public AnalizadorLexicoCodigo(java.io.Reader in) {
    this.zzReader = in;
  }*/

%}

%% //------------------>3er area<--------------------------
  <YYINITIAL>  {

   //**********************Fin de instruccion*******************************

    ";"         {return symbol(PCOMA,yytext());}

   //**********************Declaracion de variables*******************************

    "<-"        {return symbol(ASIGNACION,yytext());}
    ","         {return symbol(COMA,yytext());}

   //************************Tipos de datos**************************************

    "boolean"   {return symbol(BOOLEAN,yytext());}
    "TRUE"      {return symbol(TRUE,yytext());}
    "FALSE"     {return symbol(FALSE,yytext());}


    "char"      {return symbol(CHAR,yytext());}
    ["'"][:jletterdigit:]["'"]  {return symbol(DECLARACION_CARACTER,yytext());}


    "byte"      {return symbol(BYTE,yytext());}
    "int"       {return symbol(INT,yytext());}
    "long"      {return symbol(LONG,yytext());}
    "float"     {return symbol(FLOAT,yytext());}
    "double"    {return symbol(DOUBLE,yytext());}
    {Digitos}["."]{Digitos}["f"]   {return symbol(NUMERO_DECIMALF,yytext());}
    {Digitos} {return symbol(NUMERO_ENTERO,yytext());}
    {Digitos}["."]{Digitos} {return symbol(NUMERO_DECIMAL,yytext());}


    "string"    {return symbol(STRING,yytext());}
    "\""~"\"" {return symbol(DECLARACION_STRING,yytext());}

   //************************Operadores numericos**************************************

    "+"    {return symbol(MAS,yytext());}
    "*"    {return symbol(POR,yytext());}
    "-"    {return symbol(MENOS,yytext());}
    "/"    {return symbol(DIV,yytext());}
    "%"    {return symbol(PORCENTAJE,yytext());}

   //************************Operadores Relacionales**************************************

    "<"    {return symbol(MENOR,yytext());}
    ">"    {return symbol(MAYOR,yytext());}
    "<="    {return symbol(MENOR_IGUAL,yytext());}
    ">="    {return symbol(MAYOR_IGUAL,yytext());}
    "!="    {return symbol(DISTINTO_DE,yytext());}
    "=="    {return symbol(IGUAL_A,yytext());}

   //************************Operadores Booleanas**************************************

    "NOT"    {return symbol(NOT,yytext());}
    "AND"    {return symbol(AND,yytext());}
    "OR"    {return symbol(OR,yytext());}

   //************************Estructuras ()[]**************************************

    "("     {return symbol(PARENTESIS_ABIERTO,yytext());}
    ")"     {return symbol(PARENTESIS_CERRADO,yytext());}
    "["     {return symbol(CORCHETE_ABIERTO,yytext());}
    "]"     {return symbol(CORCHETE_CERRADO,yytext());}

   //************************Estructura IF**************************************

    "IF"    {return symbol(IF,yytext());}
    "ELSIF" {return symbol(ELSIF,yytext());}
    "ELSE"  {return symbol(ELSE,yytext());}

   //************************Estructura WHILE**************************************

    "WHILE" {return symbol(WHILE,yytext());}
    "DO"    {return symbol(DO,yytext());}

   //************************Estructura FOR**************************************

    "FOR"   {return symbol(FOR,yytext());}
    ":"     {return symbol(DOS_PUNTOS,yytext());}

   //************************Arreglos**************************************

    "ARRAY" {return symbol(ARRAY,yytext());}

   //************************Funciones predefinidas**************************************

    "PRINT" {return symbol(PRINT,yytext());}
    "PRINTLN"   {return symbol(PRINTLN,yytext());}
    "SCANS"   {return symbol(SCANS,yytext());}
    "SCANN"   {return symbol(SCANN,yytext());}

    //************************Funciones**************************************
    "void" {return symbol(VOID,yytext());}
    "return" {return symbol(RETURN,yytext());}
    "MAIN"  {return symbol(MAIN,yytext());}
   //************************Comentarios**************************************

    "--"~"\n"      {/*Se ignoran*/}
    "<!--"~"-->"   {/*Se ignoran*/}


   //**********************Identificador*******************************

    {Identificador}     {return symbol(IDENTIFICADOR,yytext());}

    {Espacio} 	{/*IGNORAMOS*/}
}

//<<EOF>>                 { return symbol(EOF);

[^]     {//ManejadorDeErrores.mostrarErrorLexico(this.editor.getErroresTextArea(), yytext(), yyline+1, yycolumn+1,manCuarteto);

        }
