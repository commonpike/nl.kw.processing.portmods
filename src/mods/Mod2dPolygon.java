
package nl.kw.processing.mods;
import processing.core.*;

public class Mod2dPolygon extends Mod2dPath {

   public Mod2dPolygon() {
     super();
     //this.debug=true;
     this.addMod("mold",new Mod2dCircle());
     this.addPort("sides").noPull().def(4);
     this.addPort("rotate").noPull().def(0);
     debug();
   }
   
   // called from extending classes to bypass setPath
   protected void pprocess() {
     super.process();
   }
   
   protected void process() {
     if (this.port("sides").hasChanged || this.port("rotate").hasChanged) {
       this.clear();
       this.make(this.port("sides").src(),this.port("rotate").src());
     }
     super.process();
   }
  

   public Mod2dPath make(float numcorners, float rotate) {
      this.debug(this,"making polygon");
      float inc = 100/numcorners;
      if (numcorners%2==0) {
        this.set("mold","phase",-rotate-inc/2);
      } else {
        this.set("mold","phase",-rotate);
      }
      for (float i = 0; i<=numcorners; i+=1) {
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