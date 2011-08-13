delete from `advanced_user_role`
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("Admin",0)
-- INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("ApplicationAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("RequestGranter",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("FDAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("HRManagement",0)

delete from `approval_strategy`
INSERT INTO `approval_strategy`(`strategy_type`,`id`,`version`,`succesor`)VALUES("LevelTop",12,0,null);
INSERT INTO `approval_strategy`(`strategy_type`,`id`,`version`,`succesor`)VALUES("Level1",13,0,12);


delete from `regular_user`
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(111,1,"2011-07-20 01:21:09",0,"flo.macicasan@gmail.com","nechifor","2011-07-20 01:21:09","asdasd","bibeanu",NULL,"test",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(112,1,"2011-07-20 01:21:09",0,"fmacicasan1@sdl.com","gigel","2011-07-20 01:21:09","asdasd","curmean",NULL,"test2",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(113,1,"2011-05-20 01:21:09",0,"fmacicasan@sdl.com","hector","2011-07-20 01:21:09","asdasd","furgea",NULL,"all",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(114,1,"2010-07-20 01:21:09",0,"fmacicasan2@sdl.com","poppiliu","2011-07-20 01:21:09","asdasd","juratu",NULL,"req",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(115,1,"2001-07-20 01:21:09",0,"burtoflex89@yahoo.com","oldie","2011-07-20 01:21:09","asdasd","goldie",NULL,"admin",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(116,1,"2006-07-20 01:21:09",0,"fmacicasan3@sdl.com","husman","2011-07-20 01:21:09","asdasd","jigu",NULL,"fdadmin",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(117,1,"2009-07-20 01:21:09",0,"fmacicasan4@sdl.com","george","2011-07-20 01:21:09","asdasd","sudica",NULL,"hrr",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(118,1,"2009-07-20 01:21:09",0,"icondor@sdl.com","ionel","2011-07-20 01:21:09","asdasd","condor",NULL,"ionel",0)

delete from `fduser`
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(111,0,0,113,111,"2011-07-20 01:21:09",4,5,26);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(112,0,0,null,112,"2011-07-11 01:21:09",2,5,23);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(113,0,0,null,113,"2011-07-11 01:21:09",5,5,25);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(114,0,0,null,114,"2010-07-20 01:21:09",3,5,26);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(115,0,0,111,115,"2009-07-20 01:21:09",4,5,22);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(116,0,0,null,116,"2009-07-20 01:21:09",3,5,27);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(117,0,0,null,117,"2009-07-20 01:21:09",4,5,23);
INSERT INTO `fduser`(`id`,`jobrole`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`)VALUES(118,3,0,null,118,"2009-07-20 01:21:09",4,5,23);

delete from `app_reg_user_adv_role`
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(111,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(111,2);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,2);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,3);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(114,2);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(115,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(116,3);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(117,4);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(118,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(118,2);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(118,3);

delete from `free_day`
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`legalday`,`version`,`approval`)VALUES("typeL",31,"Test1",0,"2011-08-08",0,13);
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`legalday`,`version`,`approval`)VALUES("typeL",32,"Test2",2,"2011-08-09",0,12);
--in progress
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`requestdate`,`approval`,`recover`)VALUES("typeC",15,"TestCc",0,0,"2011-08-04",13,null);
--waiting
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`requestdate`,`approval`,`recover`)VALUES("typeC",11,"TestCc",1,0,"2011-08-04",12,null);
--failure reject
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`requestdate`,`approval`,`recover`)VALUES("typeC",12,"TestCc",3,0,"2011-08-04",13,null);
--failure cancel
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`requestdate`,`approval`,`recover`)VALUES("typeC",13,"TestCc",3,0,"2011-08-04",13,null);
--in progress
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`recoverdate`,`approval`,`request`)VALUES("typeR",20,"TestRr",0,0,"2011-08-06",13,null);
--waiting
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`recoverdate`,`approval`,`request`)VALUES("typeR",21,"TestRr",1,0,"2011-08-06",12,null);

--waiting
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`requestdate`,`approval`,`recover`)VALUES("typeC",14,"TestCc",1,0,"2011-08-04",12,null);
--waiting
INSERT INTO `free_day`(`free_day_type`,`id`,`reason`,`status`,`version`,`recoverdate`,`approval`,`request`)VALUES("typeR",22,"TestRr",1,0,"2011-08-06",12,null);
UPDATE `free_day` SET`status` = 2,`version` = 1,`recover` = 22 WHERE `id` = 14;
UPDATE `free_day` SET`status` = 2,`version` = 1,`request` = 14 WHERE `id` = 22;

delete from `request`
--pending
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(1,0,0,115,111,31);
--pending
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(2,0,0,115,111,15);
--pending
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(3,0,0,115,111,20);
--granted
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(4,3,0,115,111,11);
--rejected
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(5,4,0,115,111,12);
--canceled
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(6,2,0,115,111,13);
--granted
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(7,3,0,115,111,21);
--granted
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(8,3,0,115,111,14);
--granted
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(9,3,0,115,111,22);
--granted
INSERT INTO `request`(`id`,`status`,`version`,`appreguser`,`approver`,`requestable`)VALUES(10,3,0,115,111,32);


