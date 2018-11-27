package nl.kw.processing.mods;

import processing.core.*;




public class Mod2dEllipse extends Mod {
  
   Mod2dEllipse() {
     super();
     addPort("tick").def(0);
     addPort("shiftx").def(0); 
     addPort("shifty").def(0); 
     addPort("speed").def(100);
     addPort("width").def(100);
     addPort("height").def(100);
     addPort("phase").def(0);
     addPort("outx").def(0); 
     addPort("outy").def(0); 
   }
   
   public void calc() {
     
      double t  = get("tick");
      double dt = get("phase")*processing.core.PApplet.TWO_PI/100;
      double ft = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
      
      double dx = get("shiftx");
      double fx = get("width");
      
      double x = dx+fx*Math.sin(ft*t-dt);
      set("outx",x);
      
      double dy = get("height");
      double fy = get("ampy");
      double y = dy+fy*Math.cos(ft*t-dt);
      set("outy",y);
      
   }
   
   public ModPlotter plotter() {
    return plotter("tick","outx","outy");
  }
}