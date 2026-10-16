# Assignment 3: пошаговые пояснения / step-by-step explanations

## 1. Assignment 3: объяснение по шагам / Assignment 3: step by step

### RU

Как из маленького графа получить ответ, а затем написать свой код.

Сначала решаем пример руками. Потом связываем каждое действие с кодом.

Идите в своём темпе. После каждого шага попробуйте предсказать следующее состояние. Вопросы внизу помогают проверить понимание. Ответ можно открыть отдельно. Java-фрагменты показывают отдельные действия. Полные шесть методов вы реализуете самостоятельно. Точные интерфейсы определяет CONTRACTS.md.

Шесть методов, понятные примеры и пояснения на двух языках.

### EN

How to turn a small graph into an answer, then write your own code.

First work through an example by hand. Then connect each action to code.

Work at your own pace. After each step, predict the next state. Use the questions below to check understanding and reveal an answer when ready. Java snippets show individual actions. You implement the six complete methods yourself. CONTRACTS.md defines the exact interfaces.

Six methods, worked examples and explanations in two languages.

## 2. Граф: точки и связи между ними / A graph: points and connections

### RU

Вершина — объект. Ребро — связь между двумя объектами.

Три площадки: 0, 1, 2. Кабели соединяют 0 с 1 и 1 с 2.

Из 0 можно добраться до 2 через 1. Прямое ребро 0–2 для этого не нужно.

Из 0 можно добраться до 2 через 1. Прямое ребро 0–2 для этого не нужно.

Запись | Смысл
n = 3 | Всего три вершины: 0, 1, 2
[0, 1] | Связь между 0 и 1
[1, 2] | Связь между 1 и 2

Сколько здесь вершин и сколько рёбер?

3 вершины и 2 ребра. Вершины считаем по объектам, рёбра — по связям.

### EN

A vertex is an object. An edge connects two objects.

Three venues: 0, 1, 2. Cables connect 0 to 1 and 1 to 2.

You can reach 2 from 0 through 1. A direct edge 0–2 is unnecessary.

You can reach 2 from 0 through 1. A direct edge 0–2 is unnecessary.

Notation | Meaning
n = 3 | Three vertices: 0, 1, 2
[0, 1] | A connection between 0 and 1
[1, 2] | A connection between 1 and 2

How many vertices and edges are there?

3 vertices and 2 edges. Count objects for vertices and connections for edges.

## 3. Направление и вес ребра / Edge direction and weight

### RU

Стрелка разрешает движение в одну сторону. Вес — число на ребре.

[0, 1, 4]: из 0 в 1, стоимость 4. Для MST связь двусторонняя.

В SCC, topo и DAG направление важно. Из существования 0→1 не следует 1→0. В MST ребро неориентированное: [0,1,4] соединяет обе вершины. Вес может означать стоимость, а отрицательный вес — скидку. Отсутствующее ребро нельзя придумывать.

Модель зависит от задачи: кабели, порядок работ и доставка используют разные графы.

### EN

An arrow permits one direction. A weight is a number attached to an edge.

[0, 1, 4]: from 0 to 1, cost 4. In MST the connection works both ways.

Direction matters in SCC, topo and DAG. Having 0→1 does not imply 1→0. An MST edge is undirected: [0,1,4] connects both vertices. A weight can represent cost, and a negative weight can represent a discount. Never invent a missing edge.

The task chooses the model: cables, work dependencies and delivery use separate graphs.

## 4. Какой вопрос решает каждый метод / What each method answers

### RU

Сначала определяем нужный результат. Потом выбираем требуемый метод.

Один и тот же сюжет фестиваля даёт разные математические задачи.

Краскал и Прим решают одну задачу, но в A3 нужны две отдельные реализации.

Краскал и Прим решают одну задачу, но в A3 нужны две отдельные реализации.

Метод | Вопрос простыми словами
DSU | Какие вершины уже в одной группе?
Краскал / Прим | Как соединить каждую группу дешевле всего?
Косарайю | Где можно пройти туда и обратно?
DFS topo | В каком порядке выполнить работы?
DAG paths | Как дешевле дойти из заданного источника?

### EN

First identify the required result. Then use the required method.

The festival story leads to different mathematical tasks.

Kruskal and Prim solve the same problem, but A3 requires separate implementations.

Kruskal and Prim solve the same problem, but A3 requires separate implementations.

Method | Question in plain words
DSU | Which vertices already belong to one group?
Kruskal / Prim | How can each connected group be linked most cheaply?
Kosaraju | Where can we travel both ways along paths?
DFS topo | In what order can tasks be completed?
DAG paths | What is the cheapest route from a given source?

## 5. ID ребра: его место во входном списке / An edge ID is its input position

### RU

Нумерация начинается с нуля. ID сохраняется при сортировке.

Два одинаковых ребра могут иметь разные ID.

Когда ответ требует edge_ids или parent_edge, возвращаем исходный ID.

Когда ответ требует edge_ids или parent_edge, возвращаем исходный ID.

ID | Входное ребро
0 | [0, 1, 4]
1 | [1, 2, 1]
2 | [0, 1, 4]

После сортировки ребро [1,2,1] стало первым. Какой у него ID?

ID = 1. Его исходная позиция не меняется.

### EN

Numbering starts at zero. Sorting must preserve the original ID.

Two identical edges may still have different IDs.

When the answer needs edge_ids or parent_edge, return the input ID.

When the answer needs edge_ids or parent_edge, return the input ID.

ID | Input edge
0 | [0, 1, 4]
1 | [1, 2, 1]
2 | [0, 1, 4]

After sorting, [1,2,1] comes first. What is its ID?

ID = 1. Its original position does not change.

## 6. Ввод и вывод: одна строка на запрос / Input and output: one line per request

### RU

Адаптер уже читает JSON и вызывает ваш метод.

Ввод: {"task":"dsu","n":3,"operations":[[0,1]]}
Вывод: {"states":[[2,2]]}

states хранит пару [components, largest] после каждой операции. components — число групп. largest — размер самой большой группы. Отладочный текст нельзя печатать в stdout: там нужен только JSON. Следующая строка ввода — независимая задача с новым состоянием.

После связи 0–1 осталось две группы. Самая большая содержит две вершины.

### EN

The adapter already reads JSON and calls your method.

Input: {"task":"dsu","n":3,"operations":[[0,1]]}
Output: {"states":[[2,2]]}

states stores [components, largest] after each operation. components counts groups. largest is the size of the biggest group. Keep debugging text off stdout, which must contain only JSON. The next input line is an independent task with fresh state.

After linking 0–1, two groups remain. The largest contains two vertices.

## 7. DSU: группы после каждого соединения / DSU: groups after each connection

### RU

DSU хранит, какие вершины уже соединены цепочкой связей.

Старт: {0}, {1}, {2}, {3}, {4}. Добавляем 0–1, затем 1–2.

Каждое соединение сливает две группы в одну: 5, затем 4, затем 3 группы. В {0,1,2} три вершины, поэтому largest=3.

Каждое соединение сливает две группы в одну: 5, затем 4, затем 3 группы. В {0,1,2} три вершины, поэтому largest=3.

Момент | Группы | [components, largest]
До операций | {0} {1} {2} {3} {4} | [5, 1]
После [0,1] | {0,1} {2} {3} {4} | [4, 2]
После [1,2] | {0,1,2} {3} {4} | [3, 3]

### EN

DSU tracks vertices already connected by a chain of links.

Start: {0}, {1}, {2}, {3}, {4}. Add 0–1, then 1–2.

Each connection merges two groups into one: 5, then 4, then 3 groups. Group {0,1,2} has three vertices, so largest=3.

Each connection merges two groups into one: 5, then 4, then 3 groups. Group {0,1,2} has three vertices, so largest=3.

Moment | Groups | [components, largest]
Before operations | {0} {1} {2} {3} {4} | [5, 1]
After [0,1] | {0,1} {2} {3} {4} | [4, 2]
After [1,2] | {0,1,2} {3} {4} | [3, 3]

## 8. parent: кому вершина указывает дорогу / parent: the next vertex to follow

### RU

parent[x] хранит следующую вершину на пути к представителю группы.

parent = [0, 0, 1, 3, 4]. Для вершины 2 идём: 2 → 1 → 0.

До первого union у каждой вершины parent[x]=x и size[x]=1. Здесь показан отдельный допустимый пример внутреннего дерева группы {0,1,2}. Это дерево указателей DSU, а не исходный граф связей. parent[2]=1 означает только следующий шаг к корню. Прямого ребра 2–0 во входе может не быть. У разных групп разные корни.

Вершина 0 указывает на себя. Это корень, или представитель группы.

x | 0 | 1 | 2 | 3 | 4
parent[x] | 0 | 0 | 1 | 3 | 4

### EN

parent[x] stores the next vertex on the way to the group representative.

parent = [0, 0, 1, 3, 4]. For vertex 2, follow 2 → 1 → 0.

Before any union, every vertex has parent[x]=x and size[x]=1. This is a separate valid example of the internal tree for group {0,1,2}. It is a DSU pointer tree, not the original connection graph. parent[2]=1 only gives the next step toward the root. The input need not contain a direct edge 2–0. Separate groups have different roots.

Vertex 0 points to itself. It is the root, or group representative.

x | 0 | 1 | 2 | 3 | 4
parent[x] | 0 | 0 | 1 | 3 | 4

## 9. find(2): пройти до корня / find(2): follow pointers to the root

### RU

find возвращает корень. Сравнение корней проверяет принадлежность группе.

2 → 1 → 0. Останавливаемся, потому что parent[0] = 0.

