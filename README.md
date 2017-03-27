# Environment/Toolchain

- `vim` for editing
- `fireplace-vim` to connect to repl
   - `:Connect` if not connected
   - `cpp` to evaluate form
   - `:%Eval ` to evaluate whole file
- `lein repl` for repl, open in another window



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

Programming to abstractions - allows use of map function
over different data types

Abstractions a names collection of operations

sequence or `seq` abstraction

if `first`, `rest`, and `cons` work on it, it's a `seq`



**indirection** is a generic term for the mechanisms
a language so that one name can have multiple, related 
meanings. `first` has multiple, data structure-specific meanings.

function **polymorphism** is one way that Clojure provides indirection.

`seq` function returns list

`seq` on hashes returns list of key/val vectors

`([:occupation "Dead mopey guy"] [:name "Bill Compton"])'

## Map

When you pass `map` to multiple collections, the elements
of the first collection will be passes as the first
argument of the mapping function, the elements of the
second collection will be passes as the second argument, and so on.

you can give map mutliple collections

```
(map str ["a" "b" "c"] ["A" "B" "C"])
; => ("aA" "bB" "cC")
```



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

### Into
since seq functions return a seq, use `into` to put
that seq into desired collection data structure

`(into {} (map identity {:sunlight-reaction "Glitter!"}))`

`into` operates on 2 data structures

### Conj
`conj` adds elements to a collection

`(conj [])`

## Apply and Partial

# Chapter 5

## Pure Functions

always return the same result if given the same args. Called **referential transparency**

andy funtion with `rand` is not

any io bound functions not referentially transparent, as outside state can change

Pure functions have non side effects

## Memoization
can be used to store the results of a pure function applied to args so
that calling the fn on the same args returns the value without
further computation


# Chapter 6

## Namespaces

namespaces used for organizing code

namespaces are objects of type `clojure.lang.Namespace`, and are a data structure

refer to current namespace with `*ns*`, get its name with `(ns-name *ns*)`

`(def var value)` _interns_ the var. Find a namespaces map of symbols-to-interned-vars
using `ns-interns`

`(create-ns)` takes a symbol, creates a namespace with that name, returns is

`(in-ns)` creates namespace and switches repl to it

use **fully qualified symbol** to reference symbols in other namespaces

### refer

refer updates current namespace symbol map with symbols of another

`(clojure.core/refer 'proj.test)`

you can now use symbols from 'proj.test' without fully qualified names

you can also give `refer` optional filters `:only`, `:exclude` and `:rename`

(clojure.core/refer 'cheese.taxonomy :only ['bries])

# Chapter 7

## Read and Eval

Lisps have 2 phase evaluation model: `read` and `eval`

read: text -> AST
eval: AST is evaluated

`eval` will evaluate a list of symbols

`(eval (list (+ 1 3))) ;=>3`

'read-string` will convert string into data structure

`(read-string "(+ 1 2)") ; => (+ 1 2)`

## Reader Macros

when read, macros such as the anonymous function macro:

`#(+ 1 %)`

are expanded into data structres:

`(fn* [p1__1321#] (+ 1 p1__1321#))`

## The Evaluator

Evaluator is a function that accepts data structure as an
argument, processes the data structure according
to the data structure's type, and returns a result

Whenever Clojure evaluates data structures that aren’t a list or symbol, 
the result is the data structure itself

### Symbols

clojure will evaluate symbols by:

1. Looking up whether the symbol is a special form
2. look up if the symbol corresponds to a local binding
3. tries to find namespace mapping
4. throws an exception

at read time symbols are data structures, not the functions they might
represent

The quote special form tells the evaluator, “Instead of evaluating my next 
data structure like normal, just return the data structure itself.” 

### `defmacro`

```
(defmacro infix-eval
    [call-list]
    (list (second call-list) (first call-list) (last call-list)))
```

### `macroexpand`

```
(macroexpand '(infix-eval (1 + 1)))
(+ 1 1)
```

