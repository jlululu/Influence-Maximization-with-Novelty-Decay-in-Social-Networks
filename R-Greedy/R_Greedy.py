def R_Greedy_IC(G, K):
    V = G.nodes()
    S_prev = []; Q = dict(); Q_prev = dict(); sigma_prev = 0; s_prev = -1
    # initialize S_prev & Q_prev
    for v in V:
        temp = PPND_Inf_IC(G,[v])
        Q[v] = temp
        Q_prev[v] = temp
    for i in range(K):
        maxMarInf = -sys.maxsize - 1; s_curr = -1; Q_curr = dict()
        q = sorted(list(set(V).difference(set(S_prev))),key=lambda i : Q.get(i),reverse=True)
        for u in q:
            if Q.get(u) < maxMarInf:
                break
            temp = 0
            if s_prev != -1:
              temp = Q.get(s_prev)
            if u in Q_prev.keys() and (Q_prev.get(u) + temp - sigma_prev < maxMarInf):
                continue
            else:
                S_curr_u = []
                S_curr_u.extend(S_prev)
                S_curr_u.append(u)
                Q_curr[u] = PPND_Inf_IC(G,S_curr_u)
                if Q_curr.get(u) - sigma_prev > maxMarInf:
                        maxMarInf = Q_curr.get(u) - sigma_prev
                        s_curr = u
        s_prev = s_curr
        Q_prev = Q_curr.copy()
        S_prev.append(s_curr)
        sigma_prev += maxMarInf
        # print('s_prev={}, S_prev={}, sigma_prev={}, maxMarInf={}'.format(s_prev,S_prev,sigma_prev,maxMarInf))
    return S_prev, sigma_prev
