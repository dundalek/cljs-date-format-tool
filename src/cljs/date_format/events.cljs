(ns date-format.events
    (:require [re-frame.core :refer [reg-event-db]]
              [date-format.db :as db]))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-db
 :set-template
  (fn [db [_ template]]
   (assoc db :template template)))
