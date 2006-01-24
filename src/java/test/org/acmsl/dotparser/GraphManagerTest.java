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
     * Tests <code>GraphManager.parse(InputStream)</code> method.
     * @see org.acmsl.dotparser.GraphManager#parse(java.io.InputStream)
     */
    public void testParse()
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
        }
        catch  (final Throwable throwable)
        {
            LogFactory.getLog(getClass()).fatal(throwable);

            fail("" + throwable);
        }
    }
}
