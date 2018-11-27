package nl.kw.processing.mods;


public class ModTri extends Mod {
  
   public ModTri() {
     super();
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
   }
   
   protected void calc() {
      float x  = get("tick");
      float dx = get("phase");
      float fx = get("speed")/100;
      float dy = get("shift");
      float fy = get("amp");
      float modx = (((fx*x-dx) % 100)+100) %100;
      
      float rx = fx*x-dx;
      //y(x) = |x % 4 - 2|-1
      float ry = 4*Math.abs(Math.abs(rx-25)%100-50)-100;
      float y = fy/100*ry+dy;
      
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}