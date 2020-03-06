package main

import (
	"encoding/json"
	"fmt"
	"strings"
)

// Manifests is an in memory map/index of the project manifests in the
// repository. The key is `%s-%s` where the first parameter is the name,
// the second parameter is the type, and both parameters are sluggified.
var Manifests = make(map[string]Manifest)

// Manifest is the object structure of what to expect for each project
// manifest JSON file.
type Manifest struct {
	Name string `json:"name"`
	Type string `json:"type"`
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

// JSON converts the manifest from an object to JSON.
func (m Manifest) JSON() []byte {
	contents, err := json.Marshal(m)
	if err != nil {
		return []byte(`{"error": "internal server error"}`)
	}

	return contents
}
