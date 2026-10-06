import sys,json,algorithms
for line in sys.stdin:
    x=json.loads(line)
    try:
        result={'status':'ready'} if x['task']=='ping' else getattr(algorithms,x['task'])(x)
    except NotImplementedError as e:
        result={'error':str(e)}
    print(json.dumps(result,separators=(',',':')))
