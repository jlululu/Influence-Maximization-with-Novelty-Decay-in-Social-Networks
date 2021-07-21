# Algorithm 5: Update for new seed
def update(G, s, d1, d2, activated, Inf):
  q = queue.Queue(); L = [s]; level = 1
  L.append(s)
  while not q.empty():
    size = q.qsize(); level += 1
    for i in range(size):
      u = q.get()
      for w in list(set(G.neighbors(u)) - set(activated)):
        p = G[u][w]['P']
        thre = G.nodes[w]['Thre']
        if p >= thre:
          activated.append(w)
          L.append(w)
          G.nodes[w]['Thre'] = 0
          if level <= d2:
            q.put(w)
        else:
          G.nodes[w]['Thre'] -= p
        updateIncome(G,w,d1,activated,Inf)
