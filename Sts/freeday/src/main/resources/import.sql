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
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(111,1,"2011-07-20 01:21:09",0,"flo.macicasan@gmail.com","nechifor","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","bibeanu",NULL,"test",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(112,1,"2011-07-20 01:21:09",0,"fmacicasan1@sdl.com","gigel","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","curmean",NULL,"test2",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(113,1,"2011-05-20 01:21:09",0,"fmacicasan@sdl.com","hector","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","furgea",NULL,"all",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(114,1,"2010-07-20 01:21:09",0,"fmacicasan2@sdl.com","poppiliu","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","juratu",NULL,"req",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(115,1,"2001-07-20 01:21:09",0,"burtoflex89@yahoo.com","oldie","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","goldie",NULL,"admin",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(116,1,"2006-07-20 01:21:09",0,"fmacicasan3@sdl.com","husman","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","jigu",NULL,"fdadmin",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(117,1,"2009-07-20 01:21:09",0,"fmacicasan4@sdl.com","george","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","sudica",NULL,"hrr",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(118,1,"2009-07-20 01:21:09",0,"icondor@sdl.com","ionel","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","condor",NULL,"ionel",0)

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



