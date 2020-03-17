package main

import (
	"fmt"
	"strings"
)

// Manifests is an in memory map/index of the project manifests in the
// repository. The key is `%s-%s` where the first parameter is the name,
// the second parameter is the type, and both parameters are sluggified.
var Manifests = make(map[string]Manifest)

// ...
var IndexTags = make(map[string][]Manifest)

// ...
var IndexCollections = make(map[string][]Manifest)

// ...
var HomepageKeys []string

// Manifest is the object structure of what to expect for each project
// manifest JSON file.
type Manifest struct {
	Name        string   `json:"name"`
	Type        string   `json:"type"`
	Description string   `json:"description"`
	Breadcrumbs []string `json:"breadcrumbs"`
	Tags        []string `json:"tags"`
	Related     []string `json:"related"`
	Collections []string `json:"collections"`
	Salable     bool     `json:"salable"`
}

// URL generates the slug-like key for the manifest.
func (m Manifest) URL() string {
	return strings.ReplaceAll(
		fmt.Sprintf(
			"%s-%s",
			strings.ToLower(m.Name),
			strings.ToLower(m.Type),
		),
		" ",
		"-",
	)
}
