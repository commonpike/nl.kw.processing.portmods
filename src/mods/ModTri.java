package nl.kw.processing.mods;


public class ModTri extends Mod {
  
   ModTri() {
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
      
      double rx = fx*x-dx;
      //y(x) = |x % 4 - 2|-1
      double ry = 4*Math.abs(Math.abs(rx-25)%100-50)-100;
      double y = fy/100*ry+dy;
      
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}