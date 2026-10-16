# Assignment 3 — explanations / подробные пояснения

## 1. Graph algorithms for a campus festival / Графовые алгоритмы для фестиваля

**English**

Welcome to Assignment 3. We will use graph algorithms to connect festival venues, understand preparation dependencies and plan delivery routes. This lesson explains the reasoning and the implementation steps. Keep the assignment contracts open as you work. The examples here are learning examples. You will complete the six methods in your own selected language and test your own implementation.

**Русский**

В Assignment 3 графы описывают связи между площадками, зависимости подготовительных работ и маршруты доставки. Это разные модели и отдельные входные графы. Урок объясняет идеи и локальные шаги реализации. Держите рядом спецификацию и CONTRACTS.md: именно они определяют интерфейс сдаваемой программы. Реализуйте шесть методов самостоятельно в одном выбранном языке.

## 2. What you will build / Шесть требуемых методов

**English**

There are six required methods. Kruskal and Prim solve the same optimization problem but must be separate implementations. Kosaraju is the required strongly connected components method. Topological ordering must use depth first search. Shortest paths use topological relaxation in a directed acyclic graph, and your helper reconstructs a route from parent edge IDs. Do not add unrelated algorithms to the assignment.

**Русский**

Краскал и Прим решают одну задачу оптимизации, но нужны две самостоятельные реализации. Для SCC требуется Косарайю. Топологическая сортировка использует DFS. Кратчайшие пути в DAG используют топологический порядок и релаксации. Вспомогательная функция восстанавливает путь по исходным ID родительских рёбер. Другие графовые алгоритмы не добавляются к обязательным требованиям.

## 3. Three independent graph models / Три независимые модели графа

**English**

The same campus story supports different mathematical questions. An undirected connection can be traversed in either direction. A preparation dependency has a direction and no numerical weight. A delivery edge has both direction and weight. Do not reuse the cable forest as a shortest-path result. For each request, build the representation for that task and reset its state.

**Русский**

История фестиваля приводит к разным математическим задачам. Неориентированную связь можно пройти в обоих направлениях. Зависимость работ имеет направление. Маршрут доставки имеет направление и вес. Остовный лес кабелей не является ответом о кратчайшем пути доставки. Для каждого запроса создаётся представление нужной задачи и новое состояние.

## 4. Vertices, edges and original IDs / Вершины, рёбра и исходные ID

**English**

A vertex number identifies a venue or task. An edge ID identifies the original input record. Do not replace an edge ID by its position after sorting. Two parallel edges can join the same endpoints and still have different IDs. For undirected algorithms both endpoints can be traversed. For directed algorithms the direction is essential. An isolated vertex still belongs to the graph.

**Русский**

Номер вершины обозначает площадку или работу, а ID ребра обозначает конкретную входную запись. После сортировки сохраняйте исходный ID, а не новый индекс. Параллельные рёбра могут иметь одинаковые концы, но разные ID. В неориентированном графе ребро доступно с обоих концов. В ориентированном графе направление обязательно учитывается. Изолированная вершина остаётся частью графа.

## 5. One request, one JSON result / Один запрос, один JSON-результат

**English**

Use the supplied launcher and input output framework. Select a language using the exact manifest value. Java is the syllabus primary language, and the existing assignment choice of four languages remains available. Avoid debug messages on standard output because they corrupt the JSON lines protocol. Multiple requests can run in one process, so initialize algorithm state separately for every request.

**Русский**

Предоставленная основная программа уже разбирает вход и печатает результат. Сохраняйте формат JSON-lines: каждый объект занимает отдельную строку. Программа может получить несколько запросов в одном процессе. Не печатайте приглашения и отладку в stdout. В примере объединение нуля и единицы при n=3 оставляет две компоненты, наибольшая содержит две вершины. Фрагменты Java ниже показывают отдельные действия в контексте метода, а не целый файл решения.

## 6. Published input limits / Опубликованные ограничения входа

**English**

