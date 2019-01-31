Chapter 1 - Create an MVP Ecommerce site
1.1 MVP
1.2 Extending MVP - Modularizing design and supportive methods
    - Dir Structure: Domain, Data, Service, organize by features not layers
    - Slices not Layers
    - Domain Object: Entity / ValueObject / Constants
    - Data: DataObject(DO), Object Impedent mismatch, Conversion methods 
    - Service: Interface + Impl
    - Repository: Interface + Impl (Only Aggregate roots should have repository)
1.3 Accounts vs Users, supporting multiple types of logins

1.4? CQRS - Query / QueryResult / Command / CommandResult / RequestHandler / Validator 


Chapter 2 - Ecommerce Business Logic
 
2.1 - C2C vs B2C vs B2B
    * User as C
    * Shop bind User as B
    * B2B?

2.2 - When No. of Items grows, Need Category System, Property, Value

2.3 - SPU vs Item, and concept of Offers, Sell

2.4 - Promotions（满减、单品限时、套装、赠品、满赠、定金预购）

2.5 - Buy, BizOrder, PayOrder, SubBizOrder, SubPayOrder, LogisticsOrder, SubLogisticsOrder

2.6 - Payment, Logistics and Order State

2.7 - Carts

2.9 - Inventory（锁定、解锁、扣减、退货返还）

2.10 - Logistics

2.11 - Finance (结算、对账、分账、分佣、分润、核算、账单、计税)


2.x - Supporting multiple types of business models, using plugins

Chapter 3 - Microservices
3.1 - Splitting into multiple modules
3.2 - MQ for eventual consistency,
3.3 - Caching / Redis / Local Cache
3.4 - Configuration / Diamond

Chapter 4 - Event-Driven Architecture

Chapter 5 - Big Data and ML