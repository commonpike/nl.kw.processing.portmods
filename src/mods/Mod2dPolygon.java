
package nl.kw.processing.mods;

import processing.core.*;
import java.util.Random;

public class Mod2dPolygon extends Mod2dPath {

   public Mod2dPolygon() {
     super();
     this.addMod("mold",new Mod2dCirc());
     this.corners(0);

     debug();
   }
   
   public Mod corners(float numcorners) {
      this.clear();
      float inc = 100/numcorners;
      if (numcorners%2==0) {
        this.set("mold","phase",-inc/2);
      } else {
        this.set("mold","phase",0);
      }
      for (int i = 0; i<=numcorners; i+=1) {
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