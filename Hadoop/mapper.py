#!/usr/bin/env python
"""mapper.py"""
# execute as:
# echo "is there any way, any way, we could make this work? Is there?" | python mapper.py | python reducer.py
# cat .\for_map_reduce.txt | python mapper.py | python reducer.py
# cat .\loren_ipsum.txt | python mapper.py | python reducer.py

import sys

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    print("words:", words)
    words.sort()
    print("sorted words:", words)
    # increase counters
    for word in words:
        # write the results to STDOUT (standard output);
        # what we output here will be the input for the
        # Reduce step, i.e. the input for reducer.py
        #
        # tab-delimited; the trivial word count is 1
        # the 1
        # the 1
        # the 1
        # only 1
        # only 1
        print( '%s %s' % (word, 1))
