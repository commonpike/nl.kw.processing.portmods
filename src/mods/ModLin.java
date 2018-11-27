package nl.kw.processing.mods;




public class ModLin extends Mod {
  ModLin() {
    super();
    addPort("tick");
    addPort("shift"); 
    addPort("speed").def(100);
    addPort("out");
  }
  protected void calc() {
    double s = get("speed")/100;
    double dy = get("shift");
    double t = get("tick");
    set("out",dy+s*t);
  }
}