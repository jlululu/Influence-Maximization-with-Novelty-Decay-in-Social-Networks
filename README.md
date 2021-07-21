# Influence-Maximization-with-Novelty-Decay-in-Social-Networks
|
|—— Data processing
|—— R-Greedy
|—— TDG
|—— LT_new
|—— results

This is an individual project which aims to solve the Influence Maximization (IM) problem with novelty decay factor in social networks. 
The model used in the project is the deterministic Linear Threshold (LT) model. The two main algorithms I have referred to are as follows:
1. R-Greedy algorithm: 
Feng, S., Chen, X., Cong, G., Zeng, Y., Chee, Y. M., & Xiang, Y. (2014, June). Influence maximization with novelty decay in social networks. In Twenty-Eighth AAAI Conference on Artificial Intelligence.
http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.708.3308&rep=rep1&type=pdf
Implemented in R-Greedy.

2. Threshold Difference Greedy (TDG) Algorithm: 
Swaminathan, A. (2014). An algorithm for influence maximization and target set selection for the deterministic linear threshold model (Doctoral dissertation, Virginia Tech).
https://vtechworks.lib.vt.edu/handle/10919/49381
Implemented in TDG.

I make use of the idea of "residual" from TDG to modify the R-Greedy Algorithm to apply to deterministic LT model. The new algorithm is implemented in LT_new. 

