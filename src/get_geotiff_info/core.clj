(ns get-geotiff-info.core
  (:gen-class :main true)
  (:require [geotiff.core :as geo]))

(defn run-this [arg]
  (let [reader (geo/get-reader arg)
        metadata (geo/get-metadata reader)
        rootNode (geo/get-root-node metadata)
        [_ _ _ x1 y1 _] (geo/get-model-tie-points rootNode)
        [scale-x scale-y _] (geo/get-model-pixel-scales rootNode)
        [width height] (try (geo/get-dimensions reader) (catch Exception e))
        ]
    (println {:top y1 :left x1 :bottom (- y1 (* height scale-y)) :right (+ x1 (* width scale-x))})))

(defn -main [& args]
  (do
    (run-this (first args))))
