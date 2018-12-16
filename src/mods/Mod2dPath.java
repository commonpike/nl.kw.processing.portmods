package nl.kw.processing.mods;

import processing.core.*;
import java.util.Arrays;
import java.util.Comparator;
import java.lang.Math;


public class Mod2dPath extends Mod {

   public class PathVector {
     protected float i;
     protected PVector v;
     protected PathVector(float i, PVector v) {
       this.i = i;
       this.v = v;
     }
   }
   
   private PathVector[] points;
   
   public Mod2dPath() {
     super();
     this.debug = false;
     addPort("tick").def(0);
     addPort("speed").def(100);
     addPort("phase").def(0);
     addPort("shiftx").def(0);
     addPort("shifty").def(0);
     addPort("ampx").def(100);
     addPort("ampy").def(100);
     addPort("outx").def(0); 
     addPort("outy").def(0); 
     
     
     
     
     
     debug();
   }
   
   private class SortPathVector implements Comparator<PathVector> { 
     public int compare(PathVector a, PathVector b) { 
       return (int)Math.signum(a.i - b.i); 
     } 
   }

   // shorthand
   public PathVector[] points() { return this.getPoints(); }
   public Mod points(PathVector[] points) { return this.setPoints(points); }
   public Mod points(float[][] points) { return this.setPoints(points); }
   public Mod clear() { return this.clearPoints(); }
   public Mod point(PathVector point) { return this.addPoint(point); }
   public Mod point(float i, PVector v) { return this.addPoint(new PathVector(i,v)); }
   public Mod point(float i, float x, float y) { return this.addPoint(new PathVector(i,new PVector(x,y))); }
   
   
   

   // method
   public PathVector[] getPoints() {
     return this.points;
   }
   
   public Mod setPoints(float[][] points) { 
     PathVector[] ppoints = new PathVector[points.length];
     for (int p=0; p< points.length;p++) {
         ppoints[p] = new PathVector(points[p][0],new PVector(points[p][1],points[p][2]));
     }
     return this.setPoints(ppoints); 
  }
  
  public Mod setPoints(PathVector[] points) {
     Arrays.sort(points, new SortPathVector());
     this.points = points;
     return this;
   }
   
   public Mod addPoint(float[] point) { 
     this.addPoint(new PathVector(point[0],new PVector(point[1],point[2])));
     return this;
   }
   
   public Mod addPoint(PathVector point) {
     PathVector[] newpoints;
     if (this.points==null) {
       newpoints = new PathVector[1];
       newpoints[0]=point;
     } else {
       newpoints = Arrays.copyOf(this.points, this.points.length + 1);
       newpoints[this.points.length] = point;
     }
     this.setPoints(newpoints);
     return this;
   }
   
   public Mod clearPoints() {
     this.points = new PathVector[0];
     return this;
   }
   
   public float posmod(float f, float m) {
     return ((f % m) + m ) % m;
   }
   
   protected PVector getPointVector(float i) {
     
     if (this.points==null) {
        throw new RuntimeException("Path has no points yet - use ModPath.point(x,y)");
     }
     float firsti = this.points[0].i;
     PVector firstv = this.points[0].v;
     float lasti = this.points[this.points.length-1].i;
     PVector lastv = this.points[this.points.length-1].v;
     //this.debug("before",i);
     float modi = posmod(i-firsti,lasti-firsti)+firsti;
     //this.debug("after",i);
     
     float previ = firsti;
     PVector prevv = firstv;
     float nexti = lasti;
     PVector nextv = lastv;
     
     int p = 0;
     while ( p < this.points.length) {
       if (modi>=this.points[p].i) {
          previ = this.points[p].i;
          int q = (int)posmod(p+1,this.points.length);
          nexti = this.points[q].i;
          if (modi<nexti) {
            prevv = this.points[p].v;
            nextv = this.points[q].v;
            //println("break ",previ+"<"+modi+"<"+nexti); 
            break;
          } else {
            // println("proceed ",this.points[p].i+"<"+modi+"<"+lasti); 
          }
       } else {
          // this shouldnt happen
          //println("proceed ",this.points[p].i+">"+modi); 
       }
       p++;
     }

      
     float progress = (modi-previ) / (nexti-previ);
     float modx = prevv.x + progress * (nextv.x - prevv.x);
     float mody = prevv.y + progress * (nextv.y - prevv.y);
     
     //println("progress", progress);
     //println(prevv+"<"+modv+"<"+nextv);
     
     return new PVector(modx,mody);
   }
   
   protected void calc() {
      float i  = get("tick");
      float di = get("phase");
      float fi = get("speed")/100;
      float dx = get("shiftx");
      float fx = get("ampx")/100;
      float dy = get("shifty");
      float fy = get("ampy")/100;
      
      float modi = i*fi+di;
      PVector modv = getPointVector(modi);
      
      float x = dx+fx*modv.x;
      float y = dy+fy*modv.y;
      
      set("outx",x);
      set("outy",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","outx","outy");
   }
}