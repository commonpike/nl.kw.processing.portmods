package nl.kw.processing.mods;




public class ModLin extends Mod {
  public ModLin() {
    super();
    addPort("tick");
    addPort("shift"); 
    addPort("speed").def(100);
    addPort("out");
  }
  protected void calc() {
    float s = get("speed")/100;
    float dy = get("shift");
    float t = get("tick");
    set("out",dy+s*t);
  }
}