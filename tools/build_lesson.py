"""Rebuild both self-contained lesson HTML entry points from the editable sources."""
from pathlib import Path
import json, hashlib

docs = Path(__file__).resolve().parents[1] / 'docs'
data = json.loads((docs / 'lesson_content.json').read_text(encoding='utf-8'))
style = '\n'.join((docs / name).read_text(encoding='utf-8') for name in ['lesson.css','practice.css','fieldwork.css'])
fieldwork=json.loads((docs/'fieldwork_content.json').read_text(encoding='utf-8'))
fieldwork_encoded=json.dumps(fieldwork,ensure_ascii=False).replace('</','<\\/')
problems=json.loads((docs/'practice_content.json').read_text(encoding='utf-8'))
practice_encoded=json.dumps(problems,ensure_ascii=False).replace('</','<\\/')
worker_version=hashlib.sha256((docs/'practice-worker.js').read_bytes()+(docs/'code/practice/judge.py').read_bytes()).hexdigest()[:10]
asset_version=hashlib.sha256((docs/'practice_content.json').read_bytes()+b''.join(p.read_bytes() for p in sorted((docs/'code/practice').glob('Practice*.java')))).hexdigest()[:10]
runtime = "const PRACTICE_WORKER_URL='practice-worker.js?v="+worker_version+"',PRACTICE_ASSET_VERSION='"+asset_version+"';\n"+(docs/'practice.js').read_text(encoding='utf-8')+'\n'+(docs/'fieldwork.js').read_text(encoding='utf-8')+'\n'+(docs / 'lesson.js').read_text(encoding='utf-8')
encoded = json.dumps(data, ensure_ascii=False).replace('</', '<\\/')
html = '''<!doctype html><html lang="ru"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><meta name="description" content="Explore graph algorithms through interactive diagrams, questions, practical applications and course reading. Russian and English."><title>Graph Algorithms | DAA</title><style>''' + style + '''</style></head><body><a class="skip" href="#main">К уроку / Skip to lesson</a><header><div class="brand">Graph Algorithms<small>Design and Analysis of Algorithms · Assignment 3</small></div><div class="languages" role="group" aria-label="Language / Язык"><button id="ru" aria-pressed="true">Русский</button><button id="en" aria-pressed="false">English</button></div></header><div class="layout"><aside><label id="searchLabel" for="search"></label><input id="search" type="search" autocomplete="off"><nav id="contents" aria-label="Lesson contents"></nav><p id="empty" class="small" role="status"></p><p id="navHint" class="navhint"></p></aside><main id="main" tabindex="-1"><article><p id="eyebrow" class="eyebrow"></p><div class="progressline" aria-hidden="true"><span id="progress"></span></div><h1 id="title"></h1><p id="story" class="story"></p><div id="routebar" class="routebar"></div><section id="activity"></section><section id="steps" class="steps"></section><section id="quiz"></section><section id="transfer" class="transfer"></section><section id="practice"></section><section id="deeper"></section><section id="reading"></section><section id="videos"></section><nav class="nextnav" aria-label="Lesson navigation"><button id="prev"></button><button id="next"></button></nav></article><footer><div class="quicklinks"><a href="https://github.com/RZhaniya/daa-assignment-3-graphs" id="templateLink" target="_blank" rel="noopener"></a><a href="https://github.com/RZhaniya/daa-assignment-3-graphs/blob/main/docs/Assignment_3_EN.pdf" target="_blank" rel="noopener">Assignment PDF · English</a><a href="https://github.com/RZhaniya/daa-assignment-3-graphs/blob/main/docs/Assignment_3_RU.pdf" target="_blank" rel="noopener">Задание PDF · русский</a></div><p id="footerNote"></p></footer></main></div><script id="lesson-data" type="application/json">''' + encoded + '''</script><script>''' + runtime + '''</script></body></html>'''
for name in ['index.html', 'A3_Bilingual_Slideshow.html']:
    page=html.replace('<section id="activity">','<section id="application"></section><section id="activity">')
    page=page.replace('<section id="deeper">','<section id="extension"></section><section id="deeper">')
    page=page.replace('<script id="lesson-data"', '<script id="fieldwork-data" type="application/json">'+fieldwork_encoded+'</script><script id="lesson-data"')
    page=page.replace('<script id="lesson-data"', '<script id="practice-data" type="application/json">'+practice_encoded+'</script><script id="lesson-data"')
    (docs / name).write_text(page, encoding='utf-8')
print(f'Built {len(data)} bilingual chapters and {len(problems)} practice problems ({len(page.encode()):,} bytes each).')
