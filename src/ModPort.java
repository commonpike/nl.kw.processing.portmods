package nl.kw.processing.mods;

import processing.core.*;
import java.util.UUID;

public class ModPort {

  protected double _default      = 0;
  protected double source        = 0;
  protected double result        = 0;
  protected ModPort pushport    = null;
  protected ModPort pullport = null;  
  protected boolean hasChanged  = true;
  protected Mod mod = null;  
  public String name  = null;
  
  ModPort() {
    // java is funny
    // this.mod.warn(this,"Creating a port without a mod");
  }
  
  ModPort(Mod mod, String name) {
    this.mod = mod;
    this.name = name;
    mod.debug("new ModPort",this);
  }
  
  public String toString() {
    return this.mod.name+"."+this.name;
  }
  
  // --------
  // process
  
  protected boolean needsProcessing() {
    if (this.hasChanged) {
      this.mod.debug(this,"hasChanged");
      return true;
    }
    if (this.pushport!=null && this.pushport.needsProcessing()) {
      this.mod.debug(this,"pushport hasChanged");
       return true;
    }
    if (this.pullport!=null && this.pullport.needsProcessing()) {
      this.mod.debug(this,"pullport hasChanged");
      return true;
    }
    return false;
  }
  
  // --------
  // setters
  
  // shorthand
  
  public ModPort set(double val) { return this.setSource(val); }
  public ModPort res(double val) { return this.setResult(val); }
  public ModPort def(double val) { return this.setDefault(val,true); }
  
  // methods
  public ModPort setSource(double val) {
    this.mod.debug(this,"setSource",val);
    if (this.source!=val) {
      this.source=val;
      // depending on where the get() comes from,
      // the question if anything changed may first
      // arrive at the mod, or at a port. 
      // for performance, we store it on both places
      this.mod.hasChangedPorts=true; // returned after mod.process()
      this.hasChanged=true; // returned after setResult()
    }
    if (this.pushport!=null) {
      this.pushport.setSource(val);
    }
    
    
   
    return this;
  }
  
  private ModPort setResult(double val) {
    this.mod.debug(this,"setResult",val);
    this.hasChanged=false;
    this.result=val;
    return this;
  }
  
  private ModPort setDefault(double val, boolean reset) {
    this.mod.debug(this,"setDefault",val);
    this._default=val;
    if (reset) this.reset();
    return this;
  }
  
  
  
  // --------
  // getters
  
  // shorthand
  
  public double get() { return this.getResult(); }
  public double src() { return this.getSource(); }
  public double def() { return this.getDefault(); }
  
  // methods
  public double getResult() {
    
    this.mod.debug(this,"getResult");
    
    // you are either calling this from outside
    // or from calc(), but not in a recursing process,
    // otherwise you would have passed a pid
  
    // if you come from mod.calc(), 
    // mod.isProcessing = true
    
    if (this.mod.isProcessing) {
      this.mod.debug(this,"getResult",": mod is processing");
      // this must be me calling myself
      // from calc, or a feedback loop
      // no questions asked
      if (this.pullport!=null) {
        UUID pid = this.mod.processid;
        this.setResult(this.pullport.getResult(pid));
      } else {
        this.setResult(this.getSource());
      }
    } else if (this.mod.needsProcessing()) {
      // this must have been called from outside
      this.mod.debug(this,"getResult",": mod needs processing");
      this.mod.process();
      // isnt this too much ?
      // process() would have returned above ?
      if (this.pullport!=null) {
        UUID pid = this.mod.processid;
        this.setResult(this.pullport.getResult(pid));
      } else {
        this.setResult(this.getSource());
      }
    } else {
      this.mod.debug(this,"getResult",": mod has processed");
      // all is good here
      if (this.pullport!=null) {
        UUID pid = this.mod.processid;
        this.setResult(this.pullport.getResult(pid));
      } else {
        this.setResult(this.getSource());
      }
    }
    return this.result;
  }
  
