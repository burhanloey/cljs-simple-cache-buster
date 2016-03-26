(ns leiningen.cljs-simple-cache-buster
  (:require [selmer.parser :as selmer]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [leiningen.compile :as lcompile]
            [robert.hooke :as hooke]))

(defn- busting [project]
  (let [template-file (get-in project [:cljs-simple-cache-buster :template-file])
        output-file   (get-in project [:cljs-simple-cache-buster :output-to])]
    (clojure.java.io/make-parents output-file)
    (selmer/set-resource-path! (:root project))
    (spit output-file (selmer/render-file template-file {:fingerprint (str (c/to-long (t/now)))}))))

(defn cljs-simple-cache-buster
  "Run simple cache buster"
  [project & args]
  (let [cljsbuild-id (get-in project [:cljs-simple-cache-buster :cljsbuild-id])]
    (when (and (seq args)
               (= (count args) 2)
               (= (first args) "once")
               (= (second args) cljsbuild-id))
      (busting project))))
