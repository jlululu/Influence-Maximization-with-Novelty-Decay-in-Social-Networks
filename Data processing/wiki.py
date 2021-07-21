wiki = np.loadtxt('Wiki_Vote.txt',dtype=int)
nodes = np.unique(wiki)
edges = [tuple(w) for w in wiki]
