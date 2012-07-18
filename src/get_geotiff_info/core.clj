(ns get-geotiff-info.core
  (:gen-class :main true)
  (:require [geotiff.core :as geo]
            [get-geotiff-info.models.raster :as raster]
            [clojure.string :as string])
  (:import [java.awt.image BufferedImage]
           [javax.imageio ImageIO])
  (:use [im4clj.core :only (convert)]
        clojure.java.io
        get-geotiff-info.models.config))

(def img-directory "/home/jeff/Documents/geotiff/")

(defn- change-extension [filename new-ext]
  (let [temp (string/split filename #"\.")
        name (string/join \. (butlast temp))]
    (string/join \. [name new-ext])))

(defn- split-filepath [filepath]
  (let [temp (string/split filepath (re-pattern (System/getProperty "file.separator")))
        directory (string/join (System/getProperty "file.separator") (butlast temp))
        filename (last temp)]
    [directory filename]))

(defn run-this [arg]
  (let [reader (geo/get-reader arg)
        metadata (geo/get-metadata reader)
        rootNode (geo/get-root-node metadata)
        [_ _ _ x1 y1 _] (geo/get-model-tie-points rootNode)
        [scale-x scale-y _] (geo/get-model-pixel-scales rootNode)
        [width height] (try (geo/get-dimensions reader) (catch Exception e))
        extent {:top y1 :left x1 :bottom (- y1 (* height scale-y)) :right (+ x1 (* width scale-x))}
        ]
    (println extent)
    (with-open [wrtr (writer (change-extension arg "txt"))]
      (.write wrtr (str extent)))
    ))

(defn convert-to-jpg [filepath]
  (let [new-name (change-extension filepath "jpg")]
    (println new-name)
    (convert filepath new-name)
    ))

(defn -main [& args]
  (do
    (run-this (first args))
    (convert-to-jpg (first args))))
