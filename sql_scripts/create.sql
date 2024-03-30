CREATE DATABASE  IF NOT EXISTS `community_application`;

USE `community_application`;


DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `user_name` varchar(12) NOT NULL,
                            `first_name` varchar(50) DEFAULT NULL,
                            `last_name` varchar(50) DEFAULT NULL,
                            `email` varchar(50) DEFAULT NULL,
                            `password` char(80) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS `Communities`;
CREATE TABLE `Communities` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(50) NOT NULL,
                        `description` varchar(2500) NOT NULL,
                        `image_url` varchar(100) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS `Roles`;
CREATE TABLE `Roles` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name` varchar(50) NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
INSERT INTO `Roles` (id,name)
VALUES
    (1, 'MEMBER'),
    (2, 'MODERATOR'),
    (3, 'OWNER');



DROP TABLE IF EXISTS `UserRoles`;
CREATE TABLE `UserRoles` (
                             `user_id` int NOT NULL,
                             `community_id` int NOT NULL,
                             `role_id` int NOT NULL,
                             CONSTRAINT PK_UserRoles PRIMARY KEY (`user_id`, `community_id`, `role_id`),
                             FOREIGN KEY (user_id) REFERENCES Users(id),
                             FOREIGN KEY (community_id) REFERENCES Communities(id),
                             FOREIGN KEY (role_id) REFERENCES Roles(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `ContenTemplates`;
CREATE TABLE `ContenTemplates` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL,
                            `community_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (community_id) REFERENCES Communities(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `Contents`;
CREATE TABLE `Contents` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `title` varchar(50) NOT NULL,
                               `user_id` int NOT NULL,
                               `content_template_id` int NOT NULL,
                               PRIMARY KEY (`id`),
                               FOREIGN KEY (user_id) REFERENCES Users(id),
                               FOREIGN KEY (content_template_id) REFERENCES ContenTemplates(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `Fields`;
CREATE TABLE `Fields` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL,
                            `data_type` varchar(50) NOT NULL,
                            `content_template_id` int NOT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (content_template_id) REFERENCES ContenTemplates(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `FieldValues`;
CREATE TABLE `FieldValues` (
                          `content_id` int NOT NULL,
                          `field_id` int NOT NULL,
                          `value` int NOT NULL,
                          CONSTRAINT PK_FieldValues PRIMARY KEY (`content_id`, `field_id`),
                          FOREIGN KEY (content_id) REFERENCES Contents(id),
                          FOREIGN KEY (field_id) REFERENCES Fields(id)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;