Если сравнить только parent[2] и parent[1], получится 1 и 0, хотя группа одна. Поэтому перед union надо найти настоящие корни. root — локальная переменная, которая движется по указателям. Цикл ниже иллюстрирует поиск без сжатия.

find(2) = 0 и find(1) = 0: вершины 2 и 1 уже в одной группе.

```java
int root = x;
while (parent[root] != root) {
    root = parent[root];
}
```

Можно сравнить parent[a] и parent[b] вместо find(a) и find(b)?

Нет. parent может указывать на промежуточную вершину. Нужно сравнивать корни.

### EN

find returns a root. Comparing roots checks group membership.

2 → 1 → 0. Stop because parent[0] = 0.

Comparing only parent[2] and parent[1] gives 1 and 0 even though the group is the same. Find actual roots before union. root is a local variable that follows the pointers. The loop below illustrates root finding without compression.

find(2) = 0 and find(1) = 0: vertices 2 and 1 already share a group.

```java
int root = x;
while (parent[root] != root) {
    root = parent[root];
}
```

Can parent[a] and parent[b] replace find(a) and find(b)?

No. parent can point to an intermediate vertex. Compare roots.

## 10. Сжатие пути: сократить будущий поиск / Path compression: shorten future searches

### RU

После find направляем посещённые вершины сразу к найденному корню.

До: 2 → 1 → 0. После: 2 → 0 и 1 → 0.

Группа та же. Меняется только внутренняя форма дерева parent.

Группа та же. Меняется только внутренняя форма дерева parent.

Массив | 0 | 1 | 2 | 3 | 4
parent до | 0 | 0 | 1 | 3 | 4
parent после | 0 | 0 | 0 | 3 | 4

Сколько стало групп после сжатия?

По-прежнему три: {0,1,2}, {3}, {4}. Сжатие не соединяет разные группы.

### EN

After find, point visited vertices directly to the root we found.

Before: 2 → 1 → 0. After: 2 → 0 and 1 → 0.

The group stays the same. Only the internal parent tree changes.

The group stays the same. Only the internal parent tree changes.

Array | 0 | 1 | 2 | 3 | 4
parent before | 0 | 0 | 1 | 3 | 4
parent after | 0 | 0 | 0 | 3 | 4

How many groups remain after compression?

Still three: {0,1,2}, {3}, {4}. Compression does not join separate groups.

## 11. Зачем сохранять next при сжатии / Why compression saves next first

### RU

Перед изменением parent[x] запоминаем, куда он указывал раньше.

Отдельный длинный путь: 3→2→1→0. Для x=3 сохраняем next=2 перед parent[3]=0.

Здесь root=0 уже найден первым проходом. На каждой итерации сначала сохраняем старую ссылку, затем заменяем её на root, затем переходим по сохранённой ссылке. Если после записи сделать x=parent[x], сразу попадём в 0 и пропустим вершину 2. Тогда parent[2] останется 1 вместо прямой ссылки на 0. Корень найден, но полное сжатие посещённого пути не выполнено.

next позволяет продолжить старый путь и сжать остальные вершины.

```java
while (x != root) {
    int next = parent[x];
    parent[x] = root;
    x = next;
}
```

### EN

Before changing parent[x], remember its old destination.

A separate longer path: 3→2→1→0. For x=3, save next=2 before setting parent[3]=0.

Here root=0 has already been found by a first pass. In each iteration, save the old link, replace it with root, then follow the saved link. Using x=parent[x] after the write jumps straight to 0 and skips vertex 2. Then parent[2] remains 1 instead of pointing directly to 0. The root is known, but the visited path has not been fully compressed.

next lets us continue along the old path and compress the remaining vertices.

```java
while (x != root) {
    int next = parent[x];
    parent[x] = root;
    x = next;
}
```

## 12. union: присоединить меньшую группу / union: attach the smaller group

### RU

Сначала находим два корня. Если они разные, соединяем деревья.

Корень 0: size=3. Корень 3: size=1. Делаем parent[3]=0.

size хранит число вершин группы у её корня. Маленькое дерево присоединяем к большому, чтобы указатели не образовывали длинную цепочку. После объединения значим именно size нового корня. Статистику обновляем только при успешном соединении разных групп.

Новый size[0] = 4. Число групп уменьшается на 1. largest становится 4.

Состояние | Группы | components / largest
До union(2,3) | {0,1,2} {3} {4} | 3 / 3
После union(2,3) | {0,1,2,3} {4} | 2 / 4

### EN

Find both roots first. If they differ, join the trees.

Root 0: size=3. Root 3: size=1. Set parent[3]=0.

size stores the number of vertices in a group at its root. Attach a smaller tree to a larger one to avoid long pointer chains. After the merge, the new root holds the meaningful size. Update statistics only when two separate groups actually join.

The new size[0] is 4. The group count drops by 1. largest becomes 4.

State | Groups | components / largest
Before union(2,3) | {0,1,2} {3} {4} | 3 / 3
After union(2,3) | {0,1,2,3} {4} | 2 / 4

## 13. Повторное соединение ничего не увеличивает / A repeated union changes no sizes

### RU

Если корни одинаковы, возвращаем прежнюю статистику.

После {0,1,2,3} добавляем [0,2] и [4,4].

[0,2] соединяет вершины одной группы. [4,4] соединяет вершину с собой. В обоих случаях новой связи между группами нет. Но в states всё равно надо добавить строку после каждой операции. При n=0 операции пусты и states тоже пуст.

Обе операции сохраняют components=2 и largest=4.

```java
if (a == b) {
    // No merge: keep components and largest.
} else {
    // Join roots and update statistics.
}
```

### EN

If the roots match, keep the current statistics.

With {0,1,2,3} already connected, add [0,2] and [4,4].

[0,2] joins vertices already in the same group. [4,4] joins a vertex to itself. Neither operation joins separate groups. Still append a states row after every operation. When n=0, operations and states are both empty.

Both operations preserve components=2 and largest=4.

```java
if (a == b) {
    // No merge: keep components and largest.
} else {
    // Join roots and update statistics.
}
```

## 14. rank и size отвечают на разные вопросы / rank and size answer different questions

### RU

size считает вершины. rank помогает выбирать корень при объединении.

Две одиночные вершины: rank 0 и 0. После соединения rank корня = 1.

В A3 разрешено объединение по size или по rank. Сжатие пути требуется в обоих случаях. При rank меньший ранг присоединяем к большему. Только при равных рангах повышаем ранг нового корня на 1. Для largest всё равно отдельно считаем size. После сжатия rank не обязан совпадать с текущей высотой.

Добавили третью вершину: size=3, но rank остаётся 1.

Группа | size | rank корня
{0} | 1 | 0
{0,1} | 2 | 1
{0,1,2} | 3 | 1

### EN

size counts vertices. rank helps choose a root when merging.

Two singletons have ranks 0 and 0. After merging, the root rank is 1.

A3 allows union by size or by rank. Both require path compression. With rank, attach a lower-rank root under a higher-rank root. Only equal ranks increase the new root rank by 1. Maintain size separately for largest. After compression, rank need not equal the current height.

Attach a third singleton: size=3, but rank remains 1.

Group | size | Root rank
{0} | 1 | 0
{0,1} | 2 | 1
{0,1,2} | 3 | 1

## 15. DSU: полный вход и ответ / DSU: complete input and result

### RU

Возвращаем состояние после каждой операции, включая повтор и соединение с собой.

Те же пять вершин. После [2,3] группа {0,1,2,3} содержит четыре вершины.

Пять операций дают ровно пять пар в states. Начальное состояние отдельно не добавляем.

Пять операций дают ровно пять пар в states. Начальное состояние отдельно не добавляем.

Что | JSON
Вход | {"task":"dsu","n":5,"operations":[[0,1],[1,2],[2,3],[0,2],[4,4]]}
Выход | {"states":[[4,2],[3,3],[2,4],[2,4],[2,4]]}

### EN

Return a state after every operation, including repeats and self-unions.

The same five vertices. After [2,3], group {0,1,2,3} contains four vertices.

Five operations produce exactly five states rows. Do not add an extra initial row.

Five operations produce exactly five states rows. Do not add an extra initial row.

Item | JSON
Input | {"task":"dsu","n":5,"operations":[[0,1],[1,2],[2,3],[0,2],[4,4]]}
Output | {"states":[[4,2],[3,3],[2,4],[2,4],[2,4]]}

## 16. Остов: соединить площадки без лишних рёбер / A spanning tree connects without extra edges

### RU

Нужно соединить доступные площадки с минимальной общей стоимостью кабелей.

У нас 0, 1, 2, 3 и отдельная площадка 4 без кабелей.

Дерево связывает вершины без цикла. Лес состоит из нескольких таких деревьев. В A3 нельзя требовать связи всего графа, если вход разъединён. Изолированная вершина тоже считается компонентой. Для n вершин и c компонент лес содержит n−c рёбер.

Площадку 4 соединить нельзя. Ответ — лес: дерево группы и отдельная вершина.

### EN

Connect the available venues at the smallest total cable cost.

We have 0, 1, 2, 3, plus venue 4 with no available cable.

A tree connects vertices without a cycle. A forest consists of several such trees. A3 must handle disconnected input instead of requiring one global tree. An isolated vertex also counts as a component. For n vertices and c components, the forest has n−c edges.

Venue 4 cannot be connected. The answer is a forest: one group tree and one singleton.

## 17. Один пример для Краскала и Прима / One example for Kruskal and Prim

### RU

Пять доступных кабелей. Все связи двусторонние.

n=5. Вершина 4 изолирована, поэтому c=2 и нужно 5−2=3 ребра.

