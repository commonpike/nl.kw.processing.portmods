package nl.kw.processing.portmods;

import processing.core.*;

public class ModNoise extends Mod  {
  
   private float ticker;
   private PApplet applet;
   
   public ModNoise() {
   	throw new RuntimeException(this.toString()+" needs access to your applet - use `new ModNoise(this)`.");
   }
   
   public ModNoise(PApplet applet) {
     super();
     this.applet = applet;
     addPort("in").def(0);  
     addPort("pink").def(100);
     addPort("amp").def(100);
     addPort("out"); 
   }
   
   protected void calc() {
      float in = get("in");
      float amp = get("amp");
      float pink = get("pink");
      this.ticker += pink/2000;
      float fuzz = applet.noise((float)this.ticker)-(float).5;
      //processing.core.PApplet.println(in,amp*fuzz);
      float out = in+amp*fuzz;
      set("out",out);
   }
   
   public ModPlotter plotter() {
    return plotter("in","in","out");
   }
}