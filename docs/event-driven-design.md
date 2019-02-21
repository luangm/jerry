Message：

Event, Command, Query:


Event:
* Something happened
* Some data changed
* Some timeout happened

3 types of consumers:
* Exclusive consumer: Waste resources
* Competing Consumer: Ordering not guranteed
* Consistent Hashing: ensuring ordered events are consumed by the same guy. Use OrderID



Command Routing:
* Round robin
* Consistent Hashing: Local Caches


Query:
* Single destination: Point to point
* Scatter - gather: Multiple queries, min(all answers), ensure a timeout (e.g. Promotion)
* Subscription


