import nl.kw.processing.mods.*;

int square;

void settings() {
  square = min(displayWidth/2,displayHeight/2);
  size(square,square);
}

void setup() {
  fill(0xff000000);
  stroke(0xffffffff);
  rect(0,0,square,square);
}

void draw() {
  stage();
  
  // --------------
  
  Mod sin = new ModSin();
  for (int i=-100; i<= 100; i++) {
    sin.set("tick",i).set("amp",100-i);
    point(i,sin.get("out"));
  }
  noLoop();

  
  // --------------
  
}

void stage() {
   translate(width/2,height/2); 
   scale(1,-1);
   grid(25,1);
   axis();
   
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
