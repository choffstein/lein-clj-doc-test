(ns clj-doc-test.util
  (:use [clojure.contrib
         [find-namespaces :only (read-file-ns-decl)]])
  (:require [clojure.java.io :as io])
  (:import [java.io.File]))

;; Horribly, horribly stolen from marginalia
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
