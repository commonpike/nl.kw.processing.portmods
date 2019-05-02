package nl.kw.processing.portmods;

import java.util.Random;



public class ModFuzz extends Mod  {
  
   Random random = new Random();
   
   public ModFuzz() {
     super();
     addPort("in").def(0);      
     addPort("amp").def(100);
     addPort("out"); 
   }
   
   protected void calc() {
      float in = get("in");
      float amp = get("amp");
      float out = in+random.nextFloat()*amp-amp/2;
      set("out",out);
   }
   
   public ModPlotter plotter() {
    return plotter("in","in","out");
   }
}