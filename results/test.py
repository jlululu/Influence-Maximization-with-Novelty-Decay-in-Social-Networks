# Monte-Carlo simulation
def LT(G,S,mc=10000):
  spread = []; V = G.nodes()
  for i in range(mc):
    activated = []; newly_activated = queue.Queue(); sigma = 0; inf_sum = dict()
    for v in V:
      inf_sum[v] = 0
    for s in S:
        newly_activated.put_nowait(s)
    while not newly_activated.empty():
        w = newly_activated.get()
        activated.append(w)
        neighbors = list(set(list(G.neighbors(w)))-(set(activated))-(set(newly_activated.queue)))
        for u in neighbors:
          inf_sum[u] += np.random.uniform(0,1)
          if inf_sum[u] >= G.nodes[u]['Thre']:
            newly_activated.put_nowait(u)
            sigma += 1
    spread.append(sigma)
  return np.mean(spread)

# LT model
time_LT = []; Inf_PPND_LT = []; Inf_LT = []
for k in range(10,110,10):
  start = time.time()
  res = R_Greedy_LT(G,k)
  end = time.time()
  time_LT.append(end-start); Inf_PPND_LT.append(res[1]); Inf_LT.append(LT(G,res[0]) + k)
  
 # TDG
Inf_TDG = []
for k in range(10,110,10)
  S = TDG(G,k,5,20)
  Inf_TDG.append(LT(G,S) + k)
  
# IC model
time_IC = []; Inf_IC = []
for k in range(10,110,10):
  start = time.time()
  res = R_Greedy_IC(G,k)
  end = time.time()
  time_IC.append(end-start); Inf_IC.append(res[1])

# plot results
x = [i for i in range(10,110,10)]
for i in range(10):
  Inf_LT[i] += (i+1)*10
  Inf_TDG[i] += (i+1)*10
# LT vs TDG: Inf
plt.plot(x,Inf_LT,'r*-',label='LT_new')
plt.plot(x,Inf_TDG,'b--',label='TDG')
plt.xlabel('Seeds Number')
plt.ylabel('Influence Spread')
plt.legend()
plt.title('twitter network')
plt.show()
# PPND vs LT general:
plt.plot(x,Inf_PPND_LT,'r--')
plt.plot(x,Inf_LT,'b--')
plt.show()
# LT vs IC: time
plt.plot(x,time_LT,'r--')
plt.plot(x,time_IC,'b--')
plt.show()
# LT vs IC: Inf:
plt.plot(x,Inf_PPND_LT,'r--')
plt.plot(x,Inf_IC,'b--')
plt.show()
