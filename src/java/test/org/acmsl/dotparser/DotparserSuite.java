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

******************************************************************************
*
* Filename: $RCSfile$
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.dotparser.
*
*/
package org.acmsl.dotparser;


/*
 * Importing project classes.
 */
import org.acmsl.dotparser.antlr.AntlrSuite;
import org.acmsl.dotparser.GraphManagerTest;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestSuite;

// JUnitDoclet begin import
// JUnitDoclet end import

/*
This file is part of  JUnitDoclet, a project to generate basic
test cases  from source code and  helping to keep them in sync
during refactoring.

Copyright (C) 2002-2005  ObjectFab GmbH  (http://www.objectfab.de/)

This library is  free software; you can redistribute it and/or
modify  it under the  terms of  the  GNU Lesser General Public
License as published  by the  Free Software Foundation; either
version 2.1  of the  License, or  (at your option)  any  later
version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You  should  have  received a  copy of the  GNU Lesser General
Public License along with this  library; if not, write  to the
Free  Software  Foundation, Inc.,  59 Temple Place,  Suite 330,
Boston, MA  02111-1307  USA
*/

/**
* Executes all tests defined for package
* org.acmsl.dotparser
* @see org.acmsl.dotparser
*/
public class DotparserSuite
// JUnitDoclet begin extends_implements
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  // JUnitDoclet end class
  
  public static TestSuite suite()
  {
    TestSuite suite;
    
    suite = new TestSuite("org.acmsl.dotparser");
    
    // JUnitDoclet begin method suite
    suite.addTestSuite(org.acmsl.dotparser.GraphManagerTest.class);
    suite.addTest(org.acmsl.dotparser.antlr.AntlrSuite.suite());
    // JUnitDoclet end method suite
    
    return suite;
  }
  
  public static void main(String[] args)
  {
    // JUnitDoclet begin method testsuite.main
    junit.textui.TestRunner.run(suite());
    // JUnitDoclet end method testsuite.main
  }
}
