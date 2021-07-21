# Algorithm 4: Compute influence spread
def Inf_compute(G, u, d, activated, inf):
  V = G.nodes(); q = queue.Queue(); L = [u]; level = 0; threshold = dict()
  sigma = 0
  q.put(u)
  for v in V:
    threshold[v] = G.nodes[v]['Thre']
  while not q.empty():
    size = q.qsize(); level += 1
    for i in range(size):
      v = q.get()
      for w in list(set(G.neighbors(v))-set(activated)-set(L)):
        p = G[v][w]['P']
        if p >= threshold.get(w):
          sigma += 1
          if level <= d:
            q.put(w)
          L.append(w)
        else:
          sigma += p/threshold.get(w)
          threshold[w] -= p
  inf[u] = sigma
