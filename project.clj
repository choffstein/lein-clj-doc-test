(defproject lein-clj-doc-test "0.0.1-SNAPSHOT"
  :description "leiningen plugin for clj-doc-test"
  :dependencies [[org.clojure/clojure "1.3.0-beta1"]
                 [org.clojure/tools.namespace "0.1.0"]]

  :plugins [[s3-wagon-private "1.1.1"]]

  :repositories {"nfr-releases" "s3p://newfound-mvn-repo/releases/"
                 "nfr-snapshots" "s3p://newfound-mvn-repo/snapshots/"})