Цель: минимальная сумма весов, а не кратчайший путь из одной вершины.

Цель: минимальная сумма весов, а не кратчайший путь из одной вершины.

ID | Между вершинами | Вес
0 | 0 – 1 | 4
1 | 1 – 2 | 1
2 | 0 – 2 | 6
3 | 2 – 3 | 2
4 | 1 – 3 | 5

### EN

Five available cables. Every connection works in both directions.

n=5. Vertex 4 is isolated, so c=2 and we need 5−2=3 edges.

Minimize the sum of selected weights, not a route from one source.

Minimize the sum of selected weights, not a route from one source.

ID | Endpoints | Weight
0 | 0 – 1 | 4
1 | 1 – 2 | 1
2 | 0 – 2 | 6
3 | 2 – 3 | 2
4 | 1 – 3 | 5

## 18. Краскал: рассмотреть дешёвые рёбра первыми / Kruskal: consider cheaper edges first

### RU

Сортируем все рёбра по весу. Для групп используем DSU.

Порядок ID: 1, 3, 0, 4, 2. Веса: 1, 2, 4, 5, 6.

До первого шага каждая вершина сама себе группа. Сортировка меняет порядок просмотра, но сохраняет ID. Если оба конца уже связаны выбранными рёбрами, добавление ребра создаст цикл и оно не нужно. Для проверки вызываем find у обоих концов.

Ребро берём, когда оно соединяет две разные группы.

```java
if (find(u) != find(v)) {
    // Select the edge with its original ID.
    // Merge the two groups.
}
```

### EN

Sort all edges by weight. Use DSU to track groups.

ID order: 1, 3, 0, 4, 2. Weights: 1, 2, 4, 5, 6.

Initially every vertex is a separate group. Sorting changes the inspection order but preserves IDs. If selected edges already connect both endpoints, adding this edge would create a cycle. Check both endpoints with find.

Take an edge when it connects two separate groups.

```java
if (find(u) != find(v)) {
    // Select the edge with its original ID.
    // Merge the two groups.
}
```

## 19. Краскал: первые два принятых ребра / Kruskal: the first two selected edges

### RU

Оба ребра соединяют разные группы, поэтому принимаем их.

ID 1 соединяет 1–2 за 1. Затем ID 3 соединяет 2–3 за 2.

Теперь группа {1,2,3}, стоимость 3. Вершины 0 и 4 пока отдельно.

Теперь группа {1,2,3}, стоимость 3. Вершины 0 и 4 пока отдельно.

Шаг | Группы после шага | Стоимость
Старт | {0} {1} {2} {3} {4} | 0
ID 1, вес 1 | {0} {1,2} {3} {4} | 1
ID 3, вес 2 | {0} {1,2,3} {4} | 3

### EN

Both edges connect separate groups, so select them.

ID 1 links 1–2 for 1. Then ID 3 links 2–3 for 2.

Now {1,2,3} is one group with cost 3. Vertices 0 and 4 remain separate.

Now {1,2,3} is one group with cost 3. Vertices 0 and 4 remain separate.

Step | Groups afterwards | Cost
Start | {0} {1} {2} {3} {4} | 0
ID 1, weight 1 | {0} {1,2} {3} {4} | 1
ID 3, weight 2 | {0} {1,2,3} {4} | 3

## 20. Краскал: закончить пример / Kruskal: finish the example

### RU

ID 0 присоединяет 0 к группе. Остальные рёбра создают цикл.

Берём ID 0 за 4: всего 1+2+4=7. ID 4 и ID 2 пропускаем.

Ответ: cost=7, edge_ids=[1,3,0], components=2.

Ответ: cost=7, edge_ids=[1,3,0], components=2.

Ребро | Решение | Причина
ID 0: 0–1, вес 4 | Взять | {0} и {1,2,3} различны
ID 4: 1–3, вес 5 | Пропустить | 1 и 3 уже связаны
ID 2: 0–2, вес 6 | Пропустить | 0 и 2 уже связаны

Почему components не равно 1?

У вершины 4 нет входных рёбер. Она остаётся отдельной компонентой.

### EN

ID 0 attaches vertex 0. The remaining edges would create cycles.

Take ID 0 for 4: total 1+2+4=7. Skip ID 4 and ID 2.

Answer: cost=7, edge_ids=[1,3,0], components=2.

Answer: cost=7, edge_ids=[1,3,0], components=2.

Edge | Decision | Reason
ID 0: 0–1, weight 4 | Take | {0} and {1,2,3} are separate
ID 4: 1–3, weight 5 | Skip | 1 and 3 already connected
ID 2: 0–2, weight 6 | Skip | 0 and 2 already connected

Why is components not 1?

Vertex 4 has no input edges. It remains a separate component.

## 21. Почему самое дешёвое соединение безопасно / Why the cheapest crossing edge is safe

### RU

Разделим вершины на две стороны. Чтобы соединить стороны, нужно пересечь границу.

Для стороны {0}: кабель 0–1 стоит 4, а 0–2 стоит 6.

Это свойство разреза. Берём минимальное ребро между двумя сторонами разреза, который не разрывает уже выбранные компоненты. Если оптимальное дерево его не содержит, добавление создаёт цикл. На цикле есть другое ребро через этот разрез. Удаляем его: связность сохраняется, стоимость не растёт. Значит, существует оптимальное дерево с выбранным ребром. При равных весах оптимум может быть не единственным.

Более дорогой переход можно заменить дешёвым, сохранив связность дерева.

### EN

Split vertices into two sides. Connecting them requires crossing the boundary.

For side {0}: cable 0–1 costs 4, while 0–2 costs 6.

This is the cut property. Choose a minimum edge crossing a cut that does not split any already selected component. If an optimal tree omits it, adding it creates a cycle. That cycle has another edge across the same cut. Remove that edge: connectivity remains and cost does not increase. Thus some optimal tree contains the chosen edge. Tied weights may allow several optimal trees.

A more expensive crossing can be replaced by the cheaper one while preserving a tree.

## 22. Прим: растить одну подключённую группу / Prim: grow one connected group

### RU

Начинаем с вершины. Каждый раз выбираем самый дешёвый выход наружу.

Стартуем в 0. Кандидаты: ID 0 за 4 и ID 2 за 6.

Список кандидатов удобно хранить в priority queue: она быстро отдаёт запись с минимальным весом. В Приме выбираем только переход из уже подключённой части в новую вершину. В отличие от Краскала, не сортируем один раз весь граф и не соединяем произвольные группы.

Берём ID 0: подключены {0,1}, стоимость 4.

### EN

Start at a vertex. Repeatedly choose the cheapest edge leaving the connected set.

Start at 0. Candidates: ID 0 for 4 and ID 2 for 6.

A priority queue keeps candidates and quickly returns the smallest weight. Prim selects an edge from the connected set to a new vertex. Unlike Kruskal, it does not sort the entire graph once and merge arbitrary groups.

Take ID 0: {0,1} are connected, with cost 4.

## 23. Прим: как меняются кандидаты / Prim: how candidates change

### RU

После новой вершины добавляем её рёбра к ещё не подключённым вершинам.

После 1 есть 1–2 за 1 и 1–3 за 5. Старое 0–2 за 6 тоже осталось.

Берём вес 1, затем вес 2. Итог снова 4+1+2=7.

Берём вес 1, затем вес 2. Итог снова 4+1+2=7.

Подключены | Кандидаты по весу | Выбор
{0} | 0–1:4, 0–2:6 | ID 0
{0,1} | 1–2:1, 1–3:5, 0–2:6 | ID 1
{0,1,2} | 2–3:2, 1–3:5, 0–2:6 | ID 3

### EN

After adding a vertex, offer its edges to vertices not yet connected.

After 1, offer 1–2 for 1 and 1–3 for 5. Old candidate 0–2 for 6 remains.

Take weight 1, then weight 2. The total is again 4+1+2=7.

Take weight 1, then weight 2. The total is again 4+1+2=7.

Connected | Candidates by weight | Choice
{0} | 0–1:4, 0–2:6 | ID 0
{0,1} | 1–2:1, 1–3:5, 0–2:6 | ID 1
{0,1,2} | 2–3:2, 1–3:5, 0–2:6 | ID 3

## 24. Прим: устаревшее ребро пропускаем / Prim: skip an outdated candidate

### RU

Когда запись извлекается, её конечная вершина уже может быть подключена.

После {0,1,2,3} запись 1–3 за 5 всё ещё лежит в очереди.

Это lazy Prim. Запись сохраняет вес, конечную вершину и исходный ID. Когда вершина уже посещена, запись устарела. Устаревшая запись не означает ошибку очереди. Следующую подходящую запись извлекаем обычным образом. Петля также не приводит к новой вершине.

Вершина 3 уже посещена: не добавляем ребро и не увеличиваем стоимость.

```java
Candidate e = queue.remove();
if (visited[e.to]) {
    continue;
}
// Add this new vertex and its input edge ID.
```

### EN

When a candidate is removed, its destination may already be connected.

After {0,1,2,3} are connected, candidate 1–3 for 5 is still in the queue.

This is lazy Prim. Each record retains the weight, destination and input ID. A record is outdated when the destination is already visited. Such records are normal in this approach. Continue removing candidates until a usable one appears. A self-loop also cannot add a new vertex.

Vertex 3 is already visited: do not add the edge or increase the cost.

```java
Candidate e = queue.remove();
if (visited[e.to]) {
    continue;
}
// Add this new vertex and its input edge ID.
```

## 25. Прим: очередь пуста, но работа ещё есть / Prim: an empty queue may leave more work

### RU

Внешний цикл ищет каждую ещё не посещённую вершину.

