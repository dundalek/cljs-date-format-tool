(ns date-format.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [date-format.logic :refer [find-match twelve-hour-heuristic guesstimate moment-formats]]))

(deftest test-logic
  (testing "find-match"
    (is (= ["" ["HH"]] (find-match "07" [] moment-formats)))
    (is (= ["x" ["prev" "HH"]] (find-match "07x" ["prev"] moment-formats)))
    (is (= ["07" ["x"]] (find-match "x07" [] moment-formats))))

  (testing "twelve-hour-heuristic"
    (is (= ["h" "a"] (twelve-hour-heuristic ["H" "a"])))
    (is (= ["hh" "A"] (twelve-hour-heuristic ["HH" "A"])))
    (is (= ["HH" "mm"] (twelve-hour-heuristic ["HH" "mm"])))
    (is (= ["H" "mm"] (twelve-hour-heuristic ["H" "mm"]))))

  (testing "guesstimate"
    (is (= "MM/DD/YYYY h:mm A" (guesstimate "02/03/1994 7:08 AM" moment-formats)))
    (is (= "YYYY-MM-DD HH:mm:ss" (guesstimate "1994-02-03 07:08:09" moment-formats)))))
