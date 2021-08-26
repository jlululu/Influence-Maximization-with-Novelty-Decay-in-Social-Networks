# Influence-Maximization-with-Novelty-Decay-in-Social-Networks
<pre>
|                                                    
|—— Datasets       
|—— IMND|—— Node                                                                                                                                                        
|       |—— Edge                                                                                                                    
|       |—— DiGraph                                                                 
|       |—— queueNode                                                                              
|                                                                            
|—— R-Greedy+PPND for ICND                                                   
|—— R-Greedy+PPND for LTND                                                                                                                  
|—— results   
</pre>

This is an individual project which aims to solve the Influence Maximization (IM) with novelty decay problem in social networks. 
The diffussion model used in the project is the Linear Threshold model with novelty decay (LTND). 
I have tried to implement the R-Greedy algorithm from Feng et al. http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.708.3308&rep=rep1&type=pdf. 
For the computation of the influence spread during the process, I developed a propagation path based algorithm for LTND model.                           
                        
Datasets used in the project:                                                                                                                           
1. Email-EU dataset: It has 1,005 nodes and 25,571 edges.                                        
Available on: https://snap.stanford.edu/data/email-Eu-core.html
2. WikiVote dataset: It contains 7,115 nodes and 103,689 edges.    
Available on: https://snap.stanford.edu/data/wiki-Vote.html
3. HEP-PH dataset: It contains 34,546 nodes and 421,578 edges.                
Available on: https://snap.stanford.edu/data/cit-HepPh.html
4. Epinions dataset: It has 75,879 nodes and 508,837 edges.                     
Available on: https://snap.stanford.edu/data/soc-Epinions1.html
</pre>