Группа {0,1,2,3} готова. Начинаем отдельную компоненту с 4.

Пустая очередь завершает только текущую компоненту. Без внешнего цикла Прим пропустит другие компоненты и изоляты. При n=0 внешний цикл не выполняется: cost=0, edge_ids=[], components=0.

У 4 нет рёбер. components=2, cost=7, выбрано 3 ребра.

### EN

An outer loop finds every vertex that has not been visited.

Group {0,1,2,3} is complete. Start a separate component at 4.

An empty queue completes only the current component. Without the outer loop, Prim misses other components and isolated vertices. For n=0 the outer loop does nothing: cost=0, edge_ids=[], components=0.

Vertex 4 has no edges. components=2, cost=7, with 3 selected edges.

## 26. Проверка двух решений MST / Checking both MST implementations

### RU

Сравниваем стоимость и корректность леса. Список ID может отличаться.

Треугольник: все три ребра стоят 1. Можно выбрать любые два.

Проверяйте, что ID существуют во входе, не повторяются, не образуют цикл и соединяют каждую входную компоненту. Число рёбер равно n−c. Затем проверяйте минимальную стоимость независимым способом на маленьких графах. Отрицательные веса допустимы, параллельные рёбра сохраняют разные ID, сумму храните в 64 битах.

У всех правильных ответов стоимость 2 и нет цикла.

Нужно ли требовать, чтобы Краскал и Прим вернули одинаковые ID?

Нет. При равных весах могут быть разные минимальные леса.

### EN

Compare cost and forest validity. The ID lists may differ.

A triangle has three edges of weight 1. Any two form an optimum.

Check that IDs exist in the input, do not repeat, form no cycle and connect every input component. The edge count is n−c. Then independently check minimum cost on small graphs. Negative weights are allowed, parallel edges keep different IDs, and totals need 64-bit storage.

Every valid optimum has cost 2 and no cycle.

Must Kruskal and Prim return identical IDs?

No. Tied weights may allow different minimum forests.

## 27. SCC: можно пройти туда и обратно / SCC: paths exist in both directions

### RU

Две вершины в одной SCC, если каждая достижима из другой по стрелкам.

0 → 1 и 1 → 0. Значит, {0,1} — одна группа.

SCC расшифровывается как strongly connected component, компонента сильной связности. Группа максимальна: в неё включаем все вершины, которые взаимно достижимы. Самостоятельная изолированная вершина также образует SCC.

Одного пути 0 → 2 недостаточно: нужен также путь из 2 в 0.

### EN

Two vertices share an SCC when each can reach the other along arrows.

0 → 1 and 1 → 0. Therefore {0,1} is one group.

SCC means strongly connected component. The group is maximal: include every vertex mutually reachable with its members. A single isolated vertex is also an SCC.

A path 0 → 2 alone is insufficient: a path from 2 back to 0 is also needed.

## 28. Пример SCC: две пары и изолят / SCC example: two pairs and a singleton

### RU

Связи внутри пар двусторонние. Между парами есть только путь вправо.

Рёбра: 0→1, 1→0, 1→2, 1→2, 2→3, 3→2. Вершина 4 отдельно.

Правильные группы: {0,1}, {2,3}, {4}. Два ребра 1→2 не меняют группы.

Правильные группы: {0,1}, {2,3}, {4}. Два ребра 1→2 не меняют группы.

Откуда | Куда можно попасть | Есть путь обратно?
0 | 1 | Да
1 | 2 | Нет: из {2,3} не выйти назад
2 | 3 | Да
4 | Только в 4 | Отдельная SCC

### EN

Each pair connects both ways. Between pairs there is only a forward connection.

Edges: 0→1, 1→0, 1→2, 1→2, 2→3, 3→2. Vertex 4 is isolated.

Correct groups: {0,1}, {2,3}, {4}. Duplicate 1→2 edges do not change them.

Correct groups: {0,1}, {2,3}, {4}. Duplicate 1→2 edges do not change them.

From | Reachable destination | Can we get back?
0 | 1 | Yes
1 | 2 | No: {2,3} has no path back
2 | 3 | Yes
4 | Only 4 | A separate SCC

## 29. DFS: завершение позже входа / DFS: finishing happens after entering

### RU

DFS идёт в ещё не посещённого соседа и сначала заканчивает его работу.

Из 0 идём в 1, затем в 2, затем в 3.

Вход означает, что мы начали разбирать вершину. Завершение означает, что обработаны её соседи и их продолжения. В цикле 0→1→0 повторно не заходим в уже посещённую 0. Для данного примера сначала завершается 3, затем 2, 1, 0. Внешний цикл после этого посещает 4.

Записываем вершину при выходе, когда все её исходящие рёбра просмотрены.

Порядок | Вершины
Входы DFS | 0, 1, 2, 3, затем 4
Завершения | 3, 2, 1, 0, 4
В обратном порядке завершения | 4, 0, 1, 2, 3

### EN

DFS follows an unvisited neighbor and completes that work before returning.

From 0, visit 1, then 2, then 3.

Entering means we have started processing a vertex. Finishing means its neighbors and their continuations are complete. In cycle 0→1→0, do not enter visited 0 again. Here the finish order is 3, then 2, 1, 0. The outer loop then visits 4.

Record a vertex on exit, after all its outgoing edges have been inspected.

Order | Vertices
DFS entries | 0, 1, 2, 3, then 4
Finishes | 3, 2, 1, 0, 4
Reversed finishes | 4, 0, 1, 2, 3

## 30. Косарайю: развернуть каждую стрелку / Kosaraju: reverse every arrow

### RU

Транспонированный граф — тот же набор вершин со всеми обратными рёбрами.

Было 1→2. Станет 2→1. Пары 0↔1 и 2↔3 останутся двусторонними.

Первый проход был по исходному графу. Второй будет по транспонированному в порядке 4,0,1,2,3. Допустима и симметричная версия с обратным графом первым, если второй граф — его транспонирование. Здесь весь пример придерживается одного выбранного варианта.

Перед вторым обходом очищаем visited. Иначе все вершины уже помечены.

### EN

The transpose has the same vertices and every edge reversed.

Originally 1→2. Now 2→1. Pairs 0↔1 and 2↔3 still connect both ways.

The first pass used the original graph. The second uses its transpose in order 4,0,1,2,3. A symmetric version may start with the transpose if the second graph reverses it. This example consistently uses the first convention.

Clear visited before the second pass. Otherwise every vertex is already marked.

## 31. Косарайю: второй обход выделяет группы / Kosaraju: the second pass finds groups

### RU

Второй обход начинает новую группу с каждой ещё не помеченной вершины.

Порядок старта 4,0,1,2,3. Уже найденную группу повторно не обходим.

Из 2 в обратном графе есть путь к 1, но 1 уже помечена. Через неё не идём.

Из 2 в обратном графе есть путь к 1, но 1 уже помечена. Через неё не идём.

Старт | Новые вершины | Группа
4 | 4 | {4}
0 | 0, 1 | {0,1}
1 | Уже помечена | Пропустить
2 | 2, 3 | {2,3}
3 | Уже помечена | Пропустить

### EN

The second pass starts a new group at each vertex not yet labeled.

Start order is 4,0,1,2,3. Skip vertices already assigned to a group.

From 2 the transpose reaches 1, but 1 is already labeled. Do not traverse it again.

From 2 the transpose reaches 1, but 1 is already labeled. Do not traverse it again.

Start | New vertices | Group
4 | 4 | {4}
0 | 0, 1 | {0,1}
1 | Already labeled | Skip
2 | 2, 3 | {2,3}
3 | Already labeled | Skip

## 32. component: метка для каждой вершины / component: one label per vertex

### RU

Одинаковая метка означает одну SCC. Само число метки не важно.

Можно вернуть [7,7,9,9,12] или [1,1,2,2,0].

Индекс массива — номер вершины. Значение — неотрицательная целая метка её группы. Нельзя сравнивать правильность только с одним эталонным массивом меток. Проверяем, что равенство меток точно соответствует взаимной достижимости.

Оба ответа задают те же группы: {0,1}, {2,3}, {4}.

Допустимо ли [7,9,9,9,12] для нашего графа?

Нет. 0 и 1 должны иметь одну метку, а 1 и 2 — разные.

### EN

The same label means the same SCC. The label value itself is arbitrary.

You may return [7,7,9,9,12] or [1,1,2,2,0].

An array index identifies the vertex. Its value is a nonnegative integer group label. Correctness cannot require one exact reference label array. Check that equal labels correspond exactly to mutual reachability.

Both answers represent the same groups: {0,1}, {2,3}, {4}.

Is [7,9,9,9,12] valid for this graph?

No. Vertices 0 and 1 must share a label, while 1 and 2 must differ.

## 33. Конденсация: заменить группу одной вершиной / Condensation: replace each group by one vertex

### RU

Оставляем только переходы между разными SCC, без повторений.

При метках [7,7,9,9,12] два ребра 1→2 дают один переход [7,9].

Пройдите каждое исходное ребро u→v. Получите a=component[u] и b=component[v]. Если a=b, пропустите ребро. Если разные, добавьте пару в множество, чтобы убрать повторы. Для изолированной группы 12 не требуется вымышленное ребро. Циклов между SCC уже нет, иначе эти группы были бы одной SCC.

Внутренние рёбра группы в ответ не входят. condensation=[[7,9]].

```java
int a = component[u];
int b = component[v];
if (a != b) {
    pairs.add(new Pair(a, b));
}
```

### EN

Keep only edges between different SCCs, with no duplicate pairs.

With labels [7,7,9,9,12], two 1→2 edges become one [7,9] edge.