The graph may contain up to one hundred thousand vertices and two hundred fifty thousand edges or DSU operations. Empty graphs are valid for DSU, MST, SCC and topo. A DAG shortest-path request needs at least one vertex because it specifies a source. With zero DSU vertices the operations array must be empty. Do not invent mandatory tests for malformed JSON, which the contract excludes. Weighted edges use integer weights from minus one billion to plus one billion.

**Русский**

Допустимы до ста тысяч вершин и до двухсот пятидесяти тысяч рёбер или операций DSU. Для DSU, MST, SCC и topo разрешён пустой граф. DAG shortest paths требует хотя бы одну вершину, потому что задаётся источник. При нулевом n операции DSU пустые. Веса взвешенных рёбер от минус миллиарда до миллиарда. Некорректный JSON не входит в обязательные проверяемые входы.

## 7. DSU: maintain a partition / DSU: разбиение на компоненты

**English**

A disjoint set union structure maintains a partition into components. Initially each vertex is a singleton. Find gives a representative of the component. Union must first find both representatives. If they are equal, no component is merged. The output still needs a state after that operation. With zero vertices and no operations, the output states list is empty.

**Русский**

Сначала каждая вершина образует отдельную компоненту. В примере шесть вершин. После union(0,1) остаётся пять компонент размера до двух. union(1,2) даёт четыре компоненты и наибольший размер три. union(3,4) оставляет три компоненты. Повторное union(2,0) и self-union(5,5) ничего не меняют. Перед выполнением предскажите каждую строку таблицы. Для n=0 операций быть не должно, результат states пустой.

## 8. Path compression: preserve the next link / Сжатие пути: сохранение следующей ссылки

**English**

Path compression changes the parent structure without changing component membership. In the example, vertex three is the root. Once the root has been found, every vertex on the path can point directly to it. The important implementation detail is saving the previous parent before overwriting it. Otherwise you can lose the next vertex and compress only the first link.

**Русский**

Сжатие пути меняет структуру дерева, сохраняя принадлежность вершины её компоненте. Здесь root уже найден и равен трём. Переменная next запоминает старого родителя. Затем parent[v] меняется на root, а обход продолжается через next. Если сначала перезаписать parent[v], а потом использовать его как следующий шаг, часть исходного пути останется необработанной. Проверьте итоговый массив [3,3,3,3].

## 9. Union by size and component statistics / Объединение по размеру и статистика

**English**

Use path compression together with a union heuristic. This fragment uses union by size. If you choose rank instead, rank is a tree height estimate, not a component size; keep size separately for the statistics. Repeated and self unions must not reduce the component count. Initialization costs linear time, while a sequence of operations has the inverse Ackermann amortized bound.

**Русский**

Перед фрагментом a и b уже являются различными корнями. При необходимости они меняются местами, чтобы a обозначал большую компоненту. После присоединения b к a суммируются размеры, число компонент уменьшается ровно на один и обновляется largest. При одинаковых корнях нужно выйти раньше. Для положительного n начальный largest равен единице, для нуля равен нулю. Сжатие пути требуется вместе с балансировкой.

## 10. Union by rank still needs actual sizes / Ранг не заменяет размер компоненты

**English**

You may use rank instead of size for balancing, together with path compression. Rank approximates the tree height before later compression. It is not the number of vertices. Two singleton roots both have rank zero. After merging them the surviving rank becomes one, but the component size becomes two. A repeated union changes neither value. Keep a separate size array to update largest.

**Русский**

Для балансировки разрешён ранг вместо размера вместе со сжатием пути. Ранг связан с высотой дерева до последующих сжатий и не равен числу вершин. Ранги двух одиночных корней равны нулю. После объединения ранг корня один, а размер компоненты два. Присоединение одиночной вершины даёт размер три при том же ранге. Повторное union ничего не меняет. Для largest нужен отдельный size.

## 11. Minimum spanning forest / Минимальный остовный лес

**English**

A minimum spanning tree exists only when the graph is connected. Assignment 3 requires a minimum spanning forest, covering every connected component. Here vertex four is isolated, so the graph has two components. The forest has five minus two, or three edges. Its cost is seven. A self loop cannot help connect different vertices. Negative edge weights are allowed.

**Русский**

