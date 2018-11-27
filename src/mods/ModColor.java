package nl.kw.processing.mods;


public class ModColor extends Mod {
 
  private int col=0xff000000;

  public ModColor() {
    super();
    addPort("red").def(0); 
    addPort("green").def(0); 
    addPort("blue").def(0); 
    addPort("alpha").def(100); 
    addPort("out"); 
  }
  
  // shorthand
  // PDE preprocessors fails on this 
  public void color(float val) { this.setColor(val); }
  public int color() { return this.getColor(); }
  
  // methods
  
  public void setColor(float val) {
    this.setColor((int)val);
  }
  
  public void setColor(int val) {
    //processing.core.PApplet.println(this,"setColor",val);
    this.col=val;
    this.set("red",  (val >> 16 & 0xFF)/(float)2.55);
    this.set("green",(val >> 8 & 0xFF)/(float)2.55);
    this.set("blue", (val & 0xFF)/(float)2.55);
    this.set("alpha",(val >>> 24)/(float)2.55);
  }
  
  public int getColor() {
    // update yourself if you must
    if (this.needsProcessing()) this.process();
    return this.col;
  }
  
  public void calc() {
    
    int a = (int)(Math.min(100,Math.abs(get("alpha")))*2.55) << 24;   // Binary: 11111111000000000000000000000000
    int r = (int)(Math.min(100,Math.abs(get("red")))*2.55)   << 16;   // Binary: 00000000110011000000000000000000
    int g = (int)(Math.min(100,Math.abs(get("green")))*2.55) << 8;    // Binary  00000000000000001100110000000000
    int b = (int)(Math.min(100,Math.abs(get("blue")))*2.55);          // Binary: 00000000000000000000000000110011
    // OR the values together:                            					  // Binary: 11111111110011001100110000110011 
    this.col = a | r | g | b; 
    set("out",100*(float)this.col/0xffffff);
  }
}