(ns app.pages.pdp
  "PDP"
  (:require [reagent.core :as reagent]
            [reagent.session :as session]
            [app.util.api :refer [fetch]]
            [app.pages.error-404 :refer [page-not-found]]
            [app.pages.error :refer [page-error]]))

;; PDP data reducer
(defn reduce-data [data]
  {:error (get-in data [:error])
   :name (get-in data [:name])})

;; Rendered page while loading.
(defn loading [state] ())

;; Rendered page after load+success
(defn page [state]
  [:<>
   [:div "yo"]
   [:h3 (get-in @state [:name])]])

;; Conditional rendering based on state
(defn page-pdp []
  (let [state (reagent/atom {:loading true})]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (fetch
         (str "/projects/" (session/get :product) ".json")
         (fn [data] (reset! state (reduce-data data)))
         (fn [details]
           (if (= (get-in details [:status]) 404)
             (reset! state (reduce-data {:error 404})) "b"
             ))))

      :reagent-render
      (fn []
        (cond
          (= true (get-in @state [:loading])) (loading state)
          (= 404 (get-in @state [:error])) (page-not-found)
          (not= nil (get-in @state [:error])) (page-error)
          :else (page state)))})))