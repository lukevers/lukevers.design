(ns app.pages.pdp
  (:require [reagent.core :as reagent]
            [reagent.session :as session]
            [app.util.api :refer [fetch]]))

(defn reduce-data [data]
  {:name (get-in data [:name])})

(defn page-pdp []
  (let [state (reagent/atom {})]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (fetch
         (str "/data/" (session/get :product) ".json")
         (fn [data] (reset! state (reduce-data data)))))

      :reagent-render
      (fn []
        [:<>
         [:div "yo"]
         [:h3 (get-in @state [:name])]])})))