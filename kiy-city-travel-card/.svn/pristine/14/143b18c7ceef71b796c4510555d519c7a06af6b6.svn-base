DROP TRIGGER IF EXISTS `kiy-travel-card`.`users_BEFORE_INSERT`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`users_BEFORE_INSERT` BEFORE INSERT ON `users` FOR EACH ROW
BEGIN
	set NEW.id = UUID_Short();
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `kiy-travel-card`.`users_BEFORE_UPDATE`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`users_BEFORE_UPDATE` BEFORE UPDATE ON `users` FOR EACH ROW
BEGIN
	set NEW.updated=now();
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `kiy-travel-card`.`cards_BEFORE_INSERT`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`cards_BEFORE_INSERT` BEFORE INSERT ON `cards` FOR EACH ROW
BEGIN
	set NEW.id = UUID_Short();
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `kiy-travel-card`.`cards_BEFORE_UPDATE`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`cards_BEFORE_UPDATE` BEFORE UPDATE ON `cards` FOR EACH ROW
BEGIN
	set NEW.updated=now();
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `kiy-travel-card`.`get_card_record_BEFORE_INSERT`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`get_card_record_BEFORE_INSERT` BEFORE INSERT ON `get_card_record` FOR EACH ROW
BEGIN
	set NEW.id = UUID_Short();
END$$
DELIMITER ;
DROP TRIGGER IF EXISTS `kiy-travel-card`.`get_card_record_BEFORE_UPDATE`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`get_card_record_BEFORE_UPDATE` BEFORE UPDATE ON `get_card_record` FOR EACH ROW
BEGIN
	set NEW.updated = now();
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `kiy-travel-card`.`read_card_record_BEFORE_INSERT`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`read_card_record_BEFORE_INSERT` BEFORE INSERT ON `read_card_record` FOR EACH ROW
BEGIN
	set NEW.id = UUID_Short();
END$$
DELIMITER ;
DROP TRIGGER IF EXISTS `kiy-travel-card`.`read_card_record_BEFORE_UPDATE`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`read_card_record_BEFORE_UPDATE` BEFORE UPDATE ON `read_card_record` FOR EACH ROW
BEGIN
	set NEW.updated=now();
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `kiy-travel-card`.`write_card_record_BEFORE_INSERT`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`write_card_record_BEFORE_INSERT` BEFORE INSERT ON `write_card_record` FOR EACH ROW
BEGIN
	set NEW.id = UUID_Short();
END$$
DELIMITER ;
DROP TRIGGER IF EXISTS `kiy-travel-card`.`write_card_record_BEFORE_UPDATE`;

DELIMITER $$
USE `kiy-travel-card`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kiy-travel-card`.`write_card_record_BEFORE_UPDATE` BEFORE UPDATE ON `write_card_record` FOR EACH ROW
BEGIN
	set NEW.updated=now();
END$$
DELIMITER ;