На рисунке пять вершин. Вершины ноль, один, два и три связаны, а четыре изолирована. Значит, c=2 и лес должен содержать 5−2=3 ребра. Минимум дают веса один, два и четыре, всего семь. Не пытайтесь соединить изолят вымышленным ребром. Петли не входят в лес. Отрицательные веса и параллельные рёбра допустимы. Возвращаются исходные ID выбранных рёбер.

## 12. Adjacency lists retain the original edge ID / Списки смежности сохраняют исходный ID

**English**

This fragment assumes the list array and an Arc value type already exist. For an undirected MST graph insert both orientations of each input edge. For a directed DAG graph insert only the forward entry. Two adjacency entries are not two input IDs. A parallel input edge receives its own ID. For Kosaraju, build reverse adjacency by reversing each directed edge. Allocate new lists for each request.

**Русский**

Фрагмент предполагает готовые списки adj и тип Arc. Для неориентированного MST каждое ребро добавляется в обе стороны. Для ориентированного DAG добавляется только прямая дуга. Две записи списка смежности не означают два разных исходных ID. Параллельное входное ребро имеет собственный ID. Для Косарайю отдельно создаётся обратная смежность. Каждый запрос получает новые списки.

## 13. Kruskal: sort, then use DSU / Краскал: сортировка и DSU

**English**

Kruskal processes edges in nondecreasing weight order. In this example it accepts edge one, then edge three, then edge zero. These connect vertices one, two, three and zero at cost one plus two plus four, which is seven. The later edges join vertices already connected and are rejected. Vertex four remains an isolated component. The comparison is not equal, as clarified for lecture six slide nine.

**Русский**

Весовой порядок рёбер в примере соответствует ID один, три, ноль, четыре, два. Первые три ребра соединяют разные компоненты и дают стоимости один, три, семь. Два последних замыкают циклы и отклоняются. На слайде 9 лекции 6 условие записано ошибочно: правильное условие принятия — разные представители. Сортировка меняет порядок просмотра, но не ID входных рёбер.

## 14. Why a light crossing edge is safe / Почему минимальное ребро разреза безопасно

**English**

The cut property explains why the greedy choice is safe. Consider a cut that does not split any already chosen tree. An edge of minimum weight crossing that cut can be added while preserving the possibility of an optimal forest. The exchange argument replaces a crossing edge in an optimal solution without increasing cost. This does not mean the minimum edge or the entire optimum must be unique.

**Русский**

Разрез делит вершины на две части. Он должен уважать уже выбранный лес: никакое выбранное ребро не пересекает этот разрез. Легчайшее пересекающее ребро безопасно для продолжения такого леса. В доказательстве обмена его можно добавить в подходящий оптимальный лес и удалить другое пересекающее ребро без увеличения стоимости. При равных весах оптимальных наборов ID может быть несколько. Код показывает локальный цикл Краскала, а структуры Edge и DSU определяются в реализации.

## 15. Prim: grow a forest through a frontier / Прим: расширение через границу

**English**

Prim uses a frontier rather than sorting the entire input list. Starting from zero, it first reaches one with weight four, then two with weight one, then three with weight two. The total is again seven, although the order differs from Kruskal. Once this heap is exhausted, the outer loop still visits isolated vertex four and counts its component.

**Русский**

Прим начинает с вершины ноль и рассматривает рёбра, выходящие из построенной части. В примере он берёт 0–1 веса четыре, затем 1–2 веса один и 2–3 веса два. Итоговая стоимость семь совпадает с Краскалом, хотя порядок выбора отличается. Вершина четыре запускает новую компоненту без входящего ребра. Фиктивное ребро корня нельзя включать в edge_ids.

## 16. Prim: stale entries and parallel edges / Прим: устаревшие и параллельные рёбра

**English**

A lazy heap implementation does not remove every obsolete candidate immediately. When a candidate reaches the top, check whether its target has already been visited. If so, skip it before adding cost or recording an edge. Parallel edges are distinct candidates, each carrying its own original ID. The initial root is not an input edge and must not be recorded as one.

**Русский**

