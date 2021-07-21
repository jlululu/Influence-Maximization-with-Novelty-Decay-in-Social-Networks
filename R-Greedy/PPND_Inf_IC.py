# compute influence spread
def PPND_Inf_IC(G, S, theta=0.001, C=5):
    V = G.nodes(); sigma = 0   
    PPND = dict(); Count = dict(); PH_con = dict()
    for v in V:
        PPND[v] = []; Count[v] = 0; PH_con[v] = []
    PH = queue.PriorityQueue()
    for s in S:
        PH.put_nowait((0,1,[s]))
    while not PH.empty():
        P = PH.get()
        w = P[2][-1]
        neighbors = list(G.neighbors(w))
        for u in list(set(neighbors).difference(set(S))):
          if Count.get(u) < C:
            nd_v = nd_value(PH_con.get(u))
            pr = P[1]*G[w][u]['P']*nd_v
            if pr > theta and u not in P[2]:
                PPND[u].append(pr)
                Count[u] += 1
                PH_con[u].append(pr)
                path = []
                path.extend(P[2])
                path.append(u)
                PH.put((P[0]+G[w][u]['T'],pr,path))
    for u in PPND.keys():
        temp = PPND.get(u)
        if len(temp) > 0:
            AP_u = 1
            for t in temp: 
                 AP_u *= (1 - t)
            sigma += 1 - AP_u
    return sigma
