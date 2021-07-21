twitter = np.loadtxt('twitter.txt',dtype=int)
nodes = np.unique(twitter)
edges = [tuple(t) for t in twitter]
