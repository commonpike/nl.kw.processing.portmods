package nl.kw.processing.portmods;


public class ModBlock extends Mod  {
  
   public ModBlock() {
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
      float modx = (((fx*(x-dx)) % 100)+100) %100;
      float y = dy+fy;
      if ( modx >= 50) y = dy-fy;
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}