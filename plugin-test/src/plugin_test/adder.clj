(ns plugin-test.adder)

(defn adder
  "A simple function to test the doctest macro with.

  => (adder 1 2)
  5 ; incorrect!
  => (adder 4 5)
  9"
  [n1 n2]
  (+ n1 n2))
