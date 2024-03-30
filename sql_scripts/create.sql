CREATE DATABASE  IF NOT EXISTS `community_application`;

USE `community_application`;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `user_name` varchar(12) NOT NULL,
                            `first_name` varchar(50) DEFAULT NULL,
                            `last_name` varchar(50) DEFAULT NULL,
                            `email` varchar(50) DEFAULT NULL,
                            `password` char(80) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
