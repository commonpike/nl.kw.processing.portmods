package nl.kw.processing.mods;

public class Mod2dCog extends Mod2dCirc {

  
  public Mod2dCog() {
    super();
    
    this.addPort("cogs").def(4);
    this.addPort("inner").def(50);
    this.addPort("outer").def(100);
    
    Mod blk = this.addMod("block",new ModBlock());
    blk.set("phase",-25);
    this.port("tick").push(blk.port("tick"));
    this.port("radius").push(this,"outer");
    this.port("radius").pull(blk.port("out"));

    this.resync();
  }
  
  public void calc() {
    this.resync();
    super.calc();
  }
  
  public void resync() {
    float inner = this.get("inner");
    float outer = this.get("outer");
    float cogs = this.get("cogs");
    float speed = this.get("speed");
    this.mod("block")
      .set("amp",(outer-inner)/2)
      .set("shift",(outer+inner)/2)
      .set("speed",cogs*speed);
  }
  
}
