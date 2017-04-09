(ns date-format.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [date-format.core-test]))

(doo-tests 'date-format.core-test)
