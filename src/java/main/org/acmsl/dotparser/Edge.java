//;-*- mode: java -*-
/*
                        DotParser

    Copyright (C) 2006-2007  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

    Thanks to ACM S.L. for distributing this library under the LGPL license.
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
 * Description: Models GraphViz's Dot edges.
 *
 * $Id$
 */
package org.acmsl.dotparser;

/*
 * Importing some project classes.
 */
import org.acmsl.dotparser.AbstractArgumentContainer;
import org.acmsl.dotparser.Node;

/**
 * Models
 * <a href="http://www.grapviz.org">GraphViz</a>'s
 * <a href="http://www.graphviz.org/doc/info/lang.html">Dot</a> edges.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class Edge
    extends  AbstractArgumentContainer
{
    /**
     * Whether the edge is directed or not.
     */
    private boolean m__bDirected;

    /**
     * The node on the left.
     */
    private Node m__LeftNode;

    /**
     * The node on the right.
     */
    private Node m__RightNode;

    /**
     * Creates a <code>Edge</code> instance.
     * @param leftNode the node on the left.
     * @param rightNode the node on the right.
     * @param directed whether the edge is directed or not.
     * @precondition leftNode != null
     * @precondition rightNode != null
     */
    public Edge(
        final Node leftNode, final Node rightNode, final boolean directed)
    {
        immutableSetLeftNode(leftNode);
        immutableSetRightNode(rightNode);
        immutableSetDirected(directed);
    }

    /**
     * Specifies the left node.
     * @param node the node.
     */
    protected final void immutableSetLeftNode(final Node node)
    {
        m__LeftNode = node;
    }

    /**
     * Specifies the left node.
     * @param node the node.
     */
    protected void setLeftNode(final Node node)
    {
        immutableSetLeftNode(node);
    }

    /**
     * Retrieves the left node.
     * @return such node.
     */
    public Node getLeftNode()
    {
        return m__LeftNode;
    }

    /**
     * Specifies the right node.
     * @param node the node.
     */
    protected final void immutableSetRightNode(final Node node)
    {
        m__RightNode = node;
    }

    /**
     * Specifies the right node.
     * @param node the node.
     */
    protected void setRightNode(final Node node)
    {
        immutableSetRightNode(node);
    }

    /**
     * Retrieves the right node.
     * @return such node.
     */
    public Node getRightNode()
    {
        return m__RightNode;
    }

    /**
     * Specifies if the node is directed.
     * @param flag such property.
     */
    protected final void immutableSetDirected(final boolean flag)
    {
        m__bDirected = flag;
    }

    /**
     * Specifies if the node is directed.
     * @param flag such property.
     */
    protected void setDirected(final boolean flag)
    {
        immutableSetDirected(flag);
    }

    /**
     * Retrieves whether the edge is directed.
     * @return such property.
     */
    public boolean getDirected()
    {
        return m__bDirected;
    }
}
