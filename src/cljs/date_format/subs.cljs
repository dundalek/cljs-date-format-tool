(ns date-format.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [reg-sub subscribe]]
            [date-format.logic :refer [guesstimate]]))

(reg-sub
 :template
 (fn [db]
   (:template db)))

(reg-sub
 :presets
 (fn [db]
   (:presets db)))

; (reg-sub
;  :format
;  (fn [query-v _]
;    (subscribe [:template]))
;  (fn [template query-v _]
;    (guesstimate template)))
