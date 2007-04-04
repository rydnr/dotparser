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
 * Description: Simple argument container, to be extended by GraphViz's Dot
 *              nodes and edges.
 *
 * $Id$
 */
package org.acmsl.dotparser;

/*
 * Importing some project classes.
 */
import org.acmsl.dotparser.ArgumentContainer;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple argument container, to be extended by
 * <a href="http://www.grapviz.org">GraphViz</a>'s
 * <a href="http://www.graphviz.org/doc/info/lang.html">Dot</a>
 * nodes and edges.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractArgumentContainer
    implements  ArgumentContainer
{
    /**
     * The argument names.
     */
    private List m__lArgNames;

    /**
     * The argument values.
     */
    private Map m__mArgValues;

    /**
     * Creates an <code>AbstractArgumentContainer</code> instance.
     */
    public AbstractArgumentContainer()
    {
        immutableSetArgNames(new ArrayList());
        immutableSetArgValues(new HashMap());
    }
    /**
     * Specifies the argument names.
     * @param names the names.
     */
    protected final void immutableSetArgNames(final List names)
    {
        m__lArgNames = names;
    }

    /**
     * Specifies the argument names.
     * @param names the names.
     */
    protected void setArgNames(final List names)
    {
        immutableSetArgNames(names);
    }

    /**
     * Retrieves the argument names.
     * @return such list.
     */
    protected final List immutableGetArgNames()
    {
        return m__lArgNames;
    }

    /**
     * Retrieves the argument names.
     * @return such list.
     */
    public List getArgNames()
    {
        return Collections.unmodifiableList(immutableGetArgNames());
    }

    /**
     * Specifies the argument values.
     * @param values the values.
     */
    protected final void immutableSetArgValues(final Map values)
    {
        m__mArgValues = values;
    }

    /**
     * Specifies the argument values.
     * @param values the values.
     */
    protected void setArgValues(final Map values)
    {
        immutableSetArgValues(values);
    }

    /**
     * Retrieves the argument values.
     * @return such values.
     */
    protected final Map immutableGetArgValues()
    {
        return m__mArgValues;
    }

    /**
     * Retrieves the argument values.
     * @return such map.
     */
    public Map getArgValues()
    {
        return Collections.unmodifiableMap(immutableGetArgValues());
    }

    /**
     * Adds a new argument.
     * @param name the argument name.
     * @param value the argument value.
     * @precondition name != null
     * @precondition value != null
     */
    public void add(final String name, final String value)
    {
        add(name, value, immutableGetArgNames(), immutableGetArgValues());
    }

    /**
     * Adds a new argument.
     * @param name the argument name.
     * @param value the argument value.
     * @param names the names of all arguments.
     * @param values the values of all arguments.
     * @precondition name != null
     * @precondition value != null
     * @precondition names != null
     * @precondition values != null
     */
    protected void add(
        final String name,
        final String value,
        final Collection names,
        final Map values)
    {
        if  (!names.contains(name))
        {
            names.add(name);
        }

        values.put(name, value);
    }
}
