digg = pd.read_csv('digg_friends.csv',header=None,usecols=[2,3],names=['user_id','friend_id'])
nodes = np.unique(np.hstack((digg['user_id'].unique(),digg['friend_id'].unique())))
edges = [(dig[0],dig[1]) for dig in digg.values]
