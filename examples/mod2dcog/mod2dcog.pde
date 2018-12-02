import nl.kw.processing.mods.*;

int square;
float rotate=0;

Mod cog = new Mod2dCog();
ModPlotter plotter = cog.plotter();
  
void settings() {
  square = min(displayWidth/2,displayHeight/2);
  size(square,square);
}

void setup() {
  
  stroke(0xffffffff);
  strokeWeight(4);
  fill(0xaa000000);
  cog.set("cogs",25).set("inner",90).set("outer",100);

  // blackout
  rect(0,0,square,square);

}

void draw() {

  stage();
  fade(0x33000000);
  
  cog.set("phase",-rotate);
  shape(plotter.shape(this));
  rotate += .5;

}

void stage() {
   translate(width/2,height/2); 
   scale(1,-1);
   grid(25,1);
   axis();
   
}

public void fade(color c) {
  pushStyle();
  blendMode(BLEND);
  fill(c);
  noStroke();
  rect(-width/2,-height/2,width,height);
  popStyle();
}

public void grid(int res, int stroke) {
   pushStyle();
   blendMode(BLEND);
   stroke(0x66aaaaaa);
   int minx = round(-width/(2*res))*res;
   for (int x=minx; x<width/2; x+=res ) {
     if (abs(x)<res) strokeWeight(1);
     else strokeWeight(stroke);
     line(x,-height/2,x,height/2);     
   }
   line(-width/2,0,width/2,0);
   int miny = round(-height/(2*res))*res;
   for (int y=miny; y<height/2; y+=res ) {
      if (abs(y)<res) strokeWeight(1);
     else strokeWeight(stroke);
     line(-width/2,y,width/2,y);     
   }
   if (res<width/2) {
      this.grid(res*4,stroke*2); 
   }
   popStyle();
}

public void axis() {
   pushStyle();
   blendMode(BLEND);
   stroke(0xffff0000);
   strokeWeight(1);
   line(-this.width/2,0,this.width/2,0);
   line(0,-this.height/2,0,this.height/2);
   popStyle();
}
