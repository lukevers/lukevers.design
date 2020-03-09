(ns app.pages.home
  "Home Page")

(defn page-home []
  [:<>
   [:p "Hello, app is running!"]
   [:a {:href "/projects/monstera-pcb"} "Monstera PCB"]])
