import os
import shutil
import subprocess
import urllib.request
import zipfile
from pathlib import Path

base = Path(r'C:\temp')
base.mkdir(exist_ok=True)
headers = {'User-Agent': 'Mozilla/5.0'}

# Java 17 download
jdk_url = 'https://api.adoptium.net/v3/binary/latest/17/ga/windows/x64/jdk/hotspot/normal/eclipse'
jdk_zip = base / 'jdk17.zip'
jdk_dir = base / 'jdk17'
if not jdk_dir.exists():
    req = urllib.request.Request(jdk_url, headers=headers)
    with urllib.request.urlopen(req) as resp, open(jdk_zip, 'wb') as out:
        shutil.copyfileobj(resp, out)
    with zipfile.ZipFile(jdk_zip, 'r') as z:
        z.extractall(base)
    candidates = sorted(base.glob('jdk-*'))
    if candidates and not jdk_dir.exists():
        shutil.move(str(candidates[0]), str(jdk_dir))

java_home = str(jdk_dir)
java_exe = jdk_dir / 'bin' / 'java.exe'
print('JAVA_HOME=' + java_home)
subprocess.run([str(java_exe), '-version'], check=True)

# Gradle 8.7 download
gradle_url = 'https://services.gradle.org/distributions/gradle-8.7-bin.zip'
gradle_zip = base / 'gradle-8.7-bin.zip'
gradle_extract = base / 'gradle-8.7'
if not gradle_extract.exists():
    req = urllib.request.Request(gradle_url, headers=headers)
    with urllib.request.urlopen(req) as resp, open(gradle_zip, 'wb') as out:
        shutil.copyfileobj(resp, out)
    with zipfile.ZipFile(gradle_zip, 'r') as z:
        z.extractall(base)

gradle_bat = gradle_extract / 'bin' / 'gradle.bat'
print('GRADLE=' + str(gradle_bat))

env = os.environ.copy()
env['JAVA_HOME'] = java_home
env['PATH'] = str(jdk_dir / 'bin') + ';' + env['PATH']
subprocess.run([str(gradle_bat), 'wrapper', '--gradle-version', '8.7'], cwd=r'C:\Users\Randani\Smart-Pantry-Manager', env=env, check=True)
print('wrapper-generated')
