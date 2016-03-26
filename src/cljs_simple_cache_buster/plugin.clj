(ns cljs-simple-cache-buster.plugin
  (:require [leiningen.cljs-simple-cache-buster :as buster]
            [leiningen.cljsbuild :as cljs]
            [robert.hooke :as hooke]))

(defn cljsbuild-hook [f project & args]
  (apply buster/cljs-simple-cache-buster project args)
  (apply f project args))

(defn hooks []
  (hooke/add-hook #'cljs/cljsbuild #'cljsbuild-hook))
