package nl.kw.processing.mods;

import java.util.Random;



public class ModFuzz extends Mod {
  
   Random random = new Random();
   
   ModFuzz() {
     super();
     addPort("in").def(0);      
     addPort("amp").def(100);
     addPort("out"); 
   }
   
   protected void calc() {
      double in = get("in");
      double amp = get("amp");
      double out = in+random.nextFloat()*amp-amp/2;
      set("out",out);
   }
   
   public ModPlotter plotter() {
    return plotter("in","in","out");
   }
}