(ns date-format.logic)

(def moment-formats [{"key" "x" "val" "760255689006"} {"key" "X" "val" "760255689"} {"key" "SSSSSSSSS" "val" "006000000"} {"key" "MMMM" "val" "February"} {"key" "dddd" "val" "Thursday"} {"key" "SSSSSSSS" "val" "00600000"} {"key" "SSSSSSS" "val" "0060000"} {"key" "SSSSSS" "val" "006000"} {"key" "Z" "val" "+01:00"} {"key" "SSSSS" "val" "00600"} {"key" "ZZ" "val" "+0100"} {"key" "YYYY" "val" "1994"} {"key" "DDDo" "val" "34th"} {"key" "SSSS" "val" "0060"} {"key" "Mo" "val" "2nd"} {"key" "MMM" "val" "Feb"} {"key" "Qo" "val" "1st"} {"key" "Do" "val" "3rd"} {"key" "DDDD" "val" "034"} {"key" "do" "val" "4th"} {"key" "ddd" "val" "Thu"} {"key" "wo" "val" "6th"} {"key" "Wo" "val" "5th"} {"key" "SSS" "val" "006"} {"key" "DDD" "val" "34"} {"key" "YY" "val" "94"} {"key" "MM" "val" "02"} {"key" "DD" "val" "03"} {"key" "dd" "val" "Th"} {"key" "ww" "val" "06"} {"key" "WW" "val" "05"} {"key" "A" "val" "AM"} {"key" "a" "val" "am"} {"key" "HH" "val" "07"} {"key" "mm" "val" "08"} {"key" "ss" "val" "09"} {"key" "SS" "val" "00"} {"key" "or" "val" "or"} {"key" "S" "val" "0"} {"key" "Q" "val" "1"} {"key" "M" "val" "2"} {"key" "D" "val" "3"} {"key" "d" "val" "4"} {"key" "W" "val" "5"} {"key" "w" "val" "6"} {"key" "H" "val" "7"} {"key" "m" "val" "8"} {"key" "s" "val" "9"}])

(def closure-formats [{"key" "GGGG" "val" "Anno Domini"} {"key" "QQQQ" "val" "1st quarter"} {"key" "ZZZZ" "val" "GMT+00:00"} {"key" "MMMM" "val" "February"} {"key" "EEEE" "val" "Thursday"} {"key" "v" "val" "Etc/GMT"} {"key" "Z" "val" "+0000"} {"key" "y" "val" "1994"} {"key" "SSSS" "val" "0060"} {"key" "MMM" "val" "Feb"} {"key" "E" "val" "Thu"} {"key" "SSS" "val" "006"} {"key" "z" "val" "UTC"} {"key" "yy" "val" "94"} {"key" "G" "val" "AD"} {"key" "Q" "val" "Q1"} {"key" "MM" "val" "02"} {"key" "ww" "val" "06"} {"key" "dd" "val" "03"} {"key" "a" "val" "AM"} {"key" "HH" "val" "07"} {"key" "mm" "val" "08"} {"key" "ss" "val" "09"} {"key" "SS" "val" "01"} {"key" "S" "val" "0"} {"key" "M" "val" "2"} {"key" "d" "val" "3"} {"key" "c" "val" "4"} {"key" "w" "val" "6"} {"key" "H" "val" "7"} {"key" "m" "val" "8"} {"key" "s" "val" "9"} {"key" "MMMMM" "val" "F"} {"key" "ccccc" "val" "T"}])

(def unix-date-formats [{"key" "%c" "val" "Thu 03 Feb 1994 07:08:09 AM UTC"} {"key" "%X" "val" "07:08:09 AM"} {"key" "%F" "val" "1994-02-03"} {"key" "%x" "val" "02/03/1994"} {"key" "%s" "val" "760259289"} {"key" "%N" "val" "006000000"} {"key" "%A" "val" "Thursday"} {"key" "%B" "val" "February"} {"key" "%D" "val" "02/03/94"} {"key" "%T" "val" "07:08:09"} {"key" "%R" "val" "07:08"} {"key" "%z" "val" "+0000"} {"key" "%Y" "val" "1994"} {"key" "%a" "val" "Thu"} {"key" "%h" "val" "Feb"} {"key" "%j" "val" "034"} {"key" "%Z" "val" "UTC"} {"key" "%C" "val" "19"} {"key" "%y" "val" "94"} {"key" "%d" "val" "03"} {"key" "%H" "val" "07"} {"key" "%m" "val" "02"} {"key" "%M" "val" "08"} {"key" "%p" "val" "AM"} {"key" "%P" "val" "am"} {"key" "%S" "val" "09"} {"key" "%V" "val" "05"} {"key" "%e" "val" "3"} {"key" "%w" "val" "4"} {"key" "%-H" "val" "7"} {"key" "%-M" "val" "8"} {"key" "%-S" "val" "9"} {"key" "%%" "val" "%"}])

(defn find-match [str out fmts]
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

(defn guesstimate [input fmts]
  (loop [[s res] [input []]]
     (if (empty? s)
        (apply str (twelve-hour-heuristic res))
        (recur (find-match s res fmts)))))
