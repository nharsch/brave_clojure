var node3 = {
    value: "last",
    next: null,
};

var node2 = {
  value: "middle",
  next: node3
};

var node1 = {
  value: "first",
  next: node2
};

var first = function(node) {
    return node.value;
};

var rest = function(node) {
    return node.next;
};

var cons = function(newValue, node) {
    return {
        value: newValue,
        next: node
    };
};

console.log(first(node1));

console.log(first(node2));

var node0 = cons("new first", node1);

console.log(first(rest(node0)));

var map = function (list, transform) {
    if (list === null) {
        return null;
    } else {
        // ooh, recursive definition
        return cons(transform(first(list)), map(rest(list), transform));
    }
}
