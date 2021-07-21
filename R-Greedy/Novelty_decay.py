def f(n):
    return pow(0.3,n-1)
  
def dfs(n, con_ct, curr, ct, con_list, con_lists):
  if ct == con_ct:
    con_lists.append(con_list)
    return
  for i in range(curr + 1,n-con_ct+ct+1):
    if i in con_list:
      continue
    con_list_new = con_list.copy()
    con_list_new.append(i)
    dfs(n,con_ct,i,ct+1,con_list_new,con_lists)

def nd_value(PH_con_list):
# compute novelty decay value
  n = len(PH_con_list); re = 0 
  if n == 0 :
    return f(1)
  index_list = [i for i in range(n)]
  for i in range(1,n+1):
    con_ct = i - 1; con_lists = []
    dfs(n,con_ct,-1,0,[],con_lists)
    for con_list in con_lists:
      dis_list = list(set(index_list).difference(set(con_list)))
      t = f(i)
      for j in con_list:
        t *= PH_con_list[j]
      for j in dis_list:
        t *= (1 - PH_con_list[j])
      re += t
  return re
