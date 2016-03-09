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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * App integrates the objects comprising the Gala (Graphics Assembly LAnguage) application.
 * @author Pete Cappello
 */
public class App extends JFrame
{
    private final JPanel controlPanel;
    private final JScrollPane controlScrollPane;
                
    App() 
    {        
        Model model = null;
        controlPanel = new ControlPanel( model );  
        controlScrollPane = new JScrollPane( controlPanel );
        setTitle( "GIDE: Gala Integrated Development Environment" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                      
        add( controlScrollPane, BorderLayout.CENTER );
        
        Dimension dimension = new Dimension( 800 , 1200 );
        setSize( dimension );
        setPreferredSize( dimension );
        setVisible( true );
    }
    
    /**
     * Run the Gala Graphics Virtual Machine application.
     * @param args unused 
     */
    public static void main( String[] args ) { App app = new App(); }
}
