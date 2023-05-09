# Practical Java 21 LTS Big Picture Notes

## Contents

I. Key Concepts
I.1. OOP
I.2. Language

II. Core Features
II.1. Big Integer and Big Decimal
II.2. Cryptography
II.3. Java I/O API
II.4. Date Time Period Duration API
II.5. Collection Frameworks
II.6. Stream API
II.7. Optional/Default
II.8. Virtual Threads

III. Laundry List Since Java 8
III.1. Language Features
III.2. Library and Tooling
III.3. Security
III.4. Observability and Debugging
III.5. Removal and Deprecations

IV. Java 21 References

## Key Concepts

### OOP

Reference: <https://dev.java/learn>

- **States** (stored in fields)
- **Behaviors** (methods for changing those internal states)
- **Encapsulation**: Hiding internal state and requiring all interaction to be performed through methods
- **Class**: A blueprint from which individual objects are created; this() calling the constructor -> default params; Statics can only be shadowed
- **Inheritance**: Can only extends one superclass; Final class cannot be extended; Final method cannot be overridden; Maybe useful for debugging
- **Interface**:

1. Group of related methods with empty bodies, or an API
2. Can implements multiple interfaces;
3. A class must implements all the methods as public in that interface except the default methods, it's an implement contract
4. All fields are automatically `public static final`, and all methods are automatically `public abstract`; Can contains static methods as helpers

- **Abstract Class**: Can not be instantiated; Can be inherited; No static or final fields; Doesn't need to implement all interface's methods
- **Record**: Immutable and serializable final class, with built in constructor, accessors, toString(), equal(), hashCode(); Compact constructor
`public record Point(int x, int y) {}`
- **Overloading, Overriding, and Generics**: Allows different numbers of params; Overriding acts on interface's methods; Wildcards `? extends super`
- **Polymorphism**: An instance of subclass is also an instance of its superclass or interfaces; Appropriate methods will be called
- **Returning**: Must be a class or subclass of the return type
- **Access Modifiers**: Private, Package, Protected, Public; Overridden modifiers must be more, not less
- **Class Members**: Cannot access instance members directly nor can they use `this`, must use an object reference; Doesn't work in parallel; Blocks
- **Nested Classes**: Are bad for serialization
- **Lambda Expressions & Functional Interface**: Assignments, Returns, or Streams; Replacement for Anonymous Class; Is interface with only one method
- **Enum**: Behaves like a class with the addition of enum values
- **Object**: Clone, Compare, NotifyAll, Wait, ToString, HashCode, GetClass
- **Relationships**: `is-a`: Inherit, `has-a`: Composite

### Language

Reference: <https://dev.java/learn>

- **Instance Variables (Non-Static Fields)**: Values are unique to each instance of a class
- **Class Variables (Static Fields)**: Exactly one copy; `static final` is a constant
- **Local Variables**: No default values - Must be initialized
- **Primitives**: `byte`, `short`, `int`, `long`, `float`, `double`, `boolean`, `char` (16-bit Unicode); Default zero values
- **Numbers**: To boxing primitives for an object param, i.e. Collections; PrintStream, DecimalFormat, Math, Min Max, Compare, Parse; Random
- **String**: Immutable, optimized concat; Split, subSequence, trim, toLowerCase, lastIndexOf, replace, endsWith, regionMatches; String Templates
- **StringBuilder & StringBuffer**: Reverse a string; Buffer is thread safe
- **Arrays**: `int[] arr = new int[5]; int[] vec = {1, 2, 3}; Player[] players = new Player[5]; for (int i = 0; ...) ...`
- **arraycopy()**: `int[] dest = new int[5]; arraycopy(src, srcPos, dest, destPos, length)`
- **java.util.Arrays**: Copying, Sorting, Searching, Filling, Streaming, toString, etc.; `String[] rest = Arrays.copyOfRange(src, 2, 4);`
- **var**: Declares local variables; `var path = Path.of("debug.log"); try (var stream = Files.newInputStream(path)) {...}`
- **params**: Primitives and String are passed by value; Objects are as value (of the pointer), but their contents can be changed
- **GC**: If there are no more references to the object, or when it is set to null
- **java.util.function**: Predicate, Comparator, Supplier, Consumer, Function, NullFirst
- **Pattern Matching**: With Switch to write unit tests `return switch(o) {case Long l -> String.format("%d", l);}`; Instanceof; For(:)
- **RegEx**: Use online tools to generate
- **Exceptions**: Stay away from these
- **switch expression & yield**: Exits and returns the value; `return switch(x) {case ...; default -> {int a = 5; yield a;}};`
- **switch expression & pattern matching**
- **Record Patterns**
- **Vector API**

## Core Features

### Big Integer and Big Decimal

Reference: <https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/math/BigInteger.html>

### Cryptography

