'use strict';
const practiceProblems = JSON.parse(document.getElementById('practice-data').textContent);
const practiceDrafts = new Map(), predictionDrafts = new Map(), caseDrafts = new Map();
let selectedProblem = 'outgoing', practiceLanguage = 'Python', activePracticeRun = null;
const practiceProblemFor = {model:'outgoing',dsu:'sizes',dfs:'positions',dag:'relax',prim:'boundary',kruskal:'boundary',topo:'order',scc:'pairs',route:'walk'};
function practiceSame(id, actual, expected) {
  if (id !== 'pairs') return JSON.stringify(actual) === JSON.stringify(expected);
  if (!Array.isArray(actual) || actual.some(p => !Array.isArray(p) || p.length!==2 || p.some(x=>!Number.isSafeInteger(x)))) return false;
  const keys = actual.map(p=>JSON.stringify(p));
  return new Set(keys).size===keys.length && keys.sort().join('|')===expected.map(p=>JSON.stringify(p)).sort().join('|');
}
function problemInput(p, test) { return Object.fromEntries(p.args.map((name,i)=>[name,test.args[i]])); }
function problemLink(id) { return '#'+new URLSearchParams({topic:'challenges',lang:language,problem:id}); }
function stopPracticeRun(message) {
  if (!activePracticeRun) return;
  const run=activePracticeRun;activePracticeRun=null;
  clearTimeout(run.timer);run.worker.terminate();run.runButton.disabled=false;run.stopButton.disabled=true;
  if (message) run.status.textContent=message;
}
function attachPrediction(c) {
  if (!c.practice) return;
  const root=document.createElement('div');root.className='prediction';
  const state=predictionDrafts.get(c.id)||{answer:''};predictionDrafts.set(c.id,state);
  root.innerHTML='<label for="predictionAnswer">'+tr('Предскажите вывод: каждая println — в отдельной строке','Predict the output: one println per line')+'</label><textarea id="predictionAnswer" rows="3" spellcheck="false"></textarea><div class="controls"><button data-check-prediction>'+tr('Проверить предсказание','Check prediction')+'</button><button data-clear-prediction>'+tr('Очистить','Clear')+'</button></div><p class="live small" role="status" aria-live="polite"></p>';
  const field=root.querySelector('textarea'), status=root.querySelector('[role=status]');field.value=state.answer;field.oninput=()=>state.answer=field.value;
  root.querySelector('[data-check-prediction]').onclick=()=>{
    try {
      const parse=text=>text.split(/\r?\n/).filter(x=>x.trim()).map(x=>JSON.parse(x));
      const actual=parse(field.value), expected=parse(c.practice.output);
      if (!actual.length) throw new Error('empty');
      const ok=JSON.stringify(actual)===JSON.stringify(expected);
      status.textContent=ok?tr('Верно. Теперь сопоставьте каждую строку с действием кода.','Correct. Now connect each line with its code operation.'):tr('Результат отличается. Проследите изменения по строкам; при необходимости откройте разбор.','The result differs. Trace each change; open the walkthrough if needed.');
    } catch {
      status.textContent=tr('Введите числа, true/false или массивы, например [2, 0], по одной строке вывода. Пробелы внутри массивов допустимы.','Enter numbers, true/false or arrays such as [2, 0], one output line at a time. Spaces inside arrays are accepted.');
    }
  };
  root.querySelector('[data-clear-prediction]').onclick=()=>{field.value='';state.answer='';status.textContent='';};
  document.getElementById('practice').querySelector('.practice > details').before(root);
}
function paintExtensions(c) {
  const box=document.getElementById('extension');box.replaceChildren();
  if(c.id==='challenges') {document.getElementById('steps').replaceChildren();document.getElementById('transfer').replaceChildren();paintChallengeHub(box);return; }
  const id=practiceProblemFor[c.id];
  if(id) box.innerHTML='<p class="practiceLink"><a href="'+problemLink(id)+'">'+tr('Следующий шаг: самостоятельная задача с тестами','Next: an independent problem with test cases')+' → '+esc(practiceProblems.find(p=>p.id===id)[language].title)+'</a></p>';
  if(['dag','route'].includes(c.id)) paintRelaxationCases(box,c.id);
  if(['prim','kruskal','mst'].includes(c.id)) paintBoundaryCases(box,c.id);
}
function paintChallengeHub(box) {
  const requested=new URLSearchParams(location.hash.slice(1)).get('problem');
  if(practiceProblems.some(p=>p.id===requested)) selectedProblem=requested;
  const p=practiceProblems.find(p=>p.id===selectedProblem),t=p[language];
  box.innerHTML='<section class="challenge"><label for="problemChoice">'+tr('Выберите задачу','Choose a problem')+'</label><select id="problemChoice">'+practiceProblems.map(q=>'<option value="'+q.id+'"'+(q.id===p.id?' selected':'')+'>'+esc(q[language].title)+'</option>').join('')+'</select><p class="small">'+tr('01–04: разминка после примера. 05–08: самостоятельная проверка и выбор. Все тесты учебные; результат не является оценкой за задание.','01–04: warm-ups after a worked example. 05–08: independent validation and selection. These public practice cases do not award assignment marks.')+'</p><h2>'+esc(t.title)+'</h2><p>'+esc(t.task)+'</p><p class="small"><b>'+tr('Ограничения: ','Constraints: ')+'</b>'+esc(t.limits)+'</p><p class="small"><b>'+tr('Применение: ','Application: ')+'</b>'+esc(t.use)+'</p><div class="problemExamples"></div><details class="deep"><summary>'+tr('Подсказка','Hint')+'</summary><div class="deepbody"><p>'+esc(t.hint)+'</p></div></details><div class="answerTry"></div><div class="languageChoice controls" role="group" aria-label="'+tr('Язык решения','Solution language')+'"><button data-language="Python" aria-pressed="'+(practiceLanguage==='Python')+'">Python · '+tr('на странице','in the browser')+'</button><button data-language="Java" aria-pressed="'+(practiceLanguage==='Java')+'">Java 17 · '+tr('локально','locally')+'</button></div><div class="codeWorkspace"></div><details class="deep solutionReveal"><summary>'+tr('Показать один вариант решения и разбор','Show one solution and walkthrough')+'</summary><div class="deepbody"><pre><code></code></pre><ol>'+t.explain.map(x=>'<li>'+esc(x)+'</li>').join('')+'</ol><p><b>'+tr('Ресурсы: ','Resources: ')+'</b>'+esc(t.complexity)+'</p></div></details><details class="deep publicCases"><summary>'+tr('Все учебные тесты','All practice cases')+' · '+p.tests.length+'</summary><div class="deepbody"></div></details></section>';
  box.querySelector('#problemChoice').onchange=e=>{stopPracticeRun();selectedProblem=e.target.value;location.hash=new URLSearchParams({topic:'challenges',lang:language,problem:selectedProblem});};
  const topic=chapters.find(c=>c.id===p.topic);
  box.querySelector('h2').insertAdjacentHTML('afterend','<p class="small"><a href="#'+new URLSearchParams({topic:p.topic,lang:language})+'">'+tr('Повторить тему: ','Review the topic: ')+esc(topic[language].title)+'</a></p>');
  const examples=box.querySelector('.problemExamples');
  p.tests.slice(0,2).forEach((test,i)=>{const div=document.createElement('div');div.className='example';div.innerHTML='<h3>'+tr('Пример ','Example ')+(i+1)+'</h3><pre>'+esc(JSON.stringify(problemInput(p,test)))+'</pre><p>'+tr('Результат: ','Result: ')+'<code>'+esc(JSON.stringify(test.expected))+'</code></p><p>'+esc(p.notes[i][language==='RU'?0:1])+'</p>';examples.append(div);});
  paintAnswerTry(box.querySelector('.answerTry'),p);
  box.querySelectorAll('[data-language]').forEach(b=>b.onclick=()=>{stopPracticeRun();practiceLanguage=b.dataset.language;paintChallengeHub(box);});
  const solution=box.querySelector('.solutionReveal code');solution.textContent=practiceLanguage==='Python'?p.python:p.java;
  box.querySelector('.publicCases .deepbody').innerHTML=p.tests.map((test,i)=>'<p><b>'+tr('Тест ','Case ')+(i+1)+'</b></p><pre>'+esc(JSON.stringify(problemInput(p,test)))+'\n'+tr('Ожидается: ','Expected: ')+esc(JSON.stringify(test.expected))+'</pre>').join('');
  paintCodeWorkspace(box.querySelector('.codeWorkspace'),p);
}
function paintAnswerTry(box,p) {
  const state=caseDrafts.get('answer-'+p.id)||{index:0,answer:''};caseDrafts.set('answer-'+p.id,state);
  const test=p.tests[state.index];
  box.innerHTML='<h3>'+tr('Сначала проверьте ответ без кода','First, predict a result without code')+'</h3><label for="answerCase">'+tr('Входные данные','Input case')+'</label><select id="answerCase">'+p.tests.map((_,i)=>'<option value="'+i+'"'+(state.index===i?' selected':'')+'>'+tr('Тест ','Case ')+(i+1)+'</option>').join('')+'</select><pre>'+esc(JSON.stringify(problemInput(p,test)))+'</pre><label for="problemAnswer">'+tr('Ваш результат в JSON','Your result as JSON')+'</label><textarea id="problemAnswer" rows="2" spellcheck="false"></textarea><div class="controls"><button data-check-answer>'+tr('Проверить ответ','Check answer')+'</button></div><p class="live" role="status" aria-live="polite"></p>';
  const input=box.querySelector('textarea'),status=box.querySelector('[role=status]');input.value=state.answer;input.oninput=()=>state.answer=input.value;
  box.querySelector('select').onchange=e=>{state.index=Number(e.target.value);state.answer='';paintAnswerTry(box,p);};
  box.querySelector('[data-check-answer]').onclick=()=>{try{const actual=JSON.parse(input.value);status.textContent=practiceSame(p.id,actual,test.expected)?tr('Верно для этого входа. Теперь проверьте свою функцию на остальных случаях.','Correct for this input. Now check your function on the other cases.'):tr('Ответ отличается. Проверьте контракт, направление рёбер и граничный случай. Подсказка и разбор доступны отдельно.','The answer differs. Check the contract, edge directions and boundary case. The hint and solution are available separately.');}catch{status.textContent=tr('Нужен JSON: число, true/false, null или массив. Например: [0,2] либо [[5,9],[9,3]].','Enter JSON: a number, true/false, null or an array. For example: [0,2] or [[5,9],[9,3]].');}};
}
function paintCodeWorkspace(box,p) {
  const isPython=practiceLanguage==='Python',key=p.id+'-'+practiceLanguage;
  const starter=isPython?p.starter_python:p.starter_java;
  box.innerHTML='<h3>'+tr('Напишите свою функцию','Write your function')+'</h3><p class="small">'+(isPython?tr('Python выполняется в вашем браузере через Pyodide. При первом запуске нужен интернет для загрузки интерпретатора. После загрузки на все тесты даётся 5 секунд; выполнение можно остановить. Возвращайте результат из solve, а не только печатайте его.','Python runs in your browser using Pyodide. The first run needs internet to load the interpreter. After loading, the whole test batch has 5 seconds; you can stop execution. Return the result from solve rather than only printing it.'):tr('Java запускается локально с JDK 17. На странице редактируется только метод; кнопка скачивания добавит imports, класс и тесты. Установку JSON-библиотек делать не нужно.','Java runs locally with JDK 17. Edit the method here; downloading adds imports, the class and tests. No JSON library is needed.'))+'</p><label for="practiceEditor">'+(isPython?'Python · solve(...)':'Java 17 · static solve(...)')+'</label><textarea id="practiceEditor" class="codeEditor" rows="13" spellcheck="false" autocapitalize="off" autocomplete="off"></textarea><div class="controls"><button data-run class="primary">'+(isPython?tr('Запустить все тесты','Run all cases'):tr('Скачать мой Java-файл с тестами','Download my Java file with tests'))+'</button>'+(isPython?'<button data-stop disabled>'+tr('Остановить','Stop')+'</button>':'')+'<button data-reset-code>'+tr('Вернуть заготовку','Reset starter')+'</button><button data-copy-editor>'+tr('Копировать мой код','Copy my code')+'</button></div><p class="live runStatus" role="status" aria-live="polite"></p><div class="runResults"></div>'+(isPython?'':'<pre>javac --release 17 '+p.java_class+'.java\njava '+p.java_class+'</pre><p class="small"><a href="'+p.java_file+'?v='+PRACTICE_ASSET_VERSION+'" download>'+tr('Скачать разобранный Java-пример с теми же тестами','Download the worked Java example with the same tests')+'</a></p>');
  const editor=box.querySelector('textarea'),status=box.querySelector('.runStatus');editor.value=practiceDrafts.get(key)??starter;editor.oninput=()=>practiceDrafts.set(key,editor.value);
  box.querySelector('[data-reset-code]').onclick=()=>{stopPracticeRun();editor.value=starter;practiceDrafts.set(key,starter);status.textContent=tr('Заготовка восстановлена.','Starter restored.');box.querySelector('.runResults').replaceChildren();};
  box.querySelector('[data-copy-editor]').onclick=async()=>{try{await navigator.clipboard.writeText(editor.value);status.textContent=tr('Ваш код скопирован.','Your code was copied.');}catch{editor.focus();editor.select();status.textContent=tr('Код выделен. Нажмите Ctrl+C / ⌘C.','Code selected. Press Ctrl+C / ⌘C.');}};
  box.querySelector('[data-run]').onclick=async()=>{
    if(isPython) { startPythonPractice(box,p,editor.value);return; }
    try {
      const response=await fetch(p.java_file+'?v='+PRACTICE_ASSET_VERSION);if(!response.ok) throw new Error('HTTP '+response.status);
      const file=await response.text();const begin=file.indexOf('static '),end=file.indexOf('\nstatic String json(');
      if(begin<0||end<begin)throw new Error('Invalid example file');
      downloadPractice(p.java_class+'.java',file.slice(0,begin)+editor.value.trim()+'\n'+file.slice(end));
      status.textContent=tr('Скачивание файла запущено. Выполните две команды ниже; программа проверит все учебные случаи.','Download started. Run the two commands below to check all practice cases.');
    } catch(error){status.textContent=tr('Не удалось загрузить оболочку Java: ','Could not load the Java harness: ')+String(error);}
  };
}
function downloadPractice(name,text){const url=URL.createObjectURL(new Blob([text],{type:'text/plain;charset=utf-8'}));const a=document.createElement('a');a.href=url;a.download=name;a.click();setTimeout(()=>URL.revokeObjectURL(url),1000);}
function startPythonPractice(box,p,source) {
  stopPracticeRun();const status=box.querySelector('.runStatus'),results=box.querySelector('.runResults'),runButton=box.querySelector('[data-run]'),stopButton=box.querySelector('[data-stop]');
  results.replaceChildren();runButton.disabled=true;stopButton.disabled=false;
  let worker;try{worker=new Worker(PRACTICE_WORKER_URL);}catch(error){status.textContent=tr('Интерпретатор не запущен. Откройте сайт по HTTP/HTTPS. ','Interpreter not started. Open the site over HTTP/HTTPS. ')+String(error);runButton.disabled=false;stopButton.disabled=true;return;}
  const run={worker,status,runButton,stopButton,timer:null};activePracticeRun=run;
  status.textContent=tr('Загружается Python. Первый запуск может занять некоторое время…','Loading Python. The first run may take a while…');
  run.timer=setTimeout(()=>stopPracticeRun(tr('Загрузка заняла больше 90 секунд. Проверьте подключение и попробуйте снова; это не результат проверки кода.','Loading exceeded 90 seconds. Check your connection and retry; this is not a code verdict.')),90000);
  stopButton.onclick=()=>stopPracticeRun(tr('Выполнение остановлено. Можно изменить код и запустить снова.','Execution stopped. Edit the code and run again.'));
  worker.onerror=()=>stopPracticeRun(tr('Не удалось запустить Python. Проверьте интернет и повторите попытку; решение ещё не проверено.','Python could not start. Check your connection and retry; the solution has not been checked.'));
  worker.onmessage=e=>{
    if(activePracticeRun!==run)return;
    if(e.data.type==='ready') {clearTimeout(run.timer);status.textContent=tr('Выполняются учебные тесты…','Running practice cases…');run.timer=setTimeout(()=>stopPracticeRun(tr('Превышены 5 секунд выполнения. Проверьте завершение циклов и попробуйте снова.','The 5-second execution limit was exceeded. Check loop termination and retry.')),5000);worker.postMessage({type:'run',id:p.id,source,cases:p.tests});}
    else if(e.data.type==='error'){stopPracticeRun(tr('Ошибка интерпретатора: ','Interpreter error: ')+e.data.message);}
    else if(e.data.type==='result'){const report=e.data.result;stopPracticeRun();if(report.error){status.textContent=tr('Код не удалось выполнить.','The code could not be executed.');const pre=document.createElement('pre');pre.textContent=report.error;results.append(pre);}else{const passed=report.cases.filter(t=>t.passed).length;status.textContent=tr('Пройдено учебных тестов: ','Practice cases passed: ')+passed+' / '+report.cases.length+'. '+tr('Это проверка показанного набора, а не доказательство корректности на всех входах.','This checks the displayed set, not correctness for every possible input.');report.cases.forEach(test=>{const d=document.createElement('details');d.className='caseResult';d.innerHTML='<summary>'+tr('Тест ','Case ')+(test.index+1)+' · '+(test.passed?tr('пройден','passed'):tr('не пройден','failed'))+'</summary><pre></pre>';d.querySelector('pre').textContent=JSON.stringify(problemInput(p,test))+'\n'+tr('Ожидалось: ','Expected: ')+JSON.stringify(test.expected)+'\n'+(test.error||tr('Получено: ','Actual: ')+JSON.stringify(test.actual));results.append(d);});}if(report.stdout){const pre=document.createElement('pre');pre.textContent='stdout / stderr:\n'+report.stdout;results.append(pre);}}
  };
  worker.postMessage({type:'init'});
}

