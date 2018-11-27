package nl.kw.processing.mods;

import processing.core.*;



public class ModSin extends Mod {
  
   ModSin() {
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
      double x  = get("tick");
      double dx = get("phase")*processing.core.PApplet.TWO_PI/100;
      double fx = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
      double dy = get("shift");
      double fy = get("amp");
      double y = dy+fy*Math.sin(fx*x-dx);
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}