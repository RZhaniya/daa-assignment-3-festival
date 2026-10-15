# Assignment 3 — Graph Algorithms for a Campus Festival

## Lecture clarifications (English)

**Lecture 6, slide 9, Kruskal pseudocode, line 8.** Replace the equality condition `find(x) = find(y)` with `find(x) != find(y)`. Add the edge and union its endpoints only when they are in different components. If they already have the same representative, the edge creates a cycle and must be skipped. This agrees with slide 8 and CLRS 4e §21.2 (MST-KRUSKAL, printed p.594).

Counterexample to the printed equality: two initially separate vertices 0 and 1 with one connecting edge have different roots. The incorrect condition rejects the only edge required to span them.

The slide assumes a connected graph. A3 requires a minimum spanning **forest**: process available edges safely even when the graph is disconnected, include isolates in the component count, and return n-c edges. Do not wait indefinitely for n-1 accepted edges. Prim must likewise restart in each unvisited component.

**Lecture 7, slide 14, unreachable distances.** The lecture's generic alternative “-1” is unsuitable for A3 because negative weights are allowed. For example, a single edge 0→1 of weight -1 gives a reachable distance of -1. The A3 JSON distance for an unreachable vertex is **null**. Both the source and unreachable vertices have parent_edge=-1, distinguished by their distances (0 and null). Skip relaxation from unreachable vertices.

**Scope.** The original lecture files are preserved and have not been replaced. This is a separate A3 clarification. A3 requires DSU, separate Kruskal/Prim, Kosaraju, DFS topological ordering and DAG shortest paths; other methods mentioned in the lectures do not become extra required implementations.

## Уточнения к лекциям (русский перевод)

**Лекция 6, слайд 9, псевдокод Краскала, строка 8.** Вместо равенства `find(x) = find(y)` должно быть `find(x) != find(y)`. Добавляйте ребро и объединяйте концы, только если они принадлежат разным компонентам. Если представитель уже общий, ребро создаёт цикл и пропускается. Это согласуется со слайдом 8 и CLRS 4e §21.2 (MST-KRUSKAL, печатная с.594).

Контрпример к напечатанному равенству: две отдельные вершины 0 и 1 с единственным соединяющим ребром имеют разные корни. Ошибочное условие отвергает единственное необходимое ребро.

Слайд предполагает связный граф. A3 требует минимальный остовный **лес**: безопасно обработайте доступные рёбра несвязного графа, учтите изоляты в числе компонент и верните n-c рёбер. Не ждите бесконечно n-1 принятых рёбер. Прим также должен перезапускаться в каждой непосещённой компоненте.

**Лекция 7, слайд 14, недостижимые расстояния.** Общий вариант «-1» из лекции не подходит для A3, где разрешены отрицательные веса. Единственное ребро 0→1 веса -1 даёт достижимое расстояние -1. В JSON A3 недостижимое расстояние — **null**. Источник и недостижимая вершина имеют parent_edge=-1, но различаются расстояниями (0 и null). Не релаксируйте из недостижимых вершин.

**Границы уточнения.** Исходные лекции сохранены и не заменялись. Это отдельное уточнение для A3. Требуются DSU, отдельные Краскал/Прим, Косарайю, DFS-топологический порядок и кратчайшие пути в DAG; другие упомянутые в лекциях методы не становятся дополнительными обязательными реализациями.
