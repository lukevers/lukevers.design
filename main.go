package main

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"path/filepath"
	"strings"

	"github.com/go-chi/chi"
	"github.com/go-chi/chi/middleware"
)

func main() {
	// Index the manifest files
	err := indexProjects()
	if err != nil {
		log.Fatal("Could not index projects:", err)
	}

	// Create the router, middleware stack, and setup routing.
	r := chi.NewRouter()

	r.Use(middleware.RequestID)
	r.Use(middleware.RealIP)
	r.Use(middleware.Logger)
	r.Use(middleware.Recoverer)
	r.Use(middleware.Compress(5))

	r.Route("/", func(root chi.Router) {
		root.Get("/*", func(w http.ResponseWriter, r *http.Request) {
			p := filepath.Join("public", filepath.Clean(r.URL.Path))
			_, file := filepath.Split(p)

			// Serve the manifest JSON for projects where applicable
			if strings.Contains(p, "/projects") && strings.HasSuffix(file, ".json") {
				if manifest, ok := Manifests[file[0:len(file)-5]]; ok {
					w.Header().Set("Content-Type", "application/json")
					w.Write(manifest.JSON())
					return
				}
			}

			// Serve files
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

	log.Fatal(http.ListenAndServe(":3333", r))
}

// The function indexProjects loops through every project in the repository,
// reads the `manifest.json` files, and generates an internal index to use for
// the API.
func indexProjects() error {
	files, err := ioutil.ReadDir("projects")
	if err != nil {
		return err
	}

	for _, file := range files {
		if !file.IsDir() {
			continue
		}

		contents, err := ioutil.ReadFile(
			filepath.Join(
				"projects",
				file.Name(),
				"manifest.json",
			),
		)

		if err != nil {
			return err
		}

		var manifest Manifest
		err = json.Unmarshal(contents, &manifest)
		if err != nil {
			return err
		}

		Manifests[manifest.URL()] = manifest
	}

	return nil
}
