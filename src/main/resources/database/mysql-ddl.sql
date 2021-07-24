-- cleaning --
drop schema if exists scout_pro_development;
drop user if exists 'scout_pro_dev'@'localhost';

-- creating --
create schema if not exists scout_pro_development;

create table if not exists scout_pro_development.player (
	id bigint auto_increment not null,
	player_name varchar(256) not null,
    transfermarkt_url varchar(256) null unique,
    who_scored_url varchar(256) null,
    pes_db_url varchar(256) null,
    psml_url varchar(256) null,
    transfer_last_check timestamp null,
    market_value_last_check timestamp null,
	statistic_last_check timestamp null,
	inserted timestamp not null default current_timestamp,
    primary key(id))
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.transfermarkt_info(
	id bigint not null,
    date_of_birth varchar(15) not null,
    age int not null,
    nationality varchar(50) not null,
    national_team varchar(50),
    club_team varchar(50) not null,
    contract_until varchar(12) not null,
    position varchar(40) not null,
    primary key(id),
    constraint fk_tm_player_id foreign key(id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.market_value(
	id bigint auto_increment not null,
    worth decimal(15,2) not null,
    date_point date not null,
    club_team varchar(50) not null,
    player_id bigint not null,
    primary key(id),
    index ix_market_value_player_id(player_id ASC),
    constraint fk_market_value_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.transfer(
	id bigint auto_increment not null,
    from_team varchar(50) not null,
    to_team varchar(50) not null,
    date_of_transfer date not null,
    market_value varchar(25) not null,
    transfer_fee varchar(25) not null,
    player_id bigint not null,
    primary key(id),
    index ix_transfer_player_id(player_id ASC),
    constraint fk_transfer_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.characteristic(
	id bigint auto_increment not null,
    strengths varchar(512) null,
    weaknesses varchar(512) null,
    style_of_play varchar(512) null,
    primary key(id),
    constraint fk_characteristic_player_id foreign key(id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.competition_statistic(
	id bigint auto_increment not null,
    competition varchar(100) not null,
    started_apps int not null,
    sub_apps int not null,
    mins int not null,
    goals int not null,
    assists int not null,
    yellow_cards int not null,
    red_cards int not null,
    shots_per_game decimal(5,2) not null,
    pass_success decimal(5,2) not null,
    aerials_won decimal(5,2) not null,
    man_of_the_match int not null,
    rating decimal(5,2) not null,
    player_id bigint not null,
    primary key(id),
    index ix_compst_player_id(player_id ASC),
    constraint fk_compst_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
	engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.position_statistic(
	id bigint auto_increment not null,
    position varchar(3) not null,
    apps int not null,
    goals int not null,
    assists int not null,
    rating decimal(5,2) not null,
	player_id bigint not null,
	primary key(id),
    index ix_posst_player_id(player_id ASC),
    constraint fk_posst_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
	engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.game_statistic(
	id bigint auto_increment not null,
    competition varchar(50) not null,
    date_of_game date not null,
    team1 varchar(50) not null,
    team2 varchar(50) not null,
    result varchar(5) not null,
    man_of_the_match boolean not null,
    goals int not null,
    assists int not null,
    yellow_card boolean not null,
    red_card boolean not null,
    minutes_played int not null,
    rating decimal(5,2) not null,
	player_id bigint not null,
    primary key(id),
    index ix_gamest_player_id(player_id ASC),
    constraint fk_gamest_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
	engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.pes_db_info(
	id bigint auto_increment not null,
	player_name varchar(256) not null,
    team_name varchar(50) not null,
    foot varchar(5) not null,
    week_condition char(1) not null,
    primary_position varchar(3) not null,
    other_strong_positions varchar(100) null,
    other_weak_positions varchar(100) null,
    offensive_awareness int not null,
    ball_control int not null,
    dribbling int not null,
    tight_possession int not null,
    low_pass int not null,
    lofted_pass int not null,
    finishing int not null,
    heading int not null,
    place_kicking int not null,
    curl int not null,
    speed int not null,
    acceleration int not null,
    kicking_power int not null,
    jump int not null,
    physical_contact int not null,
    balance int not null,
    stamina int not null,
    defensive_awareness int not null,
    ball_winning int not null,
    aggression int not null,
    gk_awareness int not null,
    gk_catching int not null,
    gk_clearing int not null,
    gk_reflexes int not null,
    gk_reach int not null,
    weak_foot_usage int not null,
    weak_foot_accuracy int not null,
    form int not null,
    injury_resistance int not null,
    overall_rating int not null,
    playing_style varchar(30) null,
    player_skills varchar(512) null,
    com_playing_styles varchar(512) null,
    last_check timestamp null,
    primary key(id),
    constraint fk_pes_db_info_player_id foreign key(id) references scout_pro_development.player(id) on delete no action on update no action)
	engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.psml_info(
	id bigint not null,
    psml_team varchar(50) not null default 'Free Agent',
    psml_value decimal(15,2) null default 00.00,
    last_check timestamp null,
    primary key(id),
    constraint fk_psml_player_id foreign key(id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.psml_transfer(
    id bigint auto_increment not null,
    from_team varchar(50) not null,
    to_team varchar(50) not null,
    transfer_fee decimal(15,2) default 00.00,
    date_of_transfer datetime not null,
    psml_info_id bigint not null,
    primary key(id),
    index ix_psml_transfer_psml_info_id(psml_info_id ASC),
    constraint fk_psml_transfer_psml_info_id foreign key(psml_info_id) references scout_pro_development.psml_info(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.app_user(
    id bigint auto_increment not null,
    username varchar(50) not null unique,
    pass varchar(50) not null,
    enabled boolean not null,
    primary key(id),
    index ix_app_user_username(username ASC))
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.app_user_player(
    app_user_id bigint not null,
    player_id bigint not null,
    my_player boolean not null,
    primary key(app_user_id, player_id),
    constraint fk_up_app_user_id foreign key(app_user_id) references scout_pro_development.app_user(id) on delete no action on update no action,
    constraint fk_up_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.app_role(
    id bigint auto_increment not null,
    role_name varchar(10) not null,
    primary key(id))
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.app_user_role(
    app_user_id bigint not null,
    app_role_id bigint not null,
    primary key(app_user_id, app_role_id),
    constraint fk_ur_app_user_id foreign key(app_user_id) references scout_pro_development.app_user(id) on delete no action on update no action,
    constraint fk_ur_app_role_id foreign key(app_role_id) references scout_pro_development.app_role(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.news(
    id bigint auto_increment not null,
    content text,
    inserted_date timestamp null,
    player_id bigint not null,
    primary key(id),
    index ix_news_player_id (player_id ASC),
    constraint fk_news_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action)
    engine = InnoDB
    collate = utf8_unicode_ci;

create table if not exists scout_pro_development.scrape_reg_expression(
    id bigint auto_increment not null,
    field_name varchar(50) not null unique,
    regex varchar(128) not null,
    primary key(id),
    index ix_scrape_reg_expression_field_name (field_name ASC))
    engine = InnoDB
    collate = utf8_unicode_ci;

create user 'scout_pro_dev'@'localhost' identified by 'scout_pro_dev_user';
grant select, insert, update, delete on scout_pro_development.* to 'scout_pro_dev'@'localhost';