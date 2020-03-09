(ns app.router
  (:require [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [app.components.nav :refer [nav]]
            [app.pages.home :refer [page-home]]
            [app.pages.pcp :refer [page-pcp]]
            [app.pages.pdp :refer [page-pdp]]))

(defn current-page []
  [:<>
   (nav)
   [(session/get :current-page)]])

(secretary/defroute "/" []
  (session/put! :current-page page-home))

(secretary/defroute "/projects/:product" [product]
  (session/put! :product product)
  (session/put! :current-page page-pdp))

(secretary/defroute "/collections/:collection" [collection]
  (session/put! :collection collection)
  (session/put! :collection-type "collections")
  (session/put! :current-page page-pcp))

(secretary/defroute "/tags/:collection" [collection]
  (session/put! :collection collection)
  (session/put! :collection-type "tags")
  (session/put! :current-page page-pcp))