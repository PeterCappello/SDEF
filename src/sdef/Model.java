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

import java.awt.Graphics;

/**
 *
 * @author Peter Cappello
 */
public class Model 
{
    private static final int N = 4;
    private int time;
    private final Vertex[][][] space = new Vertex[N][N][N];
    
    private void init()
    {
        for ( int x = 0; x < N; x++ )
        for ( int y = 0; y < N; y++ )
        for ( int z = 0; z < N; z++ )
        {
            space[x][y][y] = new Vertex( x, y, z );
        }
    }
    
    private void stepForward( Graphics graphics )
        {
            time++;
            view( graphics );
        }
        
        private void stepBackward( Graphics graphics )
        {
            time--;
            view( graphics );
        }
    
    private void view( Graphics graphics )
    {
        for ( int x = 0; x < N; x++ )
        for ( int y = 0; y < N; y++ )
        for ( int z = 0; z < N; z++ )
        {
            space[x][y][y].view( graphics );
            //?? Do I need to first draw all edges, then draw all vertices?
        }
        
        // ?? What about drawing axes and projection of vertices onto processor subspace?
    }
    
    private void reset() { time = 0; }
    
    private class Vertex
    {
        private final int x;
        private final int y;
        private final int z;
        
        private int xt;
        private int yt;
        private int zt;
        
        Vertex( int x, int y, int z )
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        private void view( Graphics graphics )
        {
            // draw inedges: if (previous coordinate < 0) omit edge.
            // draw vertex
        }
        
        private void transform( int[] s1, int[] s2, int[] time)
        {
            xt = x * s1[0] + y * s1[1] + z * s1[2];
            yt = x * s2[0] + y * s2[1] + z * s2[2];
            zt = x * time[0] + y * time[1] + z * time[2];
        }    
    }
}


