
package nl.kw.processing.portmods;
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
   

   protected void process() {
     if (this.port("sides").hasChanged || this.port("rotate").hasChanged) {
       this.make();
     }
     super.process();
   }
  

   public Mod2dPolygon make() {
      this.debug(this,"making polygon");
      float sides = this.port("sides").src();
      int isides = (int)sides;
      float rotate = this.port("rotate").src();
      float[][] path = new float[isides+1][3];
      float inc = 100/sides;
      if (isides%2==0) {
        this.set("mold","phase",-rotate-inc/2);
      } else {
        this.set("mold","phase",-rotate);
      }
      for (int i = 0; i<=isides; i+=1) {
        float tick = (float)i*inc;
        this.set("mold","tick",tick);
        float x=this.get("mold","outx");
        float y=this.get("mold","outy");
        path[i] = new float[] {tick,x,y};
      }
      this.setPoints(path);
      return this;
   }
   
}