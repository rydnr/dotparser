header
{
  package org.acmsl.dotparser.antlr;
}
{
//  import java.util.ArrayList;
}
class DotLexer extends Lexer;

options
{
  k=1;
  charVocabulary = '\3'..'\377';
  exportVocab=Dot;
}
{
//  Initialization logic
}

COMPASS_PT
    :  (  ("ne") => "ne"
        | ("nw") => "nw"
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

WS  :
       (   ' '
        |  '\t'
        |  '\r' '\n' { newline(); }
        |  '\n'      { newline(); }
       ) {$setType(Token.SKIP);} //ignore this token
    ;

