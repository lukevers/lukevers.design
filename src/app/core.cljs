(ns app.core
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [app.router :as router])
  (:import goog.history.Html5History
           goog.Uri))

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
        (let [path (.getPath (.parse Uri (.-href (.-target e))))
              title (.-title (.-target e))]
          (when (secretary/locate-route path)
            ((. e preventDefault) (. history (setToken path title)))))))))

(hook-browser-navigation!)

(defn ^:export main []
  (reagent/render-component [router/current-page] (.getElementById js/document "app")))