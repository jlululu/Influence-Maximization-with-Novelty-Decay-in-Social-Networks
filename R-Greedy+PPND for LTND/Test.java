import IMND.*;
import java.util.*;
import java.io.*;

public public class Test{
  public static double Inf_ND(int cnt, List<double[]> Pcon_list, int curr, int con, double pro, double coe, double add){
	if(cnt == curr)
	  return pro*Math.pow(0.3,con)+add;
    double t1 = Pcon_list.get(curr)[0], t2 = Pcon_list.get(curr)[1];
	return Inf_ND(cnt,Pcon_list,curr+1,con+1,pro*t2,coe*t2,add+coe*t2*t1*Math.pow(0.3,con))+Inf_ND(cnt,Pcon_list,curr+1,con,pro*(1-t2),coe*(1-t2),add);
  }

  public static double PPND_Inf_LT(DiGraph G, Set<Node> S){
	int C = 5;
    double theta = 0.04, sigma = 0;
    Set<Node> V = G.nodes;
	Map<Integer,Node> M = G.m;
    Map<List<Node>,Edge> E = G.edges;
    Map<Node,Integer> Count = new HashMap<Node,Integer>();
    Map<Node,List<Double>> PPND = new HashMap<Node,List<Double>>();
	Map<Node,List<double[]>> Pcon = new HashMap<Node,List<double[]>>();
    PriorityQueue<queueNode> PH = new PriorityQueue<queueNode>(new Comparator<queueNode>(){
      @Override
      public int compare(queueNode o1, queueNode o2){
        return o1.time - o2.time;
      }
    });
    for(Node s : S){
	  Set<Node> visited = new HashSet<Node>();
      visited.add(s);
      PH.offer(new queueNode(0,1.0,new Node(),s,visited));
    }
    while(!PH.isEmpty()){
      queueNode q = PH.poll();
      int time = q.time;
	  double pcon = q.p_con;
      Node u = q.from_node;
	  Node w = q.to_node;
	  Set<Node> path = q.path;
	  int t = Count.getOrDefault(w,0);
	  if(t >= C)
	    continue;
	  Edge v = new Edge();
	  double inf = 1.0;
	  if(u.label != -1){
		v = E.get(Arrays.asList(u,w));
	    inf = Inf_ND(t,Pcon.getOrDefault(w,new ArrayList<double[]>()),0,0,v.weight*pcon,1.0,0.0);
	  }
	  if(inf >= theta){
	    Count.put(w,t+1);
	    List<double[]> list1 = Pcon.getOrDefault(w,new ArrayList<double[]>());
	    list1.add(new double[]{v.weight,pcon});
	    Pcon.put(w,list1);
	    List<Double> list2 = PPND.getOrDefault(w,new ArrayList<Double>());
	    list2.add(inf);
	    PPND.put(w,list2);
		for(int ind : w.successor){
		  Node next = M.get(ind);
		  if(path.contains(next))
			continue;
		  Edge v_next = E.get(Arrays.asList(w,next));
		  Set<Node> path_new = new HashSet<Node>();
		  path_new.addAll(path);
	      path_new.add(next);
		  PH.offer(new queueNode(time+v_next.time,inf,w,next,path_new));
		}
	  }
    }
	for(List<Double> list : PPND.values()){
	  double pro = 1.0;
	  for(double k : list)
		pro *= (1-k);
	  sigma += 1-pro;
	}
    return sigma;
  }

//   R-Greedy + PPND
  public static Set<Node> R_Greedy_LT(DiGraph G, int k, Set<Node> S_prev1, Set<Node> visited1, Map<Node,Double> Q1, Map<Node,Double> Q_prev1, TreeSet<Node> Q_list1){
	  Map<Integer,Node> M = G.m;
    Set<Node> S_prev = new HashSet<Node>();
    Set<Node> visited = new HashSet<Node>();
    Map<Node,Double> Q = new HashMap<Node,Double>();
    Map<Node,Double> Q_prev = new HashMap<Node,Double>();
    TreeSet<Node> Q_list = new TreeSet<Node>(new Comparator<Node>(){
      @Override
      public int compare(Node o1, Node o2){
        return Double.compare(Q.get(o2),Q.get(o1));
      }
    });
    S_prev.addAll(S_prev1);
    visited.addAll(visited1);
    Q.putAll(Q1);
    Q_prev.putAll(Q_prev1);
    Q_list.addAll(Q_list1);
    double sigma_prev = 0.0; Node s_prev = new Node();
    for(int i = 0;i < k;i ++){
      double maxMarInf = Double.MIN_VALUE; Node s_curr = new Node();
      Set<Node> vis = new HashSet<Node>();
      for(Node u : Q_list){
        if(Q.get(u) < maxMarInf)
          break;
        if(visited.contains(u) && (Q_prev.get(u)+Q.getOrDefault(s_prev,0.0)-sigma_prev<maxMarInf))
          continue;
	      vis.add(u);
        Set<Node> set = new HashSet<Node>();
        set.addAll(S_prev);
        set.add(u);
        double temp = PPND_Inf_LT(G,set);
        Q_prev.put(u,temp);
        if(temp-sigma_prev>maxMarInf){
          maxMarInf = temp-sigma_prev;
          s_curr = u;
        }
      }
      if(s_curr.label == -1)
        break;
	    Q_list.remove(s_curr);
      S_prev.add(s_curr);
      sigma_prev += maxMarInf;
      visited = new HashSet<Node>();
	    visited.addAll(vis);
      s_prev = s_curr;
    }
    return S_prev;
  }
  
