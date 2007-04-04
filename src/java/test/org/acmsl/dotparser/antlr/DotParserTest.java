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

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Performs some unit tests on DotParser class.
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
package org.acmsl.dotparser.antlr;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.dotparser.antlr.DotLexer;
import org.acmsl.dotparser.antlr.DotParser;

/*
 * Importing some ANTLR classes.
 */
import antlr.CommonAST;
import antlr.Token;
import antlr.TokenBuffer;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/*
 * Importing some JDK1.3 classes.
 */
import java.io.StringBufferInputStream;

/*
 * Importing some Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Performs some unit tests on DotParser class.
 * @testedclass org.acmsl.dotparser.antlr.DotParser
 * @see org.acmsl.dotparser.antlr.DotParser
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro Armendaáriz</a>
 * @version $Revision$
 */
public class DotParserTest
    extends  TestCase
{
    /**
     * The first test input.
     */
    public static final String TEST_INPUT_1 =
          "graph nlpMessagingGenerator {\n"
        + "    start [label=\"\", shape=circle, fillcolor=black, fixedsize=true, width=0.2];\n\n"
        + "    readXml [label=\"Read XML\"];\n"
        + "    retrieveNextCommType [label=\"Retrieve next\nCommunication Type\"];\n"
        + "    checkCommType[label=\"Check whether\nCommunication Type\nexists\", fillcolor=\"orange\", shape=\"diamond\"];\n"
        + "    // beginTransaction[label=\"Begin transaction\", fillcolor=\"#D2D263\"];\n"
        + "    createCommType[label=\"Create Communication Type\"];\n"
        + "    createTemplate[label=\"Create Template\"];\n"
        + "    // commitTransaction[label=\"Commit transaction\", fillcolor=\"#D2D263\"];\n"
        + "    // rollbackTransaction[label=\"Rollback transaction\", fillcolor=\"#D2D263\"];\n"
        + "    error [label=\"Error\", layer=\"error\", fillcolor=\"red\"];\n"
        + "    end [label=\"\", shape=doublecircle, fillcolor=\"black\", fixedsize=true, width=0.2];\n"
        + "    start -- readXml [label=\"0\"];\n"
        + "    readXml -- retrieveNextCommType [label=\"0\"];\n"
        + "    readXml -- error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    retrieveNextCommType -- checkCommType [label=\"0\", decorate=\"true\"];\n"
        + "    retrieveNextCommType -- end[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    retrieveNextCommType -- error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    checkCommType -- retrieveNextCommType[label=\"0\"];\n"
        + "    // checkCommType -- beginTransaction[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    checkCommType -- createCommType[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    checkCommType -- error[label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // beginTransaction -- createCommType[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    // beginTransaction -- error[label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createCommType -- createTemplate [label=\"0\"];\n"
        + "    // createCommType -- rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createCommType -- error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // createTemplate -- commitTransaction [label=\"0\"];\n"
        + "    createTemplate -- retrieveNextCommType [label=\"0\"];\n"
        + "    // createTemplate -- rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createTemplate -- error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // commitTransaction -- retrieveNextCommType [label=\"0\"];\n"
        + "    // commitTransaction -- rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // rollbackTransaction -- error [label=\"0\"];\n"
        + "    // rollbackTransaction -- error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    error -- end[label=\"0\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\", fontcolor=\"gray85\", fontcolor=\"gray85\"];\n"
        + "}\n";
    
    /**
     * The second test input
     */
    public static final String TEST_INPUT_2 =
          "digraph nlpMessagingGenerator {\n"
        + "    overlap=scale;\n"
        + "    splines=true;\n"
        + "    layers=\"process:errors\";\n"
        + "    rankdir=TD;\n"
        + "    concentrate=true;\n"
        + "    node [shape=box, style=\"rounded,filled\", layer=all, fillcolor=\"darkslategray3\"];\n"
        + "    edge [layer=all];\n"
        + "    start [label=\"\", shape=circle, fillcolor=black, fixedsize=true, width=0.2];\n\n"
        + "    readXml [label=\"Read XML\"];\n"
        + "    retrieveNextCommType [label=\"Retrieve next\nCommunication Type\"];\n"
        + "    checkCommType[label=\"Check whether\nCommunication Type\nexists\", fillcolor=\"orange\", shape=\"diamond\"];\n"
        + "    // beginTransaction[label=\"Begin transaction\", fillcolor=\"#D2D263\"];\n"
        + "    createCommType[label=\"Create Communication Type\"];\n"
        + "    createTemplate[label=\"Create Template\"];\n"
        + "    // commitTransaction[label=\"Commit transaction\", fillcolor=\"#D2D263\"];\n"
        + "    // rollbackTransaction[label=\"Rollback transaction\", fillcolor=\"#D2D263\"];\n"
        + "    error [label=\"Error\", layer=\"error\", fillcolor=\"red\"];\n"
        + "    end [label=\"\", shape=doublecircle, fillcolor=\"black\", fixedsize=true, width=0.2];\n"
        + "    start -> readXml [label=\"0\"];\n"
        + "    readXml -> retrieveNextCommType [label=\"0\"];\n"
        + "    readXml -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    retrieveNextCommType -> checkCommType [label=\"0\", decorate=\"true\"];\n"
        + "    retrieveNextCommType -> end[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    retrieveNextCommType -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    checkCommType -> retrieveNextCommType[label=\"0\"];\n"
        + "    // checkCommType -> beginTransaction[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    checkCommType -> createCommType[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    checkCommType -> error[label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // beginTransaction -> createCommType[label=\"1\", color=\"darkgoldenrod4\", fontcolor=\"darkgoldenrod4\"];\n"
        + "    // beginTransaction -> error[label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createCommType -> createTemplate [label=\"0\"];\n"
        + "    // createCommType -> rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createCommType -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // createTemplate -> commitTransaction [label=\"0\"];\n"
        + "    createTemplate -> retrieveNextCommType [label=\"0\"];\n"
        + "    // createTemplate -> rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    createTemplate -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // commitTransaction -> retrieveNextCommType [label=\"0\"];\n"
        + "    // commitTransaction -> rollbackTransaction [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    // rollbackTransaction -> error [label=\"0\"];\n"
        + "    // rollbackTransaction -> error [label=\"-1\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\"];\n"
        + "    error -> end[label=\"0\", layer=\"error\", color=\"gray85\", fontcolor=\"gray85\", fontcolor=\"gray85\", fontcolor=\"gray85\"];\n"
        + "}\n";

    /**
     * Another test.
     */
    public static final String TEST_INPUT_3 =
          "digraph NlpProcessGenerator {\n"
        + "    rankdir=TD;\n"
        + "\n"
        + "    node [shape=\"box\", style=\"rounded,filled\", fillcolor=\"darkslategray3\"];\n"
        + "    \n"
        + "    start [label=\"\", shape=\"circle\", fillcolor=\"black\", fixedsize=\"true\", width=\"0.2\"];\n"
        + "    \n"
        + "    readDot [label=\"Read dot file\"];\n"
        + "    validateDot [label=\"Validate dot file\"];\n"
        + "    retrieveProcessMetadata [label=\"Retrieve process metadata\"];\n"
        + "    checkStateList [label=\"Any state left?\", shape=\"diamond\", fillcolor=\"darkorange\"];\n"
        + "    retrieveNextState [label=\"Retrieve next state\"];\n"
        + "    createStateCommand [label=\"Create state command\"];\n"
        + "    createStateAction [label=\"Create state action\"];\n"
        + "    createProcessCommand [label=\"Create process command\"];\n"
        + "    createProcessAction [label=\"Create abstract process action\"];\n"
        + "    createProcessApi [label=\"Create process API\"];\n"
        + "    readProcessWorkflowXml [label=\"Read process-workflow.xml\"];\n"
        + "    updateProcessWorkflowXml [label=\"Update process-workflow.xml\"];\n"
        + "    writeProcessWorkflowXml [label=\"Write process-workflow.xml\"];\n"
        + "    error [label=\"Error\", fillcolor=\"red\"];\n"
        + "\n"
        + "    end [label=\"\", shape=\"doublecircle\", fillcolor=\"black\", fixedsize=\"true\", width=\"0.2\"];\n"
        + "\n"
        + "    start -> readDot;\n"
        + "    readDot -> validateDot;\n"
        + "    validateDot -> retrieveProcessMetadata;\n"
        + "    retrieveProcessMetadata -> checkStateList;\n"
        + "    checkStateList -> createProcessCommand [label=\"stateList.size() == currentNodeIndex - 1\"];\n"
        + "    checkStateList -> retrieveNextState [color=\"darkkhaki\"];\n"
        + "    retrieveNextState -> createStateCommand;\n"
        + "    createStateCommand -> createStateAction;\n"
        + "    createStateAction -> retrieveNextState;\n"
        + "    createProcessCommand -> createProcessAction;\n"
        + "    createProcessAction -> createProcessApi;\n"
        + "    createProcessApi -> readProcessWorkflowXml;\n"
        + "    readProcessWorkflowXml -> updateProcessWorkflowXml;\n"
        + "    updateProcessWorkflowXml -> writeProcessWorkflowXml;\n"
        + "    writeProcessWorkflowXml -> end;\n"
        + "    error -> end;\n"
        + "\n"
        + "    subgraph clusterProcess {\n"
        + "        style=\"filled\";\n"
        + "        color=\"lightsteelblue\";\n"
        + "        fillcolor=\"deeppink4\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"NLP-Process-Generator\";\n"
        + "        node [shape=\"record\", style=\"filled\", layer=all, fillcolor=\"azure\", fontcolor=\"black\"];\n"
        + "        processMetadata [label=\"{svn+ssh://svntest/var/svn/repos/nlp-process-generator/trunk|com.ventura24.nlp.processgenerator}\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterReadDotParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        readDotParamClasses [label=\"{ java.io.File | org.acmsl.dotparser.Graph }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        readDotParamNames [label=\"{ dot | graph }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        readDotParamTypes [label=\"{ IN | OUT }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterValidateDotParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        validateDotParamClasses [label=\"{ org.acmsl.dotparser.Graph }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        validateDotParamNames [label=\"{ graph }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        validateDotParamTypes [label=\"{ IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterRetrieveProcessMetadataParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        retrieveProcessMetadataParamClasses [label=\"{ org.acmsl.dotparser.Graph | java.util.List | org.acmsl.dotparser.Node }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        retrieveProcessMetadataParamNames [label=\"{ graph | stateList | processMetadata}\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        retrieveProcessMetadataParamTypes [label=\"{ IN | OUT | OUT }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterCheckStateListParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        checkStateListParamClasses [label=\"{ java.util.List | int }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        checkStateListParamNames [label=\"{ stateList | currentNodeIndex }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        checkStateListParamTypes [label=\"{ IN | IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterRetrieveNextStateParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        retrieveNextStateParamClasses [label=\"{ java.util.List | org.acmsl.dotparser.Node | int }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        retrieveNextStateParamNames [label=\"{ stateList | currentNode | currentNodeIndex }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        retrieveNextStateParamTypes [label=\"{ IN | OUT | IN/OUT }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterCreateStateCommandParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        createStateCommandParamClasses [label=\"{ org.acmsl.dotparser.Node }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        createStateCommandParamNames [label=\"{ currentNode }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        createStateCommandParamTypes [label=\"{ IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterCreateStateActionParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        createStateActionParamClasses [label=\"{ org.acmsl.dotparser.Node }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        createStateActionParamNames [label=\"{ currentNode }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        createStateActionParamTypes [label=\"{ IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterCreateProcessCommandParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        createProcessCommandParamClasses [label=\"{ org.acmsl.dotparser.Node }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        createProcessCommandParamNames [label=\"{ processMetadata }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        createProcessCommandParamTypes [label=\"{ IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterCreateProcessActionParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        createProcessActionParamClasses [label=\"{ org.acmsl.dotparser.Node }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        createProcessActionParamNames [label=\"{ processMetadata }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        createProcessActionParamTypes [label=\"{ IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterCreateProcessApiParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        createProcessApiParamClasses [label=\"{ org.acmsl.dotparser.Node }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        createProcessApiParamNames [label=\"{ processMetadata }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        createProcessApiParamTypes [label=\"{ IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterReadProcessWorkflowXmlParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        readProcessWorkflowXmlParamClasses [label=\"{ java.io.File | com.ventura24.nlp.processworkflow.ProcessWorkflowDefinitionProvider }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        readProcessWorkflowXmlParamNames [label=\"{ processWorkflowXml | processWorkflowDefinitionProvider }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        readProcessWorkflowXmlParamTypes [label=\"{ IN | OUT }\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterUpdateProcessWorkflowXmlParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        updateProcessWorkflowXmlParamClasses [label=\"{ com.ventura24.nlp.processworkflow.ProcessWorkflowDefinitionProvider }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        updateProcessWorkflowXmlParamNames [label=\"{ processWorkflowDefinitionProvider }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        updateProcessWorkflowXmlParamTypes [label=\"{ IN/OUT}\"];\n"
        + "    }\n"
        + "\n"
        + "    subgraph clusterWriteProcessWorkflowXmlParams {\n"
        + "        style=\"filled\";\n"
        + "        color=\"darkslateblue\";\n"
        + "        fontcolor=\"white\";\n"
        + "        label=\"Parameters\";\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray80\", fontcolor=\"black\"];\n"
        + "        writeProcessWorkflowXmlParamClasses [label=\"{ java.io.File | com.ventura24.nlp.processworkflow.ProcessWorkflowDefinitionProvider }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"gray50\", fontcolor=\"ivory\"];\n"
        + "        writeProcessWorkflowXmlParamNames [label=\"{ processWorkflowXml | processWorkflowDefinitionProvider }\"];\n"
        + "        node [shape=record, style=\"filled\", layer=all, fillcolor=\"olivedrab1\", fontcolor=\"black\"];\n"
        + "        writeProcessWorkflowXmlParamTypes [label=\"{ IN | IN }\"];\n"
        + "    }\n"
        + "\n"
        + "    edge [arrowHead=\"halfopen\", style=\"dotted\", color=\"gray50\"];\n"
        + "\n"
        + "    processMetadata -> start;\n"
        + "    readDotParamNames -> readDot;\n"
        + "    validateDotParamNames -> validateDot;\n"
        + "    retrieveProcessMetadataParamNames -> retrieveProcessMetadata;\n"
        + "    checkStateListParamNames -> checkStateList;\n"
        + "    retrieveNextStateParamNames -> retrieveNextState;\n"
        + "    createStateCommandParamNames -> createStateCommand;\n"
        + "    createStateActionParamNames -> createStateAction;\n"
        + "    createProcessCommandParamNames -> createProcessCommand;\n"
        + "    createProcessActionParamNames -> createProcessAction;\n"
        + "    createProcessApiParamNames -> createProcessApi;\n"
        + "    readProcessWorkflowXmlParamNames -> readProcessWorkflowXml;\n"
        + "    updateProcessWorkflowXmlParamNames -> updateProcessWorkflowXml;\n"
        + "    writeProcessWorkflowXmlParamNames -> writeProcessWorkflowXml;\n"
        + "}";

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public DotParserTest(final String name)
    {
        super(name);
    }

    /**
     * Tests the DotParser.
     */
    public void testParser()
    {
       try 
       {
           DotLexer t_DotLexer =
               new DotLexer(
                   new StringBufferInputStream(TEST_INPUT_1));

           assertNotNull(t_DotLexer);

           TokenBuffer t_TokenBuffer = new TokenBuffer(t_DotLexer);

           assertNotNull(t_TokenBuffer);

           DotParser t_DotParser = new DotParser(t_TokenBuffer);

           assertNotNull(t_DotParser);

           t_DotParser.graph();

           assertNotNull(t_DotParser.getAST());

           t_DotLexer =
               new DotLexer(
                   new StringBufferInputStream(TEST_INPUT_2));

           assertNotNull(t_DotLexer);

           t_TokenBuffer = new TokenBuffer(t_DotLexer);

           assertNotNull(t_TokenBuffer);

           t_DotParser = new DotParser(t_TokenBuffer);

           assertNotNull(t_DotParser);

           t_DotParser.graph();

           assertNotNull(t_DotParser.getAST());

           t_DotLexer =
               new DotLexer(
                   new StringBufferInputStream(TEST_INPUT_3));

           assertNotNull(t_DotLexer);

           t_TokenBuffer = new TokenBuffer(t_DotLexer);

           assertNotNull(t_TokenBuffer);

           t_DotParser = new DotParser(t_TokenBuffer);

           assertNotNull(t_DotParser);

           t_DotParser.graph();

           assertNotNull(t_DotParser.getAST());

           antlr.debug.misc.ASTFrame frame =
               new antlr.debug.misc.ASTFrame(
                   "AST JTree Example", t_DotParser.getAST());
           frame.setVisible(true);

           while (true);
        }
        catch  (final Throwable throwable)
        {
            LogFactory.getLog(getClass()).fatal(throwable);

            fail("" + throwable);
        }
    }
}
