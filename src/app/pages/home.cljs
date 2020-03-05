(ns app.pages.home
  "Home Page")

(defn page-home []
  [:<>
   [:p "Hello, lukevers.design is running!"]
   [:a {:href "/p/monstera"} "about page"]])
