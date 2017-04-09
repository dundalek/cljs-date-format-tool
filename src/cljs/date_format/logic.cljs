(ns date-format.logic)

(def fmts [{"key" "x" "val" "760255689006"} {"key" "X" "val" "760255689"} {"key" "SSSSSSSSS" "val" "006000000"} {"key" "MMMM" "val" "February"} {"key" "dddd" "val" "Thursday"} {"key" "SSSSSSSS" "val" "00600000"} {"key" "SSSSSSS" "val" "0060000"} {"key" "SSSSSS" "val" "006000"} {"key" "Z" "val" "+01:00"} {"key" "SSSSS" "val" "00600"} {"key" "ZZ" "val" "+0100"} {"key" "YYYY" "val" "1994"} {"key" "DDDo" "val" "34th"} {"key" "SSSS" "val" "0060"} {"key" "Mo" "val" "2nd"} {"key" "MMM" "val" "Feb"} {"key" "Qo" "val" "1st"} {"key" "Do" "val" "3rd"} {"key" "DDDD" "val" "034"} {"key" "do" "val" "4th"} {"key" "ddd" "val" "Thu"} {"key" "wo" "val" "6th"} {"key" "Wo" "val" "5th"} {"key" "SSS" "val" "006"} {"key" "DDD" "val" "34"} {"key" "YY" "val" "94"} {"key" "MM" "val" "02"} {"key" "DD" "val" "03"} {"key" "dd" "val" "Th"} {"key" "ww" "val" "06"} {"key" "WW" "val" "05"} {"key" "A" "val" "AM"} {"key" "a" "val" "am"} {"key" "HH" "val" "07"} {"key" "mm" "val" "08"} {"key" "ss" "val" "09"} {"key" "SS" "val" "00"} {"key" "or" "val" "or"} {"key" "S" "val" "0"} {"key" "Q" "val" "1"} {"key" "M" "val" "2"} {"key" "D" "val" "3"} {"key" "d" "val" "4"} {"key" "W" "val" "5"} {"key" "w" "val" "6"} {"key" "H" "val" "7"} {"key" "m" "val" "8"} {"key" "s" "val" "9"}])

(defn find-match [str out]
  (loop [[x & t] fmts]
    (cond
      (nil? x)
      [(subs str 1) (conj out (first str))]
      (and
        (>= (count str) (count (x "val")))
        (=
          (subs str 0 (count (x "val")))
          (x "val")))
      [(subs str (count (x "val")))
       (conj out (x "key"))]
      :default (recur t))))

(defn twelve-hour-heuristic [tokens]
  (if (some #(or (= % "a") (= % "A")) tokens)
    (map #(cond
           (= % "H") "h"
           (= % "HH") "hh"
           :default %)
        tokens)
    tokens))

(defn guesstimate [input]
  (loop [[s res] [input []]]
     (if (empty? s)
        (apply str (twelve-hour-heuristic res))
        (recur (find-match s res)))))

; (guesstimate "03.02.1994 07:08")
