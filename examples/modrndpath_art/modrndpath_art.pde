import nl.kw.processing.mods.*;

int square;
boolean odd =true;

void settings() {
  square = min(displayWidth/2,displayHeight/2);
  size(square,square);
}

void setup() {
  fill(0xff000000);
  noStroke();
  rect(0,0,square,square);
}

void draw() {
  stage();
  
  // --------------
  
  ModRndPath path = new ModRndPath();
  fill((odd)?0xffffffff:0x00000000);
  for (int j=square;j>=-square;j-=40) {
    for (int i=-square; i<= square; i+=10) {
      path.set("tick",i).set("shift",j).set("amp",40);
      ellipse(path.get("out"),i,10,10);
    }
    path.random(10);
  }
  odd = !odd;
  filter(BLUR, 5);
  
  // --------------
  
}

void stage() {
   translate(width/2,height/2); 
   scale(1,-1);
}
