(ns app.components.card
  "Card")
  ; (:require
  ;  [secretary.core :as secretary :include-macros true]))

(defn card [title type href]
  [:a.card {:key (str title " " type) :href href}
    [:img {:src "https://placehold.co/500x500"}]
    [:div.wrap
     [:h4 (str title " " type)]]])