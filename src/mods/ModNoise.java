package nl.kw.processing.mods;

import processing.core.*;

public class ModNoise extends Mod {
  
   private double ticker;
   private PApplet applet;
   
   ModNoise() {
   	throw new RuntimeException(this.toString()+" needs access to your applet - use `new ModNoise(this)`.");
   }
   
   ModNoise(PApplet applet) {
     super();
     this.applet = applet;
     addPort("in").def(0);  
     addPort("pink").def(100);
     addPort("amp").def(100);
     addPort("out"); 
   }
   
   protected void calc() {
      double in = get("in");
      double amp = get("amp");
      double pink = get("pink");
      this.ticker += pink/2000;
      double fuzz = applet.noise((float)this.ticker)-.5;
      //processing.core.PApplet.println(in,amp*fuzz);
      double out = in+amp*fuzz;
      set("out",out);
   }
   
   public ModPlotter plotter() {
    return plotter("in","in","out");
   }
}