delete from `advanced_user_role`
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("Admin",0)
-- INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("ApplicationAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("RequestGranter",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("FDAdmin",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`)VALUES("HRManagement",0)


delete from `regular_user`
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(1,1,"2011-07-20 01:21:09",0,"burtoflex89@yahoo.com","nechifor","2011-07-20 01:21:09","testtest","bibeanu",NULL,"test",0)
INSERT INTO `regular_user`(`id`,`activ`,`creationdate`,`deleted`,`email`,`firstname`,`lastmodified`,`password`,`surename`,`usermodifier`,`username`,`version`)VALUES(2,1,"2011-07-20 01:21:09",0,"burtoflex89@yahoo.com","gigel","2011-07-20 01:21:09","testtest","curmean",NULL,"test2",0)