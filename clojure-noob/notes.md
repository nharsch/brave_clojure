# Chapter 1

`lein new app <appname>` creates project

`lein run` runs clj in interpreter

`lein uberjar` builds jar

`lein repl` in project opens repl in project namespace

# Chapter 2 - Emacs
looks cool, but fuck this for now, using Vim

# Chapter 3

`do` wraps up multiple commands

`or` returns first truthy value

**Higher order functions** either take a function
as an argument or return a function

all functions are created equal

## anonymouse functions:
(fn [args] procedure)

or #(op % 3) where % is argument

(#(str %1 " and " %2) "cornbread" "butter beans")

## Loops

### For Loops
Characterized as a sequence operator

# Chapter 4

**indirection** is a generic term for the mechanisms
a language so that one name can have multiple, related 
meanings. `first` has multiple, data structure-specific meanings.

function **polymorphism** is one way that Clojure provides indirection.

Clojure sequence function use `seq` on their args

## Map

When you pass `map` to multiple collections, the elements
of the first collection will be passes as the first
argument of the mapping function, the elements of the 
second collection will be passes as the second argument, and so on.

## Reduce
reduce processes each element in a sequence to build a result

## Take-While, Drop-While, Reduce
(Take-while filter-fun seq) => (seq where filter-fun true)

(Drop-while filter-fun seq) => (seq where filter-fun false)

(filter filter-fun seq) => (seq where filter-fun is true)

Take-while my be more efficient that filter in some cases, like when 
the sequence is in order, although I'm not sure why because how do it know
a sequence is in order?

## Lazy Sequences
`map` firsts calls `seq` on a collection

`map` and `filter` return lazy sequences. Lazy sequences' values
 aren't computed until you try to access them.

If `map` wasn't lazy, it would evaulate predicate function on every
value in sequence. This makes chaining filters slower than necessary

For example: say we want to find all brown foxes from a sequence like this:

    (def animals [{:species "not-fox" :coloring "green"}...])

We'd want something like:
    (take-while #(= (:species %) "fox")
        (take-while #(= (:coloring %) brown) animals)) 

We don't want to first firlter all animals on color, then all brown animals 
by species. We'd rather lazily check both predicates on every item. Of course 
the above example could be rewitting with both params in one predicate function.

Very much like generators.

This will be useful in data pipelining.

You can think of a lazy seq as consisting of two parts: a recipe for how to realize the elements of a sequence and the elements that have been realized so far. 

## Collections

Like sequences

Sequences are about operating on individual members, 
Collections are about the data structure as a whole.
For example: **empty? count, every?**

## Apply and Partial


