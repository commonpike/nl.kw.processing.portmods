package nl.kw.processing.portmods;
import processing.core.*;


public class ModSaw extends Mod  {
     
   public ModSaw() {
     super();
     this.debug = false;
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0);
     addPort("edge").def(0);
     debug();
   }
   
   protected float posmod(float f, float m) {
     return (f % m + m) % m;  
   }
   
   protected void calc() {
      float e  = get("edge");
      float x  = get("tick");
      float dx = get("phase");
      float fx = get("speed")/100;
      float dy = get("shift");
      float fy = get("amp")/100;
      
      float modx = posmod(x*fx+dx+e/2,100);
      float mody;
      if (modx<e) {
        mody = 200*(modx/e)-100;
      } else {
        mody = 200*((100-modx)/(100-e))-100;
      }
       
      float y = dy+fy*mody;
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}