(defproject clojure-noob "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  ;TODO: what is this:
  :main ^:skip-aot clojure-noob.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
