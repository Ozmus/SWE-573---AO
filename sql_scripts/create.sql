DROP DATABASE `community_application`;
CREATE DATABASE  IF NOT EXISTS `community_application`;

USE `community_application`;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `user_name` varchar(12) NOT NULL,
                            `first_name` varchar(50) DEFAULT NULL,
                            `last_name` varchar(50) DEFAULT NULL,
                            `email` varchar(50) DEFAULT NULL,
                            `password` char(80) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS `communities`;
CREATE TABLE `communities` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(50) NOT NULL,
                        `description` varchar(2500) NOT NULL,
                        `image_url` varchar(100) NOT NULL,
                        `is_archived` boolean NOT NULL DEFAULT false,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
                             `user_id` int NOT NULL,
                             `community_id` int NOT NULL,
                             `role` ENUM('MEMBER', 'OWNER', 'MOD') NOT NULL,
                             CONSTRAINT PK_UserRoles PRIMARY KEY (`user_id`, `community_id`),
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (community_id) REFERENCES communities(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `content_templates`;
CREATE TABLE `content_templates` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL,
                            `community_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (community_id) REFERENCES communities(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `contents`;
CREATE TABLE `contents` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `title` varchar(50) NOT NULL,
                               `user_id` int NOT NULL,
                               `content_template_id` int NOT NULL,
                               PRIMARY KEY (`id`),
                               FOREIGN KEY (user_id) REFERENCES users(id),
                               FOREIGN KEY (content_template_id) REFERENCES content_templates(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `fields`;
CREATE TABLE `fields` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL,
                            `data_type` varchar(50) NOT NULL,
                            `content_template_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (content_template_id) REFERENCES content_templates(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `field_values`;
CREATE TABLE `field_values` (
                          `content_id` int NOT NULL,
                          `field_id` int NOT NULL,
                          `value` varchar(250) NOT NULL,
                          CONSTRAINT PK_FieldValues PRIMARY KEY (`content_id`, `field_id`),
                          FOREIGN KEY (content_id) REFERENCES contents(id),
                          FOREIGN KEY (field_id) REFERENCES fields(id)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;