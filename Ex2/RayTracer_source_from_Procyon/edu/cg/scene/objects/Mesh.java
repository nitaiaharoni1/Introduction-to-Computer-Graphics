// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Collection;
import java.util.ArrayList;
import edu.cg.algebra.Point;
import java.util.List;

public class Mesh extends Shape implements Iterable<Triangle>
{
    private List<Point> vertices;
    private List<Triplet> indices;
    private transient List<Triangle> triangles;
    
    public Mesh() {
        this.triangles = null;
    }
    
    public Triangle makeTriangle(final Triplet triplet) {
        return new Triangle(this.vertices.get(triplet.i1), this.vertices.get(triplet.i2), this.vertices.get(triplet.i3));
    }
    
    public Mesh initVertices(final List<Point> vertices) {
        this.vertices = new ArrayList<Point>(vertices);
        return this;
    }
    
    public Mesh initIndices(final List<Triplet> indices) {
        this.indices = indices;
        return this;
    }
    
    public Mesh initIndices(final int[] indices) {
        final List<Triplet> triplets = new ArrayList<Triplet>(indices.length / 3);
        for (int i = 0; i < indices.length; i += 3) {
            triplets.add(new Triplet(indices[i], indices[i + 1], indices[i + 2]));
        }
        return this.initIndices(triplets);
    }
    
    public Mesh initIndices(final Object[] indices) {
        final List<Triplet> triplets = new ArrayList<Triplet>(indices.length / 3);
        for (int i = 0; i < indices.length; i += 3) {
            triplets.add(new Triplet((int)indices[i], (int)indices[i + 1], (int)indices[i + 2]));
        }
        return this.initIndices(triplets);
    }
    
    public Mesh initMesh() {
        try {
            final BufferedReader f_points = new BufferedReader(new FileReader(new File("points.txt")));
            final List<Point> points = new LinkedList<Point>();
            String line;
            while ((line = f_points.readLine()) != null) {
                final String[] points_str = line.split(" ");
                final double x = Double.parseDouble(points_str[0]);
                final double y = Double.parseDouble(points_str[1]);
                final double z = Double.parseDouble(points_str[2]);
                points.add(new Point(x, y, z));
            }
            f_points.close();
            for (final Point point : points) {
                final Point p = point;
                point.z -= 5.0;
            }
            final BufferedReader f_indices = new BufferedReader(new FileReader(new File("indices.txt")));
            final List<Integer> indices = new LinkedList<Integer>();
            while ((line = f_indices.readLine()) != null) {
                final int i = Integer.parseInt(line);
                indices.add(i);
            }
            f_indices.close();
            this.initVertices(points);
            this.initIndices(indices.toArray());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Mesh:" + endl + "Vertices:" + endl + this.vertices + endl + "Triangles: " + this.indices + endl;
    }
    
    @Override
    public Iterator<Triangle> iterator() {
        return new Iterator<Triangle>() {
            private int currentTripletIndex = 0;
            
            @Override
            public boolean hasNext() {
                return this.currentTripletIndex < Mesh.this.indices.size();
            }
            
            @Override
            public Triangle next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Triplet triplet = Mesh.this.indices.get(this.currentTripletIndex++);
                return Mesh.this.makeTriangle(triplet);
            }
        };
    }
    
    private synchronized List<Triangle> getTriangles() {
        if (this.triangles == null) {
            this.triangles = new LinkedList<Triangle>();
            for (final Triangle triangle : this) {
                this.triangles.add(triangle);
            }
        }
        return this.triangles;
    }
    
    @Override
    public Hit intersect(final Ray ray) {
        Hit minHit = null;
        final List<Triangle> triangles = this.getTriangles();
        for (final Triangle triangle : triangles) {
            final Hit newHit = triangle.intersect(ray);
            if (minHit == null || (newHit != null && newHit.compareTo(minHit) < 0)) {
                minHit = newHit;
            }
        }
        return minHit;
    }
    
    public static class Triplet
    {
        public int i1;
        public int i2;
        public int i3;
        
        public Triplet() {
            final boolean i1 = false;
            this.i3 = (i1 ? 1 : 0);
            this.i2 = (i1 ? 1 : 0);
            this.i1 = (i1 ? 1 : 0);
        }
        
        public Triplet(final int i1, final int i2, final int i3) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
        }
    }
}
