(ns ictclas-clj.nlpir-jna-interface)

(defn def-code
  "定义编码和对应的编号"
  [code-name code-num & {:keys [nns] :or {nns *ns*}}]
  (intern nns code-name {:code-name (name code-name) :code-num code-num}))

(def-code 'gbk 0) ;默认支持GBK编码
(def-code 'utf-8 1) ;UTF8编码
(def-code 'big5 2) ;BIG5编码

;下面为标注的标注，0为不标注
(def no-tag 0) ;没有标注b
(def pos-map-number 4) ;add by qp 2008.11.25
(def ict-pos-map-first 1)  ;计算所一级标注集
(def ict-pos-map-second 0) ;计算所二级标注集
(def pku-pos-map-second 2) ;北大二级标注集
(def pku-pos-map-first 3)  ;北大一级标注集
(def pos-size 40)

;ictclas接口的c++库的函数参数有许多为const char *的类型，可以使用两种方式声明：
;1、声明类型为#=(java.lang.Class/forName "[B") 之前必须加上#=,否则出来的是函数不是类型
;2、声明类型为String
;本工程为了阅读方便，统一使用String类型
(gen-interface
 :name ictclasClj.NlpirJnaInterface
 :extends [com.sun.jna.Library]
 :methods [
           [NLPIR_Init [String int String] int]
           [NLPIR_ParagraphProcess [String int] String]
           [NLPIR_GetKeyWords [String int boolean] String]
           [NLPIR_Exit [] void]])
