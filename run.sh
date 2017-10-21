#!/bin/zsh
pwd
#spice.trace
echo spice.trace ----------------------------------------------------------------- -e
java -jar CacheSimulator.jar -bs 4 -cs 32 traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -split traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -wt -wna traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -split traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -wt -wna -split traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -sa 10 traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -sa 10 -split traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -split traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -sa 10 traces/spice.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -sa 10 -split traces/spice.trace

echo cc.trace ----------------------------------------------------------------- -e
#cc.trace
java -jar CacheSimulator.jar -bs 4 -cs 32 traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -split traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -wt -wna traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -split traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -wt -wna -split traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -sa 10 traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -sa 10 -split traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -split traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -sa 10 traces/cc.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -sa 10 -split traces/cc.trace

echo cc.trace ----------------------------------------------------------------- -e
#tex.trace
java -jar CacheSimulator.jar -bs 4 -cs 32 traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -split traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -wt -wna traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -split traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -fa -wt -wna -split traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -sa 10 traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -sa 10 -split traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -split traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -sa 10 traces/tex.trace
echo -e
java -jar CacheSimulator.jar -bs 4 -cs 32 -wt -wna -sa 10 -split traces/tex.trace