For each input edge u→v, compute a=component[u] and b=component[v]. Skip it if a=b. Otherwise add the pair to a set to remove duplicates. The isolated group 12 needs no invented edge. There is no cycle between SCCs, since such groups would actually be one SCC.

Edges inside a group disappear. condensation=[[7,9]].

```java
int a = component[u];
int b = component[v];
if (a != b) {
    pairs.add(new Pair(a, b));
}
```

## 34. SCC: полный вход и один правильный ответ / SCC: complete input and one valid answer

### RU

component содержит метку каждой из пяти вершин. condensation перечисляет связи меток.

Два ребра 1→2 дают только одну пару [7,9]. Вершина 4 имеет отдельную метку 12.

Другие неотрицательные метки допустимы, если группы и связи остаются теми же.

Другие неотрицательные метки допустимы, если группы и связи остаются теми же.

Что | JSON
Вход | {"task":"scc","n":5,"edges":[[0,1],[1,0],[1,2],[1,2],[2,3],[3,2]]}
Выход | {"component":[7,7,9,9,12],"condensation":[[7,9]]}

### EN

component gives a label for each of five vertices. condensation lists edges between labels.

Two 1→2 edges yield only one [7,9] pair. Vertex 4 has its own label 12.

Other nonnegative labels work if the groups and their connections remain the same.

Other nonnegative labels work if the groups and their connections remain the same.

Item | JSON
Input | {"task":"scc","n":5,"edges":[[0,1],[1,0],[1,2],[1,2],[2,3],[3,2]]}
Output | {"component":[7,7,9,9,12],"condensation":[[7,9]]}

## 35. Topo: порядок работ без нарушения зависимостей / Topo: a task order that respects dependencies

### RU

Ребро u→v означает: работа u должна закончиться раньше v.

0: подготовить площадку. 1: установить сцену. 2: провести питание. 3: начать концерт.

Топологический порядок — список всех вершин по одному разу. Для каждого ребра его начало находится раньше конца. Например, [0,1,2,3] и [0,2,1,3] подходят. Начало DFS не равно готовому ответу: нужный порядок строим по завершениям.

Зависимости: 0→1, 0→2, 1→3, 2→3. Концерт идёт после обеих подготовок.

### EN

An edge u→v means task u must finish before v.

0: prepare venue. 1: install stage. 2: connect power. 3: start concert.

A topological order lists every vertex exactly once. For every edge, its source appears before its destination. Both [0,1,2,3] and [0,2,1,3] work. DFS discovery order is not automatically an answer: use finish order.

Dependencies: 0→1, 0→2, 1→3, 2→3. The concert follows both preparations.

## 36. Три состояния DFS / The three DFS states

### RU

Нужно отличать текущую незавершённую цепочку от уже обработанной вершины.

WHITE: не начинали. GRAY: начали, ещё не вышли. BLACK: закончили.

Ребро к GRAY обнаруживает цикл. Ребро к BLACK само по себе цикла не доказывает.

Ребро к GRAY обнаруживает цикл. Ребро к BLACK само по себе цикла не доказывает.

Состояние | Простой смысл | Когда меняем
WHITE / 0 | Ещё не разбирали | Начальное значение
GRAY / 1 | Сейчас в цепочке DFS | При входе
BLACK / 2 | Все соседи обработаны | При выходе

### EN

Distinguish the current unfinished chain from a completed vertex.

WHITE: not started. GRAY: entered, not exited. BLACK: completed.

An edge to GRAY reveals a cycle. An edge to BLACK alone does not.

An edge to GRAY reveals a cycle. An edge to BLACK alone does not.

State | Plain meaning | When assigned
WHITE / 0 | Not processed yet | Initially
GRAY / 1 | On the current DFS chain | On entry
BLACK / 2 | All neighbors processed | On exit

## 37. DFS по примеру: сначала 0, затем 1 и 3 / DFS trace: first 0, then 1 and 3

### RU

Соседей 0 смотрим в порядке 1, затем 2.

Входим в 0, затем в 1, затем в 3. У 3 соседей нет.

Завершаем 3 и записываем [3]. Возвращаемся в 1, завершаем: [3,1].

Завершаем 3 и записываем [3]. Возвращаемся в 1, завершаем: [3,1].

Момент | Текущая цепочка GRAY | Завершены
Вход в 0 | 0 | []
Вход в 1 | 0, 1 | []
Вход в 3 | 0, 1, 3 | []
Выход из 3 | 0, 1 | [3]
Выход из 1 | 0 | [3,1]

### EN

Inspect neighbors of 0 in order 1, then 2.

Enter 0, then 1, then 3. Vertex 3 has no outgoing edges.

Finish 3 and record [3]. Return to 1 and finish it: [3,1].

Finish 3 and record [3]. Return to 1 and finish it: [3,1].

Moment | Current GRAY chain | Finished
Enter 0 | 0 | []
Enter 1 | 0, 1 | []
Enter 3 | 0, 1, 3 | []
Exit 3 | 0, 1 | [3]
Exit 1 | 0 | [3,1]

## 38. DFS по примеру: ветка 2 и разворот / DFS trace: branch 2 and reversal

### RU

Из 0 переходим в 2. Её сосед 3 уже BLACK.

Цикла нет. Завершаем 2, затем 0: [3,1,2,0].

Почему разворот работает? Для ребра u→v без цикла либо v заканчивается внутри обхода u, либо v уже BLACK. В обоих случаях v завершается раньше u. Поэтому после разворота u стоит раньше v. Проверьте четыре зависимости нашего примера отдельно.

Разворачиваем список: [0,2,1,3]. Это один правильный topo order.

Ребро | Позиции в [0,2,1,3] | Порядок верный?
0→1 | 0 < 2 | Да
0→2 | 0 < 1 | Да
1→3 | 2 < 3 | Да
2→3 | 1 < 3 | Да

### EN

From 0, enter 2. Its neighbor 3 is already BLACK.

There is no cycle. Finish 2, then 0: [3,1,2,0].

Why does reversal work? For an edge u→v in an acyclic graph, v either finishes inside the traversal of u or is already BLACK. In both cases v finishes before u. Reversal therefore puts u before v. Check all four example dependencies separately.

Reverse the list: [0,2,1,3]. This is one valid topological order.

Edge | Positions in [0,2,1,3] | Valid?
0→1 | 0 < 2 | Yes
0→2 | 0 < 1 | Yes
1→3 | 2 < 3 | Yes
2→3 | 1 < 3 | Yes

## 39. Цикл: работа зависит от самой себя через цепочку / A cycle makes a task depend on itself

### RU

Добавим 3→0. Теперь 0 должна предшествовать 3, а 3 — 0.

В цепочке 0→1→3 видим ребро к GRAY-вершине 0.

acyclic означает «без цикла». Замкнутая цепочка зависимостей не позволяет выбрать первый элемент этой цепочки. Петля u→u также цикл. Проверяем все компоненты: цикл может быть в стороне от вершины 0. В A3 обязателен DFS с тремя состояниями.

Ответ: {"acyclic":false,"order":[]}. Частичный порядок возвращать нельзя.

Почему ребро 2→3 из прошлого примера не было циклом?

3 была BLACK и не находилась в текущей цепочке. Пути назад к 2 не было.

### EN

Add 3→0. Now 0 must precede 3, while 3 must precede 0.

On chain 0→1→3, an edge reaches GRAY vertex 0.

acyclic means having no cycle. A closed dependency chain has no possible first task. A self-loop u→u is also a cycle. Check every component, since a cycle may be disconnected from vertex 0. A3 requires DFS with three states.

Answer: {"acyclic":false,"order":[]}. Do not return a partial order.

Why was edge 2→3 in the previous example not a cycle?

Vertex 3 was BLACK and outside the current chain. There was no path back to 2.

## 40. Глубокий DFS: запомнить место возврата / Deep DFS: remember where to resume

### RU

Для длинной цепочки рекурсивный стек может закончиться. Используем явный стек.

Кадр хранит вершину v и номер следующего соседа next.

Стек — список незавершённых вызовов. Верхний кадр соответствует текущему вызову. Перед спуском к соседу увеличьте next у родителя. Когда соседи кончились, покрасьте вершину BLACK, добавьте в finish и удалите кадр. Обычный стек только из номеров вершин без момента выхода не сохраняет правильный finish order автоматически.

Пока у вершины есть необработанный сосед, она ещё не завершена.

```java
// Frame: v = current vertex, next = next neighbor
if (frame.next == adj[v].size()) {
    color[v] = BLACK;
    finish.add(v);
    stack.pop();
}
```

### EN

A long chain may overflow the recursive call stack. Use an explicit stack.

A frame stores vertex v and the index next of its next neighbor.

The stack represents unfinished calls. Its top frame is the current call. Before descending, advance the parent frame’s next index. When neighbors run out, mark the vertex BLACK, append it to finish and remove the frame. A stack of vertex IDs alone does not automatically preserve exit times.

A vertex is unfinished while it still has a neighbor to inspect.

```java
// Frame: v = current vertex, next = next neighbor
if (frame.next == adj[v].size()) {
    color[v] = BLACK;
    finish.add(v);
    stack.pop();
}
```

## 41. Topo: полный вход и ответ / Topo: complete input and result

### RU

В order все вершины встречаются один раз. Каждая зависимость направлена вперёд.

Для нашего примера подходят и [0,2,1,3], и [0,1,2,3].

Если добавить цикл, acyclic=false и order=[] независимо от частичного обхода.

Если добавить цикл, acyclic=false и order=[] независимо от частичного обхода.

