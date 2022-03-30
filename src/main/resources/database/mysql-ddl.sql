-- cleaning --
drop SCHEMA if exists scout_pro_db;
drop user if exists 'scout_pro'@'localhost';

-- creating --
CREATE SCHEMA if not exists scout_pro_db;

-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: scout_pro_db
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_role`
--

DROP TABLE IF EXISTS `app_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) DEFAULT NULL,
  `pass` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_user_player`
--

DROP TABLE IF EXISTS `app_user_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user_player` (
  `app_user_id` bigint NOT NULL,
  `my_player` bit(1) DEFAULT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`player_id`,`app_user_id`),
  KEY `FKm8lfva83fs5nhl51sxmqj693f` (`app_user_id`),
  CONSTRAINT `FKm8lfva83fs5nhl51sxmqj693f` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FKmcea5q2dwa4343043vf30xc1d` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_user_role`
--

DROP TABLE IF EXISTS `app_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user_role` (
  `app_user_id` bigint NOT NULL,
  `app_role_id` bigint NOT NULL,
  PRIMARY KEY (`app_user_id`,`app_role_id`),
  KEY `FKjgh563pbvngdj5q1ije0xvcy0` (`app_role_id`),
  CONSTRAINT `FK11mbw51w6otlim6pw3jl44sjr` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FKjgh563pbvngdj5q1ije0xvcy0` FOREIGN KEY (`app_role_id`) REFERENCES `app_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_privilege`
--

DROP TABLE IF EXISTS `app_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_privilege` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_role_privilege`
--

DROP TABLE IF EXISTS `app_role_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_role_privilege` (
  `app_role_id` bigint NOT NULL,
  `app_privilege_id` bigint NOT NULL,
  PRIMARY KEY (`app_role_id`,`app_privilege_id`),
  KEY `app_privilege_id` (`app_privilege_id`),
  CONSTRAINT `app_role_privilege_ibfk_1` FOREIGN KEY (`app_role_id`) REFERENCES `app_role` (`id`),
  CONSTRAINT `app_role_privilege_ibfk_2` FOREIGN KEY (`app_privilege_id`) REFERENCES `app_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_statistic`
--

DROP TABLE IF EXISTS `competition_statistic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_statistic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aerials_won` decimal(19,2) NOT NULL,
  `assists` int NOT NULL,
  `competition` varchar(100) NOT NULL,
  `goals` int NOT NULL,
  `man_of_the_match` int NOT NULL,
  `mins` int NOT NULL,
  `pass_success` decimal(19,2) NOT NULL,
  `rating` decimal(19,2) NOT NULL,
  `red_cards` int NOT NULL,
  `shots_per_game` decimal(19,2) NOT NULL,
  `started_apps` int NOT NULL,
  `sub_apps` int NOT NULL,
  `yellow_cards` int NOT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8b80shlrwtdtalxgill24625n` (`player_id`),
  CONSTRAINT `FK8b80shlrwtdtalxgill24625n` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game_statistic`
--

DROP TABLE IF EXISTS `game_statistic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_statistic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assists` int NOT NULL,
  `competition` varchar(50) NOT NULL,
  `date_of_game` date NOT NULL,
  `goals` int NOT NULL,
  `man_of_the_match` bit(1) NOT NULL,
  `minutes_played` int NOT NULL,
  `rating` decimal(19,2) NOT NULL,
  `red_card` bit(1) NOT NULL,
  `result` varchar(5) NOT NULL,
  `team1` varchar(50) NOT NULL,
  `team2` varchar(50) NOT NULL,
  `yellow_card` bit(1) NOT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdefhiawlaja3qvm51rj6n1vsj` (`player_id`),
  CONSTRAINT `FKdefhiawlaja3qvm51rj6n1vsj` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `job_info`
--

DROP TABLE IF EXISTS `job_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cron_expression` varchar(255) DEFAULT NULL,
  `cron_job` bit(1) DEFAULT NULL,
  `custom_config_data` longtext,
  `end_time` datetime(6) DEFAULT NULL,
  `job_class` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `job_status` varchar(255) DEFAULT NULL,
  `repeat_count` int DEFAULT NULL,
  `repeat_interval_in_seconds` int DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `market_value`
--

