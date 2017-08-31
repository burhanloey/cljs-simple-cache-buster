(ns leiningen.cljs-simple-cache-buster
  (:require [selmer.parser :as selmer]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clojure.java.io :as io]
            [leiningen.core.main :as lmain]
            [leiningen.compile :as lcompile]
            [robert.hooke :as hooke]))

(defn- busting [{config :cljs-simple-cache-buster :as project}]
  (let [template-files (flatten (vector (:template-file config)))
        output-files   (flatten (vector (:output-to config)))
        fingerprint    (or (:fingerprint config)
                           (str (c/to-long (t/now))))]
    (selmer/set-resource-path! (:root project))
    (doseq [[in-file out-file] (map vector template-files output-files)]
      (io/make-parents out-file)
      (->> {:fingerprint fingerprint}
           (selmer/render-file in-file)
           (spit out-file)))
    (lmain/info "Template fingerprinted with" fingerprint)))

(defn cljs-simple-cache-buster
  "Run simple cache buster"
  [{config :cljs-simple-cache-buster :as project} & args]
  (let [cljsbuild-id (flatten (vector (:cljsbuild-id config)))]
    (when (and (seq args)
               (= (count args) 2)
               (= (first args) "once")
               (some #(= (second args) %) cljsbuild-id))
      (busting project))))
