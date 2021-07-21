def PPND_Inf_LT(G, S, C=5):
    V = G.nodes(); activated = []; Count = dict(); threshold = dict(); sigma = 0
    for v in V:
        Count[v] = 0; threshold[v] = G.nodes[v]['Thre']
    PH = queue.PriorityQueue()
    for s in S:
        activated.append(s)
        threshold[s] = 0
        PH.put_nowait((0,[s]))
    while not PH.empty():
        P = PH.get()
        w = P[1][-1]
        neighbors = list(G.neighbors(w))
        for u in list(set(neighbors).difference(set(activated))):
            if Count.get(u) < C:
              Count[u] += 1
              nd_v = f(Count.get(u))
              inf = G[w][u]['P']*nd_v
              thre = threshold.get(u)
              threshold[u] = max(0,thre - inf)
              if threshold.get(u) == 0:
                activated.append(u)
                path = []
                path.extend(P[1])
                path.append(u)
                PH.put((P[0]+G[w][u]['T'],path))
                sigma += 1
              else:
                sigma += inf/thre
    return sigma
