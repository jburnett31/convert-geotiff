(defproject get-geotiff-info "0.1.0"
  :description "Gets the extent of a geotiff image"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
				 [geotiff "0.1.1"]
                 [im4clj "0.0.1-SNAPSHOT"]
                 [clj-record "1.1.3"]
			     [org.clojure/java.jdbc "0.2.3"]
			     [mysql/mysql-connector-java "5.1.6"]]
  :main get-geotiff-info.core)
