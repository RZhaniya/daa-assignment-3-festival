# Assignment 3 — English video guide / видео на английском

[20-minute course lesson / Видеоурок на 20 минут](video20/index.html) · English narration, EN/RU captions.

These English videos explain the six required A3 algorithms. Watch the short topic sequence rather than unrelated sections of a general algorithms course. The optional DFS introduction helps with Kosaraju and topological ordering.

Эти английские видео объясняют шесть требуемых алгоритмов A3. Смотрите их по темам ниже. Дополнительное введение в DFS помогает перед Косарайю и топологической сортировкой.

|Order / Порядок|Topic / Тема|Video / Видео|What to focus on / На что обратить внимание|
|---|---|---|---|
|1|DSU|[Union Find Introduction](https://www.youtube.com/watch?v=ibjEGG7ylHk) — William Fiset|Start here: why groups and group representatives are useful. / Начните здесь: зачем нужны группы и представитель группы.|
|2|DSU|[Union Find - Union and Find Operations](https://www.youtube.com/watch?v=0jNmHPfA_yE) — William Fiset|How root finding and merging maintain the groups. / Как поиск корня и объединение поддерживают группы.|
|3|DSU|[Union Find Path Compression](https://www.youtube.com/watch?v=VHRhJWacxis) — William Fiset|How path compression shortens later searches. / Как сжатие пути сокращает будущий поиск.|
|4|MST: Kruskal / Prim|[Union Find Kruskal's Algorithm](https://www.youtube.com/watch?v=JZBQLXgSGfs) — William Fiset|How to select cheap edges using DSU. / Как выбирать дешёвые рёбра и использовать DSU.|
|5|MST: Kruskal / Prim|[Prim's Minimum Spanning Tree Algorithm](https://www.youtube.com/watch?v=jsmMtJpPnhU) — William Fiset|How a tree grows through its cheapest outgoing edges. / Как растить дерево через самые дешёвые выходящие рёбра.|
|6|Kosaraju SCC|[Strongly Connected Components: Kosaraju's Algorithm](https://www.youtube.com/watch?v=RpgcYiky7uw) — Tushar Roy|Kosaraju specifically: finish order, reversed graph and the second DFS. / Именно Косарайю: порядок завершений, обратный граф и второй DFS.|
|7|DFS topological ordering|[Topological Sort Algorithm](https://www.youtube.com/watch?v=eL-KzMXSXXI) — William Fiset|Dependency ordering and construction of an order using DFS. / Порядок зависимостей и построение ответа через DFS.|
|8|DAG shortest paths|[Shortest/Longest Path on a Directed Acyclic Graph](https://www.youtube.com/watch?v=TXkDpqjDMHA) — William Fiset|Shortest routes through topological ordering and cost updates. / Кратчайшие пути через топологический порядок и улучшение стоимости.|
|9|Optional DFS / Дополнительно|[Depth First Search Algorithm](https://www.youtube.com/watch?v=7fujbpJ0LB4) — William Fiset|Optional preparation for Kosaraju and topo: how DFS explores a graph. / Дополнительно перед Косарайю и topo: как DFS обходит граф.|

## Apply the idea to A3 / Как применять к A3

- DSU: report `[components, largest]` after every operation, including repeated and self unions. / Возвращайте статистику после каждой операции, включая повторы и соединения с собой.
- MST: A3 needs a forest for all components, including isolated vertices. Preserve input edge IDs. / Для A3 нужен лес всех компонент с изолятами и исходными ID рёбер.
- SCC: use Kosaraju. Return a label per vertex and the exact deduplicated condensation. / Используйте Косарайю, верните метки и конденсацию без повторов.
- Topo: implement DFS with three states, detect cycles in every component and reverse finish order. / Нужен DFS с тремя состояниями, проверкой циклов во всех компонентах и разворотом порядка завершений.
- DAG: reject a cycle anywhere. Unreachable distance is `null`; `parent_edge` stores an input edge ID. Test your reconstruction helper. / Отклоняйте цикл в любой компоненте. Недостижимое расстояние — `null`, parent_edge хранит ID ребра. Проверьте helper восстановления.

The assignment contracts remain authoritative. External videos explain the algorithms; A3 JSON, test requirements, benchmark, analysis and submission are explained in the lesson and repository documents. The “longest path” portion of the DAG video is optional. Tarjan, Kahn and Dijkstra are not substitutes for the required A3 methods.

Точные требования определяют документы A3. Внешние видео объясняют алгоритмы. JSON, собственные тесты, benchmark, анализ и сдача разобраны в уроке и документах репозитория. Часть видео о longest path дополнительная. Tarjan, Kahn и Dijkstra не заменяют требуемые методы A3.

Use the creator’s original YouTube pages. Internet access is required for these external videos. The full downloaded docs folder includes the lesson and the 28-minute course MP4 for offline use. / Открывайте оригинальные страницы авторов на YouTube. Внешним видео нужен интернет. Полная скачанная папка docs содержит урок и 28-минутное видео курса для работы без интернета.

Source links: [William Fiset’s algorithm index](https://github.com/williamfiset/Algorithms), [Tushar Roy’s Kosaraju implementation](https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/StronglyConnectedComponent.java). Video titles and creators were checked with YouTube metadata on 19 October 2026. The selection was checked against descriptions and creator materials; it is not a claim that every video was watched in full.
