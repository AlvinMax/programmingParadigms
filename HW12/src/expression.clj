(defn tokenize [expr]
  (let [to-chars #(clojure.string/split (clojure.string/replace % " " "") #"")
        is-digit? #(and % (re-find #"^\d+$" %))]
    (reverse
      (reduce
        (fn [[t & ts :as tokens] token]
          (if (and (is-digit? token) (is-digit? t))
            (cons (str t token) ts)
            (cons token tokens)))
        '(), (to-chars expr)))))

(defn shunting-yard [tokens]
  (let [ops {"~" 2, "&" 1, "|" 1}]
    (flatten
      (reduce
        (fn [[rpn stack] token]
          (let [less-op? #(and (contains? ops %) (<= (ops token) (ops %)))
                not-open-paren? #(not= "(" %)]
            (cond
              (= token "(") [rpn (cons token stack)]
              (= token ")") [(vec (concat rpn (take-while not-open-paren? stack))) (rest (drop-while not-open-paren? stack))]
              (contains? ops token) [(vec (concat rpn (take-while less-op? stack))) (cons token (drop-while less-op? stack))]
              :else [(conj rpn token) stack])))
        [[] ()]
        tokens))))

(defn rpn [tokens]
  (let [ops1 {"~" bit-not}]
    (first
      (reduce
        (fn [stack token]
          (if (contains? ops1 token)
            (cons ((ops1 token) (first stack)) (drop 1 stack))
            (cons (read-string token) stack)
            )
          )
        [] tokens))))

(def log #(do (println %) %))
(def calc (comp rpn shunting-yard tokenize))
(def calc-debug (comp rpn log shunting-yard log tokenize))

(println(rpn(shunting-yard (tokenize "~1"))))