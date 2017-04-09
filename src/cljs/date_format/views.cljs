(ns date-format.views
    (:require [re-frame.core :refer [subscribe dispatch]]))

(defn render-option [option checked]
  [:div {:key option}
    [:label
      [:input {:type "radio" :checked checked :on-change #(dispatch [:set-template option])}]
      " "
      option]])

(defn main-panel []
  (let [template (subscribe [:template])
        presets (subscribe [:presets])
        format (subscribe [:format])]
    (fn []
      [:div
        [:nav.navbar.navbar-default
          [:div.container-fluid
            [:div.navbar-header
              [:div.navbar-brand "Date Format Tool"]]]]
        [:div.container
          [:p
            "This tool lets you create date formatting strings for "
            [:a {:href "http://momentjs.com/docs/#/displaying/"} "Moment.js"]
            " using a natural example instead of looking up placeholder characters in a documentation."]
          [:div.panel.panel-default
            [:div.panel-heading "Example date"]
            [:div.panel-body
              "Monday 3rd of February 1994"
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
            [:div.panel-heading "Moment.js format"]
            [:div.panel-body [:pre @format]]]]])))
