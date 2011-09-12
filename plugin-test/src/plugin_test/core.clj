(ns plugin-test.core)

(defn adder
  "A simple function to test the doctest macro with.

  => ((adder 1) 2)
  4 ; incorrect!
  => ((adder 4) 5)
  9"
  [n1]
  (fn [n2] (+ n1 n2)))
