(ns ictclas-clj.core
  (:use [ictclas-clj nlpir-jna-interface])
  (:import [com.sun.jna Library Native]))

(defn- encode-fn
  "编码转换函数"
  [input & {:keys [encoding] :or {encoding "utf-8"}}]
  (String. (.getBytes input "utf-8")))

(defn create-nlpir
  "创建一个的实例并将其初始化"
  [nlpir-lib-path nlpir-data-path & {:keys [text-code licence-code] :or {text-code utf-8 licence-code "0"}}]
  (let [nlpir (Native/loadLibrary nlpir-lib-path ictclasClj.NlpirJnaInterface)]
    (if (zero? (.NLPIR_Init nlpir
                            (encode-fn nlpir-data-path :enconding (:code-name text-code))
                            (:code-num text-code)
                            (encode-fn (str licence-code))))
      (throw (Exception. "初始化失败"))
      nlpir)))

(defn nlpir-paragraph-process
  "分词函数"
  [nlpir-instance text & {:keys [tag] :or {tag no-tag}}]
   (.NLPIR_ParagraphProcess nlpir-instance text tag))

(defn nlpir-get-keywords
  "获取关键词，最后一个参数决定关键词的词性和权重要不要输出"
  [nlpir-instance text keywords-max-num tag-weight-out?]
  (.NLPIR_GetKeyWords nlpir-instance text keywords-max-num tag-weight-out?))

(defn nlpir-exit
  "销毁分词器，否则会造成内存泄露"
  [nlpir-instance]
  (.NLPIR_Exit nlpir-instance))
