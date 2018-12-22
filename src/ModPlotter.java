package nl.kw.processing.mods;

import processing.core.*;

public class ModPlotter {

    public ModPort in;
    public float[] rangein = {-100,100};
    public float step=1;
    
    public ModPort outx;
    public float[] rangex = {-100,100};
    public float[] domainx = {-100,100};
     
    public ModPort outy;
    public float[] rangey = {-100,100};
    public float[] domainy = {-100,100};
     
    ModPlotter() {
      // javac is stupid
    }
    
    ModPlotter(ModPort in, ModPort outx, ModPort outy) {
      this.ports(in,outx,outy);
    }
    
    // ---------------
    // shorthand
    
    public ModPlotter in(ModPort in, float min, float max, float step) {
       return this.setPortIn(in).setRangeIn(min,max,step);
    }
    public ModPlotter outx(ModPort outx, float min, float max) {
      return this.setPortX(outx).setDomainX(min,max);
    }
    public ModPlotter outy(ModPort outy, float min, float max) {
       return this.setPortY(outy).setDomainY(min,max);
    }
    public ModPlotter ports(ModPort in, ModPort outx, ModPort outy) {
      return this.setPortIn(in).setPortX(outx).setPortY(outy);
    }
    public ModPlotter range(float min, float max) {
       return this.setRangeIn(min,max);
    }
    public ModPlotter range(float min, float max, float step) {
       return this.setRangeIn(min,max,step);
    }
    public ModPlotter rangex(float min, float max) {
       return this.setRangeX(min,max);
    }
    public ModPlotter rangey(float min, float max) {
       return this.setRangeY(min,max);
    }
    public ModPlotter step(float step) {
       this.step=step;
       return this;
    }
    public ModPlotter domain(float xmin, float ymin, float xmax, float ymax) {
       this.setDomainX(xmin,xmax);
       this.setDomainY(ymin,ymax);
       return this;
    }
    public ModPlotter domainx(float min, float max) {
       return this.setDomainX(min,max);
    }
    public ModPlotter domainy(float min, float max) {
       return this.setDomainY(min,max);
    }
    
    // ---------------
    // methods
    
    public ModPlotter setPortIn(ModPort in) {
      this.in = in; 
      return this;
    }
    public ModPlotter setRangeIn(float min, float max) {
       this.rangein[0]=min;
       this.rangein[1]=max;
       return this;
    }
    public ModPlotter setRangeIn(float min, float max, float step) {
       this.rangein[0]=min;
       this.rangein[1]=max;
       this.step = step;
       return this;
    }
    public ModPlotter setPortX(ModPort outx) {
      this.outx = outx; 
      return this;
    }
    public ModPlotter setRangeX(float min, float max) {
       this.rangex[0]=min;
       this.rangey[1]=max;
       return this;
    }
    public ModPlotter setDomainX(float min, float max) {
       this.domainx[0]=min;
       this.domainx[1]=max;
       return this;
    }
    public ModPlotter setPortY(ModPort outy) {
      this.outy = outy; 
      return this;
    }
    public ModPlotter setRangeY(float min, float max) {
       this.rangey[0]=min;
       this.rangey[1]=max;
       return this;
    }
    public ModPlotter setDomainY(float min, float max) {
       this.domainy[0]=min;
       this.domainy[1]=max;
       return this;
    }
    
    // ---------------
    // drawing methods
    
    // eg
    // rangex 10,30 
    // domainx -20,20 
    // fx = (20--20)/(30-10) = 2
    // outx => 20,60
    // dx <= -40 = -20-fx*10
    
    public void plot(PApplet applet) { 
      float fx = (domainx[1]-domainx[0])/(rangex[1]-rangex[0]);
      float fy = (domainy[1]-domainy[0])/(rangey[1]-rangey[0]);
      float dx = domainx[0]-fx*rangex[0];
      float dy = domainy[0]-fy*rangey[0];
      for (float i=rangein[0];i<rangein[1];i+=step) {
        in.set(i);
        float x = dx+fx*outx.get();
        float y = dy+fy*outy.get();
        //processing.core.PApplet.println(i,x,y);
        applet.point((float)x,(float)y);
      }
    }
    
    public float[][] points() { 
      float fx = (domainx[1]-domainx[0])/(rangex[1]-rangex[0]);
      float fy = (domainy[1]-domainy[0])/(rangey[1]-rangey[0]);
      float dx = domainx[0]-fx*rangex[0];
      float dy = domainy[0]-fy*rangey[0];
      
      int steps = (int)Math.floor(rangein[1]-rangein[0]/step);
      float[][] points = new float[steps][];
      int c = 0;
      for (float i=rangein[0];i<rangein[1];i+=step) {
        in.set(i);
        float x = dx+fx*outx.get();
        float y = dy+fy*outy.get();
        //processing.core.PApplet.println(i,x,y);
        points[c++] = new float[] {x,y};
      }
      return points;
    }
    
    public PShape shape(PApplet applet) { 
      return shape(applet,true);
    }
    
    public PShape shape(PApplet applet,boolean close) { 
      PShape s = applet.createShape();
      s.beginShape();
      float fx = (domainx[1]-domainx[0])/(rangex[1]-rangex[0]);
      float fy = (domainy[1]-domainy[0])/(rangey[1]-rangey[0]);
      float dx = domainx[0]-fx*rangex[0];
      float dy = domainy[0]-fy*rangey[0];
      for (float i=rangein[0];i<rangein[1];i+=step) {
        in.set(i);
        float x = dx+fx*outx.get();
        float y = dy+fy*outy.get();
        //processing.core.PApplet.println(i,x,y);
        s.vertex((float)x,(float)y);
      }
      if (close) s.endShape(processing.core.PApplet.CLOSE);
      else s.endShape();
      return s;
    }
  }