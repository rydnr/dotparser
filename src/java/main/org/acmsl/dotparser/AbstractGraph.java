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
import org.acmsl.dotparser.AbstractArgumentContainer;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Provides the commonalities between
 * <a href="http://www.grapviz.org">GraphViz</a>'s
 * graphs and subgraphs.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class AbstractGraph
    extends  AbstractArgumentContainer
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The nodes.
     */
    private List m__lNodes;

    /**
     * Creates an <code>AbstractGraph</code> instance.
     * @param name the name.
     * @precondition name != null
     */
    protected AbstractGraph(final String name)
    {
        immutableSetName(name);
        immutableSetNodes(new ArrayList());
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
}
