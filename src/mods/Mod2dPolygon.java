
package nl.kw.processing.mods;
import processing.core.*;

public class Mod2dPolygon extends Mod2dPath {

   public Mod2dPolygon() {
     super();
     this.addMod("mold",new Mod2dCirc());
     debug();
   }
   
  
   public Mod corners(float numcorners, double offset) {
     return this.corners(numcorners,(float)offset,true);
   }
   
   public Mod corners(float numcorners, double offset, boolean clear) {
   	 return this.corners(numcorners,(float)offset,clear);
   }
   
   public Mod corners(float numcorners) {
     return this.corners(numcorners,0,true);
   }
   public Mod corners(float numcorners, float offset) {
     return this.corners(numcorners,offset,true);
   }
   
   public Mod corners(float numcorners, float offset, boolean clear) {
      if (clear) this.clear();
      float inc = 100/numcorners;
      if (numcorners%2==0) {
        this.set("mold","phase",-inc/2);
      } else {
        this.set("mold","phase",0);
      }
      for (float i = offset; i<=numcorners+offset; i+=1) {
         this.set("mold","tick",i*inc);
         float x=this.get("mold","outx");
         float y=this.get("mold","outy");
         this.point(i*inc,x,y);
         //println(numcorners,i,i*inc,x,y);
      }
      //println();
      return this;
   }
   
}