В ленивом варианте Прима в куче остаются старые варианты после того, как вершина уже включена в лес. Проверка used[e.to] предотвращает повторное включение. После выбора новой вершины добавляются рёбра к ещё не посещённым соседям. Отдельный внешний цикл запускает поиск в каждой компоненте. Куча должна различать входные ID параллельных рёбер, а не только пары концов.

## 17. Kruskal and Prim: compare valid results / Краскал и Прим: сравнение результатов

**English**

A correct validator must accept another feasible optimum. Checking only the expected edge list can reject a correct solution when weights tie. On the other hand, the correct minimum cost alone is not enough: a response might repeat an ID, include a cycle or omit a component. Validate the actual forest witness. Then compare Kruskal and Prim timing on exactly the same graph instances.

**Русский**

Совпадения только суммы недостаточно. Свидетель должен состоять из реальных неповторяющихся ID, не содержать циклов и соединять каждую исходную компоненту. Число рёбер равно n−c. Для ленивого Прима и Краскала допустима оценка O(n+m log(m+1)) при стандартных структурах. Учтите память графа, DSU или кучи и возвращаемого результата. Разные допустимые оптимальные леса принимаются.

## 18. Strongly connected components / Компоненты сильной связности

**English**

Strong connectivity is a directed concept. In this teaching example, zero and one form one component, two and three form another, and four is isolated. Components can be named differently while describing the same partition. The condensation graph contains one edge between the first two components. Duplicate original edges must not create duplicate condensation edges, and edges inside a component disappear.

**Русский**

В примере ноль и один взаимно достижимы, как и два и три. Четыре изолирована. Метки [7,7,9,9,12] являются одним допустимым ответом. Любое согласованное переименование, сохраняющее это разбиение, тоже допустимо. Два одинаковых перехода из первой SCC во вторую образуют одно ребро 7→9 в конденсации. Внутренних петель между метками одной SCC в ней нет.

## 19. Kosaraju: finish, transpose, assign / Косарайю: завершение и транспонирование

**English**

Kosaraju needs two complete depth first search passes and a transposed graph. Finishing order means the order in which vertices are completed, not discovered. Reverse this list before choosing the roots of the second pass. The second pass must traverse the transpose. For a simple chain zero to one to two and the stated traversal order, finishing gives two, one, zero, so the reversed root order begins at zero.

**Русский**

На цепи 0→1→2 DFS открывает вершины в порядке 0,1,2, но завершает в порядке 2,1,0. Во втором проходе корни обрабатываются в порядке 0,1,2 на транспонированном графе. Отдельная цепь здесь поясняет порядок, а не изображает одну общую SCC. Можно начать с транспонированного графа, затем перейти к исходному, если согласованно использовать обратный порядок завершения первого прохода.

## 20. Kosaraju: exact output and reset / Косарайю: точный результат и сброс

**English**

Both DFS passes must include vertices in disconnected components. A new request must not reuse labels or visited values from a previous graph. Once labels are assigned, scan the original edges and retain only pairs whose labels differ. Use a set or an equivalent deduplication method. The full algorithm takes linear time in vertices and edges; the transpose and traversal arrays also fit in linear total memory.

**Русский**

После двух проходов сравните метки концов каждого исходного ребра. Если метки различаются, добавьте пару в множество. Pair в Java должен сравниваться по значениям, например через record, иначе HashSet не удалит смысловые дубликаты. Равные метки означают внутреннее ребро, которое пропускается. Пустой граф даёт два пустых массива. Для глубокого графа используйте явный стек.

## 21. DFS topological ordering / Топологический порядок через DFS

**English**

A topological order places every prerequisite before its dependent task. DFS needs three states. A gray target is on the active ancestor path, so this edge closes a directed cycle. A black target has already finished and does not by itself prove a cycle. Search every component. If no cycle exists, reverse the finishing order and include every vertex exactly once.

**Русский**

В графе 0→1, 0→2, 1→3, 2→3 сначала рассматривается сосед один, затем два. Порядок открытия 0,1,3,2, порядок завершения 3,1,2,0. После разворота получаем 0,2,1,3, и каждое ребро направлено вперёд. Когда поиск из двух встречает уже BLACK-вершину три, цикла нет. Не путайте вершину на активном стеке с уже завершённой вершиной.

