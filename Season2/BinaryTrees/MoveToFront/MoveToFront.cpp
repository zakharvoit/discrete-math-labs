#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
 
typedef struct Node* NodePtr;
 
struct Node {
    int cnt, prior, cost;
    NodePtr left, right;
 
    Node() {};
    Node(int prior, int cost, NodePtr l = NULL, NodePtr r = NULL): cnt(1), prior(prior), cost(cost), left(l), right(r) {};
};
 
int cnt(NodePtr t) {
    return t ? t->cnt : 0;
}
 
void updCnt(NodePtr &t) {
    if (t)
        t->cnt = cnt(t->left) + cnt(t->right) + 1;
}
 
void merge(NodePtr &t, NodePtr l, NodePtr r) {
    if (!l) t = r;
    else if (!r) t = l;
   
    else if (l->prior > r->prior)
        merge(l->right, l->right, r), t = l;
    else
        merge(r->left, l, r->left), t = r;
    updCnt(t);
}
 
void split(NodePtr t, int pos, NodePtr &l, NodePtr &r, int add = 0) {
    if (!t) return void(l = r = NULL);
 
    int newCnt = add + cnt(t->left);
    if (pos <= newCnt)
        split(t->left, pos, l, t->left, add), r = t;
    else
        split(t->right, pos, t->right, r, newCnt + 1), l = t;
    updCnt(l);
    updCnt(r);
}
 
void insert(NodePtr &t, int pos, int prior, int cost) {
    if (!t) return void(t = new Node(prior, cost));
 
    NodePtr l = NULL, r = NULL, m = new Node(prior, cost);
    split(t, pos, l, r);
    merge(l, l, m);
    merge(t, l, r);
 
    updCnt(t);
}
 
void output(NodePtr t) {
    if (!t) return;
 
    output(t->left);
    cout << t->cost + 1 << " ";
    output(t->right);
}
 
int main() {
    freopen("movetofront.in", "r", stdin);
    freopen("movetofront.out", "w", stdout);
 
    int n, m;
    cin >> n >> m;
 
    NodePtr t = NULL;
    for (int i = 0; i < n; i++) insert(t, i, rand(), i);
 
    for (int i = 0; i < m; i++) {
        int L, R;
        cin >> L >> R;
        NodePtr t1 = NULL, t2 = NULL, t3 = NULL;
        split(t, --L, t1, t2);
        split(t2, --R - L + 1, t2, t3);
        merge(t1, t2, t1);
        merge(t, t1, t3);
    }
 
    output(t);
       
    return 0;
}
