/*
 Navicat Premium Data Transfer

 Source Server         : 作业板
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 805807.cn:3306
 Source Schema         : board

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 20/05/2024 21:35:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_users
-- ----------------------------
DROP TABLE IF EXISTS `admin_users`;
CREATE TABLE `admin_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `college_id` int(11) NULL DEFAULT NULL,
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码的盐值',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '用户状态【1：可用，0：锁定】',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_users
-- ----------------------------
INSERT INTO `admin_users` VALUES (1, 'ciusyan', '93f46309a83bd484fa661b8ebaa265a8', 'admin1@example.com', '555-1234', '2024-04-27 11:42:19', '2024-05-14 22:39:12', 'http://avatar.com/admin1.jpg', 1, 'ciusyan', 1, 'Ciusyan');
INSERT INTO `admin_users` VALUES (2, 'admin2', 'hashed_pw_admin2', 'admin2@example.com', '555-5678', '2024-04-27 11:42:19', '2024-04-27 11:42:19', 'http://avatar.com/admin2.jpg', 2, NULL, 1, '');
INSERT INTO `admin_users` VALUES (3, '1533460130@qq.com', '5ad3a2376efd03e2b5e352694ad8a969', '1533460130@qq.com', NULL, '2024-05-15 09:33:11', '2024-05-15 09:33:11', NULL, 11, '49eae2', 1, '');
INSERT INTO `admin_users` VALUES (4, '2767525216@qq.com', 'd641bf94a71ee9257e5aec30a6504f01', '2767525216@qq.com', NULL, '2024-05-17 00:40:00', '2024-05-17 00:40:00', NULL, 12, '91fe2d', 1, '');
INSERT INTO `admin_users` VALUES (5, '3200745439@qq.com', '4396fcccdf7ed81ed8bd79f4b78cc70b', '3200745439@qq.com', NULL, '2024-05-19 20:50:14', '2024-05-19 20:50:14', NULL, 13, '283ec3', 1, '');

-- ----------------------------
-- Table structure for admin_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `admin_users_roles`;
CREATE TABLE `admin_users_roles`  (
  `user_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `role_id` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_users_roles
-- ----------------------------
INSERT INTO `admin_users_roles` VALUES (1, 1);
INSERT INTO `admin_users_roles` VALUES (3, 1);
INSERT INTO `admin_users_roles` VALUES (4, 1);
INSERT INTO `admin_users_roles` VALUES (5, 1);

-- ----------------------------
-- Table structure for authorizations
-- ----------------------------
DROP TABLE IF EXISTS `authorizations`;
CREATE TABLE `authorizations`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permissions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `publish_id` int(11) NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorizations
-- ----------------------------
INSERT INTO `authorizations` VALUES (1, 'homework:create', 3, '2024-04-27 11:35:21', '2024-05-17 16:06:57');
INSERT INTO `authorizations` VALUES (2, 'notice:create', 4, '2024-04-27 11:35:21', '2024-05-17 16:07:12');

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `grade_id` int(11) NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (1, 'Class A', 1, '2024-04-27 11:35:21', '2024-04-27 11:35:21');
INSERT INTO `classes` VALUES (2, '3333', 1, '2024-04-27 11:35:21', '2024-05-06 00:12:07');
INSERT INTO `classes` VALUES (5, 'string', 1, '2024-05-06 12:22:21', '2024-05-06 12:22:21');
INSERT INTO `classes` VALUES (6, 'string', 4, '2024-05-06 12:22:52', '2024-05-06 12:22:52');
INSERT INTO `classes` VALUES (7, 'str1111111111111in1g', 4, '2024-05-06 12:24:26', '2024-05-06 14:18:28');

-- ----------------------------
-- Table structure for codes
-- ----------------------------
DROP TABLE IF EXISTS `codes`;
CREATE TABLE `codes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动递增的主键标识符',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一的代码字符串，用于标识授权码',
  `publish_id` int(11) NULL DEFAULT NULL COMMENT '生成授权码的用户ID',
  `course_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的课程ID，逻辑外键到courses表',
  `class_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的班级ID，逻辑外键到classes表',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '代码的状态（1表示未使用，2表示已使用，3表示已吊销）',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储授权码的表，包括生成用户、过期时间、关联的课程和班级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of codes
-- ----------------------------
INSERT INTO `codes` VALUES (12, 'fa3e524518', 1, '1', '1', 1, '2024-05-18 07:30:47', '2024-05-18 07:30:47');
INSERT INTO `codes` VALUES (13, '391df0bee9', 1, '1', '1', 1, '2024-05-18 07:33:32', '2024-05-18 07:33:32');
INSERT INTO `codes` VALUES (14, '2a0821ded2', 1, '1', '1', 1, '2024-05-18 07:47:54', '2024-05-18 07:47:54');
INSERT INTO `codes` VALUES (15, '54ed10af37', 1, '1', '2,3', 1, '2024-05-18 10:18:48', '2024-05-18 10:18:48');
INSERT INTO `codes` VALUES (16, '443b6d1487', 1, '1,6,7', '2,3,9', 2, '2024-05-18 10:36:09', '2024-05-20 08:35:34');
INSERT INTO `codes` VALUES (17, 'b17bf1bc5b', 1, '1,6,7', '2,3,9', 2, '2024-05-18 10:36:58', '2024-05-20 08:28:35');

-- ----------------------------
-- Table structure for colleges
-- ----------------------------
DROP TABLE IF EXISTS `colleges`;
CREATE TABLE `colleges`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of colleges
-- ----------------------------
INSERT INTO `colleges` VALUES (1, '华信软件学院', 'A leading university in technology and science education.', 'http://college.com/springfield_logo.jpg', '2024-04-27 11:35:21', '2024-05-14 22:39:43');
INSERT INTO `colleges` VALUES (2, 'Shelbyville University', 'A university known for its humanities and arts programs.', 'http://college.com/shelbyville_logo.jpg', '2024-04-27 11:35:21', '2024-04-27 11:35:21');
INSERT INTO `colleges` VALUES (5, 'string', 'string', 'string', '2024-05-06 12:31:52', '2024-05-06 12:31:52');
INSERT INTO `colleges` VALUES (6, 'string1', 'string', 'string', '2024-05-06 12:32:13', '2024-05-06 12:37:35');
INSERT INTO `colleges` VALUES (7, '1', '1', '1', '2024-05-06 12:39:15', '2024-05-06 12:39:21');
INSERT INTO `colleges` VALUES (8, '8', '8', '8', '2024-05-06 12:39:31', '2024-05-06 12:39:31');
INSERT INTO `colleges` VALUES (9, '9', '9', '9', '2024-05-06 12:39:36', '2024-05-06 12:39:36');
INSERT INTO `colleges` VALUES (10, 'stritestng', 'strintestg', 'strintestg', '2024-05-08 11:21:25', '2024-05-08 11:21:25');
INSERT INTO `colleges` VALUES (11, NULL, NULL, NULL, '2024-05-15 09:33:11', '2024-05-15 09:33:11');
INSERT INTO `colleges` VALUES (12, NULL, NULL, NULL, '2024-05-17 00:40:00', '2024-05-17 00:40:00');
INSERT INTO `colleges` VALUES (13, NULL, NULL, NULL, '2024-05-19 20:50:14', '2024-05-19 20:50:14');

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `college_id` int(11) NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES (100, 'Mathematics', 'Advanced Mathematics', 1, 1, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (101, 'Physics', 'Quantum Mechanics', 2, 1, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (102, 'Chemistry', 'Organic Chemistry', 3, 2, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (103, 'Biology', 'Genetics', 4, 2, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (104, 'Computer Science', 'Data Structures and Algorithms', 5, 3, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (105, 'English', 'Shakespearean Literature', 6, 3, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (106, 'History', 'World War II', 7, 4, '2024-05-19 09:05:20', '2024-05-19 09:05:20');
INSERT INTO `courses` VALUES (107, 'Geography', 'Physical Geography', 8, 4, '2024-05-19 09:05:20', '2024-05-19 09:05:20');

-- ----------------------------
-- Table structure for grades
-- ----------------------------
DROP TABLE IF EXISTS `grades`;
CREATE TABLE `grades`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `college_id` int(11) NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grades
-- ----------------------------
INSERT INTO `grades` VALUES (1, 'Freshman', 1, '2024-04-27 11:35:21', '2024-04-27 11:35:21');
INSERT INTO `grades` VALUES (2, 'string', 1, '2024-04-27 11:35:21', '2024-05-06 13:32:15');
INSERT INTO `grades` VALUES (3, 'string', 0, '2024-05-05 22:53:36', '2024-05-05 22:53:36');

-- ----------------------------
-- Table structure for homeworks
-- ----------------------------
DROP TABLE IF EXISTS `homeworks`;
CREATE TABLE `homeworks`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '作业标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '作业描述',
  `picture_links` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '作业图片链接',
  `deadline` datetime NULL DEFAULT NULL COMMENT '作业截止时间',
  `course_id` int(11) NOT NULL DEFAULT 0 COMMENT '作业课程ID',
  `publisher_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发布者 ID，有可能是 admin_user 的，也可能是 Students 的 wechatId',
  `publish_platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发布平台「WEB or WX」',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '作业状态「0：不启用，1：启用」',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 120 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of homeworks
-- ----------------------------
INSERT INTO `homeworks` VALUES (100, 'Math Homework 1', 'Solve problems 1 to 10 on page 42', '', '2024-06-01 23:59:59', 100, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (101, 'Math Homework 2', 'Complete exercises 5-10 from chapter 3', '', '2024-06-10 23:59:59', 100, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);
INSERT INTO `homeworks` VALUES (102, 'Physics Lab Report', 'Write a lab report on the double-slit experiment', '', '2024-06-05 23:59:59', 101, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (103, 'Chemistry Assignment', 'Read and summarize chapter 4 of the textbook', '', '2024-06-08 23:59:59', 102, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);
INSERT INTO `homeworks` VALUES (104, 'Biology Project', 'Prepare a presentation on Mendelian genetics', '', '2024-06-15 23:59:59', 103, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (105, 'Computer Science Homework', 'Implement a binary search algorithm', '', '2024-06-12 23:59:59', 104, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);
INSERT INTO `homeworks` VALUES (106, 'English Essay', 'Analyze the themes of Macbeth', '', '2024-06-20 23:59:59', 105, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (107, 'History Assignment', 'Discuss the causes of World War II', '', '2024-06-18 23:59:59', 106, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (108, 'Geography Report', 'Describe the features of physical geography', '', '2024-06-25 23:59:59', 107, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (109, 'Math Homework 3', 'Solve the integrals in section 5.3', '', '2024-06-22 23:59:59', 100, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (110, 'Physics Assignment', 'Explain the uncertainty principle', '', '2024-06-28 23:59:59', 101, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);
INSERT INTO `homeworks` VALUES (111, 'Chemistry Lab', 'Conduct an experiment on acid-base titration', '', '2024-07-01 23:59:59', 102, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (112, 'Biology Homework', 'Label the parts of a cell', '', '2024-07-05 23:59:59', 103, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);
INSERT INTO `homeworks` VALUES (113, 'Computer Science Project', 'Create a sorting algorithm', '', '2024-07-10 23:59:59', 104, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (114, 'English Analysis', 'Compare Hamlet and Macbeth', '', '2024-07-15 23:59:59', 105, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (115, 'History Essay', 'Evaluate the impact of the Industrial Revolution', '', '2024-07-18 23:59:59', 106, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (116, 'Geography Homework', 'Map the major rivers of the world', '', '2024-07-20 23:59:59', 107, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (117, 'Math Project', 'Graph the functions given in chapter 7', '', '2024-07-22 23:59:59', 100, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:13', 1);
INSERT INTO `homeworks` VALUES (118, 'Physics Homework', 'Describe the photoelectric effect', '', '2024-07-25 23:59:59', 101, 'ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'WX', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);
INSERT INTO `homeworks` VALUES (119, 'Chemistry Assignment 2', 'Explain the periodic table trends', '', '2024-07-28 23:59:59', 102, 'ciusyan', 'WEB', '2024-05-19 09:06:19', '2024-05-19 09:21:12', 1);

-- ----------------------------
-- Table structure for notification_classes
-- ----------------------------
DROP TABLE IF EXISTS `notification_classes`;
CREATE TABLE `notification_classes`  (
  `notification_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`notification_id`, `class_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification_classes
-- ----------------------------
INSERT INTO `notification_classes` VALUES (1, 1);
INSERT INTO `notification_classes` VALUES (2, 2);

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `publish_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notifications
-- ----------------------------
INSERT INTO `notifications` VALUES (1, NULL, '1', '1', '1', NULL, '2024-05-10 12:58:55', '2024-05-19 12:01:53', b'0');
INSERT INTO `notifications` VALUES (2, NULL, '7', '7', '77', NULL, '2024-05-10 12:58:24', '2024-05-19 12:01:55', b'0');
INSERT INTO `notifications` VALUES (4, NULL, 'New4', 'Message4', '4', NULL, '2024-05-10 10:57:38', '2024-05-19 12:01:55', b'0');
INSERT INTO `notifications` VALUES (5, NULL, 'New5', 'Message5', '4', NULL, '2024-05-10 12:58:12', '2024-05-19 12:01:56', b'0');
INSERT INTO `notifications` VALUES (6, NULL, '6', 'Message6', '6', NULL, '2024-05-10 12:58:20', '2024-05-19 12:01:58', b'0');
INSERT INTO `notifications` VALUES (20, '643ab68943', 'string', 'string', 'ohxOK4m83f_GkKW7JS4j70Fj_UT8', 'string', '2024-05-19 16:23:19', '2024-05-19 16:25:55', b'0');
INSERT INTO `notifications` VALUES (21, '91411005af', 'string', 'string', 'ohxOK4m83f_GkKW7JS4j70Fj_UT8', 'string', '2024-05-19 16:31:09', '2024-05-19 16:31:09', b'0');
INSERT INTO `notifications` VALUES (22, '11f53f109a', 'string', 'string', 'ohxOK4m83f_GkKW7JS4j70Fj_UT8', 'string', '2024-05-19 16:32:08', '2024-05-19 16:32:08', b'0');

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources`  (
  `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源名称',
  `uri` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '映射路由',
  `permission` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '具体权限',
  `type` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '资源类型【1目录，2：菜单，3：资源】',
  `sn` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '序号',
  `icon` varchar(280) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图标',
  `parent_id` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父级资源【0：无父资源】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES (1, '系统数据', '/system/data', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (2, '作业通知中心', '/homework/notification', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (3, '组织管理', '/organization', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (4, '权限管理', '/permission', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (5, '用户管理', '/user', '', 1, 0, '', 0);
INSERT INTO `resources` VALUES (6, '组织数据', '/system/data/organization', '', 2, 0, '', 1);
INSERT INTO `resources` VALUES (7, '用户数据', '/system/data/user', '', 2, 0, '', 1);
INSERT INTO `resources` VALUES (8, '作业', '/homework/notification/work', '', 2, 0, '', 2);
INSERT INTO `resources` VALUES (9, '通知', '/homework/notification/notice', '', 2, 0, '', 2);
INSERT INTO `resources` VALUES (10, '学院', '/organization/college', '', 2, 0, '', 3);
INSERT INTO `resources` VALUES (11, '年级', '/organization/grade', '', 2, 0, '', 3);
INSERT INTO `resources` VALUES (12, '班级', '/organization/class', '', 2, 0, '', 3);
INSERT INTO `resources` VALUES (13, '课程', '/organization/course', '', 2, 0, '', 3);
INSERT INTO `resources` VALUES (14, '角色', '/permission/role', '', 2, 0, '', 4);
INSERT INTO `resources` VALUES (15, '授权', '/permission/authorize', '', 2, 0, '', 4);
INSERT INTO `resources` VALUES (16, '管理员', '/user/admin', '', 2, 0, '', 5);
INSERT INTO `resources` VALUES (17, '学生', '/user/student', '', 2, 0, '', 5);
INSERT INTO `resources` VALUES (18, '查询组织数据', '', 'orgData:read', 3, 0, '', 6);
INSERT INTO `resources` VALUES (22, '查询用户数据', '', 'userData:read', 3, 0, '', 7);
INSERT INTO `resources` VALUES (26, '查询作业', '', 'homework:read', 3, 0, '', 8);
INSERT INTO `resources` VALUES (27, '创建作业', '', 'homework:create', 3, 0, '', 8);
INSERT INTO `resources` VALUES (28, '修改作业', '', 'homework:update', 3, 0, '', 8);
INSERT INTO `resources` VALUES (29, '删除作业', '', 'homework:delete', 3, 0, '', 8);
INSERT INTO `resources` VALUES (30, '查询通知', '', 'notice:read', 3, 0, '', 9);
INSERT INTO `resources` VALUES (31, '创建通知', '', 'notice:create', 3, 0, '', 9);
INSERT INTO `resources` VALUES (32, '修改通知', '', 'notice:update', 3, 0, '', 9);
INSERT INTO `resources` VALUES (33, '删除通知', '', 'notice:delete', 3, 0, '', 9);
INSERT INTO `resources` VALUES (34, '查询学院', '', 'college:read', 3, 0, '', 10);
INSERT INTO `resources` VALUES (35, '创建学院', '', 'college:create', 3, 0, '', 10);
INSERT INTO `resources` VALUES (36, '修改学院', '', 'college:update', 3, 0, '', 10);
INSERT INTO `resources` VALUES (37, '删除学院', '', 'college:delete', 3, 0, '', 10);
INSERT INTO `resources` VALUES (38, '查询年级', '', 'grade:read', 3, 0, '', 11);
INSERT INTO `resources` VALUES (39, '创建年级', '', 'grade:create', 3, 0, '', 11);
INSERT INTO `resources` VALUES (40, '修改年级', '', 'grade:update', 3, 0, '', 11);
INSERT INTO `resources` VALUES (41, '删除年级', '', 'grade:delete', 3, 0, '', 11);
INSERT INTO `resources` VALUES (42, '查询班级', '', 'class:read', 3, 0, '', 12);
INSERT INTO `resources` VALUES (43, '创建班级', '', 'class:create', 3, 0, '', 12);
INSERT INTO `resources` VALUES (44, '修改班级', '', 'class:update', 3, 0, '', 12);
INSERT INTO `resources` VALUES (45, '删除班级', '', 'class:delete', 3, 0, '', 12);
INSERT INTO `resources` VALUES (46, '查询课程', '', 'course:read', 3, 0, '', 13);
INSERT INTO `resources` VALUES (47, '创建课程', '', 'course:create', 3, 0, '', 13);
INSERT INTO `resources` VALUES (48, '修改课程', '', 'course:update', 3, 0, '', 13);
INSERT INTO `resources` VALUES (49, '删除课程', '', 'course:delete', 3, 0, '', 13);
INSERT INTO `resources` VALUES (50, '查询角色', '', 'role:read', 3, 0, '', 14);
INSERT INTO `resources` VALUES (51, '创建角色', '', 'role:create', 3, 0, '', 14);
INSERT INTO `resources` VALUES (52, '修改角色', '', 'role:update', 3, 0, '', 14);
INSERT INTO `resources` VALUES (53, '删除角色', '', 'role:delete', 3, 0, '', 14);
INSERT INTO `resources` VALUES (54, '查询授权', '', 'authorize:read', 3, 0, '', 15);
INSERT INTO `resources` VALUES (55, '创建授权', '', 'authorize:create', 3, 0, '', 15);
INSERT INTO `resources` VALUES (56, '修改授权', '', 'authorize:update', 3, 0, '', 15);
INSERT INTO `resources` VALUES (57, '删除授权', '', 'authorize:delete', 3, 0, '', 15);
INSERT INTO `resources` VALUES (58, '查询管理员', '', 'admin:read', 3, 0, '', 16);
INSERT INTO `resources` VALUES (59, '创建管理员', '', 'admin:create', 3, 0, '', 16);
INSERT INTO `resources` VALUES (60, '修改管理员', '', 'admin:update', 3, 0, '', 16);
INSERT INTO `resources` VALUES (61, '删除管理员', '', 'admin:delete', 3, 0, '', 16);
INSERT INTO `resources` VALUES (62, '查询学生', '', 'student:read', 3, 0, '', 17);
INSERT INTO `resources` VALUES (63, '创建学生', '', 'student:create', 3, 0, '', 17);
INSERT INTO `resources` VALUES (64, '修改学生', '', 'student:update', 3, 0, '', 17);
INSERT INTO `resources` VALUES (65, '删除学生', '', 'student:delete', 3, 0, '', 17);
INSERT INTO `resources` VALUES (66, '修改用户密码', '', 'admin:forget', 3, 0, '', 16);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `intro` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色简介',
  `college_id` int(11) NOT NULL DEFAULT 0 COMMENT '学院 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roles_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '超级管理员', '什么权限都有，组织发起者必须是这个角色', 1);

-- ----------------------------
-- Table structure for roles_resources
-- ----------------------------
DROP TABLE IF EXISTS `roles_resources`;
CREATE TABLE `roles_resources`  (
  `role_id` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  `resource_id` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '资源ID',
  PRIMARY KEY (`role_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles_resources
-- ----------------------------
INSERT INTO `roles_resources` VALUES (1, 1);
INSERT INTO `roles_resources` VALUES (1, 2);
INSERT INTO `roles_resources` VALUES (1, 3);
INSERT INTO `roles_resources` VALUES (1, 4);
INSERT INTO `roles_resources` VALUES (1, 5);
INSERT INTO `roles_resources` VALUES (1, 6);
INSERT INTO `roles_resources` VALUES (1, 7);
INSERT INTO `roles_resources` VALUES (1, 8);
INSERT INTO `roles_resources` VALUES (1, 9);
INSERT INTO `roles_resources` VALUES (1, 10);
INSERT INTO `roles_resources` VALUES (1, 11);
INSERT INTO `roles_resources` VALUES (1, 12);
INSERT INTO `roles_resources` VALUES (1, 13);
INSERT INTO `roles_resources` VALUES (1, 14);
INSERT INTO `roles_resources` VALUES (1, 15);
INSERT INTO `roles_resources` VALUES (1, 16);
INSERT INTO `roles_resources` VALUES (1, 17);
INSERT INTO `roles_resources` VALUES (1, 18);
INSERT INTO `roles_resources` VALUES (1, 19);
INSERT INTO `roles_resources` VALUES (1, 20);
INSERT INTO `roles_resources` VALUES (1, 21);
INSERT INTO `roles_resources` VALUES (1, 22);
INSERT INTO `roles_resources` VALUES (1, 23);
INSERT INTO `roles_resources` VALUES (1, 24);
INSERT INTO `roles_resources` VALUES (1, 25);
INSERT INTO `roles_resources` VALUES (1, 26);
INSERT INTO `roles_resources` VALUES (1, 27);
INSERT INTO `roles_resources` VALUES (1, 28);
INSERT INTO `roles_resources` VALUES (1, 29);
INSERT INTO `roles_resources` VALUES (1, 30);
INSERT INTO `roles_resources` VALUES (1, 31);
INSERT INTO `roles_resources` VALUES (1, 32);
INSERT INTO `roles_resources` VALUES (1, 33);
INSERT INTO `roles_resources` VALUES (1, 34);
INSERT INTO `roles_resources` VALUES (1, 35);
INSERT INTO `roles_resources` VALUES (1, 36);
INSERT INTO `roles_resources` VALUES (1, 37);
INSERT INTO `roles_resources` VALUES (1, 38);
INSERT INTO `roles_resources` VALUES (1, 39);
INSERT INTO `roles_resources` VALUES (1, 40);
INSERT INTO `roles_resources` VALUES (1, 41);
INSERT INTO `roles_resources` VALUES (1, 42);
INSERT INTO `roles_resources` VALUES (1, 43);
INSERT INTO `roles_resources` VALUES (1, 44);
INSERT INTO `roles_resources` VALUES (1, 45);
INSERT INTO `roles_resources` VALUES (1, 46);
INSERT INTO `roles_resources` VALUES (1, 47);
INSERT INTO `roles_resources` VALUES (1, 48);
INSERT INTO `roles_resources` VALUES (1, 49);
INSERT INTO `roles_resources` VALUES (1, 50);
INSERT INTO `roles_resources` VALUES (1, 51);
INSERT INTO `roles_resources` VALUES (1, 52);
INSERT INTO `roles_resources` VALUES (1, 53);
INSERT INTO `roles_resources` VALUES (1, 54);
INSERT INTO `roles_resources` VALUES (1, 55);
INSERT INTO `roles_resources` VALUES (1, 56);
INSERT INTO `roles_resources` VALUES (1, 57);
INSERT INTO `roles_resources` VALUES (1, 58);
INSERT INTO `roles_resources` VALUES (1, 59);
INSERT INTO `roles_resources` VALUES (1, 60);
INSERT INTO `roles_resources` VALUES (1, 61);
INSERT INTO `roles_resources` VALUES (1, 62);
INSERT INTO `roles_resources` VALUES (1, 63);
INSERT INTO `roles_resources` VALUES (1, 64);
INSERT INTO `roles_resources` VALUES (1, 65);

-- ----------------------------
-- Table structure for student_authorizations
-- ----------------------------
DROP TABLE IF EXISTS `student_authorizations`;
CREATE TABLE `student_authorizations`  (
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `course_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `class_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_authorizations
-- ----------------------------
INSERT INTO `student_authorizations` VALUES ('1', NULL, NULL);
INSERT INTO `student_authorizations` VALUES ('2', NULL, NULL);
INSERT INTO `student_authorizations` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', '1,2,3,4,6,7', '1,2,3,4,5,9');
INSERT INTO `student_authorizations` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', '1,2,3,4', '1,2,3,4,5');
INSERT INTO `student_authorizations` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', '1,2,3,4', '1,2,3,4');
INSERT INTO `student_authorizations` VALUES ('ohxOK4uQ3pa76r_nm4Vl0Wo6KYx8', '1,2,3,4', '1,2,3,4,5,6');

-- ----------------------------
-- Table structure for student_code
-- ----------------------------
DROP TABLE IF EXISTS `student_code`;
CREATE TABLE `student_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_code
-- ----------------------------

-- ----------------------------
-- Table structure for student_courses
-- ----------------------------
DROP TABLE IF EXISTS `student_courses`;
CREATE TABLE `student_courses`  (
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`, `course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_courses
-- ----------------------------
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 100);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 101);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 102);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 103);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 104);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 105);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 106);
INSERT INTO `student_courses` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 107);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 100);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 101);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 102);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 103);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 104);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 105);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 106);
INSERT INTO `student_courses` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 107);
INSERT INTO `student_courses` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 100);
INSERT INTO `student_courses` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 103);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 100);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 101);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 102);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 103);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 104);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 105);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 106);
INSERT INTO `student_courses` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 107);

-- ----------------------------
-- Table structure for student_homeworks
-- ----------------------------
DROP TABLE IF EXISTS `student_homeworks`;
CREATE TABLE `student_homeworks`  (
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `homework_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '作业状态「0：未读未完成，1：已读未完成，2：已读已完成」',
  `pin` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否置顶「0：不置顶，1：置顶」',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`student_id`, `homework_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_homeworks
-- ----------------------------
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 100, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 101, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 102, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 103, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 104, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 105, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 106, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 107, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 108, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 109, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 110, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 111, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 112, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 113, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 114, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 115, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 116, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 117, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 118, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 119, 0, 0, '2024-05-20 19:49:04', '2024-05-20 19:49:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 100, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 101, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 102, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 103, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 104, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 105, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 106, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 107, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 108, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 109, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 110, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 111, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 112, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 113, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 114, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 115, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 116, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 117, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 118, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 119, 0, 0, '2024-05-20 16:14:16', '2024-05-20 16:14:16');
INSERT INTO `student_homeworks` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 100, 0, 0, '2024-05-20 15:47:54', '2024-05-20 15:47:54');
INSERT INTO `student_homeworks` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 101, 0, 0, '2024-05-20 15:47:54', '2024-05-20 15:47:54');
INSERT INTO `student_homeworks` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 104, 0, 0, '2024-05-20 15:48:04', '2024-05-20 15:48:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 109, 0, 0, '2024-05-20 15:47:54', '2024-05-20 15:47:54');
INSERT INTO `student_homeworks` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 112, 0, 0, '2024-05-20 15:48:04', '2024-05-20 15:48:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', 117, 0, 0, '2024-05-20 15:47:54', '2024-05-20 15:47:54');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 100, 2, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 101, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 102, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 103, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 104, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 105, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 106, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 107, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 108, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 109, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 110, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 111, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 112, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 113, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 114, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 115, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 116, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 117, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 118, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');
INSERT INTO `student_homeworks` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', 119, 0, 0, '2024-05-20 14:59:04', '2024-05-20 14:59:04');

-- ----------------------------
-- Table structure for student_notifications
-- ----------------------------
DROP TABLE IF EXISTS `student_notifications`;
CREATE TABLE `student_notifications`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `notification_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_read` bit(1) NULL DEFAULT NULL COMMENT '0代表未读; 1代表已读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_notifications
-- ----------------------------

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `wechat_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `college_id` int(11) NULL DEFAULT NULL,
  `grade_id` int(11) NULL DEFAULT NULL,
  `class_id` int(11) NULL DEFAULT NULL,
  `student_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `author` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否是 C 端的管理员「1：是，0：不是」',
  PRIMARY KEY (`wechat_id`) USING BTREE,
  UNIQUE INDEX `wechat_id`(`wechat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('ohxOK4m83f_GkKW7JS4j70Fj_UT8', 'c79b176da3', 'https://hx.404fwf.cn/notifyBoard/img/default-avatar.png', NULL, '2024-05-20 13:46:10', '2024-05-20 19:48:58', 1, 1, 1, '0', 0);
INSERT INTO `students` VALUES ('ohxOK4mmh3aG-EOa--hBfuL3M7LM', 'ea77fba933', 'https://hx.404fwf.cn/notifyBoard/img/default-avatar.png', NULL, '2024-05-20 16:13:53', '2024-05-20 16:14:09', 1, 1, 1, '0', 0);
INSERT INTO `students` VALUES ('ohxOK4nubN7rhKJxDM03ngnTUcLY', '13efbc8d7c', 'https://hx.404fwf.cn/notifyBoard/img/default-avatar.png', NULL, '2024-05-20 15:28:10', '2024-05-20 15:47:41', 1, 1, 2, '0', 0);
INSERT INTO `students` VALUES ('ohxOK4tT0JlfpNLYIzDwqKf46FME', '605181b0a5', 'https://hx.404fwf.cn/notifyBoard/img/default-avatar.png', NULL, '2024-05-20 14:58:49', '2024-05-20 14:58:58', 1, 1, 1, '0', 0);

SET FOREIGN_KEY_CHECKS = 1;
