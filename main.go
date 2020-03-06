package main

import (
	"net/http"
	"os"
	"path/filepath"

	"github.com/go-chi/chi"
	"github.com/go-chi/chi/middleware"
)

func main() {
	r := chi.NewRouter()

	r.Use(middleware.RequestID)
	r.Use(middleware.RealIP)
	r.Use(middleware.Logger)
	r.Use(middleware.Recoverer)
	r.Use(middleware.Compress(5))

	r.Route("/", func(root chi.Router) {
		root.Get("/*", func(w http.ResponseWriter, r *http.Request) {
			p := filepath.Join("public", filepath.Clean(r.URL.Path))

			if info, err := os.Stat(p); err != nil {
				http.ServeFile(w, r, filepath.Join("public", "index.html"))
				return
			} else if info.IsDir() {
				http.ServeFile(w, r, filepath.Join("public", "index.html"))
				return
			}

			http.ServeFile(w, r, p)
		})
	})

	http.ListenAndServe(":3333", r)
}
