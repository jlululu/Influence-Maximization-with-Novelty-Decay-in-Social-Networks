package IMND;

import java.util.*;

public class Edge{
    int time;
    double weight;
    Node start, end;
	
    Edge(){
      this.weight = -1;
    }

    Edge(Node start, Node end){
      this.start = start;
      this.end = end; 
      this.weight = 1.0/end.indegree;
      Random rand = new Random();
      this.time = rand.nextInt(15)+1;
      // set time using a geometric distribution
      // double temp = 5.0/(end.outdegree+5);
	    // this.time = Geometric(temp);
      // if(this.time > 15)
        // this.time = rand.nextInt(15)+1;
    }
	
    private int Geometric(double p){
      int re = 0;
      while(true){
        re ++;
        if(Math.random() < p)
          break;
      }
      return re;
    }
    
    public static void main(String[] args){}
}
