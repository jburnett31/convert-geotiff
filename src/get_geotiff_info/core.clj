(ns get-geotiff-info.core
  (:gen-class)
  (:require [geotiff.core :as geo]))

(defn -main
  "I don't do a whole lot."
  [& args]
  (let [fpath (first args)
        reader (geo/get-reader fpath)
        metadata (geo/get-metadata reader)
        rootNode (geo/get-root-node metadata)
        [_ _ _ x1 y1 _] (geo/get-model-tie-points rootNode)
        [scale-x scale-y _] (geo/get-model-pixel-scales rootNode)
        [width height] (try (geo/get-dimensions reader) (catch Exception e))
        ]
    (println x1 y1 scale-x scale-y width height)
    (println {:top y1 :left x1 :bottom (- y1 (* height scale-y)) :right (+ x1 (* width scale-x))})))
