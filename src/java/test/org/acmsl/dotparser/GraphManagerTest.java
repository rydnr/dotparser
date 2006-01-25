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
 * Description: Performs some unit tests on GraphManager class.
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
package org.acmsl.dotparser;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.dotparser.Graph;
import org.acmsl.dotparser.GraphManager;
import org.acmsl.dotparser.antlr.DotParserTest;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/*
 * Importing some JDK classes.
 */
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;
import java.util.Map;

/*
 * Importing some Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Performs some unit tests on GraphManager class.
 * @testedclass org.acmsl.dotparser.GraphManager
 * @see org.acmsl.dotparser.GraphManager
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro Armendaáriz</a>
 * @version $Revision$
 */
public class GraphManagerTest
    extends  TestCase
{
    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public GraphManagerTest(final String name)
    {
        super(name);
    }

    /**
     * Tests <code>GraphManager.getGraph()</code> method, with the
     * input defined in <code>DotParserTest.TEST_INPUT_1</code>.
     * @see org.acmsl.dotparser.GraphManager#getGraph()
     * @see org.acmsl.dotparser.antlr.DotParserTest#TEST_INPUT_1
     */
    public void testParse1()
    {
       try 
       {
           InputStream t_isDotInput =
               new StringBufferInputStream(DotParserTest.TEST_INPUT_1);

           GraphManager t_GraphManager = new GraphManager(t_isDotInput);

           assertNotNull(t_GraphManager);

           Graph t_Graph = t_GraphManager.getGraph();

           assertNotNull(t_Graph);

           assertEquals("nlpMessagingGenerator", t_Graph.getName());
           assertTrue(t_Graph.getDirected() == false);

           List t_lNodes = t_Graph.getNodes();

           assertNotNull(t_lNodes);

           Node[] t_aNodes = (Node[]) t_lNodes.toArray(new Node[0]);

           assertTrue(t_aNodes.length == 8);
           int t_iIndex = 0;

           testNode(t_aNodes[t_iIndex], "start");
           testArg(t_aNodes[t_iIndex], "label", "");
           testArg(t_aNodes[t_iIndex], "shape", "circle");
           testArg(t_aNodes[t_iIndex], "fillcolor", "black");
           testArg(t_aNodes[t_iIndex], "fixedsize", "true");
           testArg(t_aNodes[t_iIndex++], "width", "0.2");

           testNode(t_aNodes[t_iIndex], "readXml");
           testArg(t_aNodes[t_iIndex++], "label", "Read XML");

           testNode(t_aNodes[t_iIndex], "retrieveNextCommType");
           testArg(
               t_aNodes[t_iIndex++],
               "label",
               "Retrieve next\nCommunication Type");

           testNode(t_aNodes[t_iIndex], "checkCommType");
           testArg(
               t_aNodes[t_iIndex++],
               "label",
               "Check whether\nCommunication Type\nexists");

           testNode(t_aNodes[t_iIndex], "createCommType");
           testArg(
               t_aNodes[t_iIndex++], "label", "Create Communication Type");

           testNode(t_aNodes[t_iIndex], "createTemplate");
           testArg(
               t_aNodes[t_iIndex++], "label", "Create Template");

           testNode(t_aNodes[t_iIndex], "error");
           testArg(t_aNodes[t_iIndex], "label", "Error");
           testArg(t_aNodes[t_iIndex], "layer", "error");
           testArg(t_aNodes[t_iIndex++], "fillcolor", "red");

           testNode(t_aNodes[t_iIndex], "end");
           testArg(t_aNodes[t_iIndex], "label", "");
           testArg(t_aNodes[t_iIndex], "shape", "doublecircle");
           testArg(t_aNodes[t_iIndex], "fillcolor", "black");
           testArg(t_aNodes[t_iIndex], "fixedsize", "true");
           testArg(t_aNodes[t_iIndex++], "width", "0.2");

           List t_lEdges = t_Graph.getEdges();
           Edge[] t_aEdges = (Edge[]) t_lEdges.toArray(new Edge[0]);

           assertTrue(t_aEdges.length == 14);
           t_iIndex = 0;
           Node t_LeftNode = null;
           Node t_RightNode = null;

           testEdge(t_aEdges[t_iIndex], t_aNodes[0], t_aNodes[1], false);
           testArg(t_aEdges[t_iIndex++], "label", "0");

           testEdge(t_aEdges[t_iIndex], t_aNodes[1], t_aNodes[2], false);
           testArg(t_aEdges[t_iIndex++], "label", "0");

           testEdge(t_aEdges[t_iIndex], t_aNodes[1], t_aNodes[6], false);
           testArg(t_aEdges[t_iIndex], "label", "-1");
           testArg(t_aEdges[t_iIndex], "layer", "error");
           testArg(t_aEdges[t_iIndex], "color", "gray85");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "gray85");

           testEdge(t_aEdges[t_iIndex], t_aNodes[2], t_aNodes[3], false);
           testArg(t_aEdges[t_iIndex], "label", "0");
           testArg(t_aEdges[t_iIndex++], "decorate", "true");

           testEdge(t_aEdges[t_iIndex], t_aNodes[2], t_aNodes[7], false);
           testArg(t_aEdges[t_iIndex], "label", "1");
           testArg(t_aEdges[t_iIndex], "color", "darkgoldenrod4");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "darkgoldenrod4");

           testEdge(t_aEdges[t_iIndex], t_aNodes[2], t_aNodes[6], false);
           testArg(t_aEdges[t_iIndex], "label", "-1");
           testArg(t_aEdges[t_iIndex], "layer", "error");
           testArg(t_aEdges[t_iIndex], "color", "gray85");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "gray85");

           testEdge(t_aEdges[t_iIndex], t_aNodes[3], t_aNodes[2], false);
           testArg(t_aEdges[t_iIndex++], "label", "0");

           testEdge(t_aEdges[t_iIndex], t_aNodes[3], t_aNodes[4], false);
           testArg(t_aEdges[t_iIndex], "label", "1");
           testArg(t_aEdges[t_iIndex], "color", "darkgoldenrod4");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "darkgoldenrod4");

           testEdge(t_aEdges[t_iIndex], t_aNodes[3], t_aNodes[6], false);
           testArg(t_aEdges[t_iIndex], "label", "-1");
           testArg(t_aEdges[t_iIndex], "layer", "error");
           testArg(t_aEdges[t_iIndex], "color", "gray85");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "gray85");

           testEdge(t_aEdges[t_iIndex], t_aNodes[4], t_aNodes[5], false);
           testArg(t_aEdges[t_iIndex++], "label", "0");

           testEdge(t_aEdges[t_iIndex], t_aNodes[4], t_aNodes[6], false);
           testArg(t_aEdges[t_iIndex], "label", "-1");
           testArg(t_aEdges[t_iIndex], "layer", "error");
           testArg(t_aEdges[t_iIndex], "color", "gray85");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "gray85");

           testEdge(t_aEdges[t_iIndex], t_aNodes[5], t_aNodes[2], false);
           testArg(t_aEdges[t_iIndex++], "label", "0");

           testEdge(t_aEdges[t_iIndex], t_aNodes[5], t_aNodes[6], false);
           testArg(t_aEdges[t_iIndex], "label", "-1");
           testArg(t_aEdges[t_iIndex], "layer", "error");
           testArg(t_aEdges[t_iIndex], "color", "gray85");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "gray85");

           testEdge(t_aEdges[t_iIndex], t_aNodes[6], t_aNodes[7], false);
           testArg(t_aEdges[t_iIndex], "label", "0");
           testArg(t_aEdges[t_iIndex], "layer", "error");
           testArg(t_aEdges[t_iIndex], "color", "gray85");
           testArg(t_aEdges[t_iIndex++], "fontcolor", "gray85");
        }
        catch  (final Throwable throwable)
        {
            throwable.printStackTrace(System.err);

            LogFactory.getLog(getClass()).fatal(throwable);

            fail("" + throwable);
        }
    }

    /**
     * Tests <code>GraphManager.getGraph()</code> method, with the
     * input defined in <code>DotParserTest.TEST_INPUT_2</code>.
     * @see org.acmsl.dotparser.GraphManager#getGraph()
     * @see org.acmsl.dotparser.antlr.DotParserTest#TEST_INPUT_2
     */
    public void testParse2()
    {
    }

    /**
     * Tests given node.
     * @param node the node.
     * @param name the name.
     * @precondition name != null
     */
    protected void testNode(final Node node, final String name)
    {
        assertNotNull(node);
        assertEquals(name, node.getName());
    }

    /**
     * Tests given argument.
     * @param container the argument container.
     * @param name the argument's name.
     * @param value the argument's value.
     */
    protected void testArg(
        final ArgumentContainer container,
        final String name,
        final String value)
    {
        assertNotNull(container);
        List t_lArgNames = container.getArgNames();
        assertNotNull(t_lArgNames);
        assertTrue(t_lArgNames.contains(name));
        Map t_mArgValues = container.getArgValues();
        assertNotNull(t_mArgValues);
        assertTrue(t_mArgValues.containsKey(name));
        Object t_Value = t_mArgValues.get(name);
        assertNotNull(t_Value);
        assertEquals(value, t_Value);
    }

    /**
     * Tests given edge.
     * @param edge the edge.
     * @param leftNode the left node.
     * @param rightNode the node on the right.
     * @param directed whether the edge is directed or not.
     */
    protected void testEdge(
        final Edge edge,
        final Node leftNode,
        final Node rightNode,
        final boolean directed)
    {
        assertNotNull(edge);
        Node t_LeftNode = edge.getLeftNode();
        assertNotNull(t_LeftNode);
        assertEquals(leftNode, t_LeftNode);
        Node t_RightNode = edge.getRightNode();
        assertNotNull(t_RightNode);
        assertEquals(rightNode, t_RightNode);
        assertEquals(directed, edge.getDirected());
    }
}
