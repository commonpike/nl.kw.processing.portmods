package nl.kw.processing.portmods;

import processing.core.*;
import java.util.Arrays;
import java.util.Comparator;
import java.lang.Math;


public class ModPath extends Mod  {

   public class PathPoint {
     protected float i;
     protected float v;
     public PathPoint(float i, float v) {
       this.i = i;
       this.v = v;
     }
   }
   
   private PathPoint[] points;
   
   public ModPath() {
     super();
     this.debug = false;
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
     
     
     
     
     
     debug();
   }
   
   private class SortPathPoint implements Comparator<PathPoint> { 
     public int compare(PathPoint a, PathPoint b) { 
       return (int)Math.signum(a.i - b.i); 
     } 
   }

   // shorthand
   public PathPoint[] points() { return this.getPoints(); }
   public Mod points(PathPoint[] points) { return this.setPoints(points); }
   public Mod points(float[][] points) { return this.setPoints(points); }
   public Mod clear() { return this.clearPoints(); }
   public Mod point(PathPoint point) { return this.addPoint(point); }
   public Mod point(float i, float v) { return this.addPoint(new PathPoint(i,v)); }
   
   
   

   // method
   public PathPoint[] getPoints() {
     return this.points;
   }
   public Mod setPoints(float[][] points) { 
     PathPoint[] ppoints = new PathPoint[points.length];
     for (int p=0; p< points.length;p++) {
         ppoints[p] = new PathPoint(points[p][0],points[p][1]);
     }
     return this.setPoints(ppoints); 
  }
  public Mod setPoints(PathPoint[] points) {
     Arrays.sort(points, new SortPathPoint());
     this.points = points;
     return this;
   }
   
   public Mod addPoint(float[] point) { 
     return this.addPoint(new PathPoint(point[0],point[1])); 
  }
  
   public Mod addPoint(PathPoint point) {
     PathPoint[] newpoints;
     if (this.points==null) {
       newpoints = new PathPoint[1];
       newpoints[0]=point;
     } else {
       newpoints = Arrays.copyOf(this.points, this.points.length + 1);
       newpoints[this.points.length] = point;
     }
     this.setPoints(newpoints);
     return this;
   }
   public Mod clearPoints() {
     this.points = new PathPoint[0];
     return this;
   }
   
   public float posmod(float f, float m) {
     return ((f % m) + m ) % m;
   }
   
   protected float getPointValue(float i) {
     
     if (this.points==null) {
        return 0;
     }
     if (this.points.length==0) {
        return 0;
     }
     if (this.points.length==1) {
        return this.points[0].v;
     }
     float firsti = this.points[0].i;
     float firstv = this.points[0].v;
     float lasti = this.points[this.points.length-1].i;
     float lastv = this.points[this.points.length-1].v;
     //this.debug("before",i);
     float modi = posmod(i-firsti,lasti-firsti)+firsti;
     //this.debug("after",i);
     
     float previ = firsti;
     float prevv = firstv;
     float nexti = lasti;
     float nextv = lastv;
     
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
     float modv = prevv + progress * (nextv - prevv);
     
     //println("progress", progress);
     //println(prevv+"<"+modv+"<"+nextv);
     
     return modv;
   }
   
   protected void calc() {
      float x  = get("tick");
      float dx = get("phase");
      float fx = get("speed")/100;
      float dy = get("shift");
      float fy = get("amp")/100;
      
      float modx = x*fx+dx;
      float mody = getPointValue(modx);
      
      float y = dy+fy*mody;
      
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}