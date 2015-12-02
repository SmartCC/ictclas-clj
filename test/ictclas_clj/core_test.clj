(ns ictclas-clj.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [ictclas-clj.core :refer :all]))


(def text "据悉，质检总局已将最新有关情况再次通报美方，要求美方加强对输华玉米的产地来源、运输及仓储等环节的管控措施，有效避免输华玉米被未经我国农业部安全评估并批准的转基因品系污染。")


(def nlpir (create-nlpir "/home/scc/ICTCLAS/lib/linux64/libNLPIR.so" "/home/scc/ICTCLAS"))

(def p-out (nlpir-paragraph-process nlpir text))

(def p-keywords (nlpir-get-keywords nlpir text 3 true))

(deftest nlpir-test
  (is (> (count (str/split p-out #" ")) (count (str/split text #" "))) "没有分词")
  (is (< 0 (count (str/split p-keywords #" "))) "提取关键词失败"))

(nlpir-exit nlpir)
