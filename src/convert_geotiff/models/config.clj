(ns convert-geotiff.models.config
  (:require [clojure.java.jdbc :as sql]))

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :user "root"
         :password "geotree123"
         :subname "//127.0.0.1:3306/geotiff"})
