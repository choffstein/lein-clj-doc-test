(ns leiningen.doc-test
  (:use [leiningen.compile :only (eval-in-project)])
  (:gen-class))

(defn doc-test
  "Create tests in test directory using doc-test from clj-doc-test"
  [project]
  (let [source-path (:source-path project)]
    (eval-in-project project
                     `(let [sources# (format-sources ~source-path)]
                        (map (fn [file-name#]
                               (let [loaded-file-ns# (file-ns file-name#)
                                     function-names# (keys (ns-interns (find-ns (symbol loaded-file-ns#))))]
                                 (map doc-test function-names#))) sources#))
                     nil
                     false
                     `(use '[clj-doc-test.util]
                           '[clj-doc-test.core]))))
