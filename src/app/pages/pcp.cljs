(ns app.pages.pcp
  "PCP"
  (:require [reagent.core :as reagent]
            [reagent.session :as session]
            [app.components.card :refer [card]]
            [app.pages.error-404 :refer [page-not-found]]
            [app.pages.error :refer [page-error]]
            [app.util.api :refer [fetch]]))

;; PCP data reducer
(defn reduce-data [data]
  {:error (get-in data [:error])
   :name (get-in data [:name])
   :type (get-in data [:type])
   :description (get-in data [:description])
   :breadcrumbs (get-in data [:breadcrumbs])})

(defn get-link [c]
  (.toLowerCase
   (.replace
    (str "/projects/"
         (get-in c [:name]) "-"
         (get-in c [:type])) " " "-")))

;; Rendered page while loading.
(defn loading [state] ())

;; Rendered page after load+success
(defn page [state]
  [:div.container
   [:div.cards
    (map (fn [c]
           (card (get-in c [:name])
                 (get-in c [:type])
                 (get-link c))) @state)]])

;; Conditional rendering based on state
(defn page-pcp []
  (let [state (reagent/atom {:loading true})]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (fetch
         (str "/" (session/get :collection-type) "/" (session/get :collection) ".json")
         (fn [data] (reset! state (map reduce-data data)))
         (fn [details]
           (if (= (get-in details [:status]) 404)
             (reset! state (reduce-data {:error 404})) ()))))

      :reagent-render
      (fn []
        (cond
          (= true (get-in @state [:loading])) (loading state)
          (= 404 (get-in @state [:error])) (page-not-found)
          (not= nil (get-in @state [:error])) (page-error)
          :else (page state)))})))