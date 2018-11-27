package nl.kw.processing.mods;

import java.util.Random;


public class ModRndTri extends Mod {
  
  private float srctick;
  private float srcval;
  private float speed;
  private float current;
  private boolean asc;
  private boolean inited=false;
  
  private Random random = new Random();
  
  public ModRndTri() {
    super();
    addPort("tick").def(0); 
    addPort("dst").def(0); 
    addPort("shift").def(0); 
    addPort("speed").def(100); 
    addPort("amp").def(100);
    addPort("out"); 

  }
   
  protected void calc() {
    
    if (!this.inited) init();
    
    float lapsed = get("tick")-srctick;
    float shift = get("shift");
    this.current = srcval+lapsed*speed;
    set("out",this.current+shift);
    
    float dst = get("dst");
    this.debug(this,this.current,dst,asc);
    if (this.asc && this.current>=dst) {
      this.jump();
    } else if (!this.asc && this.current<=dst) {
      this.jump();
    }
  }
  
  public void init() {
    this.debug(this,"init");
    this.jump();
    float amp = get("amp");
    this.srcval = random.nextFloat()*amp*2-amp;
    float speed = get("speed")/100;
    this.speed = random.nextFloat()*speed;
    this.asc = (get("dst")>srcval);
    if (!this.asc) this.speed=-this.speed;
    this.inited=true;
  }
  
  public void jump() {
    this.debug(this,"jump");
    this.srcval = this.current;
    this.srctick = get("tick");
    float speed = get("speed")/100;
    this.speed = random.nextFloat()*speed;
    float amp = get("amp");
    float dstval = random.nextFloat()*amp*2-amp;
    set("dst",dstval);
    this.asc = (dstval>srcval);
    if (!this.asc) this.speed=-this.speed;
  }
  
  public ModPlotter plotter() {
    return plotter("tick","tick","out");
  }
}