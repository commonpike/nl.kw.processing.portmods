package nl.kw.processing.mods;



public class Mod2dSpiral extends Mod2dCirc {
  public Mod2dSpiral() {
    super();
    addMod("grow",new ModLin());
    port("tick").push("grow","tick");
    port("radius").pull("grow","out");
  }
}