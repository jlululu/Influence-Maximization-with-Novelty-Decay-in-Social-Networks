# Algorithm 3: Influence Maximization
def TDG(G, K, d1, d2):
  V = G.nodes(); S = []; activated = []; Inf = dict()
  for v in V:
    Inf[v] = 0
    Inf_compute(G,v,d1,activated,Inf)
  for i in range(K):
    temp = list(set(V) - set(activated))
    s = sorted(temp,key=lambda t:Inf.get(t),reverse=True)[0]
    S.append(s)
    activated.append(s)
    update(G,s,d1,d2,activated,Inf)
  return S
