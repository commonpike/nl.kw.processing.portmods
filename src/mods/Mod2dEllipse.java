package nl.kw.processing.mods;

import processing.core.*;




public class Mod2dEllipse extends Mod {
  
   public Mod2dEllipse() {
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
     
      float t  = get("tick");
      float dt = get("phase")*processing.core.PApplet.TWO_PI/100;
      float ft = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
      
      float dx = get("shiftx");
      float fx = get("width");
      
      float x = dx+fx*(float)Math.sin(ft*t-dt);
      set("outx",x);
      
      float dy = get("height");
      float fy = get("ampy");
      float y = dy+fy*(float)Math.cos(ft*t-dt);
      set("outy",y);
      
   }
   
   public ModPlotter plotter() {
    return plotter("tick","outx","outy");
  }
}