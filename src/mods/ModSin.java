package nl.kw.processing.portmods;

import processing.core.*;



public class ModSin extends Mod  {
  
   public ModSin() {
     super();
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
     debug();
   }
   
   protected void calc() {
      float x  = get("tick");
      float dx = get("phase")*processing.core.PApplet.TWO_PI/100;
      float fx = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
      float dy = get("shift");
      float fy = get("amp");
      float y = dy+fy*(float)Math.sin(fx*x-dx);
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}