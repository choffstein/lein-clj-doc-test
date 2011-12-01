(defproject lein-clj-doc-test "0.0.1-SNAPSHOT"
  :description "leiningen plugin for clj-doc-test"
  :dependencies [[org.clojure/clojure "1.3.0-beta1"]
                 [org.clojure/tools.namespace "0.1.0"]
                 [swank-clojure "1.4.0-SNAPSHOT"
                  :exclusions [org.clojure/clojure
                               org.clojure/clojure-contrib]]])
