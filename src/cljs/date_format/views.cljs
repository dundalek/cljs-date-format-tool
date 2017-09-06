(ns date-format.views
    (:require [re-frame.core :refer [subscribe dispatch]]
              [date-format.logic :refer [guesstimate moment-formats closure-formats unix-date-formats]]))

(defn render-option [option checked]
  [:div.radio {:key option}
    [:label
      [:input {:type "radio" :checked checked :on-change #(dispatch [:set-template option])}]
      " "
      option]])

(defn main-panel []
  (let [template (subscribe [:template])
        presets (subscribe [:presets])]
    (fn []
      [:div
        [:nav.navbar.navbar-default
          [:div.container-fluid
            [:div.navbar-header
              [:div.navbar-brand "Date Format Tool"]]]]
        [:div.container
          [:p
            "This tool lets you create date formatting strings for various libraries using a natural example instead of looking up placeholder characters in a documentation."
            [:br]
            "Check out the "
            [:a {:href "https://github.com/dundalek/cljs-date-format-tool"} "source code"]
            "."]
          [:div.panel.panel-default
            [:div.panel-heading "Example date"]
            [:div.panel-body
              "Thursday 3rd of February 1994"
              [:br]
              "7 hours 8 minutes 9.006 seconds in the morning"
              [:br]
              "1st quarter, 6th week of the year"]]
          [:div.panel.panel-default
            [:div.panel-heading "Desired format"]
            [:div.panel-body [:input.form-control {:type "text" :value @template :on-change #(dispatch [:set-template (-> % .-target .-value)])}]]]
          [:div.panel.panel-default
            [:div.panel-heading "Preset formats and examples"]
            [:div.panel-body (map #(render-option % (= @template %)) @presets)]]
          [:div.panel.panel-default
            [:div.panel-heading "Format strings"]
            [:div.panel-body
              [:p [:a {:href "https://momentjs.com/docs/#/displaying/"} "Moment.js"]]
              [:pre (guesstimate @template moment-formats)]
              [:br]
              [:p [:a {:href "https://google.github.io/closure-library/api/goog.i18n.DateTimeFormat.html"} "Google Closure Library"]]
              [:pre (guesstimate @template closure-formats)]
              [:br]
              [:p [:a {:href "https://linux.die.net/man/1/date"} "Unix date"]]
              [:pre (guesstimate @template unix-date-formats)]]]]])))
