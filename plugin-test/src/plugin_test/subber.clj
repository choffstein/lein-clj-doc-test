(ns plugin-test.subber)

(defn subber
  "A simple function to test the doctest macro with.

  => (subber 1 2)
  4 ; incorrect!
  => (subber 4 5)
  -1"
  [n1 n2]
  (- n1 n2))
