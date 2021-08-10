package IMND;

import java.util.*;

public class Node{
    int label, indegree, outdegree;
    double threshold;
    List<Integer>successor;
    List<Integer>predecessor;

    Node(){
		this.label = -1;
	}

    Node(int label, List<Integer> successor, List<Integer> predecessor){
      Random rand = new Random();
      this.label = label;
      this.indegree = predecessor.size();
      this.outdegree = successor.size();
	  // this.threshold = 0.8;
      this.threshold = 1-rand.nextDouble()*0.9;
      this.successor = successor;
      this.predecessor = predecessor;
    }
    
    public static void main(String[] args){}
}
