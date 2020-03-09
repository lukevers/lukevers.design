(ns app.components.button
  "Button")

(defn button [text href on-click]
  [:button
   [:a {:href href :on-click on-click} text]])