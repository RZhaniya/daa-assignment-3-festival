"""Validate published A3 answers and witnesses without providing algorithm solutions."""

def integer(value):
    return type(value) is int and -(1 << 63) <= value < (1 << 63)

def integers(value):
    return isinstance(value, list) and all(integer(item) for item in value)

def check(request, answer, expected):
    """Malformed answers fail normally; bool and float are not integer fields."""
    if not isinstance(answer, dict):
        return False
    try:
        return validate(request, answer, expected)
    except (KeyError, TypeError, ValueError, IndexError):
        return False

def validate(x, y, e):
    task = x['task']
    if task == 'ping':
        return y == e
    if task == 'dsu':
        states = y['states']
        return (isinstance(states, list) and len(states) == len(x['operations'])
                and all(integers(state) and len(state) == 2 for state in states)
                and states == e['states'])
    if task in ('kruskal', 'prim'):
        if not integer(y['cost']) or not integer(y['components']):
            return False
        if y['cost'] != e['cost'] or y['components'] != e['components']:
            return False
        ids, n = y['edge_ids'], x['n']
        if not integers(ids) or len(set(ids)) != len(ids) or len(ids) != n - y['components']:
            return False
        parent, sizes = list(range(n)), [1] * n
        def root(vertex):
            while parent[vertex] != vertex:
                parent[vertex] = parent[parent[vertex]]
                vertex = parent[vertex]
            return vertex
        cost = 0
        for edge in ids:
            if not 0 <= edge < len(x['edges']):
                return False
            u, v, weight = x['edges'][edge]
            a, b = root(u), root(v)
            if a == b:
                return False
            if sizes[a] < sizes[b]:
                a, b = b, a
            parent[b] = a
            sizes[a] += sizes[b]
            cost += weight
        return cost == y['cost'] and all(root(u) == root(v) for u, v, _ in x['edges'])
    if task == 'scc':
        labels, reference = y['component'], e['component']
        if not integers(labels) or len(labels) != len(reference) or any(label < 0 for label in labels):
            return False
        forward, reverse = {}, {}
        for label, ref in zip(labels, reference):
            if forward.setdefault(label, ref) != ref or reverse.setdefault(ref, label) != label:
                return False
        pairs = y['condensation']
        if not isinstance(pairs, list) or not all(integers(pair) and len(pair) == 2 for pair in pairs):
            return False
        edges = [tuple(pair) for pair in pairs]
        return (len(edges) == len(set(edges))
                and set(edges) == {(labels[a], labels[b]) for a, b in x['edges'] if labels[a] != labels[b]})
    if task == 'topo':
        if type(y['acyclic']) is not bool or y['acyclic'] != e['acyclic']:
            return False
        order = y['order']
        if not y['acyclic']:
            return order == []
        n = x['n']
        if not integers(order) or len(order) != n:
            return False
        positions = [-1] * n
        for index, vertex in enumerate(order):
            if not 0 <= vertex < n or positions[vertex] != -1:
                return False
            positions[vertex] = index
        return all(positions[a] < positions[b] for a, b in x['edges'])
    if task == 'dag':
        if type(y['acyclic']) is not bool or y['acyclic'] != e['acyclic']:
            return False
        if not y['acyclic']:
            return y['distance'] == [] and y['parent_edge'] == []
        distance, edges, n, source = y['distance'], y['parent_edge'], x['n'], x['source']
        if not isinstance(distance, list) or len(distance) != n:
            return False
        if any(value is not None and not integer(value) for value in distance):
            return False
        if distance != e['distance'] or not integers(edges) or len(edges) != n:
            return False
        parents = [-1] * n
        for vertex in range(n):
            if vertex == source or distance[vertex] is None:
                if edges[vertex] != -1:
                    return False
                continue
            if not 0 <= edges[vertex] < len(x['edges']):
                return False
            a, b, weight = x['edges'][edges[vertex]]
            if b != vertex or distance[a] is None or distance[a] + weight != distance[vertex]:
                return False
            parents[vertex] = a
        # Check all predecessor chains once, reusing already verified suffixes.
        state = [0] * n
        state[source] = 2
        for vertex in range(n):
            if distance[vertex] is None or state[vertex] == 2:
                continue
            chain, current = [], vertex
            while state[current] == 0:
                state[current] = 1
                chain.append(current)
                current = parents[current]
                if current < 0:
                    return False
            if state[current] != 2:
                return False
            for current in chain:
                state[current] = 2
        return True
    return False
