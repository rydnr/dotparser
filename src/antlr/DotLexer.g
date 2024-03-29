//;-*- mode: antlr -*-
/*
                        DotParser

    Copyright (C) 2006-2007  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

    Thanks to ACM S.L. for distributing this library under the LGPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Clean ANTLR-based lexer according to GraphViz's Dot grammar.
 *
 * See http://www.graphviz.org/doc/info/lang.html
 *
 */
header
{
  package org.acmsl.dotparser.antlr;
}
{
}
class DotLexer extends Lexer;

options
{
    k=1;
    charVocabulary = '\3'..'\377';
    exportVocab=Dot;
}
{
}

LITERALS
    :  (  ("graph") => GRAPH_LITERAL {$setType(GRAPH_LITERAL);}
        | ("digraph") => DIGRAPH_LITERAL {$setType(DIGRAPH_LITERAL);}
        | ("strict") => STRICT_LITERAL {$setType(STRICT_LITERAL);}
        | ("node") => NODE_LITERAL {$setType(NODE_LITERAL);}
        | ("edge") => EDGE_LITERAL {$setType(EDGE_LITERAL);}
        | ("subgraph") => SUBGRAPH_LITERAL {$setType(SUBGRAPH_LITERAL);}
        | ("--") => EDGEOP_LITERAL {$setType(EDGEOP_LITERAL);}
        | ("->") => EDGEOP_LITERAL {$setType(EDGEOP_LITERAL);}
        | ("{") => O_BRACKET {$setType(O_BRACKET);}
        | ("}") => C_BRACKET {$setType(C_BRACKET);}
        | ("[") => O_SQR_BRACKET {$setType(O_SQR_BRACKET);}
        | ("]") => C_SQR_BRACKET {$setType(C_SQR_BRACKET);}
        | (";") => SEMI_COLON {$setType(SEMI_COLON);}
        | ("=") => EQUAL {$setType(EQUAL);}
        | (",") => COMMA {$setType(COMMA);}
        | (":") => COLON {$setType(COLON);}
        | ID {$setType(ID);})
    ;

protected GRAPH_LITERAL : "graph";
protected DIGRAPH_LITERAL : "digraph";
protected STRICT_LITERAL : "strict";
protected NODE_LITERAL : "node";
protected EDGE_LITERAL : "edge";
protected SUBGRAPH_LITERAL : "subgraph";

protected O_BRACKET : '{';
protected C_BRACKET : '}';
protected O_SQR_BRACKET : '[';
protected C_SQR_BRACKET : ']';
protected SEMI_COLON : ';';
protected EQUAL : '=';
protected COMMA : ',';
protected COLON : ':';

protected EDGEOP_LITERAL
    :  (  ("->") => "->"
        | ("--") => "--"
       )
    ;


protected ID
    :  (  VALIDSTR
        | NUMBER
        | QUOTEDSTR
        | HTMLSTR
       );

protected COMPASS_PT
    :  (  ("ne") => "ne"
        | ("nw") => "nw"
        | ("node") => NODE_LITERAL
        | "n"
        | "e"
        | ("se") => "se"
        | ("sw") => "sw"
        | "s"
        | "w"
       );

protected ALPHACHAR
    :  (   'a'..'z'
        |  'A'..'Z'
        |  '_'
       );

protected VALIDSTR
    :  ALPHACHAR
        (  ALPHACHAR
         |  '0'..'9'
        )*
    ;

protected NUMBER
    :  ('-')? ('0'..'9')+ ('.' ('0'..'9')+)?
    ;

protected QUOTEDSTR
    :  '"'!
       (  ("\\\"") => "\\\""
        | ~('"')
       )*
       '"'!
    ;

protected HTMLSTR
    :  '<' (~'>')* '>'
    ;

WS
    :
       (   ' '
        |  '\t'
        |  '\r' '\n' { newline(); }
        |  '\n'      { newline(); }
       ) {$setType(Token.SKIP);} //ignore this token
    ;

// Single-line comments
COMMENT
    :  (  ("/*") => ML_COMMENT
        | ("//") => SL_COMMENT)
       {$setType(Token.SKIP); newline();}
    ;

// Taken from ANTLR's Java grammar.
// Single-line comments
protected SL_COMMENT
    :  "//"
       (~('\n'|'\r'))* ('\n'|'\r'('\n')?)
       {$setType(Token.SKIP); newline();}
    ;

// multiple-line comments
protected ML_COMMENT
    :  "/*"
       (   /*
              '\r' '\n' can be matched in one alternative or by matching
              '\r' in one iteration and '\n' in another.  I am trying to
              handle any flavor of newline that comes in, but the language
              that allows both "\r\n" and "\r" and "\n" to all be valid
              newline is ambiguous.  Consequently, the resulting grammar
              must be ambiguous.  I'm shutting this warning off.
            */
            options
            {
                generateAmbigWarnings=false;
            }
	:
               {LA(2)!='/'}? '*'
             | '\r' '\n' {newline();}
             | '\r' {newline();}
             | '\n' {newline();}
             | ~('*'|'\n'|'\r')
       )*
       "*/"
       {$setType(Token.SKIP);}
    ;
