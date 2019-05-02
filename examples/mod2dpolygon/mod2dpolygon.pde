import nl.kw.processing.mods.*;

int square;
int rotate = 0;

void settings() {
  square = min(displayWidth/2,displayHeight/2);
  size(square,square);
}

void setup() {
  fill(0xff000000);
  noStroke();
  rect(0,0,square,square);
  fill(0xff000000);
  stroke(0xffffffff);
}

void draw() {
  stage();
  
  // --------------
  
  Mod2dPolygon mod = new Mod2dPolygon();
  for (int i = 0; i<4; i++) {
    for (int j = 0; j<4; j++) {
      float c=2+4*i+j;
      mod.set("sides",c)
        .set("rotate",rotate)
        .set("ampx",40)
        .set("ampy",40);
      PShape poly = mod.plotter()
        .range(0,100,100/c)
        .shape(this);
      shape(poly,(j-2)*100+50,(2-i)*100-50);
    }
  }
  rotate++;
  
  // --------------
  
}

void stage() {
  
   pushStyle();
   fill(0x22000000);
   rect(0,0,square,square);
   popStyle();
   
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
