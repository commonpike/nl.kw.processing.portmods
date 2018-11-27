package nl.kw.processing.mods;

public class ModNop extends Mod {
  
   ModNop() {
     super();
     debug(this,"setup");
     addPort("in"); 
     addPort("out"); 
   }

   protected void calc() {
     debug(this,"calc");
     set("out",get("in"));
   }
}