const boundaryScenarios=[
  {name:['Петля и параллельные рёбра','Loop and parallel edges'],n:4,edges:[[0,0,-100],[0,1,5],[0,1,-2],[1,2,1],[2,3,0]],inside:[true,false,false,false]},
  {name:['Два равных минимума','Two equal minima'],n:3,edges:[[0,1,2],[0,2,2],[1,2,-8],[0,0,-10]],inside:[true,false,false]},
  {name:['Устаревшее предложение','A stale offer'],n:5,edges:[[0,1,4],[0,2,2],[1,2,1],[1,3,5],[2,3,8],[2,4,6],[3,4,2]],inside:[true,true,true,false,false]},
  {name:['Граница пуста','Empty boundary'],n:3,edges:[[1,2,1],[0,0,-5]],inside:[true,false,false]}
];
function paintBoundaryCases(parent,key) {
  const root=document.createElement('section');root.className='caseLab';parent.append(root);
  const state=caseDrafts.get('boundary-'+key)||{index:0};caseDrafts.set('boundary-'+key,state);
  function draw(){const s=boundaryScenarios[state.index];const eligible=s.edges.map((e,id)=>({e,id})).filter(({e})=>s.inside[e[0]]!==s.inside[e[1]]);const min=eligible.length?Math.min(...eligible.map(x=>x.e[2])):null;
    root.innerHTML='<h2>'+tr('А если вход немного изменится?','What if the input changes?')+'</h2><label for="boundaryCase">'+tr('Случай границы','Boundary case')+'</label><select id="boundaryCase">'+boundaryScenarios.map((x,i)=>'<option value="'+i+'"'+(i===state.index?' selected':'')+'>'+x.name[language==='RU'?0:1]+'</option>').join('')+'</select>'+graph(s.edges,{n:s.n,done:s.inside.flatMap((x,v)=>x?[v]:[]),showIds:true})+'<p>'+tr('Внутри области: ','Inside the region: ')+'{'+s.inside.flatMap((x,v)=>x?[v]:[]).join(', ')+'}. '+tr('Выберите минимальное ребро с ровно одним концом внутри. Здесь при ничьей допустим любой минимум.','Choose a minimum edge with exactly one endpoint inside. This simulator accepts any tied minimum.')+'</p><div class="controls">'+s.edges.map((e,id)=>'<button data-boundary-id="'+id+'">ID '+id+' · '+e[0]+'–'+e[1]+' · '+e[2]+'</button>').join('')+'<button data-boundary-id="-1">'+tr('Кандидата нет','No candidate')+'</button></div><p role="status" aria-live="polite" class="live"></p><details class="deep"><summary>'+tr('Разобрать проверку одного ребра','Inspect one eligibility check')+'</summary><div class="deepbody"><pre><code>// boolean[] inside; int u, v;\nif (inside[u] == inside[v]) {\n    // Not a boundary edge: skip it.\n}</code></pre><p>'+tr('Оба конца внутри — связь больше не расширяет область. Оба снаружи — связь пока недоступна. После опустошения очереди полный алгоритм продолжает с непосещённой компоненты.','Two endpoints inside do not expand the region. Two outside are not available yet. After the queue is exhausted, the complete algorithm restarts at an unvisited component.')+'</p></div></details>';
    root.querySelector('select').onchange=e=>{state.index=Number(e.target.value);draw();};
    root.querySelectorAll('[data-boundary-id]').forEach(b=>b.onclick=()=>{const id=Number(b.dataset.boundaryId),f=root.querySelector('[role=status]');if(id<0){f.textContent=min===null?tr('Верно: ни одно ребро не пересекает границу. Это ещё не означает, что весь граф обработан.','Correct: no edge crosses the boundary. This does not mean the whole graph is processed.'):tr('Кандидат есть. Найдите ребро с одним посещённым и одним непосещённым концом.','A candidate exists. Look for one visited and one unvisited endpoint.');return;}const [u,v,w]=s.edges[id];f.textContent=s.inside[u]===s.inside[v]?(u===v?tr('Петля остаётся в одной вершине и не пересекает разрез.','A self-loop stays at one vertex and cannot cross the cut.'):s.inside[u]?tr('Оба конца уже внутри: предложение устарело и не добавит вершину.','Both endpoints are already inside: this stale offer adds no vertex.'):tr('Оба конца снаружи: ребро пока не относится к границе.','Both endpoints are outside: this edge is not on the boundary.')):w===min?tr('Верно: ребро пересекает границу и имеет минимальный допустимый вес.','Correct: it crosses the boundary and has minimum eligible weight.'):tr('Ребро допустимо, но на этой границе есть более дешёвое.','The edge is eligible, but a cheaper boundary edge exists.');});
  }draw();
}
const relaxationScenarios=[
 {name:['Отрицательный вес улучшает путь','A negative edge improves the route'],old:6,parent:3,from:5,weight:-4,id:2},
 {name:['До цели ещё нет пути','No known route to the destination'],old:null,parent:-1,from:5,weight:-4,id:2},
 {name:['Новый кандидат хуже','The new candidate is worse'],old:1,parent:2,from:2,weight:4,id:3},
 {name:['Равная стоимость','Equal cost'],old:5,parent:0,from:2,weight:3,id:4},
 {name:['Начало ребра недостижимо','The edge source is unreachable'],old:null,parent:-1,from:null,weight:-8,id:5},
 {name:['Конечная стоимость −1','A finite cost of −1'],old:0,parent:1,from:4,weight:-5,id:6},
 {name:['Сумма за пределами int','A total beyond int'],old:null,parent:-1,from:3000000000,weight:1000000000,id:7}
];
function paintRelaxationCases(parent,key) {
  const root=document.createElement('section');root.className='caseLab';parent.append(root);
  const state=caseDrafts.get('relax-'+key)||{index:0,answer:'',action:''};caseDrafts.set('relax-'+key,state);
  function draw(){const s=relaxationScenarios[state.index];root.innerHTML='<h2>'+tr('Выберите действие и новое состояние','Choose the action and new state')+'</h2><label for="relaxCase">'+tr('Случай релаксации','Relaxation case')+'</label><select id="relaxCase">'+relaxationScenarios.map((x,i)=>'<option value="'+i+'"'+(i===state.index?' selected':'')+'>'+x.name[language==='RU'?0:1]+'</option>').join('')+'</select><pre>distance[from] = '+JSON.stringify(s.from)+'\nweight = '+s.weight+'; edgeId = '+s.id+'\n[distance[to], parent_edge[to]] = '+JSON.stringify([s.old,s.parent])+'</pre><p>'+tr('Для этого примера обновляем только при строгом улучшении. При равенстве сохраняем прежний ID; в задании допустимы и другие корректные родители.','This example updates only on strict improvement. Keep the old ID on a tie; the assignment also accepts other valid parents.')+'</p><label for="relaxAction">'+tr('Действие','Action')+'</label><select id="relaxAction"><option value="">'+tr('Выберите…','Choose…')+'</option><option value="skip">'+tr('Пропустить: начало недостижимо','Skip: unreachable source')+'</option><option value="keep">'+tr('Оставить прежнее состояние','Keep the old state')+'</option><option value="update">'+tr('Обновить стоимость и ID','Update both cost and ID')+'</option></select><label for="relaxAnswer">'+tr('Новое [distance[to], parent_edge[to]] в JSON','New [distance[to], parent_edge[to]] as JSON')+'</label><input id="relaxAnswer" type="text" autocomplete="off" placeholder="[null, -1]"><div class="controls"><button data-check-relax>'+tr('Проверить состояние','Check state')+'</button></div><p class="live" role="status" aria-live="polite"></p><details class="deep"><summary>'+tr('Показать правило и код одного обновления','Show the rule and one update')+'</summary><div class="deepbody"><p>'+tr('Если from недостижима, кандидата нет. Иначе candidate=distance[from]+weight. При меньшем кандидате или неизвестной цели одновременно меняются стоимость и исходный ID.','If from is unreachable, there is no candidate. Otherwise candidate=distance[from]+weight. A lower candidate or an unknown destination updates both the cost and original ID.')+'</p><pre><code>// Long[] distance; int[] parentEdge;\n// from, to, weight and edgeId describe one edge.\nif (distance[from] != null) {\n    long candidate = distance[from] + (long) weight;\n    if (distance[to] == null || candidate &lt; distance[to]) {\n        distance[to] = candidate;\n        parentEdge[to] = edgeId;\n    }\n}</code></pre></div></details>';
    root.querySelector('#relaxCase').onchange=e=>{state.index=Number(e.target.value);state.answer='';state.action='';draw();};
    const answer=root.querySelector('#relaxAnswer'),action=root.querySelector('#relaxAction'),status=root.querySelector('[role=status]');answer.value=state.answer;action.value=state.action;answer.oninput=()=>state.answer=answer.value;action.onchange=()=>state.action=action.value;
    root.querySelector('[data-check-relax]').onclick=()=>{let result;try{result=JSON.parse(answer.value);if(!Array.isArray(result)||result.length!==2||(result[0]!==null&&!Number.isSafeInteger(result[0]))||!Number.isSafeInteger(result[1]))throw Error();}catch{status.textContent=tr('Введите два значения в JSON: [стоимость или null, ID]. Например [7,2].','Enter two JSON values: [cost or null, ID]. For example [7,2].');return;}if(!action.value){status.textContent=tr('Выберите действие перед проверкой.','Choose an action before checking.');return;}const candidate=s.from===null?null:s.from+s.weight;const expectedAction=s.from===null?'skip':s.old===null||candidate<s.old?'update':'keep';const expected=expectedAction==='update'?[candidate,s.id]:[s.old,s.parent];status.textContent=action.value!==expectedAction?tr('Действие не подходит. Сначала проверьте достижимость начала, затем сравните полные стоимости.','The action does not fit. Check source reachability before comparing complete route costs.'):!practiceSame('',result,expected)?result[0]===expected[0]?tr('Стоимость верна, но ID не соответствует выбранному действию. parent_edge хранит номер исходного ребра.','The cost is correct, but the ID does not match the action. parent_edge stores an original edge ID.'):tr('Проверьте сумму и прежнюю стоимость. null и −1 имеют разный смысл.','Check the sum and old cost. null and −1 have different meanings.'):expectedAction==='skip'?tr('Верно: складывать с null нельзя; состояние не меняется.','Correct: do not add to null; the state is unchanged.'):expectedAction==='update'?tr('Верно: новая стоимость и подтверждающий исходный ID обновлены вместе.','Correct: the new cost and its original edge ID were updated together.'):tr('Верно: кандидат не меньше прежней стоимости, сохраняем оба значения.','Correct: the candidate is not lower, so both old values remain.');};
  }draw();
}