Что | JSON
Вход | {"task":"topo","n":4,"edges":[[0,1],[0,2],[1,3],[2,3]]}
Выход | {"acyclic":true,"order":[0,2,1,3]}

### EN

order contains every vertex once. Every dependency points forward.

For our example, both [0,2,1,3] and [0,1,2,3] work.

Adding a cycle requires acyclic=false and order=[], regardless of any partial traversal.

Adding a cycle requires acyclic=false and order=[], regardless of any partial traversal.

Item | JSON
Input | {"task":"topo","n":4,"edges":[[0,1],[0,2],[1,3],[2,3]]}
Output | {"acyclic":true,"order":[0,2,1,3]}

## 42. DAG: направленный граф без цикла / DAG: a directed graph with no cycle

### RU

Теперь ищем самый дешёвый маршрут из заданной вершины source.

source=0. В таблице перечислены входные рёбра с исходными ID.

Вес −5 допустим: это скидка. В DAG нет возможности обходить цикл бесконечно.

Вес −5 допустим: это скидка. В DAG нет возможности обходить цикл бесконечно.

ID | Ребро | Вес
0 | 0→1 | 3
1 | 0→2 | 8
2 | 1→2 | −5
3 | 2→3 | 4
— | Вершина 4 изолирована | —

### EN

Now find the cheapest route from a given source vertex.

source=0. The table lists input edges with their original IDs.

Weight −5 is allowed: it is a discount. A DAG has no cycle to repeat forever.

Weight −5 is allowed: it is a discount. A DAG has no cycle to repeat forever.

ID | Edge | Weight
0 | 0→1 | 3
1 | 0→2 | 8
2 | 1→2 | −5
3 | 2→3 | 4
— | Vertex 4 is isolated | —

## 43. distance: лучшая известная стоимость / distance: the best cost known so far

### RU

В источнике стоимость 0. Для остальных маршрута пока нет: null.

distance = [0, null, null, null, null]. parent_edge = [−1,−1,−1,−1,−1].

Индекс массива — номер вершины. distance[v] — стоимость лучшего найденного пути из source в v. parent_edge[v] — ID последнего ребра выбранного пути. −1 в parent_edge означает отсутствие родительского ребра, в том числе у source. Эти два массива используют разные обозначения отсутствия.

null означает «маршрут не найден». Число −1 может быть настоящей стоимостью.

### EN

The source costs 0. No route is known for other vertices yet: null.

distance = [0, null, null, null, null]. parent_edge = [−1,−1,−1,−1,−1].

An array index identifies a vertex. distance[v] is the cheapest known cost from source to v. parent_edge[v] is the ID of the final edge on that chosen route. In parent_edge, −1 means no parent edge, including at source. These arrays use different absence markers.

null means no route is known. The number −1 can be a genuine route cost.

## 44. Релаксация: проверить более дешёвый путь / Relaxation: test a cheaper route

### RU

Для ребра u→v считаем: стоимость до u плюс вес ребра.

До 1 стоит 3. Ребро 1→2 весит −5. Кандидат до 2: 3−5=−2.

Слово «релаксация» здесь означает одну попытку улучшить ответ соседней вершины. Если distance[u]=null, путь до u отсутствует и прибавлять вес нельзя. Ребро ID сохраняем одновременно с новой стоимостью, чтобы потом восстановить именно этот маршрут.

Если до v пути ещё нет или новый путь дешевле, обновляем distance и parent_edge.

```java
if (distance[u] == null) continue;
long candidate = distance[u] + weight;
if (distance[v] == null || candidate < distance[v]) {
    distance[v] = candidate;
    parentEdge[v] = id;
}
```

### EN

For an edge u→v, add the cost to u and the edge weight.

Reaching 1 costs 3. Edge 1→2 weighs −5. The candidate for 2 is 3−5=−2.

Here relaxation means one attempt to improve a neighbor’s answer. If distance[u]=null, u is unreachable and no addition is valid. Save the edge ID with the new distance so that reconstruction follows this exact route.

If no route to v exists yet or the new route is cheaper, update distance and parent_edge.

```java
if (distance[u] == null) continue;
long candidate = distance[u] + weight;
if (distance[v] == null || candidate < distance[v]) {
    distance[v] = candidate;
    parentEdge[v] = id;
}
```

## 45. Шаг 1: обработать source = 0 / Step 1: process source = 0

### RU

Порядок [0,1,2,3,4] подходит: каждое ребро идёт вперёд.

Ребро ID 0: до 1 получаем 0+3=3. ID 1: до 2 получаем 0+8=8.

После 0: distance=[0,3,8,null,null], parent_edge=[−1,0,1,−1,−1].

После 0: distance=[0,3,8,null,null], parent_edge=[−1,0,1,−1,−1].

Вершина | distance | Последнее ребро
0 | 0 | −1: источник
1 | 3 | ID 0
2 | 8 | ID 1
3 | null | −1
4 | null | −1

### EN

Order [0,1,2,3,4] works: every edge points forward.

Edge ID 0 reaches 1 for 0+3=3. ID 1 reaches 2 for 0+8=8.

After 0: distance=[0,3,8,null,null], parent_edge=[−1,0,1,−1,−1].

After 0: distance=[0,3,8,null,null], parent_edge=[−1,0,1,−1,−1].

Vertex | distance | Final edge
0 | 0 | −1: source
1 | 3 | ID 0
2 | 8 | ID 1
3 | null | −1
4 | null | −1

## 46. Шаг 2: более дешёвый путь через 1 / Step 2: a cheaper route through 1

### RU

У 1 уже известна стоимость 3. Проверяем ребро ID 2: 1→2, вес −5.

Кандидат −2 дешевле старого 8. Меняем обе записи для вершины 2.

Новый путь до 2: 0→1→2. parent_edge[2]=2, а не номер вершины 1.

Новый путь до 2: 0→1→2. parent_edge[2]=2, а не номер вершины 1.

Что сравниваем | Значение
Старый путь 0→2 | 8
Новый путь 0→1→2 | 3 + (−5) = −2
distance[2] после шага | −2
parent_edge[2] после шага | 2: исходный ID ребра 1→2

### EN

Vertex 1 already has cost 3. Test edge ID 2: 1→2, weight −5.

Candidate −2 is cheaper than 8. Update both entries for vertex 2.

New route to 2: 0→1→2. parent_edge[2]=2, not predecessor vertex 1.

New route to 2: 0→1→2. parent_edge[2]=2, not predecessor vertex 1.

Quantity | Value
Old route 0→2 | 8
New route 0→1→2 | 3 + (−5) = −2
Updated distance[2] | −2
Updated parent_edge[2] | 2: input ID of edge 1→2

## 47. Шаг 3: закончить расстояния / Step 3: complete the distances

### RU

Из 2 идём в 3: −2+4=2. Изолированная 4 остаётся недостижимой.

distance=[0,3,−2,2,null]. parent_edge=[−1,0,2,3,−1].

У 3 нет исходящих рёбер. У 4 distance=null, поэтому из неё ничего не релаксируем. Итог: стоимость пути 0→1→2→3 равна 3−5+4=2. Не используйте −1 вместо null: например, ребро веса −1 из источника даёт правильное расстояние −1.

Ответ включает acyclic=true и оба массива по одному элементу на вершину.

Какое расстояние вернуть для вершины 4?

null. Вход не содержит пути из 0 в 4.

### EN

From 2, reach 3 for −2+4=2. Isolated vertex 4 stays unreachable.

distance=[0,3,−2,2,null]. parent_edge=[−1,0,2,3,−1].

Vertex 3 has no outgoing edges. Vertex 4 has distance=null, so relax nothing from it. Route 0→1→2→3 costs 3−5+4=2. Do not replace null by −1: an edge of weight −1 from the source produces a valid distance of −1.

The result includes acyclic=true and both arrays with one entry per vertex.

What distance should vertex 4 receive?

null. The input contains no path from 0 to 4.

## 48. Почему достаточно одного прохода / Why one pass is enough

### RU

В topo order все возможные предшественники вершины обработаны раньше неё.

Перед обработкой 2 мы уже проверили 0→2 и 1→2.

Поэтому к моменту обработки u её лучшее расстояние уже окончательно. Достаточно передать его всем соседям один раз. Это работает и с отрицательными весами. Сначала проверяем отсутствие цикла во всём графе. Если цикл есть даже в недостижимой части, возвращаем acyclic=false, distance=[], parent_edge=[].

Вернуться из более поздней вершины в 2 нельзя: это нарушило бы topo order.

### EN

In topological order, every possible predecessor is processed first.

Before processing 2, we have already tested 0→2 and 1→2.

Thus the best distance to u is final when u is processed. Pass it to every neighbor once. Negative weights are safe under this ordering. First check the entire graph for cycles. Even an unreachable cycle requires acyclic=false, distance=[], parent_edge=[].

No later vertex can send an edge back to 2: that would violate the order.

## 49. Восстановление: идти по последним рёбрам назад / Reconstruction: follow final edges backwards

### RU

parent_edge хранит последнюю часть маршрута. Начинаем с цели 3.

3: ID 3 ведёт из 2. Затем 2: ID 2 ведёт из 1. Затем 1: ID 0 ведёт из 0.

Собрали [3,2,1,0]. Развернули: [0,1,2,3].

Собрали [3,2,1,0]. Развернули: [0,1,2,3].

Текущая вершина | parent_edge | Предыдущая вершина
3 | 3 | 2
2 | 2 | 1
1 | 0 | 0
0 | −1 | Остановиться: источник

### EN

parent_edge stores each route’s final edge. Start at target 3.

At 3, ID 3 comes from 2. At 2, ID 2 comes from 1. At 1, ID 0 comes from 0.

