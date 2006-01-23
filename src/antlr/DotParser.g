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
 * Description: Clean ANTLR-based parser to build AST according to
 *              GraphViz's Dot grammar.
 *
 * See http://www.graphviz.org/doc/info/lang.html
 *
 */
header
{
package org.acmsl.dotparser.antlr;
}
class DotParser extends Parser;

options
{
    importVocab=Dot;
    buildAST=true;
    defaultErrorHandler=true;
}

graph
    :  (STRICT_LITERAL)?
       (GRAPH_LITERAL | DIGRAPH_LITERAL) (ID)?
       O_BRACKET stmt_list C_BRACKET
    ;

stmt_list
    :  ( stmt SEMI_COLON stmt_list )?
    ;

stmt
    :  (  (ID) =>
            (  (EQUAL) => (ID EQUAL ID)
             | (  (edgeRHS) => edge_stmt
                | node_stmt
                ))
        | attr_stmt
        | subgraph
       )
    ;

attr_stmt
    :  (GRAPH_LITERAL | NODE_LITERAL | EDGE_LITERAL) attr_list
    ;

attr_list
    :  O_SQR_BRACKET (a_list)? C_SQR_BRACKET (attr_list)?
    ;

a_list
    :  ID (EQUAL ID)? (COMMA)? (a_list)?
    ;

edge_stmt
    :  (node_id | subgraph)? edgeRHS (attr_list)?
    ;

edgeRHS
    :  EDGEOP (node_id | subgraph)? (edgeRHS)?
    ;

node_stmt
    :  node_id (ATTR_LIST)?
    ;

node_id
    :  ID (port)?
    ;

port
    :  COLON 
        (  ID (COLON COMPASS_PT)?
         | COMPASS_PT
        )
    ;

subgraph
    :  (SUBGRAPH_LITERAL
           (  (ID) =>
                (  (O_BRACKET) => subgraph_ext
                | subgraph_simple)
            | subgraph_ext))
    ;

protected subgraph_ext
    :  (SUBGRAPH_LITERAL (ID)? )? O_BRACKET stmt_list C_BRACKET
    ;

protected subgraph_simple
    :  SUBGRAPH_LITERAL ID
    ;
