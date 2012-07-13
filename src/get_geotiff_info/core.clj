(ns get-geotiff-info.core
  (:gen-class)
  (:require [geotiff :as geo])
  (:import [java.io File]
           [javax.imageio ImageReader ImageIO]))

(defn get-dimensions [filepath]
  (let [image-file (File. filepath)
        iis (ImageIO/createImageInputStream file)
        readers (ImageIO/getImageReadersByFormatName "tiff")
        reader (.next readers)]
    (.setInput reader iis)
    [(.getWidth reader 0) (.getHeight reader 0)]))

(defn -main
  "I don't do a whole lot."
  [& args]
  (let [metadata (geo/get-metadata (first args))
        rootNode (geo/get-root-node metadata)
        [_ _ _ x1 y1 _] (geo/get-model-tie-points rootNode)
        [scale-x scale-y _] (geo/get-pixel-scales)
        [width height] (get-dimensions (first args))]
    {:top y1 :left x1 :bottom (- y1 (* height scale-y)) :right (+ x1 (* width scale-x))}))
