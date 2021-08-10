package IMND;

import java.util.*;

public class DiGraph{ 
    Set<Node> nodes;
    Map<List<Node>,Edge> edges;
	  Map<Integer,Node> m;

    DiGraph(Set<Integer> set, int[][] arr){
      this.nodes = new HashSet<Node>();
      this.edges = new HashMap<List<Node>,Edge>();
	    this.m = new HashMap<Integer,Node>();
      Map<Integer,List<Integer>> succ = new HashMap<Integer,List<Integer>>();
      Map<Integer,List<Integer>> pred = new HashMap<Integer,List<Integer>>();
      for(int[] ed : arr){
        List<Integer> su = succ.getOrDefault(ed[0],new ArrayList<Integer>());
        List<Integer> pre = pred.getOrDefault(ed[1],new ArrayList<Integer>());
        su.add(ed[1]);
        pre.add(ed[0]);
        succ.put(ed[0],su);
        pred.put(ed[1],pre);
      }
      for(int n : set){
        Node node = new Node(n,succ.getOrDefault(n,new ArrayList<Integer>()),pred.getOrDefault(n,new ArrayList<Integer>()));
        this.nodes.add(node);
        m.put(n,node);
      }
      for(int[] ed : arr){
        Node s = m.get(ed[0]), en = m.get(ed[1]);
        this.edges.put(Arrays.asList(s,en),new Edge(s,en));
      }
    }
  
    public static void main(String[] args){}
}  
