package nl.kw.processing.mods;


public class Mod2dCog extends Mod {

  public Mod2dCog() {
    super();
    addPort("tick").def(0);
    addPort("speed").def(100);
    addPort("shiftx").def(0); 
    addPort("shifty").def(0); 
    addPort("radius").def(100);
    addPort("phase").def(0);
    addPort("tooth").def(8);
    addPort("extrude").def(100);
    addPort("outx").def(0); 
    addPort("outy").def(0); 
     
    Mod blk = this.addMod("block",new ModBlock());
    Mod crc = this.addMod("circle",new Mod2dCirc());

    crc.port("shiftx").pull(this.port("shiftx"));
    crc.port("shifty").pull(this.port("shifty"));
    crc.port("phase").pull(this.port("phase"));
    
    this.port("tick").push(blk.port("tick"));
    blk.port("tick").push(crc.port("tick"));
    this.port("phase").push(blk.port("phase"));

    crc.port("radius").pull(blk.port("out"));
    
    this.port("outx").pull(crc.port("outx"));
    this.port("outy").pull(crc.port("outy"));
    
  }
  
  public void calc() {
    //processing.core.PApplet.println(this,"calc",get("block","out"),get("block","speed"));
    //get("block","out");
    float s = this.get("speed")/100;
    float t = this.get("tooth");
    float a = this.get("radius");
    float p = this.get("phase");
    mod("block")
      .set("speed",s*t)
      .set("shift",a*7/8)
      .set("amp",a*1/8);
      //.set("phase",p+2*100/t);
 }
 
  public ModPlotter plotter() {
    return plotter("tick","outx","outy");
  }
  
  
}
