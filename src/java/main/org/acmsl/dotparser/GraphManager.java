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
     * An empty cached List array.
     */
    public static final List[] EMPTY_LIST_ARRAY = new List[0];

    /**
     * The Dot document.
     */
    private InputStream m__DotInputStream;

    /**
     * The nodes.
     */
    private List m__lNodes;

    /**
     * The edges.
     */
    private List m__lEdges;

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
     * Specifies the nodes.
     * @param nodes the nodes.
     */
    protected final void immutableSetNodes(final List nodes)
    {
        m__lNodes = nodes;
    }

    /**
     * Specifies the nodes.
     * @param nodes the nodes.
     */
    protected void setNodes(final List nodes)
    {
        immutableSetNodes(nodes);
    }

    /**
     * Retrieves the nodes.
     * @return such list.
     */
    protected final List immutableGetNodes()
    {
        return m__lNodes;
    }

    /**
     * Retrieves the nodes.
     * @return such list.
     */
    public List getNodes()
    {
        return Collections.unmodifiableList(immutableGetNodes());
    }

    /**
     * Specifies the edges.
     * @param edges the edges.
     */
    protected final void immutableSetEdges(final List edges)
    {
        m__lEdges = edges;
    }

    /**
     * Specifies the edges.
     * @param edges the edges.
     */
    protected void setEdges(final List edges)
    {
        immutableSetEdges(edges);
    }

    /**
     * Retrieves the edges.
     * @return such list.
     */
    protected final List immutableGetEdges()
    {
        return m__lEdges;
    }

    /**
     * Retrieves the edges.
     * @return such list.
     */
    public List getEdges()
    {
        return Collections.unmodifiableList(immutableGetEdges());
    }

    /**
     * Parses the Dot input stream.
     */
    protected void parse()
    {
        List[] t_aLists = parse(getDotInputStream());

        if  (   (t_aLists != null)
             && (t_aLists.length >= 2))
        {
            setNodes(t_aLists[0]);
            setEdges(t_aLists[1]);
        }
    }

    /**
     * Parses a Dot input stream. The visibility and signature have been
     * choosen with tests in mind.
     * @param inputStream the input stream.
     * @return an array of lists, in which at least the first element is
     * the list of nodes, and the second the edges.
     * @precondition inputStream != null
     */
    List[] parse(final InputStream inputStream)
    {
        List[] result = EMPTY_LIST_ARRAY;

        List t_aNodes = new ArrayList();
        List a_aEdges = new ArrayList();

        try
        {
            AST t_AST = buildAST(inputStream);

            System.out.println(t_AST.toStringTree());
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
}
