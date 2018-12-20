
package nl.kw.processing.mods;
import processing.core.*;

public class Mod2dStar extends Mod2dPolygon {

   public Mod2dStar() {
     super();
     addPort("inner").def(50);
     debug();
   }
   
   public Mod points(float numpoints) {
     
     super.corners(numpoints);

     float radius = this.get("mold","radius");
     float inner = this.get("inner");
     this.set("mold","radius",radius*inner/100);
     super.corners(numpoints,.5,false);
     this.set("mold","radius",radius);
     return this;
     
   }
   
}