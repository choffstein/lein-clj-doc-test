(ns leiningen.doc-test
  (:use [clojure.contrib.str-utils :only (re-split)]
        [clojure.contrib.string :only (ltrim join split)]
        [leiningen.core]
        [leiningen.compile :only (eval-in-project)]
        [clojure.contrib
         [find-namespaces :only (read-file-ns-decl)]])
  (:require [clojure.java.io :as io])
  (:import [java.io.File])
  (:gen-class))


;; Horribly, horribly stolen from marginalia
(defn- ls
  [path]
  (let [file (java.io.File. path)]
    (if (.isDirectory file)
      (seq (.list file))
      (when (.exists file)
        [path]))))

(defn- mkdir [path]
  (.mkdirs (io/file path)))

(defn- ensure-directory!
  [path]
  (when-not (ls path)
    (mkdir path)))

(defn- find-clojure-file-paths
  [dir]
  (->> (java.io.File. dir)
       (file-seq)
       (filter #(re-find #"\.clj$" (.getAbsolutePath %)))
       (map #(.getAbsolutePath %))))

(defn- dir? [path]
  (.isDirectory (java.io.File. path)))

(defn- format-sources [source-path]
  (if (nil? source-path)
    (find-clojure-file-paths "./src")
    (->> source-path
         (map #(if (dir? %)
                 (find-clojure-file-paths %)
                 [%]))
         (flatten))))


;; bastardized from marginalia
(defn file-ns [f]
  (-> (java.io.File. f)
      (read-file-ns-decl)
      (second)
      (str)))

;; my marginal contributions
(defn- context-project
  "Gets the project in which Spawn is being run, or nil if it's being run outside a project"
  []
  (if (.exists (java.io.File. "project.clj")) (leiningen.core/read-project)))

(defn- ns-to-test-dir
  "Given a name space, return the appropriate test file namespace and location to write to."
  [name-space]
  (let [split-name-space (split #"\." name-space)
        directory (str "./test/" (first split-name-space) "/test/" (join "/" (drop-last (rest split-name-space))))
        file-name (str (last split-name-space) ".clj")
        test-name-space (str (first split-name-space) ".test." (join "." (rest split-name-space)))]
    {:dir directory
     :file-name file-name
     :ns test-name-space}))

(defn- file-to-function-names
  "Open the file handle and return any defined function names"
  [file-name]
  (let [file-data (slurp file-name)
        loaded-file-ns (file-ns file-name)]
      (eval-in-project (context-project) (symbol file-data))
      (let [function-names (keys (ns-interns (find-ns (symbol loaded-file-ns))))]
        function-names)))

(defn- run-doc-test
  "Actually run the task.  Open up all source files, rip the file definitions, and
   then write out tests to the test folder."
  [source-path]
  (let [sources (format-sources source-path)
        namespaces (map file-ns sources)
        test-dir-ns (map ns-to-test-dir namespaces)]
    (println (map file-to-function-names sources))))

(defn doc-test
  "Create tests in test directory using doc-test from clj-doc-test"
  []
  (let [source (:source (context-project))]
    (run-doc-test source)))
