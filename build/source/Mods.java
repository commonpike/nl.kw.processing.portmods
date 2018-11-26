package nl.kw.processing.mods;

import processing.core.*;
import java.util.Random;

class ModNop extends Mod {
  
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

/* --------------- */
/* 1d modulators   */
/* --------------- */

class ModLin extends Mod {
  ModLin() {
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

class ModSin extends Mod {
  
   ModSin() {
     super();
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
     debug();
   }
   
   protected void calc() {
      float x  = get("tick");
      float dx = get("phase")*processing.core.PApplet.TWO_PI/100;
      float fx = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
      float dy = get("shift");
      float fy = get("amp");
      float y = dy+fy*Math.sin(fx*x-dx);
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}

class ModBlock extends Mod {
  
   ModBlock() {
     super();
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
   }
   
   protected void calc() {
      float x  = get("tick");
      float dx = get("phase");
      float fx = get("speed")/100;
      float dy = get("shift");
      float fy = get("amp");
      float modx = (((fx*x-dx) % 100)+100) %100;
      float y = dy+fy;
      if ( modx >= 50) y = dy-fy;
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}

class ModTri extends Mod {
  
   ModTri() {
     super();
     addPort("tick").def(0);
     addPort("shift").def(0);      
     addPort("speed").def(100);
     addPort("amp").def(100);
     addPort("phase").def(0);
     addPort("out").def(0); 
   }
   
   protected void calc() {
      float x  = get("tick");
      float dx = get("phase");
      float fx = get("speed")/100;
      float dy = get("shift");
      float fy = get("amp");
      float modx = (((fx*x-dx) % 100)+100) %100;
      
      float rx = fx*x-dx;
      //y(x) = |x % 4 - 2|-1
      float ry = 4*Math.abs(Math.abs(rx-25)%100-50)-100;
      float y = fy/100*ry+dy;
      
      processing.core.PApplet.println(x,y);
      set("out",y);
   }
   
   public ModPlotter plotter() {
    return plotter("tick","tick","out");
   }
}

class ModRndTri extends Mod {
  
  private float srctick;
  private float srcval;
  private float speed;
  private float current;
  private boolean asc;
  private boolean inited=false;
  
  private Random random = new Random();
  
  ModRndTri() {
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

/* --------------- */
/* 1d modifiers    */
/* --------------- */

class ModFuzz extends Mod {
  
   Random random = new Random();
   
   ModFuzz() {
     super();
     addPort("in").def(0);      
     addPort("amp").def(100);
     addPort("out"); 
   }
   
   protected void calc() {
      float in = get("in");
      float amp = get("amp");
      float out = in+random.nextFloat()*amp-amp/2;
      set("out",out);
   }
   
   public ModPlotter plotter() {
    return plotter("in","in","out");
   }
}

class ModNoise extends Mod {
  
   private float ticker;
   
   ModNoise() {
     super();
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
      float fuzz = processing.core.PApplet.noise(this.ticker)-.5;
      processing.core.PApplet.println(in,amp*fuzz);
      float out = in+amp*fuzz;
      set("out",out);
   }
   
   public ModPlotter plotter() {
    return plotter("in","in","out");
   }
}

/* --------------- */
/* 2d modulators   */
/* --------------- */

class Mod2dCirc extends Mod {
  
   Mod2dCirc() {
     super();
     addPort("tick").def(0);
     addPort("shiftx").def(0); 
     addPort("shifty").def(0); 
     addPort("speed").def(100);
     addPort("radius").def(100);
     addPort("phase").def(0);
     addPort("outx").def(0); 
     addPort("outy").def(0); 
   }
   
   public void calc() {
          
    //processing.core.PApplet.println(this,"calc");
    float t  = get("tick");
    float dt = get("phase")*processing.core.PApplet.TWO_PI/100;
    float ft = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
    
    float dx = get("shiftx");
    float fx = get("radius");
    
    float x = dx+fx*Math.sin(ft*t-dt);
    set("outx",x);
    
    float dy = get("shifty");
    float fy = get("radius");
    float y = dy+fy*Math.cos(ft*t-dt);
    set("outy",y);
      
  }

  public ModPlotter plotter() {
    return plotter("tick","outx","outy");
  }
}

class Mod2dEllipse extends Mod {
  
   Mod2dEllipse() {
     super();
     addPort("tick").def(0);
     addPort("shiftx").def(0); 
     addPort("shifty").def(0); 
     addPort("speed").def(100);
     addPort("width").def(100);
     addPort("height").def(100);
     addPort("phase").def(0);
     addPort("outx").def(0); 
     addPort("outy").def(0); 
   }
   
   public void calc() {
     
      float t  = get("tick");
      float dt = get("phase")*processing.core.PApplet.TWO_PI/100;
      float ft = get("speed")*processing.core.PApplet.TWO_PI/(100*100);
      
      float dx = get("shiftx");
      float fx = get("width");
      
      float x = dx+fx*Math.sin(ft*t-dt);
      set("outx",x);
      
      float dy = get("height");
      float fy = get("ampy");
      float y = dy+fy*Math.cos(ft*t-dt);
      set("outy",y);
      
   }
   
   public ModPlotter plotter() {
    return plotter("tick","outx","outy");
  }
}

class Mod2dSpiral extends Mod2dCirc {
  Mod2dSpiral() {
    super();
    addMod("grow",new ModLin());
    port("tick").push("grow","tick");
    port("radius").pull("grow","out");
  }
}

class Mod2dCog extends Mod {

  Mod2dCog() {
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


/* --------------- */
/* other dimensions*/
/* --------------- */

class ModColor extends Mod {
 
  private int col=0xff000000;

  ModColor() {
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
    processing.core.PApplet.println(this,"setColor",val);
    this.col=val;
    this.set("red",  (val >> 16 & 0xFF)/2.55);
    this.set("green",(val >> 8 & 0xFF)/2.55);
    this.set("blue", (val & 0xFF)/2.55);
    this.set("alpha",(val >>> 24)/2.55);
  }
  
  public int getColor() {
    // update yourself if you must
    if (this.needsProcessing()) this.process();
    return this.col;
  }
  
  public void calc() {
    
    int a = (int)Math.min(100,Math.abs(get("alpha")))*2.55 << 24;   // Binary: 11111111000000000000000000000000
    int r = (int)Math.min(100,Math.abs(get("red")))*2.55 << 16;     // Binary: 00000000110011000000000000000000
    int g = (int)Math.min(100,Math.abs(get("green")))*2.55 << 8;    // Binary  00000000000000001100110000000000
    int b = (int)Math.min(100,Math.abs(get("blue")))*2.55;          // Binary: 00000000000000000000000000110011
    // OR the values together:                            // Binary: 11111111110011001100110000110011 
    this.col = a | r | g | b; 
    set("out",100*(float)this.col/0xffffff);
  }
}