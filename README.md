# cljs-simple-cache-buster

A simple cache buster for [lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild). This plugin will append a timestamp fingerprint as a query string after the assets filename.

For a more sophisticated cache busting solution, check out [Optimus](https://github.com/magnars/optimus).

## Setup

via Leiningen:

    :plugins [[cljs-simple-cache-buster "0.2.0"]]

## Configurations

You can supply the configuration inside a `:cljs-simple-cache-buster` map like so:

```
:cljs-simple-cache-buster {:cljsbuild-id "min"
                           :template-file "resources/template/index.html"
                           :output-to "resources/public/index.html"}
```

All configuration options can be a single string or a vector of strings, so you can do something like:

```
:cljs-simple-cache-buster {:cljsbuild-id ["min" "dev"]
                           :template-file ["resources/template/index.html"
                                           "resources/template/about.html"]
                           :output-to ["resources/public/index.html"
                                       "resources/public/about.html"]}
```

## Template file

The plugin will use the template file to find the location to put the fingerprint. Append `{{ fingerprint }}` as the query string for the assets path, for example:

    <script type="text/javascript" src="js/compiled/myapp.js?v={{ fingerprint }}"></script>

and

    <link href="css/style.css?v={{ fingerprint }}" rel="stylesheet" type="text/css">

## Fingerprint method

By default, the fingerprint uses the timestamp the moment you compile your ClojureScript app using `lein-cljsbuild`. To supply your own custom value, supply the string value to `:fingerprint` option in the configuration.

## License

Copyright © 2016 Burhanuddin Baharuddin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
