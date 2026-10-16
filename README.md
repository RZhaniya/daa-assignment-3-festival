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


## Student lesson: English / Russian

[Offline bilingual slideshow](docs/A3_Bilingual_Slideshow.html) · [PowerPoint in English](docs/A3_Explanation_EN.pptx) · [PowerPoint in Russian](docs/A3_Explanation_RU.pptx) · [Detailed explanations in both languages](docs/A3_Explanations_EN_RU.md)

The lesson explains all six methods through 64 matching steps in each language. It starts with vertices and edges, then works through small examples one decision at a time. Tables show how groups, DFS states and route costs change. Plain explanations, a glossary and 10 self-check questions with revealable answers support independent study. Java fragments follow the idea and worked steps. Testing, benchmarking and submission are included. Download the HTML file and open it in your browser; the lesson text and embedded MP4 work offline. External YouTube videos require internet access. Switch languages without losing your place, choose a topic, search, or use the arrow keys. Detailed explanations appear below each slide.

Both PowerPoint files and the slideshow include a supplementary [English narrated video, about 17 minutes](docs/Assignment_3_Walkthrough_EN.mp4). Narration uses an English synthetic voice; the Russian version provides translated slides and detailed written explanations. If your PowerPoint viewer does not play embedded media, use the HTML slideshow or separate MP4. [English video transcript](docs/Narration_EN.md).

[Full English video lesson (28 minutes)](docs/video/Assignment_3_Explained_EN.mp4) · [English transcript](docs/video/Transcript_EN.md) · [Chapters](docs/video/Chapters_EN.md)

The video works through all six required algorithms with changing graph states, original edge IDs, short Java fragments, correctness reasoning and complexity. It also covers route reconstruction, testing, benchmarking and submission. English synthetic neural narration and on-screen captions are included. For chapter navigation, open [the video player](docs/video/Watch_Assignment_3_EN.html) from a downloaded repository, keeping the files in docs/video together.

[English videos by A3 topic](docs/A3_Video_Guide_EN_RU.md): eight topic videos cover all six required algorithms, plus an optional DFS introduction. The HTML lesson links to the relevant videos in each chapter.
