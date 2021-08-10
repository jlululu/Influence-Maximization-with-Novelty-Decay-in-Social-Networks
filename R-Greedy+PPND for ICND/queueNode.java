package IMND.IC;

import java.util.*;

public class queueNode{
    int time;
	  double p_con;
    Set<Node> path;
	  Node from_node;
	  Node to_node;
	
    queueNode(int time, double p_con, Node from_node, Node to_node, Set<Node> path){
      this.time = time;
      this.p_con = p_con;
      this.from_node = from_node;
      this.to_node = to_node;
      this.path = path;
    }
	
    queueNode(int time, Node from_node, Node to_node){
      this.time = time;
      this.from_node = from_node;
      this.to_node = to_node;
    }
  
    public static void main(String[] args){}
}
