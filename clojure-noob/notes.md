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


# Chapter 7 - Macros
[code notes](./src/clojure_noob/ch07.clj)

Clojure evaluation:

read text --> create data structures --> evaluate those data structures

compilers construct Abstract Syntax Trees (ASTs)


Clojure evaluates list structures

Lists are ideal for trees

## Read and Eval

Read - convert texutal code into data structure

Eval - Traverse the data structures and perform actions like
function application or var lookup

## Reader Macros

reader macros are sets of rules for transforming text into data structures

`quote` yields an unevaluted form

## The Evaluator

a function that takes a data structure as an arg

### symbols

create abstractions by associating names with vals

symbols resolves to either a **value** or **special form**

### lists

if the data structure is an empty list, it evaluates to an empty list

otherwise, it's evaluates as a _call_ tot he first element in the list

## Macros

allow you to manipulate data between `read` and `eval`

macro evaluation is called *Macro Expansion*

### `->` Macro



# Chapter 8

`when` is not a special form, but a macro defined with `if` and `do`

functions v macros: function args are first fully evaluated

multi-arity macros: `and` and `or` are defined as macros

## Symbols vs values

## Simple Quoting

`quote` will return unevaluated data structure

quoting an unbound symbol will return the symbol

single quote is reader macro

## Syntax quoting

uses backtick ``` `(+ 1 2) ```

syntax quoting returns fully qualified symbol

`~` evaulates code in quoted exp

unquote splicing unwraps a sequable data structure

(+ ~@(list 1 2 3)) == (+ 1 2 3)




