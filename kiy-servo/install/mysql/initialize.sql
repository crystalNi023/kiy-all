SET @ROLE_ID = UUID();
SET @USER_ID = UUID();

INSERT INTO `roles` (`id`,`name`,`remark`,`created`,`updated`)VALUES(@ROLE_ID,'administrators','Administrators',NOW(),NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,1,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,2,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,3,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,4,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,10,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,11,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,12,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,13,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,14,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,15,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,16,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,17,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,21,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,22,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,23,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,24,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,25,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,26,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,27,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,30,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,31,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,32,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,33,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,34,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,40,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,41,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,42,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,43,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,101,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,102,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,103,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,110,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,111,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,112,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,113,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,114,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,115,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,116,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,117,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,120,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,121,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,122,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,123,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,124,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,125,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,126,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,127,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,130,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,131,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,132,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,133,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,134,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,135,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,136,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,200,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,201,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,202,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,203,NOW());

INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,300,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,301,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,302,NOW());
INSERT INTO `role_powers`(`role`,`command`,`created`)VALUES(@ROLE_ID,303,NOW());

-- PWD:abc
INSERT INTO `users`(`id`,`name`,`password`,`realname`,`created`,`updated`,`enable`)VALUES(@USER_ID,'administrator','900150983cd24fb0d6963f7d28e17f72','System_Default',NOW(),NOW(),1);
INSERT INTO `user_roles`(`role`,`user`,`created`)VALUES(@ROLE_ID,@USER_ID,NOW());

SET @USER_ID=UUID();

-- PWD:abc
INSERT INTO `users`(`id`,`name`,`password`,`realname`,`created`,`updated`,`enable`)VALUES(@USER_ID,'test','900150983cd24fb0d6963f7d28e17f72','System_Default',NOW(),NOW(),1);
INSERT INTO `user_roles`(`role`,`user`,`created`)VALUES(@ROLE_ID,@USER_ID,NOW());