## 22. Iterative DFS: retain the neighbour index / Итеративный DFS: индекс следующего соседа

**English**

An iterative depth first search must preserve where each suspended search will continue. A frame contains the current vertex and the next outgoing edge index. Advance that index before pushing a child. When no neighbours remain, color the vertex black, append it to the finishing list and pop the frame. Restart at every white root. This avoids recursion stack failure on deep chains without rescanning adjacency lists.

**Русский**

В рекурсивном DFS возврат происходит только после обработки всех соседей. В итеративной версии это состояние хранит кадр Frame. Если соседи закончились, вершина становится BLACK, добавляется в finish, а кадр удаляется. Иначе считывается следующий сосед и увеличивается индекс. Для WHITE создаётся новый кадр, GRAY означает цикл, BLACK пропускается. Обычный порядок извлечения из стека не равен порядку завершения.

## 23. A cycle outside the source component / Цикл вне компоненты источника

**English**

Starting DFS only at the source visits zero and one, but misses the directed cycle between two and three. Topological ordering must examine every unvisited root. DAG shortest paths must also reject a cycle anywhere, even if the source cannot reach it. For topo return acyclic false and an empty order. For dag return acyclic false and both result arrays empty.

**Русский**

Поиск только из нуля посещает ноль и один, но не замечает цикл между двумя и тремя. Для топологического порядка нужно начинать обход из каждой ещё не посещённой вершины. DAG shortest paths тоже отклоняет цикл в любой компоненте, даже недостижимой от источника. Для topo ответ acyclic=false, order=[]. Для dag ответ acyclic=false и оба массива пустые.

## 24. DAG shortest paths: a numeric trace / Кратчайшие пути в DAG: трассировка

**English**

Use the directed edges shown here, with source zero. After processing zero, distances to one and two are three and eight. Processing one improves distance to two to minus two. Processing two sets the distance to three to two. Vertex four stays unreachable. A negative distance such as minus two is valid; that is why minus one cannot represent unreachable distance in this assignment.

**Русский**

Источник равен нулю. После его обработки distance[1]=3 и distance[2]=8. Ребро 1→2 веса минус пять улучшает расстояние до двух до минус двух. Затем 2→3 веса четыре даёт distance[3]=2. До вершины четыре пути нет, её расстояние null. Здесь минус один тоже мог бы быть корректным расстоянием, поэтому нельзя использовать его как обозначение недостижимости.

## 25. Why one topological pass is sufficient / Почему достаточно одного прохода

**English**

Topological order is the reason one relaxation pass is enough, even with negative weights. When a vertex is processed, every possible predecessor has already been processed, so no later edge can improve its distance. Do not relax from an unreachable vertex. A disconnected cycle still makes the whole input invalid as a DAG. Return the specified empty arrays when a cycle is detected.

**Русский**

В топологическом порядке все пути к вершине приходят через уже обработанных предшественников. Поэтому её оптимальное расстояние известно к моменту релаксации исходящих рёбер. Это рассуждение не требует неотрицательности весов. К null прибавлять вес нельзя: такая вершина недостижима. Фрагмент использует Long для возможности null и long для арифметики. Полная проверка ацикличности уже выполнена до этого цикла.

## 26. Route reconstruction from parent_edge / Восстановление маршрута по parent_edge

**English**

For the preceding DAG, parent edges for vertices one, two and three are zero, two and three. Starting at target three, follow edge three to vertex two, edge two to one, and edge zero to source zero. Reverse the collected vertices to obtain the route zero, one, two, three. The route to the source is the one-element list containing the source. An unreachable target has no route.

**Русский**

В предыдущем DAG parent_edge для вершин один, два и три равны нулю, двум и трём. От цели три ребро три ведёт назад к двум, ребро два — к одному, ребро ноль — к источнику ноль. Разворот даёт [0,1,2,3]. Для источника результат [0]. Для недостижимой цели документируется отсутствие маршрута: [] в таблице является примером, а не новым обязательным sentinel. Вызовите helper в реальных собственных тестах и сохраните результаты.

