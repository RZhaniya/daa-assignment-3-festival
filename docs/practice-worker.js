'use strict';
// Python executes in this worker; stopping it never blocks lesson navigation.
let python;
self.onmessage = async event => {
  try {
    if (event.data.type === 'init') {
      const base = 'https://cdn.jsdelivr.net/pyodide/v0.29.3/full/';
      importScripts(base + 'pyodide.js');
      python = await loadPyodide({indexURL: base});
      const checkerURL=new URL('code/practice/judge.py', self.location.href);
      checkerURL.searchParams.set('v',new URL(self.location.href).searchParams.get('v')||'1');
      const response = await fetch(checkerURL);
      if (!response.ok) throw new Error('Practice checker could not be loaded');
      python.runPython(await response.text());
      self.postMessage({type: 'ready'});
    } else if (event.data.type === 'run' && python) {
      python.globals.set('_practice_source', event.data.source);
      python.globals.set('_practice_id', event.data.id);
      python.globals.set('_practice_cases', JSON.stringify(event.data.cases));
      const result = python.runPython('json.dumps(run_practice(_practice_source, _practice_id, json.loads(_practice_cases)), ensure_ascii=False)');
      self.postMessage({type: 'result', result: JSON.parse(result)});
    }
  } catch (error) {
    self.postMessage({type: 'error', message: String(error)});
  }
};
