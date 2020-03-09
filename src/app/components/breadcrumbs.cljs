(ns app.components.breadcrumbs
  "Breadcrumbs")

(defn breadcrumb [item]
  [:li
   [:a {:href (str "/" item)} item]])

(defn breadcrumbs [items]
  [:ul.breadcrumbs (map breadcrumb items)])