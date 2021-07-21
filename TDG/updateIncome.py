# Algorithm 6: Update incoming neighbor influence
def updateIncome(G, u, d1, activated, Inf):
  V = G.nodes(); q = queue.Queue(); L = [u]; level = 1
  q.put(u)
  while not q.empty():
    size = q.qsize(); level += 1
    for i in range(size):
      v = q.get()
      for w in list(set(G.predecessors(v))-set(activated)-set(L)):
        Inf_compute(G,w,d1,Inf)
        if G[w][v] >= G.nodes[v]['Thre'] and level <= d1:
          q.put(w)
