(ns app.core
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [app.router :as router])
  (:import goog.history.Html5History
           goog.Uri))

(defn findParentLink [element]
  (if (or (= element nil) (= (.toUpperCase (.-tagName element)) "A"))
    element
    (findParentLink (.-parentNode element))))

(defn hook-browser-navigation! []
  (let [history (doto (Html5History.)
                  (events/listen
                    EventType/NAVIGATE
                    (fn [event]
                      (secretary/dispatch! (.-token event))))
                  (.setUseFragment false)
                  (.setPathPrefix "")
                  (.setEnabled true))]
    
    (events/listen js/document "click"
      (fn [e]
        (try
          (let [target (findParentLink (.-target e))
                path (.getPath (.parse Uri (.-href target)))
                title (.-title (.-target target))]
            (when (secretary/locate-route path)
              ((. e preventDefault) (. history (setToken path title)))))
          (catch js/Object _ nil))))))

(hook-browser-navigation!)

(defn ^:export main []
  (reagent/render-component [router/current-page] (.getElementById js/document "app")))