  // Degree_ND
  public static Set<Node> Degree(DiGraph G, int k){
    Set<Node> re = new HashSet<Node>();
	Set<Node> V = G.nodes;
	Map<Integer,Node> M = G.m;
	Map<Node,Integer> Degree = new HashMap<Node,Integer>();
	for(Node v : V)
	  Degree.put(v,v.successor.size()+v.predecessor.size());
    List<Map.Entry<Node,Integer>> list = new ArrayList<Map.Entry<Node,Integer>>(Degree.entrySet());
	  Collections.sort(list,new Comparator<Map.Entry<Node,Integer>>(){
	    @Override
	    public int compare(Map.Entry<Node,Integer> o1, Map.Entry<Node,Integer> o2){
		  return o2.getValue()-o1.getValue();
	    }
	  });
	for(int i = 0;i < k;i ++){
	  Node s = list.get(i).getKey();
	  re.add(s);
	}
    return re;
  }
 
  // MC_LT
  public static double Inf_LT(DiGraph G, Set<Node> S){
	double sigma = 0.0;
	Map<Integer,Node> M = G.m;  
	Map<List<Node>,Edge> E = G.edges;
	for(int i = 0;i < 10000;i ++){
	  Map<Node,Double> H = new HashMap<Node,Double>();
	  Set<Node> activated = new HashSet<Node>();
	  PriorityQueue<queueNode> q = new PriorityQueue<queueNode>(new Comparator<queueNode>(){
          @Override
          public int compare(queueNode o1, queueNode o2){
            return o1.time - o2.time;
          }
        });
	  Map<Node,Integer> Count = new HashMap<Node,Integer>();
	  for(Node s : S)
	    q.offer(new queueNode(0,new Node(),s));
	  while(!q.isEmpty()){
	    queueNode curr = q.poll();
		Node u = curr.from_node;
		Node w = curr.to_node;
		int time = curr.time;
		int t = Count.getOrDefault(w,0);
		double h = -1;
		if(u.label != -1){
		  Edge v = E.get(Arrays.asList(u,w));
		  h = H.getOrDefault(w,Math.random())-v.weight*Math.pow(0.3,t);
		}
		if(h <= 0){
		  activated.add(w);
		  sigma ++;
		  for(int ind : w.successor){
			Node next = M.get(ind);
			if(activated.contains(next))
		      continue;
			Edge v_next = E.get(Arrays.asList(w,next));
			q.offer(new queueNode(time+v_next.time,w,next));
		  }
	    }
		else{
		  Count.put(w,t+1);
		  H.put(w,h);
		}
      }
	}
    return sigma/10000;
  }
  
//   R-Greedy + Inf_LT
  public static Set<Node> R_Greedy_LT1(DiGraph G, int k, Set<Node> S_prev1, Set<Node> visited1, Map<Node,Double> Q1, Map<Node,Double> Q_prev1, TreeSet<Node> Q_list1){
	Map<Integer,Node> M = G.m;
    Set<Node> S_prev = new HashSet<Node>();
    Set<Node> visited = new HashSet<Node>();
    Map<Node,Double> Q = new HashMap<Node,Double>();
    Map<Node,Double> Q_prev = new HashMap<Node,Double>();
    TreeSet<Node> Q_list = new TreeSet<Node>(new Comparator<Node>(){
      @Override
      public int compare(Node o1, Node o2){
        return Double.compare(Q.get(o2),Q.get(o1));
      }
    });
    S_prev.addAll(S_prev1);
    visited.addAll(visited1);
    Q.putAll(Q1);
    Q_prev.putAll(Q_prev1);
    Q_list.addAll(Q_list1);
    double sigma_prev = 0.0; Node s_prev = new Node();
    for(int i = 0;i < k;i ++){
      double maxMarInf = Double.MIN_VALUE; Node s_curr = new Node();
      Set<Node> vis = new HashSet<Node>();
      for(Node u : Q_list){
        if(Q.get(u) < maxMarInf)
          break;
        if(visited.contains(u) && (Q_prev.get(u)+Q.getOrDefault(s_prev,0.0)-sigma_prev<maxMarInf))
          continue;
	      vis.add(u);
        Set<Node> set = new HashSet<Node>();
        set.addAll(S_prev);
        set.add(u);
        double temp = Inf_LT(G,set);
        Q_prev.put(u,temp);
        if(temp-sigma_prev>maxMarInf){
          maxMarInf = temp-sigma_prev;
          s_curr = u;
        }
      }
      if(s_curr.label == -1)
        break;
	  Q_list.remove(s_curr);
      S_prev.add(s_curr);
      sigma_prev += maxMarInf;
      visited = new HashSet<Node>();
	  visited.addAll(vis);
      s_prev = s_curr;
    }
    return S_prev;
  }

