def generate_Graph:
  G = nx.DiGraph()
  G.add_nodes_from(nodes)
  G.add_edges_from(edges)
  indegree = dict(G.in_degree())
  outdegree = dict(G.out_degree())
  
# set weight & time for each edge in LT model
  attr = {}
  for ed in G.edges():
      m = 5/(outdegree.get(ed[1])+5)
      t = np.random.geometric(m)
      if t > 15:
          t = random.randint(1, 15)
      attr[ed] = {'P':1/indegree.get(ed[1]),'T':t}

  nx.set_edge_attributes(G, attr)

  # set threshold for each node in LT model
  attr_n = {}
  for n in G.nodes():
    attr_n[n] = random.uniform(0.1,1)
    
  nx.set_node_attributes(G,attr_n,'Thre')
  
  return G
