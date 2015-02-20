(ns guestbook.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))


(def db {:classname   "org.sqlite.jdbc", 
         :subprotocol "sqlite",
         :subname     "db.sq3"})

(defn create-guestbook-table
  []
  (sql/with-connection db
    (sql/create-table :guestbook
                      [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
                      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
                      [:name "TEXT"]
                      [:message "text"])
    (sql/do-commands "create index guestbook_idx on guestbook(timestamp)")))




(defn read-guests
  []
  (sql/with-connection db
    (sql/with-query-results res
      ["select * from guestbook order by timestamp desc"]
      (doall res))))



(defn save-message
  [name message]
  (sql/with-connection db
    (sql/insert-values :guestbook
                       [:name :message :timestamp]
                       [name message (java.util.Date.)])))

