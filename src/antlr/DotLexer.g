//;-*- mode: antlr -*-
/*
                        DotParser

    Copyright (C) 2006  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
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
import java.util.ArrayList;
import java.util.List;
}
class DotLexer extends Lexer;

options
{
    k=1;
    charVocabulary = '\3'..'\377';
    exportVocab=Dot;
}
{
  List nodes = new ArrayList();
  List links = new ArrayList();
}

COMPASS_PT
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

protected ID
    :  (  VALIDSTR
        | NUMBER
        | QUOTEDSTR
        | HTMLSTR
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
    :  '"'
       (  ("\\\"") => "\\\""
        | ~('"')
       )*
       '"'
    ;

protected HTMLSTR
    :  '<' (~'>')* '>'
    ;

protected EDGEOP
    :  (  ("->") => "->"
        | ("--") => "--"
       )
    ;

WS
    :
       (   ' '
        |  '\t'
        |  '\r' '\n' { newline(); }
        |  '\n'      { newline(); }
       ) {$setType(Token.SKIP);} //ignore this token
    ;

protected O_BRACKET : '{';
protected C_BRACKET : '}';
protected O_SQR_BRACKET : '[';
protected C_SQR_BRACKET : ']';
protected SEMI_COLON : ';';
protected EQUAL : '=';
protected COMMA : ',';
protected COLON : ':';
protected STRICT_LITERAL : "strict";
protected GRAPH_LITERAL : "graph";
protected NODE_LITERAL : "node";
protected EDGE_LITERAL : "edge";
protected DIGRAPH_LITERAL : "digraph";
