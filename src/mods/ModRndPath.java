package nl.kw.processing.portmods;

import processing.core.*;
import java.util.Random;

public class ModRndPath extends ModPath {

   public ModRndPath() {
     super();
     
     this.random(10);

     debug();
   }
   
   public void random(int max) {
      this.clear();
      Random r = new Random();
      int num = r.nextInt(max-2)+2;
      for (int p =0; p<num; p++) {
        float i = Math.abs(r.nextFloat()*100);
        float v = r.nextFloat()*200-100;
        this.point(i,v);
      }
   }
   
}