Collected [3,2,1,0]. Reverse it: [0,1,2,3].

Collected [3,2,1,0]. Reverse it: [0,1,2,3].

Current vertex | parent_edge | Previous vertex
3 | 3 | 2
2 | 2 | 1
1 | 0 | 0
0 | −1 | Stop: source

## 50. Helper: три обязательных вызова / Helper: three required calls

### RU

В собственных тестах вызываем helper для достижимой цели, источника и недостижимой цели.

Наш пример: target=3 даёт [0,1,2,3]. target=0 даёт [0].

Helper — вспомогательная функция вашей реализации, а не новый JSON task. Перед восстановлением проверяйте достижимость. Не пытайтесь получить ребро по ID −1. Для источника верните [source]. Для достижимой цели переходите по началу выбранного исходного ребра. Запишите реальные вызовы и результаты в ANALYSIS.md.

Для target=4 выберите и опишите результат «пути нет», например [].

### EN

In your tests, call the helper for a reachable target, source and unreachable target.

In our example, target=3 gives [0,1,2,3]. target=0 gives [0].

The helper is an internal function, not a new JSON task. Check reachability before reconstructing. Do not look up edge ID −1. Return [source] for the source itself. For a reachable target, follow the source endpoint of each chosen input edge. Record actual calls and results in ANALYSIS.md.

For target=4, choose and document a no-route result, for example [].

## 51. Helper: как прочитать одно родительское ребро / Helper: reading one parent edge

### RU

v — текущая вершина. id — ID последнего ребра пути до неё.

Для v=3 получаем id=3. edges[3]=[2,3,4], значит, предыдущая вершина — 2.

Этот фрагмент выполняется внутри цикла восстановления для достижимой цели, пока v не равна source. route хранит вершины в обратном порядке. parentEdge — внутреннее Java-имя массива, который адаптер возвращает как parent_edge. edges[id][0] — начало именно выбранного исходного ребра. Доступ по −1 недопустим, поэтому источник обрабатываем отдельно.

Сохраняем 3 в маршрут и продолжаем из 2. Готовый список надо развернуть.

```java
route.add(v);
int id = parentEdge[v];
int previous = edges[id][0];
v = previous;
```

### EN

v is the current vertex. id is the final edge ID on its route.

For v=3, id=3. edges[3]=[2,3,4], so the previous vertex is 2.

This snippet belongs inside a reconstruction loop for a reachable target while v differs from source. route collects vertices in reverse order. parentEdge is the internal Java name for the array returned as parent_edge. edges[id][0] is the source of the chosen input edge. Index −1 is invalid, so handle source separately.

Save 3 in the route and continue from 2. Reverse the collected list at the end.

```java
route.add(v);
int id = parentEdge[v];
int previous = edges[id][0];
v = previous;
```

## 52. Равные пути: несколько правильных ответов / Tied routes: several answers can be correct

### RU

Если стоимость одинаковая, можно сохранить любой согласованный кратчайший путь.

ID 0: 0→1 за 2. ID 1: такой же. ID 2: 1→2 за 1. ID 3: 0→2 за 3.

До 2 стоят 3 и прямой путь, и путь через 1. Важно сохранить правильный ID.

До 2 стоят 3 и прямой путь, и путь через 1. Важно сохранить правильный ID.

Выбранный маршрут до 2 | distance | Допустимый parent_edge
Через 1, первое ребро ID 0 | [0,2,3] | [−1,0,2]
Через 1, первое ребро ID 1 | [0,2,3] | [−1,1,2]
Прямое ребро ID 3 | [0,2,3] | [−1,0,3]

Можно просто положить номер предыдущей вершины в parent_edge?

Нет. Это ID входного ребра. При параллельных рёбрах номер вершины не определяет выбранное ребро.

### EN

Equal costs allow any consistent shortest route.

ID 0: 0→1 for 2. ID 1: identical. ID 2: 1→2 for 1. ID 3: 0→2 for 3.

Both the direct route and the route through 1 cost 3. Preserve the correct edge ID.

Both the direct route and the route through 1 cost 3. Preserve the correct edge ID.

Chosen route to 2 | distance | Valid parent_edge
Via 1, first edge ID 0 | [0,2,3] | [−1,0,2]
Via 1, first edge ID 1 | [0,2,3] | [−1,1,2]
Direct edge ID 3 | [0,2,3] | [−1,0,3]

Can parent_edge simply store the previous vertex?

No. It is an input edge ID. A predecessor vertex cannot identify a chosen parallel edge.

## 53. DAG: полный ответ нашего первого примера / DAG: the full result of our first example

### RU

Возвращаем стоимость и последнее ребро для каждой вершины.

Возвращаемся к графу с весами 3, 8, −5, 4 и source=0.

null стоит только у недостижимой 4. −1 в parent_edge стоит у источника и у 4.

null стоит только у недостижимой 4. −1 в parent_edge стоит у источника и у 4.

Что | JSON
Вход | {"task":"dag","n":5,"source":0,"edges":[[0,1,3],[0,2,8],[1,2,-5],[2,3,4]]}
Выход | {"acyclic":true,"distance":[0,3,-2,2,null],"parent_edge":[-1,0,2,3,-1]}

### EN

Return the cost and final edge for every vertex.

Return to the graph with weights 3, 8, −5, 4 and source=0.

Only unreachable 4 has null. parent_edge uses −1 for the source and for 4.

Only unreachable 4 has null. parent_edge uses −1 for the source and for 4.

Item | JSON
Input | {"task":"dag","n":5,"source":0,"edges":[[0,1,3],[0,2,8],[1,2,-5],[2,3,4]]}
Output | {"acyclic":true,"distance":[0,3,-2,2,null],"parent_edge":[-1,0,2,3,-1]}

## 54. Сложность: сколько раз смотрим данные / Complexity: how often data is inspected

### RU

n — число вершин, m — число рёбер, q — число операций DSU.

O(n+m): работа растёт примерно вместе с общим размером списков.

O обозначает верхнюю оценку роста, а не точное время в миллисекундах. В Косарайю есть два DFS и построение обратного графа, но множитель 2 не меняет O(n+m). В DAG добавляется один проход по рёбрам в topo order. Helper тратит O(L), где L — число вершин возвращаемого маршрута.

Для Косарайю, topo и DAG каждый список просматриваем постоянное число раз.

### EN

n counts vertices, m counts edges, q counts DSU operations.

O(n+m): work grows with the total size of the vertex and edge lists.

O describes an upper bound on growth, not an exact millisecond runtime. Kosaraju uses two DFS passes and builds a transpose, but a factor of 2 does not change O(n+m). DAG paths add one edge pass in topological order. Reconstruction costs O(L), where L is the number of returned route vertices.

Kosaraju, topo and DAG inspect each list only a fixed number of times.

## 55. Время и память каждого метода / Time and memory for each method

### RU

Учитываем построение структур и размер возвращаемого ответа.

В DSU states содержит q строк. Его тоже надо хранить.

α(n) — очень медленно растущая функция для амортизированной оценки DSU со сжатием и объединением по размеру или рангу. Инициализация занимает O(n). Для lazy Prim очереди рёбер дают log(m+1). Индексированная очередь допускает O((n+m) log(n+1)). Сумма многих весов ±10^9 не помещается в 32 бита. Например, три ребра по 10^9 дают 3×10^9.

В Java используйте long для больших сумм весов и расстояний.

Метод | Время | Память с ответом
DSU | O(n + q α(n)) | O(n+q)
Краскал / lazy Prim | O(n + m log(m+1)) | O(n+m)
Косарайю / DFS topo / DAG | O(n+m) | O(n+m)
Helper маршрута | O(L) | O(L)

### EN

Include initialization and the returned result.

DSU states contains q rows. This result also needs memory.

α(n) is an extremely slowly growing function in the amortized DSU bound with compression and union by size or rank. Initialization costs O(n). Lazy Prim queues edges, giving log(m+1). An indexed queue permits O((n+m) log(n+1)). Sums of many ±10^9 weights may exceed 32 bits. Three edges of 10^9 already total 3×10^9.

In Java, use long for large weight totals and distances.

Method | Time | Memory including result
DSU | O(n + q α(n)) | O(n+q)
Kruskal / lazy Prim | O(n + m log(m+1)) | O(n+m)
Kosaraju / DFS topo / DAG | O(n+m) | O(n+m)
Route helper | O(L) | O(L)

## 56. Где писать реализацию / Where to write your implementation

### RU

Создайте свой репозиторий через Use this template. Выберите один язык в submission.json.

Например, для Java меняем методы в Java/source/Algorithms.java.

Java — основной язык силлабуса, поэтому локальные фрагменты урока даны на Java. В A3 сохранён выбор одного из четырёх языков. Выберите язык точно как указан в manifest. Не нужно реализовывать всё на всех четырёх языках. ANALYSIS.md пишите на английском.

Адаптеры и запуск уже предоставлены. TODO внутри алгоритмов — ваша работа.

language в manifest | Файл методов
Java | Java/source/Algorithms.java
Python | Python/source/algorithms.py
Cpp | Cpp/source/algorithms.hpp
CSharp | CSharp/source/Algorithms.cs

### EN

Create your repository with Use this template. Choose one language in submission.json.

For Java, implement methods in Java/source/Algorithms.java.

Java is the main syllabus language, so lesson snippets use Java. A3 retains the choice of one of four languages. Spell the language value exactly as listed in the manifest. You do not implement all four languages. Write ANALYSIS.md in English.

Adapters and runners are provided. Algorithm TODOs are your work.

