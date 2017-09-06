# Date Format Tool

This tool lets you create date formatting strings for various systems using a natural example instead of looking up placeholder characters in a documentation. These formats are supported:

- [Moment.js](http://momentjs.com/docs/#/displaying/)
- [Google Closure Library](https://google.github.io/closure-library/api/goog.i18n.DateTimeFormat.html)
- [Unix date](https://linux.die.net/man/1/date)

It is written in ClojureScript using [re-frame](https://github.com/Day8/re-frame).

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

### Run tests:

```
lein clean
lein doo phantom test once
```

The above command assumes that you have [phantomjs](https://www.npmjs.com/package/phantomjs) installed. However, please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many other JS environments (chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
