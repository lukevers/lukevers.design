(ns app.components.breadcrumbs
  "Breadcrumbs")

(defn breadcrumb [item base-path]
  [:li {:key item}
   [:a {:href (str base-path item)} item]])

(defn breadcrumbs [items base-path]
  [:ul.breadcrumbs
   (map (fn [item] (breadcrumb item base-path)) items)])