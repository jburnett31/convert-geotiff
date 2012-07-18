(ns convert-geotiff.models.migration
  (:require [clj-record.core :as core]
            [clojure.java.jdbc :as sql])
  (:use convert-geotiff.models.config))

(def table-specs
  {:rasters
   [[:id "SERIAL UNIQUE PRIMARY KEY"]
    [:image_path "VARCHAR(255)" "NOT NULL"]
    [:name "VARCHAR(255)" "NOT NULL"]
    [:extent "VARCHAR(255)" "NOT NULL"]
    [:timestamp "DATE"]
    :table-spec "Engine=InnoDB"]})

(defn drop-tables
  ([] (drop-tables (keys table-specs)))
  ([tables]
     (try
       (doseq [table tables]
         (sql/drop-table table))
       (catch Exception e))))

(defn create-tables
  ([] (create-tables (keys table-specs)))
  ([tables]
     (doseq [table-pair (select-keys table-specs tables)]
       (apply sql/create-table (first table-pair) (second table-pair)))))

(defn setup-db []
  (println "Creating tables")
  (sql/with-connection db
    (sql/transaction
     (create-tables)))
  (println "Tables successfully created"))
