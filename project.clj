(defproject convert-geotiff "0.1.0-alpha1"
  :description "Converts geotiff to png and stores extent info in a database"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [geotiff "0.1.1-alpha2"]
                 [im4clj "0.0.1-SNAPSHOT"]
                 [clj-record "1.1.3"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [org.clojure/data.json "0.1.2"]]
  :main convert-geotiff.core)
