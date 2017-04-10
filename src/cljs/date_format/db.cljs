(ns date-format.db)

(def presets
  ["02/03/1994 7:08 AM" "1994-02-03 07:08:09" "03.02.1994 07:08:09" "Thursday 3rd of February 1994" "7 hours 8 minutes 9.006 seconds in the morning" "1st quarter, 6th week of the year"])

(def default-db
  {:template (first presets)
   :presets presets})
