USE `kiy-servo`;

ALTER TABLE `user_roles` 
ADD CONSTRAINT `fk_user_roles_user`
  FOREIGN KEY (`user`)
  REFERENCES `users` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
ALTER TABLE `user_roles` 
ADD CONSTRAINT `fk_user_roles_role`
  FOREIGN KEY (`role`)
  REFERENCES `roles` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `role_powers` 
ADD CONSTRAINT `fk_role_powers_role`
  FOREIGN KEY (`role`)
  REFERENCES `roles` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `zones` 
ADD CONSTRAINT `fk_zones_parent`
  FOREIGN KEY (`parent`)
  REFERENCES `zones` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
ALTER TABLE `devices` 
ADD INDEX `ix_devices_zone` (`zone` ASC);

ALTER TABLE `devices` 
ADD CONSTRAINT `fk_devices_zone`
  FOREIGN KEY (`zone`)
  REFERENCES `zones` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;
  
ALTER TABLE `devices` 
ADD INDEX `ix_devices_relay` (`relay` ASC);

ALTER TABLE `devices` 
ADD CONSTRAINT `fk_devices_relay`
  FOREIGN KEY (`relay`)
  REFERENCES `devices` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;

ALTER TABLE `device_status` 
ADD INDEX `ix_device_status_device` (`device` ASC);

ALTER TABLE `device_status` 
ADD CONSTRAINT `fk_device_status_device`
  FOREIGN KEY (`device`)
  REFERENCES `devices` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
ALTER TABLE `tasks` 
ADD INDEX `ix_tasks_role` (`role` ASC);

ALTER TABLE `tasks` 
ADD CONSTRAINT `fk_tasks_role`
  FOREIGN KEY (`role`)
  REFERENCES `roles` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;

ALTER TABLE `task_devices`
ADD INDEX `fk_task_devices_task_idx` (`task` ASC);

ALTER TABLE `task_devices`
ADD INDEX `fk_task_devices_device_idx` (`device` ASC);

ALTER TABLE `task_devices`
ADD CONSTRAINT `fk_task_devices_device` 
FOREIGN KEY (`device`)
REFERENCES `devices` (`id`)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE `task_devices`
ADD CONSTRAINT `fk_task_devices_task` 
FOREIGN KEY (`task`)
REFERENCES `tasks` (`id`)
ON DELETE CASCADE
ON UPDATE CASCADE;
  
ALTER TABLE `maintains` 
ADD INDEX `ix_maintains_device` (`device` ASC);

ALTER TABLE `maintains` 
ADD CONSTRAINT `fk_maintains_device`
  FOREIGN KEY (`device`)
  REFERENCES `devices` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
 ALTER TABLE `notice`
 ADD INDEX `fk_notice_device_idx` (`device` ASC);
 
 ALTER TABLE `notice`
 ADD INDEX `fk_notice_user_idx` (`user` ASC);
 
ALTER TABLE `notice` 
ADD CONSTRAINT `fk_notice_device`
  FOREIGN KEY (`device`)
  REFERENCES `devices` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
ALTER TABLE `notice` 
ADD CONSTRAINT `fk_notice_user`
  FOREIGN KEY (`user`)
  REFERENCES `users` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE; 
  
ALTER TABLE `scene_devices` 
ADD CONSTRAINT `fk_scene_device_device`
  FOREIGN KEY (`device`)
  REFERENCES `devices` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE; 
  
ALTER TABLE `scene_devices` 
ADD CONSTRAINT `fk_scene_device_scene`
  FOREIGN KEY (`scene`)
  REFERENCES `scenes` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE; 
  
ALTER TABLE `linkage_devices` 
ADD CONSTRAINT `fk_linkage_devices_device_id`
  FOREIGN KEY (`device_id`)
  REFERENCES `devices` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE; 
  
ALTER TABLE `linkage_devices` 
ADD CONSTRAINT `fk_linkage_devices_linkages_id`
  FOREIGN KEY (`linkage_id`)
  REFERENCES `linkages` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE; 