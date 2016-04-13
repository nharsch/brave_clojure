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

