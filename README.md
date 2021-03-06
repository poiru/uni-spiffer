**Spiffer** finds the [shortest path](http://en.wikipedia.org/wiki/Shortest_path_problem)
between two points in a hand-drawn graph. It is my project for the following
courses at the [University of Helsinki](http://www.cs.helsinki.fi/en):

* [Java Programming Project](http://www.cs.helsinki.fi/en/courses/58160/2013/s/a/3)
* [Algorithms and Data Structures Project](http://www.cs.helsinki.fi/en/courses/58161/2013/s/a/3)

**Disclaimer:** Several design decisions (such as the choice of programming
language and the reimplemention of core data structures) were motivated by the
requirements of the aforementioned courses.


### Build

This is a Maven project. To compile, test, and run Spiffer, execute the
following commands:

```
cd spiffer
mvn compile
mvn test
mvn exec:java
```


### License

Spiffer is distributed under the terms of the MIT license. See LICENSE-MIT.txt
for details.