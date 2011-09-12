(ns leiningen.doc-test
  (:use [leiningen.compile :only (eval-in-project)])
  (:gen-class))

(defn doc-test
  "Run doc-test on all functions in all source files"
  [project]
  (let [source-path (:source-path project)]
    (eval-in-project project
                     `(do
                        (defonce sources# (util/format-sources ~source-path))
                        (doseq [file-name# sources#]
                          (let [loaded-file-ns# (util/file-ns file-name#)
                                function-names# (keys (ns-interns (find-ns (symbol loaded-file-ns#))))]
                            (map #(core/doc-test %) function-names#))))
                     nil
                     nil
                     '(do
                        (require '[clojure.java.io :as io]
                                 '[clj-doc-test.util :as util]
                                 '[clj-doc-test.core :as core])
                        (import '[java.io.File])))))
