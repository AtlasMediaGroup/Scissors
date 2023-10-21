# Scissors [![Build Status](https://ci.plex.us.org/job/Scissors/job/slime%252F1.20.1/badge/icon)](https://ci.plex.us.org/job/Scissors/job/slime%252F1.20.1/)

Scissors is a fork of Paper that aims to fix exploits possible in Creative Mode. Many of these exploits are ones that
Paper's own team has either refused to fix or would have.

All SWM patches/SWM API belongs to [AdvancedSlimePaper and InfernalSuite](https://github.com/InfernalSuite/AdvancedSlimePaper)

Note: This will not compile on Windows by default. To make it compile, you need to delete the `aswm-core/src`
and `aswm-api/src` files. Then open up Command Prompt as an administrator and run the following for the api project:
```mklink /D C:\full\path\to\project\aswm-api\src C:\full\path\to\project\submodules\AdvancedSlimePaper\api\src```. Now,
run the same thing again for the core
project: ```mklink /D C:\full\path\to\project\aswm-core\src C:\full\path\to\project\submodules\AdvancedSlimePaper\core\src```
Make sure you replace the placeholder `C:\full\path\to\project` with the actual full path to the root project on your
local machine. You do not need to do this if you are compiling on macOS or Linux.

## Links
### [Scissors Download](https://ci.plex.us.org/job/Scissors)
### [Scissors Javadoc](https://javadoc.scissors.gg/1.20.1)
### [Scissors Announcements](https://totalfreedom.tf/forums/scissors-announcements.55)
### [Scissors General Discussion](https://totalfreedom.tf/forums/scissors-discussion.56/)

## Tasks
```
Paperweight tasks
-----------------
applyApiPatches
applyPatches
applyServerPatches
cleanCache - Delete the project setup cache and task outputs.
createMojmapBundlerJar - Build a runnable bundler jar
createMojmapPaperclipJar - Build a runnable paperclip jar
createReobfBundlerJar - Build a runnable bundler jar
createReobfPaperclipJar - Build a runnable paperclip jar
generateDevelopmentBundle
rebuildApiPatches
rebuildPatches
rebuildServerPatches
reobfJar - Re-obfuscate the built jar to obf mappings
runDev - Spin up a non-relocated Mojang-mapped test server
runReobf - Spin up a test server from the reobfJar output jar
runShadow - Spin up a test server from the shadowJar archiveFile
```
