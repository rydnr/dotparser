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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Models 
 * <a href="http://www.grapviz.org">GraphViz</a>'s
 * <a href="http://www.graphviz.org/doc/info/lang.html">Dot</a> graphs.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class Graph
{
    /**
     * Whether the graph is directed or not.
     */
    private boolean m__bDirected;

    /**
     * The graph name.
     */
    private String m__strName;

    /**
     * The nodes.
     */
    private List m__lNodes;

    /**
     * The edges.
     */
    private List m__lEdges;

    /**
     * Creates a <code>Graph</code> instance.
     * @param name the name.
     * @param directed whether the graph is directed.
     * @precondition name != null
     */
    public Graph(final String name, final boolean directed)
    {
        immutableSetName(name);
        immutableSetDirected(directed);
        immutableSetNodes(new ArrayList());
        immutableSetEdges(new ArrayList());
    }

    /**
     * Specifies whether the graph is directed or not.
     * @param directed such type.
     */
    protected final void immutableSetDirected(final boolean directed)
    {
        m__bDirected = directed;
    }

    /**
     * Specifies whether the graph is directed or not.
     * @param directed such type.
     */
    protected void setDirected(final boolean directed)
    {
        immutableSetDirected(directed);
    }

    /**
     * Retrieves whether the graph is directed or not.
     * @return such type.
     */
    public boolean getDirected()
    {
        return m__bDirected;
    }

    /**
     * Specifies the graph name.
     * @param name the name.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the graph name.
     * @param name the name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the graph name.
     * @return the name.
     */
    public String getName()
    {
        return m__strName;
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
     * Adds a new node.
     * @param node the node.
     * @precondition node != null
     */
    void add(final Node node)
    {
        add(node, immutableGetNodes());
    }

    /**
     * Adds a new node.
     * @param node the node.
     * @precondition node != null
     * @precondition nodes != null
     */
    protected void add(final Node node, final Collection nodes)
    {
        nodes.add(node);
    }

    /**
     * Adds a new edge.
     * @param edge the edge.
     * @precondition edge != null
     */
    void add(final Edge edge)
    {
        add(edge, immutableGetEdges());
    }

    /**
     * Adds a new edge.
     * @param edge the edge.
     * @param edges the collection.
     * @precondition edge != null
     * @precondition edges != null
     */
    protected void add(final Edge edge, final Collection edges)
    {
        edges.add(edge);
    }
}
