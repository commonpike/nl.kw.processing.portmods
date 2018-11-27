package nl.kw.processing.mods;

import processing.core.*;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.*;

public class Mod {

  public boolean debug  = false; 
  protected boolean isProcessing  = false;
  protected boolean hasChangedPorts  = true;
  protected UUID processid;

  public String name  = null;

  private Hashtable<String, Mod> mods = new Hashtable<String, Mod>();
  private Hashtable<String, ModPort> ports = new Hashtable<String, ModPort>();

  // ------------
  // startup
  
  Mod() {
    this.name = this.getClass().getSimpleName();
    this.debug("new Mod", this);
    // set your mod up here
  }
  
  // ------------
  // identifiers
  
  public String toString() {
    return this.name;
  }
  
  // ------------
  // process stuff
  
  protected boolean needsProcessing() {
    if (this.hasChangedPorts) {
      this.debug(this,"needsProcessing");
      return true;
    }
    for (ModPort port : ports.values()) {
      if (port.needsProcessing()) {
        return true;
      }
    }
    return false;
  }
  
  protected void process() {
    this.process(UUID.randomUUID());
  }
  
  protected void process(UUID pid) {
    this.debug();
    this.debug(this,"process",pid);
    if (this.isProcessing) {
      throw new RuntimeException("Mod.process: "+this.name+" is already processing - feedback !");
    }
    if (this.processid==pid) {
      this.debug(this,"already processed !",pid);
    }
    
    
    // now here is the recurse magic. because calc calls
    // get() on the input ports, if the input
    // ports have a pull port, they will call get()
    // on that pull port, which will trigger the other
    // mod to call process(). so we dont have to do
    // all that here, we can just say 
    
    this.processid = pid;
    this.isProcessing = true;
    this.calc();
    this.isProcessing = false;
    this.hasChangedPorts = false;
    this.debug(this,"process","done",pid);
    this.debug();
  }
  

  protected void calc() {
    this.warn(this,"calc","not implemented");
    // up to you 
    return;
  }
  
  // --------------------
  // port stuff
  
  // shorthand
  public ModPort addPort(String portname) { return this.addPort(portname,new ModPort(this,portname)); }
  public ModPort port(String portname) { return this.getPort(portname); }
  public ModPort port(String modname, String portname) { return this.getMod(modname).getPort(portname); }

  // methods
  
  public ModPort addPort(String portname, ModPort port) {
    this.debug(this,"addPort",portname);
    if (this.ports.containsKey(portname)) {
      throw new RuntimeException("Mod.addPort: name "+portname+" is already in use");
    } else {
      port.mod = this;
      if (port.name==null) port.name = portname;
      this.ports.put(portname,port);
    }
    return port;
  }
  
  public ModPort getPort(String portname) {
    ModPort port = this.ports.get(portname);
    if (port == null) {
      throw new RuntimeException("Mod.getPort: no such Port '"+portname+"'");
    } 
    return port;
  }
  
  // --------------------
  // mod stuff
  
  // shorthand
  public Mod mod(String modname) { return this.getMod(modname); }
  public Mod addMod(Mod mod) { return this.addMod(mod.getClass().getSimpleName()+(this.mods.size()+1),mod); }
      
  
  // methods
  
  public boolean hasMod(Mod mod) {
   return this.mods.containsValue(mod);
  }
  
  public boolean hasMod(String modname) {
   return this.mods.containsKey(modname);
  }
     
  public Mod addMod(String modname, Mod mod) {
   this.debug(this,"AddMod",modname);
   if (this.mods.containsKey(modname)) {
      throw new RuntimeException("Mod.addMod: name "+modname+" is already in use");
   } else {
      this.mods.put(modname,mod);
      // there is a chance we override 
      // another name here
      mod.name = modname;
   }
   return mod;
  }
  
  public Mod getMod(String modname) {
    Mod mod = this.mods.get(modname);
    if (mod == null) {
      throw new RuntimeException("Mod.getMod: no such Mod '"+modname+"'");
    }  
    return mod;
    
  }
  
  // --------------------
  // value stuff
  
  // shorthand
  public double get(String portname) { return this.getResult(portname); }
  public double get(String modname, String portname) { return this.getResult(modname,portname); }
  public Mod set(String portname, double value) { return this.setSource(portname,value); }
  public Mod set(String modname, String portname, double value) { return this.setSource(modname,portname,value); }
  
  // methods
  public double getResult(String portname) {
    return this.getPort(portname).getResult();
  }
  public double getResult(String modname, String portname) {
    return this.getMod(modname).getResult(portname);
  }
  public Mod setSource(String portname, double value) {
    this.getPort(portname).setSource(value);
    return this;
  }
  public Mod setSource(String modname, String portname, double value) {
    this.getMod(modname).setSource(portname,value);
    return this;
  }
  
  // ---
  // plotter 
  

  public ModPlotter plotter() {
    // override this for default
    // ports for your own mod
    throw new RuntimeException(this.getClass().getName()+".plot(): specify plotting ports");
  }
  
  public ModPlotter plotter(String in,String outx, String outy) {
    return this.plotter(this.port(in),this.port(outx),this.port(outy));
  }
  
  public ModPlotter plotter(ModPort in,ModPort outx, ModPort outy) {
    return new ModPlotter(in,outx,outy);
  }
  
  
  
  // --- 
  // debug stuff
  
  
  
  public String report() {
    return report(0,0);
  }
  
  public String report(int level, int detail) {
    String report = "";
    String tabs  = ("                ").substring(0,level*2);
    String tabs2 = ("                ").substring(0,(level+1)*2);
    report += tabs+"Mod "+this+"\n";
    for (String portname : ports.keySet()) {
      report += port(portname).report(level,detail);
      report += "\n";
    }
    if (mods.size()>0) {
      for (String modname : mods.keySet()) {
        report += mod(modname).report(level+1,detail)+"\n";
      }
    }
    return report;
  }
  
  public void warn(Object ... args) { 
    String msg = "", sep ="";
    for (Object arg : args) {
      msg += sep+arg.toString();
      sep = ", ";
    }
    processing.core.PApplet.println("! "+msg);
    
  }

  public void debug(Object ... args) { 
    String msg = "", sep ="";
    if (debug) {
      for (Object arg : args) {
        msg += sep+arg.toString();
        sep = ", ";
      }
      processing.core.PApplet.println("* "+msg);
    }
  }
  
}






 