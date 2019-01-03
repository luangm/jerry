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
