# Assignment 3 — Graph Algorithms for a Campus Festival

Implement DSU, separate Kruskal and Prim, Kosaraju SCC, DFS topological ordering and DAG shortest paths in **one** language: Java, Python, C++ or C#. Java 17+ is the primary language in the syllabus; the existing four-language choice remains available for A3.

[English specification](docs/Assignment_3_EN.pdf) · [Задание на русском](docs/Assignment_3_RU.pdf)

## Work plan

1. Use **Use this template → Create a new repository**. Give the instructor access if it is private.
2. Set `language` in [submission.json](submission.json) to `Java`, `Python`, `Cpp` or `CSharp`, then implement the six methods in the selected `source` folder.
3. Add your own inputs, expected and actual results under `tests/student/`. Complete [ANALYSIS.md](ANALYSIS.md) in English, including called route-helper examples and a reproducible Kruskal–Prim benchmark.
4. Push the completed work and obtain successful **Actions → Public checks** for that commit.
5. In Moodle **Online text**, submit the **commit URL** and the **successful Public checks run URL for that same SHA**, then click **Submit assignment**.

Follow the dates and any approved individual extension shown in Moodle. The linked commit is the assessed version; keep a private repository accessible to the instructor.

[Exact contracts](CONTRACTS.md) · [Testing and runtimes](TESTING.md) · [Practical rubric](RUBRIC.md) · [Lecture clarifications (English / Russian)](docs/Lecture_Clarifications_EN_RU.pdf)

A3 combines **50 practical points + 50 quiz points** and retains its 20% contribution to the second attestation. The practical score is **50 automatic points**; the quiz is also automatically graded out of 50. There are no manual points or oral score recovery. See the rubric for group weights and assessment-defect corrections.

The supplied algorithm bodies contain intentional TODOs. A successful setup followed by failed algorithm examples is expected until you implement them. Public checks provide feedback; the instructor also uses independent checks and reviews the required methods and analysis.

Follow the syllabus AI policy: written acknowledgement of tools and contributions, permitted help with brainstorming/debugging/code suggestions, and responsibility for the final work. Record your verification in ANALYSIS.md.

## Quiz preparation

[Practice A (English / Russian)](docs/A3_Practice_A_EN_RU.pdf) · [Practice B (English / Russian)](docs/A3_Practice_B_EN_RU.pdf). Each contains 30 questions with answers and explanations on different examples. [Common pitfalls](docs/Common_Pitfalls.pdf).


## Graph algorithms: interactive lesson

[Open the lesson](https://rzhaniya.github.io/daa-assignment-3-festival/) · [Offline HTML](docs/A3_Bilingual_Slideshow.html) · [Related BFS example](docs/code/ReachabilityDemo.java)

Start with a familiar situation, try a choice on the graph, check your prediction and then open the reasoning and code fragments. Interactive activities cover connectivity, path compression, Kruskal, Prim, DFS, SCC condensation, task ordering and DAG relaxation. The English and Russian lesson connects each topic to practical applications and assignment requirements.

Implement the six assignment methods and route helper yourself. The downloadable BFS example is a different learning task; the lesson does not provide complete assignment implementations.

[20-minute English video](docs/video20/index.html) · [YouTube guide](docs/A3_Video_Guide_EN_RU.md) · [Transcript](docs/video20/Transcript_EN.md)

The topic cards link to verified original YouTube videos. Open the supplied syllabus textbooks directly from the lesson: CLRS 4e and Sedgewick–Wayne 4e (main), and Skiena 3e (additional), with links to relevant PDF pages. Offline reading and the course video require the full docs folder; external YouTube videos require internet.

To edit the lesson, update docs/lesson_content.json, docs/lesson.js or docs/lesson.css, then run `python tools/build_lesson.py`.
