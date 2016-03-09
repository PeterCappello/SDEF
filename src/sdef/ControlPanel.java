/*
 * The MIT License
 *
 * Copyright 2015 peter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package sdef;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

/**
 *
 * @author Pete Cappello
 */
public class ControlPanel extends JPanel
{    
    private final JPanel buttonPanel = new JPanel();
        private final JButton loadButton  = new JButton( "Load" );
        private final JButton runButton   = new JButton( "Run" );
        private final JButton stepButton  = new JButton( "Step" );
        
    private final JTextArea consoleTextArea = new JTextArea( 10, 40 );
        private final JScrollPane consoleScrollPane = new JScrollPane( consoleTextArea );
    
    private final JTextArea programTextArea = new JTextArea( 10, 30 );
        private final JScrollPane programScrollPane = new JScrollPane( programTextArea );
        
    private final JTextArea dataTextArea = new JTextArea( 10, 10 );
        private final JScrollPane dataScrollPane = new JScrollPane( dataTextArea );
        
    private final JTextArea dataStackTextArea = new JTextArea( 10, 30 );
        private final JScrollPane dataStackScrollPane = new JScrollPane( dataStackTextArea );
        
    private final JSplitPane debuggingSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, dataScrollPane, dataStackScrollPane );
    private final JSplitPane machineStateSplitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, programScrollPane, debuggingSplitPane );
        
    private final JSplitPane assemblerOutputSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, machineStateSplitPane, consoleScrollPane );
    
    private final View view = new View();
    private final JScrollPane viewScrollPane = new JScrollPane( view );
    private final JSplitPane appSplitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, viewScrollPane, assemblerOutputSplitPane );
    private final Model gvm;
    private final int[] dataMemory = null;
    
    ControlPanel( Model gvm ) 
    {
        this.gvm = gvm;
        
        buttonPanel.setLayout( new GridLayout( 1, 3 ) );
        buttonPanel.add( loadButton );
        buttonPanel.add( runButton );
        buttonPanel.add( stepButton );
                
        machineStateSplitPane.setOneTouchExpandable( true );
        machineStateSplitPane.setDividerLocation( 450 );
        
        debuggingSplitPane.setOneTouchExpandable( true );
        debuggingSplitPane.setDividerLocation( 175 );
        
        assemblerOutputSplitPane.setOneTouchExpandable( true );
        assemblerOutputSplitPane.setDividerLocation( 300 );

        // Provide minimum sizes for the 2 components in the split pane
        consoleScrollPane.setMinimumSize( new Dimension( 200, 200 ) );
        
        setLayout(new BorderLayout() );
        add( buttonPanel, BorderLayout.NORTH );
        add( appSplitPane, BorderLayout.CENTER );
        appSplitPane.setOneTouchExpandable( true );
        appSplitPane.setDividerLocation( 600 );

        addEventListeners();
        
        Dimension dimension = new Dimension( view.getWidth(), 
            buttonPanel.getHeight() + consoleScrollPane.getHeight() + machineStateSplitPane.getHeight() + view.getHeight() );
        setSize( dimension );
        setPreferredSize( dimension );
        
        enableExecution( false );
    }

    private void addEventListeners() 
    {
        loadButton.addActionListener( actionEvent -> loadButtonActionPerformed( actionEvent ) );
        runButton.addActionListener(  actionEvent -> runButtonActionPerformed( actionEvent  ) );
        stepButton.addActionListener( actionEvent -> stepButtonActionPerformed( actionEvent ) );
    }

    // _____________________________
    //  controller for each action
    // _____________________________
    private void loadButtonActionPerformed( ActionEvent actionEvent ) 
    {
        enableExecution( false );
        consoleTextArea.append( "\n\nStarting assembly ...\n");
        programTextArea.setText( "" );
        dataTextArea.setText( "" );
        repaint();
    }
    
    private void runButtonActionPerformed( ActionEvent actionEvent ) 
    {
        enableLoad( false );
        long startTime = System.currentTimeMillis();
        long runTime = System.currentTimeMillis() - startTime;
        String runTimeMessage = String.format("\nExecution time: %.2f sec.\n", ( (float) ( runTime / 1000.0 ) ) );
        consoleTextArea.append( runTimeMessage );
        consoleTextArea.append( "Finished executing run\n" );
        updateDataView();
        enableLoad( true );
    }
    
    private void stepButtonActionPerformed( ActionEvent actionEvent ) 
    { 
        enableLoad( false );
        updateDataView();
        enableLoad( true );
    }
    
    private void updateDataView()
    {
        populateDataMemoryTextArea();
        updateDataStackView();
    }
    
    private void populateDataMemoryTextArea()
    {
        dataTextArea.setText( "" );
        dataTextArea.append( "\n" );
    }
    
    private void populateDataMemoryTextArea( Map<String, Integer> map )
    {
        for ( String identifier : map.keySet() )
        {
            String line = String.format("\t%4d: %s%n", dataMemory[ map.get( identifier ) ], identifier );
            dataTextArea.append( line );
        }
    }
    
    private void updateDataStackView()
    {
        dataStackTextArea.setText( "" );
        
        List<Integer> reverseStack = new LinkedList<>();
        for ( Integer integer : reverseStack )
        {
            dataStackTextArea.append( integer + "\n" );
        }
        dataStackTextArea.append( "\nData Stack\n\n" );
    }

    private void enableExecution( boolean value )
    {
        runButton.setEnabled( value );
        stepButton.setEnabled( value );
    }
    
    private void enableLoad( boolean value ) { loadButton.setEnabled( value ); }
}
