package main

import (
	"encoding/json"
)

// JSON converts the manifest from an object to JSON.
func JSON(i interface{}) []byte {
	contents, err := json.Marshal(i)
	if err != nil {
		return []byte(`{"error": "internal server error"}`)
	}

	return contents
}
