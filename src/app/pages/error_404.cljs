(ns app.pages.error-404
  "Page Not Found")

(defn page-not-found []
  [:<>
   [:p "Page not found!"]
   [:a {:href "/p/monstera"} "about page"]])