## 27. Ties, original IDs and 64-bit arithmetic / Равные пути, ID и 64-битные суммы

**English**

A valid optimum does not always have a unique witness. A shortest path validator must accept another tied route when its parent edges are real and the distances are correct. With parallel edges, recording just the predecessor vertex loses the identity required by the contract. In Java, assign a long expression, not an already overflowed int expression. Cast an operand before multiplication or addition when necessary.

**Русский**

parent_edge хранит исходный ID, а не номер предыдущей вершины. У источника и недостижимой вершины parent_edge=-1. Расстояние источника ноль, недостижимое расстояние null. Значение 3L заставляет умножение выполняться как long, поэтому результат три миллиарда корректен. Если сначала умножить int, а затем присвоить long, переполнение уже произойдёт. Проверяйте сумму целого пути, а не только отдельные веса.

## 28. A tied route with parallel input IDs / Равные пути и параллельные ID: пример

**English**

There are two equal input edges from zero to one. Vertex two can be reached via one at total cost three or directly at cost three. The parent arrays in the table all use real input edges and describe shortest paths. A tie rule may choose any of these valid optima. If your implementation updates only on a strict improvement, an equal candidate does not change the existing parent. Explain the rule you use.

**Русский**

Из нуля в один есть два равных входных ребра. До двух можно дойти через один за три или напрямую за три. Все три массива parent_edge в таблице используют реальные рёбра и задают кратчайшие пути. При равенстве можно выбрать любой такой optimum. Если обновлять только при строгом улучшении, равный кандидат сохраняет прежнего родителя. Объясните выбранное поведение, не требуя одного произвольного образца.

## 29. Time and space, including the result / Время и память с учётом результата

**English**

State the bound for the representation you actually implement. DSU initialization is linear and the operations have an inverse Ackermann amortized term; storing every returned state adds space proportional to the operation count. Sorting and lazy heaps determine the minimum forest bounds. Kosaraju, DFS topological ordering and DAG relaxation are linear with adjacency lists. Reconstruction is linear in the returned route length.

**Русский**

Для DSU инициализация занимает O(n), последовательность q операций — O(q alpha(n)) амортизированно. Возвращаемые states добавляют O(q) памяти. Косарайю, DFS topo и DAG paths требуют O(n+m) при списках смежности и линейной обработке рёбер. HashSet даёт ожидаемую линейную обработку конденсации. Восстановление маршрута из L вершин требует O(L) времени и места для результата.

## 30. Test the witness, not only the number / Проверка свидетельства результата

**English**

A plausible number can accompany an invalid witness. For a forest, repeated IDs are invalid even if the listed cost equals the optimum. SCC labels may be renamed, but merging distinct components is wrong. A topological list with a duplicated vertex is not valid. For DAG paths, check edge endpoints, distance equality and that reachable parent chains terminate at the source. Include a good alternative optimum and a bad witness in your own tests.

**Русский**

Правильная сумма не оправдывает неправильные ID. Дублирующееся ребро в лесе отклоняется, даже если стоимость совпала. Согласованное переименование SCC принимается, если конденсация тоже согласована. Другой равный кратчайший путь допустим. Parent edge с неверным концом отклоняется даже при правильном массиве расстояний. Так собственные тесты проверяют смысл ответа, а не только совпадение с одним образцом.

## 31. Own tests and independent requests / Собственные тесты и независимость запросов

**English**

Your tests should explain why the expected result is right. Include empty or disconnected graphs where allowed, repeated operations, isolates, loops, parallel edges, negative weights and unreachable vertices. Then issue a different request in the same process. A large DSU request followed by a small DAG request must not leak arrays or counters. Large input checks complement small independent examples; they do not replace them.

**Русский**

Каждый ожидаемый ответ нужно обосновать отдельно. Используйте пустые графы там, где они разрешены, изоляты, повторные операции, петли, параллельные рёбра, отрицательные веса и циклы. После большой DSU отправьте маленький DAG в том же процессе. Старые массивы и счётчики не должны влиять на следующий запрос. Масштабные проверки дополняют малые независимые примеры, но не заменяют их.