Reference: <https://dev.java/learn/security/intro/>

### Java I/O API

Reference: <https://dev.java/learn/java-io/>

- Non-blocking I/O - NIO2 API; I/O Stream
- Use `Path` interface instead of `File` class to access file
- Streams of character: `Reader`, `Writer`
- Streams of bytes: `InputStream`, `OutputStream`
- Can do anything with files and directories programmatically

### Date Time Period Duration API

Reference: <https://dev.java/learn/date-time/>

### Collection Framework

Reference: <https://dev.java/learn/api/collections-framework/>

- Collection, Map, Queue, Iterator; Non Null; Immutable
- Sequenced Collections
- Collection Hierarchy: List, Set (SortedSet, NavigableSet, no duplicates), Queue (Deque)
- Map Hierarchy: Map (SortedMap, NavigableMap)
- List Implementations: ArrayList, LinkedList
- Set Implementations HashSet; SortedSet/NavigableSet -> TreeSet
- Map Implementations: HashMap, LinkedHashMap; SortedMap/NavigableMap -> TreeMap; Use Immutable Keys only
- Dequeue: Stack, Queue; PriorityQueue, ArrayDeque; Stay away from Stack
- Filter: RemoveIf(Predicate)
- Iterator: HasNext, Next
- List.of(), Set.of(), Map.of(), Map.ofEntry() factory methods; Arrays.asList(); Sort, Shuffle, Rotate, SubList; GetOrDefault, ForEach, Merge, SubMap

### Stream API

Reference: <https://dev.java/learn/api/streams/>

- Map-Filter-Reduce Algorithm; Specifying a result instead of programming an algorithm; Pipeline Pattern; No Side Effects - Functional Programming
- Flatmap for list of lists
- Using Recursive Decomposition for parallelization -> Recursive divide and merge; Fork/Join Framework
- Split in middle: LinkedList is not good because of pointer chasing; HashSet is not guaranteed equal parts; ArrayList and TreeSet are good

```java
List<String> strings = List.of("one", "two", "three", "four");
List<Integer> lengths = strings.stream().map(String::length).filter(length -> length == 3).collect(Collectors.toList());

Map<Long, List<Integer>> map = histogram.entrySet().stream().map(NumberOfLength::fromEntry).collect(Collectors.groupingBy(
    NumberOfLength::number,
    Collectors.mapping(NumberOfLength::length, Collectors.toList())
));
Map.Entry<Long, List<Integer>> result = map.entrySet().stream().max(Map.Entry.comparingByKey()).orElseThrow();
```

### Optional/Default

Reference: <https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/Optional.html>

```java
Map<Integer, Map.Entry<PairOfAuthors, Long>> histogram = articles.stream()
	.collect(
		Collectors.groupingBy(
				Article::inceptionYear,
				flatMapping
		)
	)  // Map<Integer, Optional<Map.Entry<PairOfAuthors, Long>>>
	.entrySet().stream()
	.flatMap(
		entry -> entry.getValue()
						.map(value -> Map.entry(entry.getKey(), value))
						.stream())
	.collect(Collectors.toMap(
			Map.Entry::getKey, Map.Entry::getValue
	)); // Map<Integer, Map.Entry<PairOfAuthors, Long>>
```

### Virtual Threads

- One-thread-per-request async lightweight coroutines

## Laundry List Since Java 8

Reference: <https://dev.java/evolution/>

### Language Features

- Local-Variable Type Inference
- Switch Expressions
- Records
- Sealed Classes
- Text Blocks
- Pattern Matching for instanceof
- Pattern Matching for Switch

### Library and Tooling

- Simple Web Server
- Code Snippets in Java API Documentation
- Java Shell Tool
- Foreign Function & Memory API

### Security

- Strongly Encapsulate JDK Internals
- Enhanced Pseudo-Random Number Generators
- Context-Specific Deserialization Filters

### Observability and Debugging

- JFR Event Streaming
- Helpful NullPionterExceptions

### Removal and Deprecations

- Remove the Concurrent Mark Sweep (CMS) Garbage Collector
- Disable and Deprecate Biased Locking
- Deprecate Finalization for Removal

```java
FileInputStream input = null;
FileOutputStream output = null;
try {
	input = new FileInputStream(file1);
	output = new FileOutputStream(file2);
	// ... copy bytes from input to output ...
	output.close(); output = null;
	input.close(); input = null;
} finally {
	if (output != null) output.close();
	if (input != null) input.close();
}
```

### Misc

- UTF-8 by Default
- Reimplement Core Reflection with Method Handle
- Internet-Address Resolution API

## Java 21 References

- <https://openjdk.org/jeps/440>
- <https://openjdk.org/jeps/433>
- <https://openjdk.org/jeps/448>
- <https://openjdk.org/jeps/442>
- <https://openjdk.org/jeps/444>
