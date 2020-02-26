USE `kiy-cloud`;

INSERT INTO `kiy-cloud`.`customers`(`id`,`name`,`code`,`province`,`district`,`city`,`town`,`address`)
VALUES('115a6077-774e-11e7-bb87-4061866d97d0','重庆凯迎电子科技有限公司','91500112MA5U6MA930','重庆市','重庆市','渝北区','仙桃镇','仙桃数据谷C2栋15楼');

INSERT INTO `kiy-cloud`.`servos` (`id`, `type`, `customer_id`, `name`, `address`) 
VALUES('1575f7ba-7753-11e7-bb87-4061866d97d0','0','115A6077-774e-11e7-bb87-4061866d97d0','测试','研发部');

INSERT INTO `kiy-cloud`.`servos` (`id`, `type`, `customer_id`, `name`, `address`) 
VALUES('4f46451e-7bf5-11e7-bb87-4061866d97d0','0','115A6077-774e-11e7-bb87-4061866d97d0','数据谷配电室','负二楼');

INSERT INTO `kiy-cloud`.`servos` (`id`, `type`, `customer_id`, `name`, `address`) 
VALUES ('2527a3d3-56be-4706-a0f5-196d98cb62a1', '0', '115A6077-774e-11e7-bb87-4061866d97d0', '金港国际', '5号配电室');

INSERT INTO `kiy-cloud`.`servos` (`id`, `type`, `customer_id`, `name`, `address`) 
VALUES ('282cd1c7-8e49-11e7-b79a-4061866d97d0', '0', '115A6077-774e-11e7-bb87-4061866d97d0', '智能家居', '演示墙');

