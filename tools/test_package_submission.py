"""Test committed snapshots rather than a student's working files."""
import importlib.util
import json
from pathlib import Path
import subprocess
import tempfile
import unittest
import zipfile

spec=importlib.util.spec_from_file_location('packer',Path(__file__).with_name('package_submission.py'))
packer=importlib.util.module_from_spec(spec);spec.loader.exec_module(packer)


class Packaging(unittest.TestCase):
    def setUp(self):
        self.temp=tempfile.TemporaryDirectory();self.root=Path(self.temp.name)
        self.previous=packer.ROOT;packer.ROOT=self.root
        self.git('init');self.git('config','user.name','Test');self.git('config','user.email','test@example.invalid')
        self.git('remote','add','origin','git@github.com:student/assignment3.git')
        files={'.gitignore':'.submission/\n','submission.json':'{"language":"Python"}',
               'ANALYSIS.md':'Explanation','SUBMISSION.md':'Identity','README.md':'Instructions',
               'Python/source/main.py':'print(1)\n','tests/student/case.jsonl':'{"task":"ping"}\n',
               '.gitattributes':'ANALYSIS.md export-subst\n'}
        for name,text in files.items():
            path=self.root/name;path.parent.mkdir(parents=True,exist_ok=True);path.write_text(text,encoding='utf-8')
        self.commit()

    def tearDown(self):
        packer.ROOT=self.previous;self.temp.cleanup()

    def git(self,*args):
        return subprocess.run(['git','-C',str(self.root),*args],check=True,capture_output=True).stdout

    def commit(self):
        self.git('add','.');self.git('commit','-m','test')

    def test_exact_blobs_and_metadata(self):
        (self.root/'ANALYSIS.md').write_text('$Format:%H$',encoding='utf-8');self.commit()
        packer.package('241100')
        with zipfile.ZipFile(self.root/'.submission/Assignment3_241100.zip') as archive:
            self.assertEqual(archive.read('ANALYSIS.md'),b'$Format:%H$')
            meta=json.loads(archive.read(packer.META))
            self.assertEqual(meta['commit'],self.git('rev-parse','HEAD').decode().strip())
            self.assertEqual(meta['repository_url'],'https://github.com/student/assignment3')
            self.assertNotIn('.gitattributes',archive.namelist())
        packer.package('241100')  # ignored output doesn't make a second run dirty

    def test_dirty_and_untracked_files(self):
        (self.root/'tests/student/forgotten.jsonl').write_text('{}',encoding='utf-8')
        with self.assertRaisesRegex(ValueError,'clean'):packer.package('241100')

    def test_invalid_language_type(self):
        (self.root/'submission.json').write_text('{"language":[]}',encoding='utf-8');self.commit()
        with self.assertRaisesRegex(ValueError,'select'):packer.package('241100')

    def test_credentials_rejected(self):
        (self.root/'tests/student/key.pem').write_text('secret',encoding='utf-8');self.commit()
        with self.assertRaisesRegex(ValueError,'credentials'):packer.package('241100')

if __name__=='__main__':unittest.main()
