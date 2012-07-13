(ns get-geotiff-info.core
  (:gen-class)
  (:require [geotiff :as geo]))

(defn -main
  "I don't do a whole lot."
  [& args]
  (let [metadata (geo/get-metadata (first args))
        rootNode (geo/get-root-node metadata)
        [_ _ _ x1 y1 _] (geo/get-model-tie-points rootNode)
        [scale-x scale-y _] (geo/get-pixel-scales)]
    ))
