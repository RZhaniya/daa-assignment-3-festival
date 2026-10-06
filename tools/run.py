"""Public launcher. Official grading uses instructor-controlled code and Docker."""
import argparse,json,pathlib,subprocess,sys
P=pathlib.Path(__file__).resolve().parents[1]
def build(lang):
 p=P/lang;s=p/'source';b=p/'build';b.mkdir(exist_ok=True)
 if lang=='Java':subprocess.run(['javac','--release','17','-encoding','UTF-8','-d',str(b),*[str(f) for f in sorted(s.glob('*.java'))]],check=True);return ['java','-Xmx384m','-cp',str(b),'Main']
 if lang=='Python':return [sys.executable,str(s/'main.py')]
 if lang=='Cpp':subprocess.run(['g++','-std=c++17','-O2','-o',str(b/'main'),str(s/'main.cpp')],check=True);return [str(b/'main')]
 if lang=='CSharp':subprocess.run(['dotnet','build',str(s/'App.csproj'),'--nologo','-o',str(b)],check=True);return ['dotnet',str(b/'App.dll')]
 raise ValueError(lang)
def main():
 a=argparse.ArgumentParser();a.add_argument('--language',choices=['Java','Python','Cpp','CSharp'],default='Java');a.add_argument('--setup',action='store_true');a.add_argument('--check',action='store_true');a.add_argument('--input');x=a.parse_args();cmd=build(x.language)
 if x.input:
  with open(x.input,encoding='utf-8') as f:raise SystemExit(subprocess.run(cmd,input=f.read(),text=True).returncode)
 cases=[{'input':{'task':'ping'},'output':{'status':'ready'}}] if x.setup else json.loads((P/'tests'/'public.json').read_text(encoding='utf-8'))
 failures=0
 for i,c in enumerate(cases):
  try:
   z=subprocess.run(cmd,input=json.dumps(c['input'])+'\n',capture_output=True,text=True,timeout=20);y=json.loads(z.stdout)
   from public_validate import check
   t=c['input']['task']
   ok=z.returncode==0 and check(c['input'],y,c['output'])
   print(('PASS' if ok else 'FAIL')+f' public {i+1}: {t}')
   failures+=not ok
  except Exception as ex:print(f'FAIL public {i+1}: {c["input"]["task"]} ({type(ex).__name__})');failures+=1
 return 1 if failures else 0
if __name__=='__main__':sys.exit(main())