  public static void main(String[] args)throws IOException , FileNotFoundException{
    // read in 
	Scanner sc = new Scanner(new File("wiki.txt"));
    int len = sc.nextInt();
    Set<Integer> set = new HashSet<Integer>();
    int[][] arr = new int[len][2];
    for(int i = 0;i < len;i ++){
      arr[i][0] = sc.nextInt();
      arr[i][1] = sc.nextInt();
      set.add(arr[i][0]);
      set.add(arr[i][1]);
    }
    DiGraph G = new DiGraph(set,arr);
	
	Set<Node> V = G.nodes;
	// initialize for R_Greedy_LT
    Set<Node> S_prev1 = new HashSet<Node>();
    Set<Node> visited1 = new HashSet<Node>();
    Map<Node,Double> Q1 = new HashMap<Node,Double>();
    Map<Node,Double> Q_prev1 = new HashMap<Node,Double>();
    TreeSet<Node> Q_list1 = new TreeSet<Node>(new Comparator<Node>(){
      @Override
      public int compare(Node o1, Node o2){
        return Double.compare(Q1.get(o2),Q1.get(o1));
      }
    });
    for(Node v : V){
      Set<Node> s = new HashSet<Node>();
      s.add(v);
      double temp = PPND_Inf_LT(G,s);
      Q1.put(v,temp);
      visited1.add(v);
      Q_prev1.put(v,temp);
	    Q_list1.add(v);
    }
	// initialize for R_Greedy_LT1
    Set<Node> S_prev2 = new HashSet<Node>();
    Set<Node> visited2 = new HashSet<Node>();
    Map<Node,Double> Q2 = new HashMap<Node,Double>();
    Map<Node,Double> Q_prev2 = new HashMap<Node,Double>();
    TreeSet<Node> Q_list2 = new TreeSet<Node>(new Comparator<Node>(){
      @Override
      public int compare(Node o1, Node o2){
        return Double.compare(Q2.get(o2),Q2.get(o1));
      }
    });
    for(Node v : V){
      Set<Node> s = new HashSet<Node>();
      s.add(v);
      double temp = Inf_LT(G,s);
      Q2.put(v,temp);
      visited2.add(v);
      Q_prev2.put(v,temp);
	    Q_list2.add(v);
    }
	
    System.out.println("R_Greedy_LT:");
    for(int k = 10;k <= 100;){
      long start1 = System.currentTimeMillis();
        Set<Node> R_seed = R_Greedy_LT(G,k,S_prev1,visited1,Q1,Q_prev1,Q_list1);
      long elapsedTimeMillis1 = System.currentTimeMillis()-start1;
        double inf_R = Inf_LT(G,R_seed);
      System.out.println("time="+elapsedTimeMillis1/1000F+"seed number="+R_seed.size()+"inf="+inf_R);
      k += 10;
    }
    
    System.out.println("R_Greedy_LT1:");
    for(int k = 10;k <= 100;){
      long start2 = System.currentTimeMillis();
      Set<Node> R_seed = R_Greedy_LT1(G,k,S_prev2,visited2,Q2,Q_prev2,Q_list2);
      long elapsedTimeMillis2 = System.currentTimeMillis()-start2;
      double inf_R = Inf_LT(G,R_seed);
      System.out.println("time="+elapsedTimeMillis2/1000F+"seed number="+R_seed.size()+"inf="+inf_R);
      k += 10;
    }
	
    System.out.println("Degree:");
    for(int k = 10;k <= 100;){
      long start3 = System.currentTimeMillis();
      Set<Node> seed = Degree(G,k);
      long elapsedTimeMillis3 = System.currentTimeMillis()-start3;
      double inf_D = Inf_LT(G,seed);
      System.out.println("time="+elapsedTimeMillis3/1000F+"seed number="+seed.size()+"inf="+inf_D);	
      k += 10;
    }
  }
}