## 32. Small tests with exact expected results / Малые тесты с точными ожидаемыми ответами

**English**

These tests isolate initialization and empty-result handling. Each input still needs its task name and the relevant empty operations or edges array. DAG has n one and source zero, not an invalid empty graph. After these checks add nonempty examples, negative edges, cycles, witnesses and large graphs. A passing empty case alone does not demonstrate a complete algorithm.

**Русский**

Эти тесты отдельно проверяют инициализацию и пустые результаты. Во входной JSON-объект добавляются task и нужный пустой массив operations или edges. Для DAG используется одна вершина и источник ноль, а не запрещённый пустой граф. Затем добавьте непустые примеры, отрицательные веса, циклы, свидетельства и большие входы. Один успешный пустой случай не доказывает готовность алгоритма.

## 33. Kruskal–Prim benchmark: illustrative data / Benchmark Краскала и Прима: пример данных

**English**

For each of three sizes, use exactly the same input graph for both algorithms. Record vertices, edges, random seed or generator, runtime and hardware. Exclude compilation from timing and say whether graph construction, parsing and output are included. Use at least three measurements for each method. These illustrative samples have medians ten and seven at the first size. They illustrate observations, not proof of an asymptotic bound.

**Русский**

Это иллюстративные числа, а не готовый benchmark для вашей сдачи. Для первой строки медиана Краскала из 12,9,10 равна 10, медиана Прима из 8,6,7 равна 7. Следующие медианы равны 64 и 46, затем 145 и 104. Измерьте собственные реализации на одинаковых входах и в одной среде. Укажите seed или генератор, n/m, версии и границу измерения. Не включайте компиляцию. Несколько замеров не доказывают асимптотику.

## 34. What belongs in ANALYSIS.md / Содержание ANALYSIS.md

**English**

Write the analysis in English. For every required method, explain what is maintained, why the steps are correct, and how the chosen representation determines time and memory. Include your actual route helper calls and benchmark evidence, not a list of intended tests. Follow the syllabus on assistance disclosure: identify tools or sources, their contribution and your own verification. You remain responsible for the submitted work.

**Русский**

ANALYSIS.md оформляется на английском языке, даже если вы учитесь по русскому переводу. Опишите, почему шаги сохраняют инвариант и дают нужный ответ, какие крайние случаи учтены и как структура данных определяет сложность. Покажите фактические результаты собственных тестов и вызовов helper. Согласно силлабусу письменно укажите инструменты или источники помощи, их вклад и вашу проверку. За работу отвечает студент.

## 35. Implement and run the selected language / Реализация в выбранном языке

**English**

Start from the official template and select one language. The input output framework is already present. Implement the methods, add tests, and push a commit. Inspect the Public checks workflow for that commit. Successful compilation with algorithm failures is normal while TODO methods remain. Once implementation is complete, the run used for submission must be successful. Do not replace the required algorithms with a library implementation.

**Русский**

Основная программа и JSON-адаптеры уже даны. Реализуйте методы в исходной папке выбранного языка, добавьте собственные тесты и отправьте изменения. Для Cpp файл алгоритмов называется algorithms.hpp. Успешная сборка с падением алгоритмических примеров ожидаема, пока TODO не реализованы. Для сдачи нужен успешный Public checks именно вашего конечного коммита. Не подменяйте требуемый алгоритм готовой библиотечной реализацией.

## 36. Reading Public checks feedback / Как читать результат Public checks

**English**

A build error means the program could not compile or start. If build succeeds and a TODO algorithm returns a placeholder, algorithm tests fail as expected. A format error may come from debug output on stdout or a missing JSON result. A CI service or checker failure needs investigation and a rerun. Do not treat an infrastructure failure as a proven student algorithm error or a student zero.

**Русский**

Ошибка сборки означает, что программа не скомпилировалась или не запустилась. Если сборка прошла, но TODO вернул заглушку, алгоритмические тесты закономерно падают. Ошибка формата может возникнуть из-за отладки в stdout или отсутствия JSON-ответа. Сбой CI или checker нужно расследовать и повторить запуск. Инфраструктурная ошибка сама по себе не является доказанной ошибкой алгоритма или студенческим нулём.