  public double getResult(UUID pid) {
    
    // a port is calling this in a process
    // that is updating the whole cluster

    if (this.mod.isProcessing) {
      if (this.mod.processid!=pid) {
         throw new RuntimeException(this.toString()+".getResult.pid - mod is processing under a different PID");
      } else {
         this.mod.debug(this,"getResult.pid",": mod is processing");
         // the current result should be fine (?)
      } 
    } else if (this.mod.needsProcessing()) {
      // some port wanted this value and
      // it needs updating. start a new process
      // on the mod, with the global pid
      this.mod.debug(this,"getResult.pid",": mod needs processing");
      this.mod.process(pid);
      // isnt this too much ?
      // process() would have returned above ?
      if (this.pullport!=null) {
        this.setResult(this.pullport.getResult(pid));
      } else {
        this.setResult(this.getSource());
      }
    } else {
      // all is good 
      this.mod.debug(this,"getResult.pid",": mod has processed");
      if (this.mod.processid!=pid) {
        // apparently nothing changed
        // in this process
      }
      if (this.pullport!=null) {
        this.setResult(this.pullport.getResult(pid));
      } else {
        this.setResult(this.getSource());
      }
      
    }
    return this.result;
  }
  
  private double getSource() {
    return this.source;
  }
  
  private double getDefault() {
    return this._default;
  }
  
  private void reset() {
    this.setSource(this.getDefault());
  }
  
  // --------
  // ports
  
  // shorthand
  public ModPort push(ModPort port) { return this.setPushPort(port);  }
  public ModPort push(Mod mod, String portname) { return this.setPushPort(mod.getPort(portname));  }
  public ModPort push(String modname, String portname) { return this.setPushPort(mod.getMod(modname).getPort(portname));  }
  public ModPort pull(ModPort port) { return this.setPullPort(port);  }
  public ModPort pull(Mod mod, String portname) { return this.setPullPort(mod.getPort(portname));  }
  public ModPort pull(String modname, String portname) { return this.setPullPort(mod.getMod(modname).getPort(portname));  }
  
  // methods
  public ModPort setPushPort(ModPort port) {
    this.mod.debug(this,"setPushPort",port);
    if (pushport!=null) {
      this.mod.warn(this,"setPushPort","overwriting pushport");
    }
    this.pushport = port;
    if (!this.mod.hasMod(port.mod)) {
       this.mod.addMod(port.mod); 
    }
    return port;
  }
  
  public ModPort setPullPort(ModPort port) {
    this.mod.debug(this,"setPullPort",port);
    if (pullport!=null) {
      this.mod.warn(this,"setPullPort","overwriting pullport");
    }
    this.pullport = port;
    if (!this.mod.hasMod(port.mod)) {
       this.mod.addMod(port.mod); 
    }
    return port;
  }
  
  // ------------
  // chain, prepend
  
  // shorthand
  
  
  
  public ModPort chain(String modname, String pushname, String pullname) {
    Mod mod = this.mod.getMod(modname);
    return this.chain(mod.getPort(pushname),mod.getPort(pullname));
  }
  public ModPort prechain(String modname, String pushname, String pullname) {
    Mod mod = this.mod.getMod(modname);
    return this.prechain(mod.getPort(pushname),mod.getPort(pullname));
  }
  public ModPort chain(Mod mod, String pushname, String pullname) {
    return this.chain(mod.getPort(pushname),mod.getPort(pullname));
  }
  public ModPort prechain(Mod mod, String pushname, String pullname) {
    return this.prechain(mod.getPort(pushname),mod.getPort(pullname));
  }
    
  // methods
  
  public ModPort prechain(ModPort pushport, ModPort pullport) {
    this.mod.debug(this,"prechain",pushport,pullport);
    if (this.pushport==null) {
      this.setPushPort(pushport);
      this.setPullPort(pullport);
    } else {
      this.pushport.prechain(pushport,pullport);
    }
    return this;
  }
  
  public ModPort chain(ModPort pushport, ModPort pullport) {
    this.mod.debug(this,"chain",pushport,pullport);
    if (this.pullport==null) {
      this.setPushPort(pushport);
      this.setPullPort(pullport);
    } else {
      this.pullport.chain(pushport,pullport);
    }
    return this;
  }
  
  // --- 
  // debug stuff
  
  public String report(int level, int detail) {
    String report = "";
    String tabs = ("                ").substring(0,(level+1)*2);
    report += tabs+"Port "+this+": ";
    report += "\tsrc "+src()+"\tres "+get();
    if (pushport!=null) report += "\tpush: "+pushport;
    if (pullport!=null) report += "\tpull: "+pullport;
    return report;
  }
}

