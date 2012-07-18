(ns convert-geotiff.models.raster
  (:require clj-record.boot
            [clj-record.validation.built-ins :as valid])
  (:use convert-geotiff.models.config))

(clj-record.core/init-model
 (:validation
  (:name "empty!" #(not (empty? %)))
  (:name "starts with whitespace!" (valid/non-match #"^\s"))
  (:name "ends with whitespace!" (valid/non-match #"\s$"))
  (:image-path "empty!" #(not (empty? %)))
  (:extent "emtpy!" #(not (empty? %)))
  (:timestamp "empty!" #(not (empty? %)))))
