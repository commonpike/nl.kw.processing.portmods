
//package nl.kw.processing.mods;

import processing.core.*;
import java.util.Random;

public class Mod2dRndPath extends Mod2dPath {

   public Mod2dRndPath() {
     super();
     
     this.random(25);

     debug();
   }
   
   public void random(int max) {
      this.clear();
      Random r = new Random();
      int num = r.nextInt(max-2)+2;
      for (int p =0; p<num; p++) {
        float i = Math.abs(r.nextFloat()*100);
        float x = r.nextFloat()*200-100;
        float y = r.nextFloat()*200-100;
        this.point(i,x,y);
      }
   }
   
}
