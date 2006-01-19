/*
                       DotParser

    Copyright (C) 2006  Jose San Leandro Armendáriz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Performs some unit tests on DotParser class.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *                  ("Name" means no concrete version has been checked out)
 *
 * $Id$
 *
 */
package org.acmsl.dotparser.antlr;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.dotparser.antlr.DotLexer;
import org.acmsl.dotparser.antlr.DotParser;

/*
 * Importing some ANTLR classes.
 */
import antlr.CommonAST;
import antlr.Token;
import antlr.TokenBuffer;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/*
 * Importing some JDK1.3 classes.
 */
import java.io.StringBufferInputStream;

/*
 * Importing some Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Performs some unit tests on DotParser class.
 * @testedclass org.acmsl.dotparser.antlr.DotParserParser
 * @see org.acmsl.dotparser.antlr.DotParserParser
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro Armendaáriz</a>
 * @version $Revision$
 */
public class DotParserTest
    extends  TestCase
{
    /**
     * The test input
     */
    public static final String TEST_INPUT =
          "digraph nlpMessagingGenerator {\n"
        + "    overlap=scale;\n"
        + "    splines=true;\n"
        + "    layers=\"process:errors\";\n"
        + "    rankdir=TD;\n"
        + "    concentrate=true;\n"
        + "        \n"
        + "    node [shape=box, style=\"rounded,filled\", layer=all, fillcolor=\"darkslategray3\"];\n"
        + "    edge [layer=all];\n"
        + "    \n"
        + "    start [label=\"\", shape=circle, fillcolor=black, fixedsize=true, width=0.2];\n"
        + "    \n"
        + "    readXml [label=\"Read XML\"];\n"
        + "    retrieveNextCommType [label=\"Retrieve next\nCommunication Type\"];\n"
        + "    checkCommType[label=\"Check whether\nCommunication Type\nexists\", fillcolor=\"orange\", shape=\"diamond\"];\n"
        + "    // beginTransaction[label=\"Begin transaction\", fillcolor=\"#D2D263\"];\n"
        + "    createCommType[label=\"Create Communication Type\"];\n"
        + "    createTemplate[label=\"Create Template\"];\n"
        + "    // commitTransaction[label=\"Commit transaction\", fillcolor=\"#D2D263\"];\n"
        + "    // rollbackTransaction[label=\"Rollback transaction\", fillcolor=\"#D2D263\"];\n"
        + "\n"
        + "    error [label=\"Error\", layer=\"error\", fillcolor=\"red\"];\n"
        + "\n"
        + "    end [label=\"\", shape=doublecircle, fillcolor=\"black\", fixedsize=true, width=0.2];\n"
        + "\n"
        + "    start -> readXml [label=\"0\"];\n"
        + "    readXml -> retrieveNextCommType [label=\"0\"];\n"
        + "    readXml -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    retrieveNextCommType -> checkCommType [label=\"0\", decorate=\"true\"];\n"
        + "    retrieveNextCommType -> end[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    retrieveNextCommType -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    checkCommType -> retrieveNextCommType[label=\"0\"];\n"
        + "    // checkCommType -> beginTransaction[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    checkCommType -> createCommType[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    checkCommType -> error[label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // beginTransaction -> createCommType[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    // beginTransaction -> error[label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createCommType -> createTemplate [label=\"0\"];\n"
        + "    // createCommType -> rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createCommType -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // createTemplate -> commitTransaction [label=\"0\"];\n"
        + "    createTemplate -> retrieveNextCommType [label=\"0\"];\n"
        + "    // createTemplate -> rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createTemplate -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // commitTransaction -> retrieveNextCommType [label=\"0\"];\n"
        + "    // commitTransaction -> rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // rollbackTransaction -> error [label=\"0\"];\n"
        + "    // rollbackTransaction -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    error -> end[label=\"0\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\", fontcolor=\"gray85\", fontcolor=\"gray85\"];\n"
        + "}\n";
    

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public DotParserTest(String name)
    {
        super(name);
    }

    /**
     * Tests the DotParser.
     */
    public void testParser()
    {
       try 
       {
           DotLexer t_DotLexer =
               new DotLexer(
                   new StringBufferInputStream(TEST_INPUT));

           assertNotNull(t_DotLexer);

           TokenBuffer t_TokenBuffer = new TokenBuffer(t_DotLexer);

           assertNotNull(t_TokenBuffer);

           DotParser t_DotParser = new DotParser(t_TokenBuffer);

           assertNotNull(t_DotParser);
        }
        catch  (final Throwable throwable)
        {
            LogFactory.getLog(getClass()).fatal(throwable);

            fail("" + throwable);
        }
    }
}
