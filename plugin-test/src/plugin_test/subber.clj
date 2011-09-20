(ns plugin-test.subber)

(defn subber
  "A simple function to test the doctest macro with.

  => (subber 1 2)
  4 ; incorrect!
  => (subber 4 5)
  -1"
  [n1 n2]
  (- n1 n2))

(defn multr
  "A simple function to test the doctest macro with.

  => (multr 1 2)
  2
  => (multr 4 5)
  20"
  [n1 n2]
  (* n1 n2))
