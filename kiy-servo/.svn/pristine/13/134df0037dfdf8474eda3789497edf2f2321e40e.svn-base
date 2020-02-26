SET @ZONE = UUID();
SET @RELAY_1 = UUID();
SET @RELAY_2 = UUID();
SET @DEVICE_1 = UUID();
SET @DEVICE_2 = UUID();
SET @DEVICE_3 = UUID();
SET @DEVICE_4 = UUID();
SET @DEVICE_5 = UUID();
SET @DEVICE_6 = UUID();
SET @DEVICE_7 = UUID();
SET @DEVICE_8 = UUID();
SET @DEVICE_9 = UUID();
SET @DEVICE_10 = UUID();
SET @DEVICE_11 = UUID();
SET @TASK_1 = UUID();
SET @TASK_2 = UUID();
SET @TASK_3 = UUID();
SET @TASK_4 = UUID();
SET @TASK_5 = UUID();

INSERT INTO `zones` (`id`,`name`,`created`,`updated`) VALUES (@ZONE,'安防主机',NOW(),NOW());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@RELAY_1,@ZONE,NULL,8,3,0,4,0,'通讯适配器','1',1,'/dev/ttyS1',9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@RELAY_2,@ZONE,NULL,8,3,0,4,0,'通信适配器（外置温湿度）','1',1,'/dev/ttyS3',9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_1,@ZONE,@RELAY_1,8,34,91,4,0,'内置温湿度监测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_2,@ZONE,@RELAY_1,8,24,107,4,0,'断电传感器','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_3,@ZONE,@RELAY_2,8,34,92,4,0,'外置温湿度检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_4,@ZONE,@RELAY_1,8,41,109,4,32,'电源总控','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_5,@ZONE,@RELAY_1,8,37,97,4,0,'PM2.5检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_6,@ZONE,@RELAY_1,8,91,110,4,104,'紧急SOS报警检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_7,@ZONE,@RELAY_1,8,91,110,4,101,'可燃气体报警检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_8,@ZONE,@RELAY_1,8,91,110,4,103,'漏水报警检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_9,@ZONE,@RELAY_1,8,91,110,4,102,'烟雾火灾报警检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_10,@ZONE,@RELAY_1,8,91,111,4,107,'入户门开闭检测','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`devices` (`id`,`zone`,`relay`,`vendor`,`kind`,`model`,`link`,`use`,`name`,
`number`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`installed`,`produced`,`phase_check`,`phase_power`,`notice`,`detect`,
`created`,`updated`) VALUES (@DEVICE_11,@ZONE,null,10,90,300,1,0,'测试摄像头','1',1,NULL,9600,0,0,0,0,now(),now(),1,1,1,1,now(),now());

INSERT INTO `kiy-servo`.`tasks`(`id`,`name`,`start`,`stop`,`month`,`week`,`day`,`interval`,`repeat`,`read_model`,`write_model`,
`read_feature`,`write_feature`,`limit_lower`,`limit_upper`,`feed`,`feed_lower`,`feed_upper`,`enable`,`created`,`updated`)
VALUES (@TASK_1,'断电检测','2017-01-15 00:00:15','3000-01-15 23:59:30',0,0,0,0,1,107,0,0,0,0,2,0,0,0,1,now(),now());

INSERT INTO `kiy-servo`.`task_devices`(`task`,`device`,`rw`)
VALUES(@TASK_1,@DEVICE_2,0);

INSERT INTO `kiy-servo`.`tasks`(`id`,`name`,`start`,`stop`,`month`,`week`,`day`,`interval`,`repeat`,`read_model`,`write_model`,
`read_feature`,`write_feature`,`limit_lower`,`limit_upper`,`feed`,`feed_lower`,`feed_upper`,`enable`,`created`,`updated`)
VALUES (@TASK_2,'紧急SOS检测','2017-01-15 00:00:15','3000-01-15 23:59:30',0,0,0,0,1,110,0,1,0,-1,1,0,0,0,1,now(),now());

INSERT INTO `kiy-servo`.`task_devices`(`task`,`device`,`rw`)
VALUES(@TASK_2,@DEVICE_6,0);

INSERT INTO `kiy-servo`.`tasks`(`id`,`name`,`start`,`stop`,`month`,`week`,`day`,`interval`,`repeat`,`read_model`,`write_model`,
`read_feature`,`write_feature`,`limit_lower`,`limit_upper`,`feed`,`feed_lower`,`feed_upper`,`enable`,`created`,`updated`)
VALUES (@TASK_3,'可燃气体检测','2017-01-15 00:00:15','3000-01-15 23:59:30',0,0,0,0,1,110,0,1,0,-1,1,0,0,0,1,now(),now());

INSERT INTO `kiy-servo`.`task_devices`(`task`,`device`,`rw`)
VALUES(@TASK_3,@DEVICE_7,0);

INSERT INTO `kiy-servo`.`tasks`(`id`,`name`,`start`,`stop`,`month`,`week`,`day`,`interval`,`repeat`,`read_model`,`write_model`,
`read_feature`,`write_feature`,`limit_lower`,`limit_upper`,`feed`,`feed_lower`,`feed_upper`,`enable`,`created`,`updated`)
VALUES (@TASK_4,'漏水检测','2017-01-15 00:00:15','3000-01-15 23:59:30',0,0,0,0,1,110,0,1,0,-1,1,0,0,0,1,now(),now());

INSERT INTO `kiy-servo`.`task_devices`(`task`,`device`,`rw`)
VALUES(@TASK_4,@DEVICE_8,0);

INSERT INTO `kiy-servo`.`tasks`(`id`,`name`,`start`,`stop`,`month`,`week`,`day`,`interval`,`repeat`,`read_model`,`write_model`,
`read_feature`,`write_feature`,`limit_lower`,`limit_upper`,`feed`,`feed_lower`,`feed_upper`,`enable`,`created`,`updated`)
VALUES (@TASK_5,'烟雾火灾检测','2017-01-15 00:00:15','3000-01-15 23:59:30',0,0,0,0,1,110,0,1,0,-1,1,0,0,0,1,now(),now());

INSERT INTO `kiy-servo`.`task_devices`(`task`,`device`,`rw`)
VALUES(@TASK_5,@DEVICE_9,0);

