//;-*- mode: java -*-
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
 * Description: Facade to parse and manage contents of GraphViz's Dot
 *              documents.
 *
 */
package org.acmsl.dotparser;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.dotparser.antlr.DotLexer;
import org.acmsl.dotparser.antlr.DotParser;
import org.acmsl.dotparser.ArgumentContainer;

/*
 * Importing ANTLR classes.
 */
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Facade to parse and manage contents of
 * <a href="http://www.grapviz.org">GraphViz</a>'s
 * <a href="http://www.graphviz.org/doc/info/lang.html">Dot</a> documents.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class GraphManager
{
    /**
     * Identifies nodes.
     */
    public static final int NODE = 2;

    /**
     * Identifies edges.
     */
    public static final int EDGE = 3;

    /**
     * The Dot document.
     */
    private InputStream m__DotInputStream;

    /**
     * The graph.
     */
    private Graph m__Graph;

    /**
     * Creates a <code>GraphManager</code> instance.
     * @param dot the Dot document.
     * @precondition dot != null
     */
    public GraphManager(final InputStream dot)
    {
        immutableSetDotInputStream(dot);
    }

    /**
     * Specifies the Dot input stream.
     * @param input such stream.
     */
    protected final void immutableSetDotInputStream(final InputStream input)
    {
        m__DotInputStream = input;
    }

    /**
     * Specifies the Dot input stream.
     * @param input such stream.
     */
    protected void setDotInputStream(final InputStream input)
    {
        immutableSetDotInputStream(input);
    }

    /**
     * Retrieves the Dot input stream.
     * @return such stream.
     */
    public InputStream getDotInputStream()
    {
        return m__DotInputStream;
    }

    /**
     * Specifies the graph.
     * @param graph the graph.
     */
    protected final void immutableSetGraph(final Graph graph)
    {
        m__Graph = graph;
    }

    /**
     * Specifies the graph.
     * @param graph the graph.
     */
    protected void setGraph(final Graph graph)
    {
        immutableSetGraph(graph);
    }

    /**
     * Retrieves the graph.
     * @return such graph.
     */
    protected final Graph immutableGetGraph()
    {
        return m__Graph;
    }

    /**
     * Parses the Dot input stream.
     */
    public Graph getGraph()
    {
        Graph result = immutableGetGraph();

        if  (result == null)
        {
            result = buildGraph(getDotInputStream());

            setGraph(result);
        }

        return result;
    }

    /**
     * Parses a Dot input stream. The visibility and signature have been
     * choosen with tests in mind.
     * @param inputStream the input stream.
     * @return an array of lists, in which at least the first element is
     * the list of nodes, and the second the edges.
     * @precondition inputStream != null
     */
    Graph buildGraph(final InputStream inputStream)
    {
        Graph result = null;

        try
        {
            AST t_AST = buildAST(inputStream);

            result =
                new Graph(retrieveName(t_AST), retrieveDirected(t_AST));

            t_AST = t_AST.getFirstChild();

            int t_iType = -1;

            String t_strCurrentNodeName = null;
            Node t_CurrentNode = null;
            Edge t_CurrentEdge = null;
            Node t_LeftNode = null;
            Node t_RightNode = null;
            
            Map t_mDefinedNodes = new HashMap();

            while  (t_AST != null)
            {
                t_AST = t_AST.getNextSibling();

                t_iType = retrieveType(t_AST);

                switch  (t_iType)
                {
                    case  NODE:
                        t_strCurrentNodeName = t_AST.getText();
                        t_CurrentNode = new Node(t_strCurrentNodeName);
                        addArguments(t_AST, t_CurrentNode);
                        t_mDefinedNodes.put(
                            t_strCurrentNodeName, t_CurrentNode);
                        result.add(t_CurrentNode);
                        break;

                    case  EDGE:
                        t_LeftNode =
                            retrieveLeftNode(t_AST, t_mDefinedNodes);
                        t_RightNode =
                            retrieveRightNode(t_AST, t_mDefinedNodes);
                        t_CurrentEdge = new Edge(t_LeftNode, t_RightNode);
                        addArguments(t_AST, t_CurrentEdge);
                        result.add(t_CurrentEdge);
                        break;

                    default:
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            LogFactory.getLog(getClass()).fatal(
                "Invalid input", recognitionException);
        }
        catch  (final TokenStreamException tokenStreamException)
        {
            LogFactory.getLog(getClass()).fatal(
                "Invalid input", tokenStreamException);
        }

        return result;
    }

    /**
     * Builds the AST from given input stream.
     * @param input the input.
     * @return the AST.
     * @throws RecognitionException if the input is not valid according
     * to the lexical rules.
     * @throws TokenStreamException if the stream cannot be splitted in
     * tokens for any reason.
     * @precondition input != null
     */
    protected AST buildAST(final InputStream input)
        throws  RecognitionException,
                TokenStreamException
    {
        AST result = null;

        TokenStream t_DotLexer = new DotLexer(input);

        TokenBuffer t_TokenBuffer = new TokenBuffer(t_DotLexer);

        DotParser t_DotParser = new DotParser(t_TokenBuffer);

        t_DotParser.graph();

        result = t_DotParser.getAST();

        return result;
    }

    /**
     * Retrieves the name of the AST, that is, its first child.
     * @param ast the tree.
     * @return the node.
     * @precondition ast != null
     */
    protected String retrieveName(final AST ast)
    {
        String result = null;

        AST name = ast.getFirstChild();

        if  (name != null)
        {
            result = name.getText();
        }

        return result;
    }

    /**
     * Retrieves whether the graph is directed or not.
     * @param ast the tree.
     * @return the type of graph.
     * @precondition ast != null
     */
    protected boolean retrieveDirected(final AST ast)
    {
        return "digraph".equalsIgnoreCase(ast.getText());
    }

    /**
     * Retrieves the type of the root node of given AST.
     * @param ast the AST.
     * @return the type of the root node.
     */
    protected int retrieveType(final AST ast)
    {
        int result = -1;

        AST t_AST = ast;

        if  (t_AST != null)
        {
            t_AST = ast.getFirstChild();
        }

        if  (t_AST != null)
        {
            String t_strValue = t_AST.getText();

            if  (   (t_AST.getNextSibling() != null)
                 && (   ("--".equals(t_strValue))
                     || ("->".equals(t_strValue))))
            {
                result = EDGE;
            }
            else
            {
                while  (t_AST != null)
                {
                    if  (t_AST.getFirstChild() != null)
                    {
                        result = NODE;
                        break;
                    }

                    t_AST = t_AST.getNextSibling();
                }
            }
        }

        return result;
    }

    /**
     * Adds the arguments found on given AST.
     * @param ast the AST.
     * @param container the argument container.
     * @precondition ast != null
     * @precondition container != null
     */
    protected void addArguments(
        final AST ast, final ArgumentContainer container)
    {
        AST t_CurrentAST = ast.getFirstChild();

        if  (t_CurrentAST != null)
        {
            t_CurrentAST = t_CurrentAST.getNextSibling();
        }

        while  (t_CurrentAST != null)
        {
            container.add(t_CurrentAST.getText(), retrieveName(t_CurrentAST));

            t_CurrentAST = t_CurrentAST.getNextSibling();
        }
    }

    /**
     * Retrieves the left side of given edge.
     * @param ast the AST.
     * @param definedNodes the already defined nodes.
     * @return the left-side node.
     * @precondition ast != null
     */
    protected Node retrieveLeftNode(final AST ast, final Map definedNodes)
    {
        Node result = null;

        String t_strName = ast.getText();

        result = (Node) definedNodes.get(t_strName);

        if  (result == null)
        {
            result = new Node(t_strName);
        }

        return result;
    }

    /**
     * Retrieves the right side of given edge.
     * @param ast the AST.
     * @param definedNodes the already defined nodes.
     * @return the right-side node.
     * @precondition ast != null
     */
    protected Node retrieveRightNode(final AST ast, final Map definedNodes)
    {
        Node result = null;

        AST t_AST = ast.getFirstChild();

        if  (t_AST != null)
        {
            t_AST = t_AST.getNextSibling();
        }

        if  (t_AST != null)
        {
            String t_strName = t_AST.getText();

            result = (Node) definedNodes.get(t_strName);

            if  (result == null)
            {
                result = new Node(t_strName);
            }
        }

        return result;
    }
}