## 37. Submit two links for the same SHA / Сдача двух ссылок одного SHA

**English**

The submitted commit identifies the version to assess. A link to a changing main branch is not an exact version. The successful Actions run must use that same commit SHA. Paste both links into Moodle Online text, then finish the Submit assignment step. If your repository is private, the instructor needs access. Follow the actual dates and approved extensions in Moodle. No ZIP upload or student ID is required.

**Русский**

Ссылка на ветку main не фиксирует конкретную версию. Откройте нужный коммит и скопируйте его URL. В Actions найдите успешный Public checks и сравните SHA. Вставьте две ссылки в Moodle Online text, затем завершите сдачу кнопкой Submit assignment. Если репозиторий закрытый, предоставьте преподавателю доступ. Сроки и утверждённые продления смотрите в Moodle. ZIP и student ID не требуются.

## 38. Assessment: 50 automatic + 50 automatic / Оценивание: 50 автоматически + 50 автоматически

**English**

The current assessment is entirely automatic. Practical work contributes fifty points from the original test group weights. The quiz contributes another fifty. There are no manual points, oral defence, oral bonuses or oral score recovery. Required learning materials still belong in the work. Confirmed defects in a test or question are corrected consistently for everyone affected, using original evidence rather than an individual oral task.

**Русский**

Практика даёт пятьдесят баллов по исходным весам автоматических групп. Квиз даёт ещё пятьдесят. Ручных баллов, устной защиты и устного восстановления нет. Анализ, тесты и benchmark остаются учебными материалами, но не имеют отдельной ручной балльной части или произвольных вычетов. Подтверждённый дефект теста или ключа исправляется одинаково для всех затронутых исходных работ, а не через индивидуальное устное задание.

## 39. Ready to submit / Готовность к сдаче

**English**

Before submitting, trace one small example by hand for every algorithm. Test an invalid witness and an alternative correct result. Check a deep graph and a sequence of different requests. Confirm your helper was actually called. Read the contracts again for field names and sentinels. Finally check the analysis, your measurements, the exact commit and the successful workflow run. This is a reliable route from understanding the method to submitting your own implementation.

**Русский**

Перед сдачей проследите маленький пример для каждого алгоритма вручную. Проверьте неправильное свидетельство и другой корректный optimum. Выполните глубокий граф и серию разных запросов. Убедитесь, что helper действительно вызывался. Ещё раз сверьте имена полей и sentinel со спецификацией. Затем проверьте анализ, измерения, конкретный коммит и успешный запуск Public checks.

## 40. Sources and further study / Источники и дальнейшее чтение

**English**

The assignment specification is the authority for the JSON contracts and the required methods. Read the separate clarification of the Kruskal condition in lecture six and the unreachable sentinel used in lecture seven: Assignment 3 uses null. The textbooks support the invariants, proofs and complexity analysis. Use them for understanding and acknowledge assistance appropriately. The next slide contains the narrated walkthrough for revision.

**Русский**

Контракты JSON и обязательные методы определяются спецификацией A3. Отдельное уточнение исправляет условие Краскала на слайде 9 лекции 6. Для A3 недостижимое расстояние обозначается null, в отличие от общего обозначения на лекционном слайде. Книги помогают понять инварианты, доказательства и сложность. Источники и помощь нужно раскрыть письменно. Следующий слайд содержит дополнительное английское видео.

## 41. Watch the narrated walkthrough / Английское видеообъяснение

**English**

Use the video for revision. Pause at a trace or code fragment, predict the next state, and then continue. The separate video and transcript contain the same teaching explanation. Video playback depends on the presentation application; the editable slides remain available independently.

**Русский**

Видео из предыдущего урока объясняет те же основные методы на английском языке. Приостанавливайте его на трассировках и предсказывайте следующий шаг. Русские подробные пояснения доступны в заметках каждого слайда и в двуязычном слайд-шоу. Воспроизведение встроенного видео зависит от приложения. Отдельный MP4 сохранён вместе с комплектом.

