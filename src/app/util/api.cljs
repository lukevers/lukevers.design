(ns app.util.api
  (:require [ajax.core :as ajx]))

(defn fetch "Fetches data from the API"
  [endpoint callback error-callback]
  (ajx/GET endpoint
    {:handler callback
     :error-handler
     (fn [details]
       (.warn js/console (str "Failed to fetch: " details))
       (error-callback details))
     :response-format :json, :keywords? true}))