(ns convert-geotiff.core
  (:gen-class :main true)
  (:require [geotiff.core :as geo]
            [convert-geotiff.models.raster :as raster]
            [clojure.string :as string])
  (:import [java.awt.image BufferedImage]
           [javax.imageio ImageIO])
  (:use [im4clj.core :only (convert)]
        clojure.java.io
        convert-geotiff.models.config
        [clojure.data.json :only (json-str)]))

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

(defn- format-date [filename]
  (let [year (apply str (take 4 filename))
        month (apply str (take 2 (drop 5 filename)))
        day (apply str (take 2 (drop 7 filename)))]
    (format "%s-%s-%s" year month day)))

(defn get-extent [filepath]
  (let [reader (geo/get-reader filepath)
        metadata (geo/get-metadata reader)
        rootNode (geo/get-root-node metadata)
        [_ _ _ x1 y1 _] (geo/get-model-tie-points rootNode)
        [scale-x scale-y _] (geo/get-model-pixel-scales rootNode)
        [width height] (try (geo/get-dimensions reader) (catch Exception e))
        ]
    {:top y1 :left x1 :bottom (- y1 (* height scale-y)) :right (+ x1 (* width scale-x))}
    ))

(defn create-db-record [filepath]
  (let [[image_path name] (split-filepath filepath)
        timestamp (format-date name)
        extent (json-str (get-extent filepath))]
    (raster/create {:image_path image_path :name name :timestamp timestamp :extent extent})))

(defn convert-to-jpg [filepath]
  (let [new-name (change-extension filepath "jpg")]
    (println new-name)
    (convert filepath new-name)
    ))

(defn -main [& args]
  (let [filepath (first args)]
    (convert-to-jpg filepath)
    (create-db-record filepath)))
