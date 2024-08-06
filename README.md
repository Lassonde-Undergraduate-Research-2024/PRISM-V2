# Bug Detection in Randomized Algorithms

**Authors:** Hiva Karami, Adnan Ahmed

Testing is essential in software development as it plays an important role in ensuring the reliability and functionality of systems. Without proper testing, system failures can result in substantial costs and jeopardize user and data security. However, the presence of randomness in algorithms makes traditional testing methods challenging. This project explores the application of probabilistic model checking to identify bugs in algorithms that exhibit randomness. Currently, PRISM, developed at the University of Oxford, is the most popular tool used for probabilistic model checking and  It detects bugs in a system that relies on randomness by checking properties of a model of the system.<br>
One significant challenge, known as the state space explosion problem, is that the models generated are often very large, and checking properties in these models can consume lots of time and memory. To address this, we focus on model minimization techniques. By identifying states that exhibit similar behaviors, we can reduce the size of the model and significantly improve the efficiency of property checking.<br>
Besides the minimization algorithm in PRISM, we have implemented three known algorithms that perform the minimization step within PRISM and have measured their running time against each other as well as that of the original minimization algorithm in PRISM.<br>
Additionally, we have expanded PRISM so that anyone in the future can implement their own minimization algorithm and use it within PRISM. This work contributes to PRISM and, as such, offers practical solutions for improving the reliability of systems that rely on randomness.


