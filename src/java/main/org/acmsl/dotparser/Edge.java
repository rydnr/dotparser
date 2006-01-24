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
     * @precondition leftNode != null
     * @precondition rightNode != null
     */
    public Edge(final Node leftNode, final Node rightNode)
    {
        immutableSetLeftNode(leftNode);
        immutableSetRightNode(rightNode);
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
}
