(ns date-format.events
    (:require [re-frame.core :as re-frame]
              [date-format.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
