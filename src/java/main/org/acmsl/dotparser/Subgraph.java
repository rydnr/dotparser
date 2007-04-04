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
 * Description: Represents Graphviz's subgraph elements.
 *
 */
package org.acmsl.dotparser;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.dotparser.AbstractGraph;

/**
 * Models 
 * <a href="http://www.grapviz.org">GraphViz</a>'s
 * <i>subgraph</i> elements.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class Subgraph
    extends  AbstractGraph
{
    /**
     * Creates a <code>Subgraph</code> instance.
     * @param name the name.
     * @param directed whether the graph is directed.
     * @precondition name != null
     */
    public Subgraph(final String name)
    {
        super(name);
    }
}
