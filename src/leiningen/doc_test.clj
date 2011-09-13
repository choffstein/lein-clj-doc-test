(ns leiningen.doc-test
  (:use [leiningen.compile :only (eval-in-project)])
  (:gen-class))

(defn doc-test
  "Run doc-test on all functions in all source files"
  [project]
  (let [source-path (:source-path project)
        seq-source-path (if (not (sequential? source-path)) [source-path] source-path)]
    (eval-in-project project
                     `(do
                        (defonce sources# (util/format-sources ~seq-source-path))
                        (doseq [file-name# sources#]
                          (try (let [loaded-file-ns# (util/file-ns file-name#)
                                     function-names# (do
                                                       (load-file file-name#)
                                                       (keys (ns-interns (find-ns (symbol loaded-file-ns#)))))]
                                 (doseq [function-name# function-names#]
                                   (let [fn-symbol# (symbol (str loaded-file-ns# "/" function-name#))]
                                     (core/doc-test fn-symbol#))))
                               (catch java.lang.NullPointerException e# ()))))
                     nil
                     nil
                     '(do
                        (require '[clj-doc-test.util :as util]
                                 '[clj-doc-test.core :as core])))))
