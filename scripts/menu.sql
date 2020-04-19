DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `displayName` varchar(20) NOT NULL,
  `iconName` varchar(20) NOT NULL,
  `route` varchar(100) NOT NULL,
  `parent` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;