DROP TABLE IF EXISTS `market_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `market_value` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `club_team` varchar(255) NOT NULL,
  `date_point` date NOT NULL,
  `worth` decimal(19,2) NOT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc8io3l9h33oifjjcpef32gxdg` (`player_id`),
  CONSTRAINT `FKc8io3l9h33oifjjcpef32gxdg` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `inserted_date` datetime(6) DEFAULT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjcrcc9fpthgxekntw516fq06v` (`player_id`),
  CONSTRAINT `FKjcrcc9fpthgxekntw516fq06v` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pesdb_acceleration` int NOT NULL,
  `age` int NOT NULL,
  `pesdb_aggression` int NOT NULL,
  `pesdb_balance` int NOT NULL,
  `pesdb_ball_control` int NOT NULL,
  `pesdb_ball_winning` int NOT NULL,
  `club_team` varchar(50) NOT NULL,
  `pesdb_com_playing_styles` varchar(255) DEFAULT NULL,
  `contract_until` varchar(12) NOT NULL,
  `pesdb_curl` int NOT NULL,
  `date_of_birth` varchar(15) NOT NULL,
  `pesdb_defensive_awareness` int NOT NULL,
  `pesdb_dribbling` int NOT NULL,
  `pesdb_finishing` int NOT NULL,
  `pesdb_foot` varchar(255) NOT NULL,
  `pesdb_form` int DEFAULT NULL,
  `pesdb_gk_awareness` int NOT NULL,
  `pesdb_gk_catching` int NOT NULL,
  `pesdb_gk_clearing` int NOT NULL,
  `pesdb_gk_reach` int NOT NULL,
  `pesdb_gk_reflexes` int NOT NULL,
  `pesdb_heading` int NOT NULL,
  `pesdb_injury_resistance` int NOT NULL,
  `inserted` datetime(6) DEFAULT NULL,
  `pesdb_jump` int NOT NULL,
  `pesdb_kicking_power` int NOT NULL,
  `pesdb_lofted_pass` int NOT NULL,
  `pesdb_low_pass` int NOT NULL,
  `market_value_last_check` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `national_team` varchar(50) DEFAULT NULL,
  `nationality` varchar(50) NOT NULL,
  `pesdb_offensive_awareness` int NOT NULL,
  `pesdb_other_strong_positions` varchar(255) DEFAULT NULL,
  `pesdb_other_weak_positions` varchar(255) DEFAULT NULL,
  `pesdb_overall_rating` int NOT NULL,
  `pesdb_last_check` datetime(6) DEFAULT NULL,
  `pesdb_player_name` varchar(256) NOT NULL,
  `pesdb_team_name` varchar(50) NOT NULL,
  `pesdb_url` varchar(255) DEFAULT NULL,
  `pesdb_physical_contact` int NOT NULL,
  `pesdb_place_kicking` int NOT NULL,
  `pesdb_player_skills` varchar(255) DEFAULT NULL,
  `pesdb_playing_style` varchar(255) DEFAULT NULL,
  `pesdb_primary_position` varchar(255) NOT NULL,
  `psml_last_check` datetime(6) DEFAULT NULL,
  `psml_team` varchar(50) NOT NULL,
  `psml_url` varchar(255) DEFAULT NULL,
  `psml_value` decimal(19,2) DEFAULT NULL,
  `pesdb_speed` int NOT NULL,
  `pesdb_stamina` int NOT NULL,
  `statistic_last_check` datetime(6) DEFAULT NULL,
  `whoscored_strengths` varchar(255) DEFAULT NULL,
  `whoscored_style_of_play` varchar(255) DEFAULT NULL,
  `pesdb_tight_possession` int NOT NULL,
  `transfer_last_check` datetime(6) DEFAULT NULL,
  `transfermarkt_position` varchar(40) NOT NULL,
  `transfermarkt_url` varchar(255) DEFAULT NULL,
  `pesdb_weak_foot_accuracy` int NOT NULL,
  `pesdb_weak_foot_usage` int DEFAULT NULL,
  `whoscored_weaknesses` varchar(255) DEFAULT NULL,
  `pesdb_week_condition` char(1) NOT NULL,
  `whoscored_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `position_statistic`
--

DROP TABLE IF EXISTS `position_statistic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_statistic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apps` int NOT NULL,
  `assists` int NOT NULL,
  `goals` int NOT NULL,
  `position` varchar(3) NOT NULL,
  `rating` decimal(19,2) NOT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc69sk62ywdsfw7cet0yq1pcco` (`player_id`),
  CONSTRAINT `FKc69sk62ywdsfw7cet0yq1pcco` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `psml_transfer`
--

DROP TABLE IF EXISTS `psml_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `psml_transfer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_of_transfer` datetime(6) DEFAULT NULL,
  `from_team` varchar(255) DEFAULT NULL,
  `to_team` varchar(255) DEFAULT NULL,
  `transfer_fee` decimal(19,2) DEFAULT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5gs2nh408j6c9x189oxmxkrqp` (`player_id`),
  CONSTRAINT `FK5gs2nh408j6c9x189oxmxkrqp` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrape_field`
--

DROP TABLE IF EXISTS `scrape_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scrape_field` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `selector` varchar(255) DEFAULT NULL,
  `scrape_site_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1v1jd7u0srcorn5rlr1qeq5a8` (`scrape_site_id`),
  CONSTRAINT `FK1v1jd7u0srcorn5rlr1qeq5a8` FOREIGN KEY (`scrape_site_id`) REFERENCES `scrape_site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrape_site`
--

DROP TABLE IF EXISTS `scrape_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scrape_site` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transfer`
--

DROP TABLE IF EXISTS `transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transfer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_of_transfer` date NOT NULL,
  `from_team` varchar(50) NOT NULL,
  `market_value` varchar(25) NOT NULL,
  `to_team` varchar(50) NOT NULL,
  `transfer_fee` varchar(25) NOT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe98lusfudhauj501o005b2nma` (`player_id`),
  CONSTRAINT `FKe98lusfudhauj501o005b2nma` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-03 12:44:07

create user 'scout_pro'@'localhost' identified by 'scout_pro_user';
grant select, insert, update, delete on scout_pro_db.* to 'scout_pro'@'localhost';