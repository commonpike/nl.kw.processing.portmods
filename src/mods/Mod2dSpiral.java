package nl.kw.processing.portmods;



public class Mod2dSpiral extends Mod2dCircle {
  public Mod2dSpiral() {
    super();
    addMod("grow",new ModLin());
    port("tick").push("grow","tick");
    port("radius").pull("grow","out");
  }
}