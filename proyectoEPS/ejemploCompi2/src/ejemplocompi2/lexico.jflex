package ejemplo;
import java_cup.runtime.Symbol;
%%//Separador de area

%class AnalizadorLexico
%cup
%cupdebug
%line
%column

/*Identifiers*/
Numero = [0123456789]
WHITE_SPACE_CHAR=[\ \n\r\t\f]

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

%}
%%//Separdar de area

<YYINITIAL> {
	({Numero})+                                {return symbol(sym.NUMERO,Integer.parseInt(yytext()));}
        "+"                             {return symbol(sym.MAS,new String(yytext()));}
	"-"                                {return symbol(sym.MENOS);}
        "*"                             {return symbol(sym.POR);}
        "="                             {return symbol(sym.IGUAL);}
        "X"                             {return symbol(sym.LETRAX);}
        "Y"                             {return symbol(sym.LETRAY);}
        "Z"                             {return symbol(sym.LETRAZ);}
        ","                             {return symbol(sym.COMA);}   
        ";"                             {return symbol(sym.PUNTOYCOMA);}
        "/"                                {return symbol(sym.DIV);}
        .               {System.out.println("Error Lexico: "+yytext());}  
}