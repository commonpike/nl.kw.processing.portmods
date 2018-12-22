
package nl.kw.processing.mods;
import processing.core.*;

public class Mod2dStar extends Mod2dPolygon {
	
   public Mod2dStar() {
     super();
     addPort("points").def(4).noPull().push(this,"sides");
     addPort("inner").def(50).noPull();
     debug();
   }
   
   protected void process() {
     if (this.port("points").hasChanged || this.port("rotate").hasChanged || this.port("inner").hasChanged) {
       this.clear();
       this.make(this.port("points").src(),this.port("rotate").src(),this.port("inner").src());
     }
     // call super.super.process()
     super.pprocess();
   }
  

   public Mod2dStar make(float points, float rotate, float inner) {
      this.debug(this,"making star");
      
      super.make(points,rotate);
      float outer = get("mold","radius");
      set("mold","radius",inner);
      super.make(points,rotate+25/points);
      set("mold","radius",outer);
      
      return this;
   }
   
  
}