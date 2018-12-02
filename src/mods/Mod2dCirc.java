package nl.kw.processing.mods;

import processing.core.*;



public class Mod2dCirc extends Mod {
  
   public Mod2dCirc() {
     super();
     addPort("tick").def(0);
     addPort("shiftx").def(0); 
     addPort("shifty").def(0); 
     addPort("speed").def(100);
     addPort("radius").def(100);
     addPort("phase").def(0);
     addPort("outx").def(0); 
     addPort("outy").def(0); 
   }
   
   public void calc() {
          
    //processing.core.PApplet.println(this,"calc");
    float t  = get("tick");
    float dt = get("phase")*2*(float)Math.PI/100;
    float ft = get("speed")*2*(float)Math.PI/(100*100);
    
    float dx = get("shiftx");
    float fx = get("radius");
    
    float x = dx+fx*(float)Math.sin(ft*t-dt);
    set("outx",x);
    
    float dy = get("shifty");
    float fy = get("radius");
    float y = dy+fy*(float)Math.cos(ft*t-dt);
    set("outy",y);
      
  }

  public ModPlotter plotter() {
    return plotter("tick","outx","outy").range(0,100);
  }
}