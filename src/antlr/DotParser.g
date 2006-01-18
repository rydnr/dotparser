header
{
package org.acmsl.dotparser.antlr;
}

class DotParser extends Parser;

options
{
    importVocab=Dot;
    buildAST=true;
    defaultErrorHandler=false;
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
