;; author helio frota
(ns org.wildfly.swarm.examples.clojure.core
  (:import (javax.ws.rs Path Produces GET)))

(definterface IClojureResource
  (get []))

(deftype ^{Path "/"} ClojureResource []
  IClojureResource
  (^{GET true
     Produces ["text/plain"]}
  get [this] (format "Hail clojure wildfly swarm !")))
