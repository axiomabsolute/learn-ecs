(ns learn-ecs.core)

(defn uuid [] (str (java.util.UUID/randomUUID)))

;; An **entity** is simply an identity and a list of components
(defn create-entity2 [] {:id (uuid), :components (list)})

(def example-entity (create-entity2))

;; A **component** is a collection of related state
(defn component-2d-coord [] {:x 0, :y 0})

;; A **system** is a behavior that transforms entities
;; (often bypassing them if they don't have a required component)
(defn system-proceed 
  "Incrememts the x-position of an entity"
  [entity]
  (map
    (fn [component]
      (if ((and (contains? component :x) (contains? component :y)))
        (assoc component :x (+ (:x component) 1))
        component))
    (:components entity))
  )

;; We need a nice way to associate entities and components.  This is
;; accomplished by overloading create-entity on arity
(defn create-entity
  "doc-string"
  ([] {:id (uuid), :components (list)})
  ([components] {
    :id (uuid),
    :components (map (fn [component] (component)) components)}))