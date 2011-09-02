delete from `advanced_user_role`
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("Admin",0)
-- INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("ApplicationAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("RequestGranter",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("FDAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("HRManagement",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("TimesheetAdmin",0)

delete from `approval_strategy`
INSERT INTO `approval_strategy`(`strategy_type`,`id`,`version`,`succesor`)VALUES("LevelTop",12,0,null);
INSERT INTO `approval_strategy`(`strategy_type`,`id`,`version`,`succesor`)VALUES("Level1",13,0,12);


delete from `regular_user`
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(111,1,"2011-07-20 01:21:09",0,"nechifor","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","bibeanu",NULL,"flo.macicasan@gmail.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(112,1,"2011-07-20 01:21:09",0,"gigel","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","curmean",NULL,"fmacicasan1@sdl.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(113,1,"2011-05-20 01:21:09",0,"hector","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","furgea",NULL,"fmacicasan@sdl.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(114,1,"2010-07-20 01:21:09",0,"poppiliu","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","juratu",NULL,"fmacicasan2@sdl.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(115,1,"2001-07-20 01:21:09",0,"oldie","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","goldie",NULL,"burtoflex89@yahoo.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(116,1,"2006-07-20 01:21:09",0,"husman","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","jigu",NULL,"fmacicasan3@sdl.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(117,1,"2009-07-20 01:21:09",0,"george","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","sudica",NULL,"fmacicasan4@sdl.com",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(118,1,"2009-07-20 01:21:09",0,"ionel","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","condor",NULL,"icondor@sdl.com",0)

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

delete from `project`
INSERT INTO `project`(`code`,`name`) VALUES("0001","DummyProj1")
INSERT INTO `project`(`code`,`name`) VALUES("0002","DummyProj2")
INSERT INTO `project`(`code`,`name`) VALUES("0003","DummyProj3")
INSERT INTO `project`(`code`,`name`) VALUES("0004","DummyProj4")
INSERT INTO `project`(`code`,`name`) VALUES("0005","DummyProj5")
INSERT INTO `project`(`code`,`name`) VALUES("9999","Vacation")

delete from `phase`
INSERT INTO `phase`(`code`,`name`) VALUES("0001","DummyPhase1")
INSERT INTO `phase`(`code`,`name`) VALUES("0002","DummyPhase2")
INSERT INTO `phase`(`code`,`name`) VALUES("0003","DummyPhase3")
INSERT INTO `phase`(`code`,`name`) VALUES("0004","DummyPhase4")
INSERT INTO `phase`(`code`,`name`) VALUES("0005","DummyPhase5")
INSERT INTO `phase`(`code`,`name`) VALUES("9999","Not applicable")

delete from `phase_project_lst`
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(1,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(2,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(3,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(4,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(1,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(2,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(3,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(6,6)

delete from `labor_billing`
INSERT INTO `labor_billing`(`code`,`name`) VALUES("01","DummyLaborBilling1")
INSERT INTO `labor_billing`(`code`,`name`) VALUES("02","DummyLaborBilling2")
INSERT INTO `labor_billing`(`code`,`name`) VALUES("03","DummyLaborBilling3")
INSERT INTO `labor_billing`(`code`,`name`) VALUES("9999","Not applicable")

delete from `pattern`
INSERT INTO `pattern`(`no_of_hours`) VALUES(8)

delete from `phase_labor`
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(20,1,1,1,1)
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(20,1,1,2,1)
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(20,2,1,3,1)
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(40,2,1,4,1)

delete from `timesheet_user`
INSERT INTO `timesheet_user`(`id`,`fduser`) VALUES(100,111);
INSERT INTO `timesheet_user`(`id`,`fduser`) VALUES(113,113);

delete from `schedule`
INSERT INTO `schedule`(`start_date`,`end_date`,`employee`,`pattern`) VALUES("2011-07-20", "2011-12-20", 100, 1)
delete from `schedule`
