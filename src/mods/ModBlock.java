package nl.kw.processing.mods;


public class ModBlock extends Mod {
  
   ModBlock() {
     super();
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
   }
   
   protected void calc() {
      double x  = get("tick");
      double dx = get("phase");
      double fx = get("speed")/100;
      double dy = get("shift");
      double fy = get("amp");
      double modx = (((fx*x-dx) % 100)+100) %100;
      double y = dy+fy;
      if ( modx >= 50) y = dy-fy;
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}