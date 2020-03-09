(ns app.router
  (:require [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [app.pages.home :refer [page-home]]
            [app.pages.pdp :refer [page-pdp]]))

(defn current-page []
  [(session/get :current-page)])

(secretary/defroute "/" []
  (session/put! :current-page page-home))

(secretary/defroute "/projects/:product" [product]
  (session/put! :product product)
  (session/put! :current-page page-pdp))