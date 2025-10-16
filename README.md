PDS data extractor for MDOT
### JAVA version that this application was tested and verified:

    C:\Program Files\Java\jdk-18.0.2

### Create Executable in IntelliJ IDEA

Instructions:
File -> Project Structure -> Project Settings -> Artifacts -> Click + (plus sign) -> Jar -> From modules with dependencies...

Select a Main Class (the one with main() method) if you need to make the jar runnable.

Select Extract to the target Jar

Click OK

Click Apply/OK

The above sets the "skeleton" to where the jar will be saved to. To actually build and save it do the following:

Build -> Build Artifact -> Build

Try Extracting the .jar file

### Version problems

If you have any version problems: edit environment variables for your account
then delete these in your Windows Environment variable: system variable: Path
    
    C:\Program Files (x86)\Common Files\Oracle\Java\javapath
    C:\Program Files\Common Files\Oracle\Java\javapath

then if you're using java 18
environment variable: system variable : Path
add path 

    C:\Program Files\Java\jdk-18.0.2
is enough