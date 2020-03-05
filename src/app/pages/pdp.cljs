(ns app.pages.pdp
  (:require [reagent.core :as r]
            [reagent.session :as session]))

(defn click-counter [click-count]
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(def click-count (r/atom 0))

(defn page-pdp []
  (js/console.log (str "Product: " (session/get :product)))
  [:<>
   [:p "Hello, lukevers.design is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]])
