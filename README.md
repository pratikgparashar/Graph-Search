You are a zookeeper in the reptile house. One of your rare South Pacific Tufted Wizzo lizards
(Tufticus Wizzocus) has just had several babies. Your job is to find a place to put each baby lizard
in a nursery.

However, there is a catch, the baby lizards have very long tongues. A baby lizard can shoot out
its tongue and eat any other baby lizard before you have time to save it. As such, you want to
make sure that no baby lizard can eat another baby lizard in the nursery (burp).
For each baby lizard, you can place them in one spot on a grid. From there, they can shoot out
their tongue up, down, left, right and diagonally as well. Their tongues are very long and can
reach to the edge of the nursery from any location.

In addition to baby lizards, your nursery may have some trees planted in it. Your lizards cannot
shoot their tongues through the trees nor can you move a lizard into the same place as a tree. As
such, a tree will block any lizard from eating another lizard if it is in the path. Additionally, the
tree will block you from moving the lizard to that location.

Input: The file input.txt in the current directory of your program will be formatted as follows:

First line: instruction of which algorithm to use: BFS, DFS or SA
Second line: strictly positive 32-bit integer n, the width and height of the square nursery
Third line: strictly positive 32-bit integer p, the number of baby lizards
Next n lines: the n x n nursery, one file line per nursery row (to show you where the trees are).
              It will have a 0 where there is nothing, and a 2 where there is a tree.

Example Input :
SA
8
9
00000000
00000000
00000000
00002000
00000000
00000200
00000000
00000000

One possible correct output.txt is:
OK
00000100
10000000
00001000
01002001
00001000
00100200
00000010
00010000
