delete from `advanced_user_role`
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("Admin",0)
-- INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("ApplicationAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("RequestGranter",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("FDAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("HRManagement",0)


delete from `regular_user`
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(111,1,"2011-07-20 01:21:09",0,"burtoflex89@yahoo.com","nechifor","2011-07-20 01:21:09","testtest","bibeanu",NULL,"test",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(112,1,"2011-07-20 01:21:09",0,"iulia_teglas@yahoo.com","gigel","2011-07-20 01:21:09","testtest","curmean",NULL,"test2",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(113,1,"2011-05-20 01:21:09",0,"burtoflex89@yahoo.com","hector","2011-07-20 01:21:09","asdasd","furgea",NULL,"all",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(114,1,"2010-07-20 01:21:09",0,"burtoflex89@yahoo.com","poppiliu","2011-07-20 01:21:09","asdasd","juratu",NULL,"req",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(115,1,"2001-07-20 01:21:09",0,"burtoflex89@yahoo.com","oldie","2011-07-20 01:21:09","asdasd","goldie",NULL,"admin",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(116,1,"2006-07-20 01:21:09",0,"burtoflex89@yahoo.com","husman","2011-07-20 01:21:09","asdasd","jigu",NULL,"fdadmin",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(117,1,"2009-07-20 01:21:09",0,"burtoflex89@yahoo.com","george","2011-07-20 01:21:09","asdasd","sudica",NULL,"hr",0)

delete from `fduser`
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(111,0,null,111,"2011-07-20 01:21:09",4,26);
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(112,0,null,112,"2011-07-10 01:21:09",2,23);
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(113,0,null,113,"2011-07-11 01:21:09",5,25);
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(114,0,null,114,"2010-07-20 01:21:09",3,26);
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(115,0,111,115,"2001-07-20 01:21:09",4,22);
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(116,0,null,116,"2006-07-20 01:21:09",3,27);
INSERT INTO `fduser`(`id`,`version`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_free_days`)VALUES(117,0,null,117,"2009-07-20 01:21:09",4,23);

delete from `app_reg_user_adv_role`
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(111,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,2);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,3);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(113,4);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(114,2);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(115,1);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(116,3);
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(117,4);