language in manifest | Method source file
Java | Java/source/Algorithms.java
Python | Python/source/algorithms.py
Cpp | Cpp/source/algorithms.hpp
CSharp | CSharp/source/Algorithms.cs

## 57. Собственные тесты: вход и ожидаемый ответ / Your tests: input and expected result

### RU

Начните с маленьких случаев, где ответ можно вычислить руками.

Потом проверьте крайние случаи и большую глубину.

В tests/student сохраните тесты, которые обнаруживают конкретную ошибку.

В tests/student сохраните тесты, которые обнаруживают конкретную ошибку.

Тест | Что проверяет
DSU: [0,1], [0,1] | Повтор не увеличивает size
MST: изолят + петля | Лес всех компонент без петель
SCC: 0↔1, 1→2 | Одностороннее ребро не объединяет SCC
Topo: 0→1, 2↔3 | Цикл в другой компоненте
DAG: отрицательное ребро | null и корректное улучшение пути

### EN

Start with small cases whose answers you can compute by hand.

Then test edge cases and deep graphs.

Store tests in tests/student that catch specific mistakes.

Store tests in tests/student that catch specific mistakes.

Test | What it checks
DSU: [0,1], [0,1] | A repeated union does not grow size
MST: isolate + self-loop | All-component forest without loops
SCC: 0↔1, 1→2 | A one-way edge does not merge SCCs
Topo: 0→1, 2↔3 | A cycle in another component
DAG: a negative edge | null and correct route improvement

## 58. Новый запрос: чистые массивы / A new request starts with fresh arrays

### RU

Два запроса могут выполняться в одном процессе, но описывают разные задачи.

Запрос 1: DSU n=3, [0,1] → [2,2]. Запрос 2: DSU n=3, [1,2] → [2,2].

Создавайте новые parent, size, visited, component, distance, очереди и списки результата для каждого вызова. Проверьте также смешанную последовательность: DSU, SCC, DAG, снова DSU. Пустые задачи допустимы для DSU, MST, SCC и topo. Для DAG n≥1, source должен существовать.

Если второй ответ [1,3], вы случайно сохранили группы первого запроса.

### EN

Two requests may share a process, but they describe separate tasks.

Request 1: DSU n=3, [0,1] → [2,2]. Request 2: DSU n=3, [1,2] → [2,2].

Create fresh parent, size, visited, component, distance, queues and result lists for each call. Also test a mixed sequence: DSU, SCC, DAG, DSU again. Empty tasks are valid for DSU, MST, SCC and topo. DAG requires n≥1 and an existing source.

If the second answer is [1,3], you accidentally retained the first request’s groups.

## 59. Benchmark: честно сравнить Краскала и Прима / Benchmark: compare Kruskal and Prim fairly

### RU

Сначала проверьте правильность. Затем измеряйте одинаковую работу на одинаковых графах.

Один размер: Краскал 12, 9, 10 мс. Прим 8, 6, 7 мс.

Это учебные числа, а не результаты измерения шаблона. Возьмите три размера и минимум три измерения каждого метода на каждом размере. Укажите n, m, генератор/seed, язык и версии, компьютер, границу таймера. Например, оба метода измеряются с построением своих рабочих структур, но без чтения файла. Покажите все измерения и медиану. Один запуск не доказывает асимптотику.

После сортировки: 9,10,12 и 6,7,8. Медианы: 10 мс и 7 мс.

### EN

Check correctness first. Then time the same scope on the same graphs.

One size: Kruskal takes 12, 9, 10 ms. Prim takes 8, 6, 7 ms.

These are teaching numbers, not measured template results. Use three sizes and at least three runs per method per size. Report n, m, generator/seed, language and versions, machine and timer boundary. For example, include construction of each method’s working structures but exclude file reading for both. Show all samples and the median. One run cannot prove asymptotic complexity.

Sort the samples: 9,10,12 and 6,7,8. The medians are 10 ms and 7 ms.

## 60. ANALYSIS.md: объяснить собственную работу / ANALYSIS.md: explain your own work

### RU

Пишем на английском. Для каждого метода объясняем идею и причину правильности.

Пример: “I accept an edge only when its endpoints have different DSU roots.”

Текст должен описывать именно вашу реализацию. Для Краскала объясните, почему не создаёте цикл и почему выбор дешёвого ребра безопасен. Для DAG объясните topo order, null и parent_edge. По силлабусу письменно раскройте использованные AI-инструменты и вклад помощи. Ответственность за понимание, код и проверку остаётся у студента.

Добавьте сложность, свои тесты, реальные вызовы helper и результаты benchmark.

### EN

Write in English. Explain each method’s idea and why it is correct.

Example: “I accept an edge only when its endpoints have different DSU roots.”

Describe your actual implementation. For Kruskal, explain how cycles are avoided and why cheap crossing edges are safe. For DAG, explain topological order, null and parent_edge. Following the syllabus, disclose AI tools and their contributions in writing. Students remain responsible for understanding, code and verification.

Include complexity, your tests, actual helper calls and benchmark results.

## 61. Public checks: понять причину красного статуса / Public checks: understand a red result

### RU

Сначала смотрим, собрался ли проект. Потом читаем упавшую проверку.

Setup прошёл, но DSU вернул TODO: значит, надо реализовать метод.

Ошибка окружения требует исправления запуска и повторной проверки.

Ошибка окружения требует исправления запуска и повторной проверки.

Сообщение | Следующее действие
Compilation failed | Исправить синтаксис / файлы / язык
Expected [2,2], got ... | Вручную повторить операции DSU
Invalid JSON / extra output | Убрать служебный текст из stdout
Runtime unavailable | Разобрать сбой среды и перезапустить

### EN

First check whether the project built. Then read the failing check.

Setup passed, but DSU returned TODO: implement the method.

An environment failure requires fixing the run and checking again.

An environment failure requires fixing the run and checking again.

Message | Next action
Compilation failed | Fix syntax / source files / language
Expected [2,2], got ... | Trace the DSU operations by hand
Invalid JSON / extra output | Remove debugging text from stdout
Runtime unavailable | Investigate the environment and rerun

## 62. Сдача: две ссылки на один SHA / Submission: two links for the same SHA

### RU

После успешной проверки зафиксируйте точный коммит.

В Moodle Online text: 1) commit URL; 2) успешный Public checks run URL того же коммита.

SHA — идентификатор конкретного снимка кода. Если после успешного запуска изменить код, старый run уже не подтверждает новый коммит. ZIP, весь проект и student ID для этой процедуры не нужны. Соблюдайте действующие даты и индивидуальные продления в Moodle.

Затем Submit assignment и проверка статуса сдачи. Приватный репозиторий доступен преподавателю.

### EN

After a successful check, identify the exact commit.

In Moodle Online text: 1) commit URL; 2) successful Public checks run URL for that commit.

A SHA identifies one exact snapshot of code. Changing code after a successful run makes that old run insufficient for the new commit. This workflow needs no ZIP, project upload or student ID. Follow the dates and individual extensions shown in Moodle.

Then click Submit assignment and confirm its status. Give the instructor access to a private repository.

## 63. Оценивание и итоговая самопроверка / Assessment and final self-check

### RU

Практика: 50 автоматических баллов. Квиз: 50 автоматических баллов.

Квиз: 30 вопросов за 30 минут. 15 правильных → 25 из 50.

Квиз имеет одну попытку. Каждый правильный ответ даёт 1 сырой балл, неправильный или пустой — 0. Итог raw/30×50. Практика использует опубликованные автоматические группы из RUBRIC.md. Ручных баллов и устного восстановления нет. Обязательные материалы и требуемые алгоритмы сохраняются.

Проверьте шесть методов, tests/student, английский ANALYSIS.md и две ссылки одного SHA.

### EN

Practical: 50 automatic points. Quiz: 50 automatic points.

Quiz: 30 questions in 30 minutes. 15 correct → 25 out of 50.

The quiz has one attempt. A correct answer gives 1 raw point, an incorrect or blank answer gives 0. Scale as raw/30×50. The practical uses the published automatic groups in RUBRIC.md. There are no manual points or oral score recovery. Required deliverables and methods still apply.

Check six methods, tests/student, English ANALYSIS.md and two links for one SHA.

## 64. Источники и дополнительное видео / Sources and supplementary video

### RU

Точные поля и ограничения смотрите в CONTRACTS.md. Требования сдачи — в README и Moodle.

Дополнительное видео на английском встроено в HTML и PowerPoint.

Источники: актуальные материалы A3, уточнения к лекциям 6–7, CLRS 4e §§19.3–19.4,20.3–20.5,21.1–21.2,22.2; Sedgewick & Wayne 4e §§1.5,4.2–4.4; Skiena 3e §§7.10,8.1,8.3. Английское видео является дополнительным обзором основных методов с синтетической озвучкой. Оно не заменяет новые подробные шаги. В лекции 6, слайд 9, для Краскала правильное условие принятия: find(x) != find(y).

Урок можно проходить без видео: каждый шаг объяснён письменно на обоих языках.

### EN

Use CONTRACTS.md for exact fields and limits. Use README and Moodle for submission.

A supplementary English video is embedded in HTML and PowerPoint.

Sources: current A3 materials, clarifications for lectures 6–7, CLRS 4e §§19.3–19.4,20.3–20.5,21.1–21.2,22.2; Sedgewick & Wayne 4e §§1.5,4.2–4.4; Skiena 3e §§7.10,8.1,8.3. The synthetic-voice English video is a supplementary overview of the core methods. It does not replace the new detailed steps. For lecture 6 slide 9, Kruskal accepts an edge when find(x) != find(y).

The lesson works without video: every step has a written explanation in both languages.

