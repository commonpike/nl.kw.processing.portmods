package nl.kw.processing.mods;

import processing.core.*;

class ModPlotter {

    public ModPort in;
    public double[] rangein = {-100,100};
    public double step=1;
    
    public ModPort outx;
    public double[] rangex = {-100,100};
    public double[] domainx = {-100,100};
     
    public ModPort outy;
    public double[] rangey = {-100,100};
    public double[] domainy = {-100,100};
     
    ModPlotter() {
      // javac is stupid
    }
    
    ModPlotter(ModPort in, ModPort outx, ModPort outy) {
      this.ports(in,outx,outy);
    }
    
    // ---------------
    // shorthand
    
    public ModPlotter in(ModPort in, double min, double max, double step) {
       return this.setPortIn(in).setRangeIn(min,max,step);
    }
    public ModPlotter outx(ModPort outx, double min, double max) {
      return this.setPortX(outx).setDomainX(min,max);
    }
    public ModPlotter outy(ModPort outy, double min, double max) {
       return this.setPortY(outy).setDomainY(min,max);
    }
    public ModPlotter ports(ModPort in, ModPort outx, ModPort outy) {
      return this.setPortIn(in).setPortX(outx).setPortY(outy);
    }
    public ModPlotter range(double min, double max) {
       return this.setRangeIn(min,max);
    }
    public ModPlotter range(double min, double max, double step) {
       return this.setRangeIn(min,max,step);
    }
    public ModPlotter rangex(double min, double max) {
       return this.setRangeX(min,max);
    }
    public ModPlotter rangey(double min, double max) {
       return this.setRangeY(min,max);
    }
    public ModPlotter domain(double xmin, double ymin, double xmax, double ymax) {
       this.setDomainX(xmin,xmax);
       this.setDomainY(ymin,ymax);
       return this;
    }
    public ModPlotter domainx(double min, double max) {
       return this.setDomainX(min,max);
    }
    public ModPlotter domainy(double min, double max) {
       return this.setDomainY(min,max);
    }
    
    // ---------------
    // methods
    
    public ModPlotter setPortIn(ModPort in) {
      this.in = in; 
      return this;
    }
    public ModPlotter setRangeIn(double min, double max) {
       this.rangein[0]=min;
       this.rangein[1]=max;
       return this;
    }
    public ModPlotter setRangeIn(double min, double max, double step) {
       this.rangein[0]=min;
       this.rangein[1]=max;
       this.step = step;
       return this;
    }
    public ModPlotter setPortX(ModPort outx) {
      this.outx = outx; 
      return this;
    }
    public ModPlotter setRangeX(double min, double max) {
       this.rangex[0]=min;
       this.rangey[1]=max;
       return this;
    }
    public ModPlotter setDomainX(double min, double max) {
       this.domainx[0]=min;
       this.domainx[1]=max;
       return this;
    }
    public ModPlotter setPortY(ModPort outy) {
      this.outy = outy; 
      return this;
    }
    public ModPlotter setRangeY(double min, double max) {
       this.rangey[0]=min;
       this.rangey[1]=max;
       return this;
    }
    public ModPlotter setDomainY(double min, double max) {
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
      double fx = (domainx[1]-domainx[0])/(rangex[1]-rangex[0]);
      double fy = (domainy[1]-domainy[0])/(rangey[1]-rangey[0]);
      double dx = domainx[0]-fx*rangex[0];
      double dy = domainy[0]-fy*rangey[0];
      for (double i=rangein[0];i<rangein[1];i+=step) {
        in.set(i);
        double x = dx+fx*outx.get();
        double y = dy+fy*outy.get();
        //processing.core.PApplet.println(i,x,y);
        applet.point((float)x,(float)y);
      }
    }
    
    public double[][] points() { 
      double fx = (domainx[1]-domainx[0])/(rangex[1]-rangex[0]);
      double fy = (domainy[1]-domainy[0])/(rangey[1]-rangey[0]);
      double dx = domainx[0]-fx*rangex[0];
      double dy = domainy[0]-fy*rangey[0];
      
      int steps = (int)Math.floor(rangein[1]-rangein[0]/step);
      double[][] points = new double[steps][];
      int c = 0;
      for (double i=rangein[0];i<rangein[1];i+=step) {
        in.set(i);
        double x = dx+fx*outx.get();
        double y = dy+fy*outy.get();
        //processing.core.PApplet.println(i,x,y);
        points[c++] = new double[] {x,y};
      }
      return points;
    }
    
    public PShape shape(PApplet applet) { 
      return shape(applet,true);
    }
    
    public PShape shape(PApplet applet,boolean close) { 
      PShape s = applet.createShape();
      s.beginShape();
      double fx = (domainx[1]-domainx[0])/(rangex[1]-rangex[0]);
      double fy = (domainy[1]-domainy[0])/(rangey[1]-rangey[0]);
      double dx = domainx[0]-fx*rangex[0];
      double dy = domainy[0]-fy*rangey[0];
      for (double i=rangein[0];i<rangein[1];i+=step) {
        in.set(i);
        double x = dx+fx*outx.get();
        double y = dy+fy*outy.get();
        //processing.core.PApplet.println(i,x,y);
        s.vertex((float)x,(float)y);
      }
      if (close) s.endShape(processing.core.PApplet.CLOSE);
      else s.endShape();
      return s;
    }
  }