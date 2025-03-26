create table tb_user
(
    user_id   bigint       not null comment '用户id',
    password  varchar(128) null comment '用户密码',
    nick_name varchar(10)  null comment '昵称',
    img       varchar(256) null comment '头像'
);

INSERT INTO yihenChat.tb_user (user_id, password, nick_name, img) VALUES (1, '1234', '小明', 'https://baomidou.com/assets/asset.cIbiVTt__ZgvyzK.svg');
INSERT INTO yihenChat.tb_user (user_id, password, nick_name, img) VALUES (2, '6666', '小刚', 'https://png.pngtree.com/png-clipart/20230516/original/pngtree-idea-bulb-cartoon-png-image_9162702.png');
INSERT INTO yihenChat.tb_user (user_id, password, nick_name, img) VALUES (3, '8888', '小童', 'https://www.brandora.net/wp/wp-content/uploads/2016/06/logo_for_website1.jpg');
INSERT INTO yihenChat.tb_user (user_id, password, nick_name, img) VALUES (4, 'yihen0214', '用户-d13ad84', 'https://baomidou.com/assets/asset.cIbiVTt__ZgvyzK.svg');
