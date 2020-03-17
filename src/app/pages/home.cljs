(ns app.pages.home
  "Home Page"
  (:require [reagent.core :as reagent]
            [reagent.session :as session]
            [app.components.breadcrumbs :refer [breadcrumbs]]
            [app.components.button :refer [button]]
            [app.pages.error-404 :refer [page-not-found]]
            [app.pages.error :refer [page-error]]
            [app.util.api :refer [fetch]]))

;; Rendered page while loading.
(defn loading [state] ())

;; Rendered page after load+success
(defn page [state]
  [:ul.container.collection
      (map (fn [collection]
             [:li.collection {:key collection}
              [:a.pill {:href (str "/collections/" collection)}
               (str "THE " collection " COLLECTION")]]) @state)])


;; Conditional rendering based on state
(defn page-home []
  (let [state (reagent/atom {:loading true})]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (fetch
         (str "/homepage.json")
         (fn [data] (reset! state data))
         (fn [details] ())))

      :reagent-render
      (fn []
        (cond
          (= true (get-in @state [:loading])) (loading state)
          (= 404 (get-in @state [:error])) (page-not-found)
          (not= nil (get-in @state [:error])) (page-error)
          :else (page state)))})))