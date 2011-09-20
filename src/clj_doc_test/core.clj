(ns
    #^{:author "Andy Kish"
       :contributor "Corey M. Hoffstein"
       :doc "Verifies correctness of example expressions in doc-strings."}
  clj-doc-test.core
  (:use clojure.test)
  (:use [clojure.contrib.str-utils :only (re-split)]))

(defn- read-expr-pair
  "Read two expressions from expr-string and return a tuple of them.

  => (read-expr-pair \"(+ 1 2) 3\")
  [(+ 1 2) 3]"
  [expr-string]
  (with-open [sreader (new java.io.StringReader expr-string)
              pbreader (new java.io.PushbackReader sreader)]
    [(read pbreader) (read pbreader)]))

(defn- find-expression-strings
  "Finds expressions that they belong in a REPL. Namely, the => arrow
  beginning a line followed by 2 expressions.

  => (find-expression-strings (str \\newline \"=> ((adder 1) 2) 3\"))
  (\" ((adder 1) 2) 3\")"
  [docstr]
  (drop 1 (re-split #"\n\s*=>" docstr)))

(defn to-is
  "Converts a doc-test to forms using clojure.test/is.

  => (to-is (:doc (meta (var read-expr-pair))))
  ((clojure.test/is (clojure.core/= (read-expr-pair \"(+ 1 2) 3\")
                                    '[(+ 1 2) 3])))"
  [doc]
  (let [expr-strs (find-expression-strings doc)
        exprs (map read-expr-pair expr-strs)]
    (map (fn [[expr result]] `(is (= ~expr '~result)))
         exprs)))

;; transformed this from a macro to a function that runs real-time
(defn doc-test
  [name-space function-name]
  (let [f-meta (or (meta (resolve (symbol (str name-space "/" function-name)))) {})
        is-statements (to-is (or (:doc f-meta) ""))] ;; incase there is an empty (or no) doc-string
    (if (sequential? is-statements) ; only make a test if there are doc-tests
      (binding [*ns* (create-ns `clj-doc-test.sandbox#)] ;;ripped from technomancy's slamhound (line 33 of regrow.clj)
        (try
          (eval `(do
                   (use '[~name-space])
                   ~@is-statements))
          (finally
           (remove-ns (.name *ns*))))))))
