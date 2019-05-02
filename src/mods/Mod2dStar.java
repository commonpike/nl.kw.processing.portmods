
package nl.kw.processing.portmods;
import processing.core.*;

public class Mod2dStar extends Mod2dPath {

   public Mod2dStar() {
     super();
     //this.debug=true;
     this.addMod("outer",new Mod2dCircle());
     this.addMod("inner",new Mod2dCircle()).set("radius",50);
     this.addPort("points").noPull().def(4);
     this.addPort("rotate").noPull().def(0);
     debug();
   }
   
   // called from extending classes to bypass setPath
   protected void pprocess() {
     super.process();
   }
   
   protected void process() {
     if (this.port("points").hasChanged || this.port("rotate").hasChanged) {
       this.make();
     }
     super.process();
   }
  

   public Mod2dStar make() {
      this.debug(this,"making star");
      float points = this.port("points").src();
      int ipoints = (int)points;
      float rotate = this.port("rotate").src();
      float[][] path = new float[2*ipoints+1][3];
      float inc = 100/points;
      //if (ipoints%2==0) {
      //  this.set("outer","phase",-rotate-inc/2);
      //  this.set("inner","phase",-rotate-inc/2);
      //} else {
        this.set("outer","phase",-rotate);
        this.set("inner","phase",-rotate);
      //}
      for (int i = 0; i<=ipoints; i+=1) {
         float tick = (float)i*inc;
         this.set("outer","tick",tick);
         float x=this.get("outer","outx");
         float y=this.get("outer","outy");
         path[i*2] = new float[] {tick,x,y};
         if (i<ipoints) {
         		tick = tick + inc/2;
         		this.set("inner","tick",tick);
         		x=this.get("inner","outx");
         		y=this.get("inner","outy");
         		path[i*2+1] = new float[] {tick,x,y};
         }
      }
      this.setPoints(path);
      return this;
   }
   
}