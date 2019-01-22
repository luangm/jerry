CREATE TABLE `order` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `buyer_id` int(20) NOT NULL,
  `seller_id` int(20) NOT NULL,
  `item_id` int(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_fee` int(20) NOT NULL,
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SELECT
    *
FROM
    (SELECT
        id
    FROM
        `order`
    WHERE
        is_main = 1
        order by gmt_create desc
    LIMIT 1, 3) t1

    LEFT JOIN `order` t2
    ON t2.parent_id = t1.id;