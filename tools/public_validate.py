"""Validate public examples, including witnesses. Contains no algorithm solutions."""
def check(x,y,e):
    t=x['task']
    if t in ('kruskal','prim'):
        if y['cost']!=e['cost'] or y['components']!=e['components']:return False
        ids=y['edge_ids'];n=x['n'];parent=list(range(n))
        def root(u):
            while parent[u]!=u:u=parent[u]
            return u
        if len(set(ids))!=len(ids) or len(ids)!=n-y['components']:return False
        cost=0
        for i in ids:
            if type(i)!=int or not 0<=i<len(x['edges']):return False
            u,v,w=x['edges'][i];a,b=root(u),root(v)
            if a==b:return False
            parent[a]=b;cost+=w
        return cost==y['cost'] and all(root(u)==root(v) for u,v,_ in x['edges'])
    if t=='scc':
        u,v=y['component'],e['component']
        if len(u)!=len(v) or any(type(c)!=int or c<0 for c in u):return False
        if not all((u[a]==u[b])==(v[a]==v[b]) for a in range(len(v)) for b in range(len(v))):return False
        edges=[tuple(z) for z in y['condensation']]
        return len(edges)==len(set(edges)) and set(edges)=={(u[a],u[b]) for a,b in x['edges'] if u[a]!=u[b]}
    if t=='topo':
        if y['acyclic']!=e['acyclic']:return False
        if not y['acyclic']:return y['order']==[]
        p=y['order']
        return sorted(p)==list(range(x['n'])) and all(p.index(a)<p.index(b) for a,b in x['edges'])
    if t=='lcs':
        s=y['sequence']
        def sub(s,a):
            it=iter(a);return all(any(c==d for d in it) for c in s)
        return y['length']==e['length']==len(s) and sub(s,x['a']) and sub(s,x['b'])
    if t=='tree':
        v=y['selected'];chosen=set(v)
        return len(v)==len(chosen) and all(type(i)==int and 0<=i<len(x['weights']) for i in v) and not any(a in chosen and b in chosen for a,b in x['edges']) and sum(x['weights'][i] for i in v)==y['weight']==e['weight']
    if t=='merge':
        sizes=x['sizes'];blocks=[(i,i) for i in range(len(sizes))];cost=0
        for l,k,r in y['plan']:
            if (l,k) not in blocks:return False
            j=blocks.index((l,k))
            if j+1>=len(blocks) or blocks[j+1]!=(k+1,r):return False
            blocks[j:j+2]=[(l,r)];cost+=sum(sizes[l:r+1])
        return len(y['plan'])==max(0,len(sizes)-1) and cost==y['cost']==e['cost'] and len(blocks)<=1
    if t=='dag':
        if y['acyclic']!=e['acyclic']:return False
        if not y['acyclic']:return y['distance']==[] and y['parent_edge']==[]
        d,p=y['distance'],y['parent_edge'];n=x['n'];src=x['source']
        if d!=e['distance'] or len(p)!=n:return False
        for v in range(n):
            if v==src or d[v] is None:
                if p[v]!=-1:return False
                continue
            seen=set();u=v
            while u!=src:
                if u in seen or type(p[u])!=int or not 0<=p[u]<len(x['edges']):return False
                seen.add(u);a,b,w=x['edges'][p[u]]
                if b!=u or d[a] is None or d[a]+w!=d[u]:return False
                u=a
        return True
    if t=='suffix':
        s=y['repeated'];text=x['text']
        return y['sa']==e['sa'] and y['lcp']==e['lcp'] and len(s)==len(e['repeated']) and (not s or sum(text.startswith(s,i) for i in range(len(text)))>=2)
    return y==e
