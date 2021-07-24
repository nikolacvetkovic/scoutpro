-- cleaning --
drop schema scout_pro_development cascade;
drop user if exists scout_pro_dev;

-- creating --
create schema if not exists scout_pro_development;
create table if not exists scout_pro_development.player (
	id serial primary key,
	player_name varchar(256) not null,
    transfermarkt_url varchar(256) null unique,
    who_scored_url varchar(256) null,
    pes_db_url varchar(256) null,
    psml_url varchar(256) null,
    transfermarkt_last_measured timestamp not null,
    who_scored_last_measured timestamp not null,
    pes_db_last_measured timestamp not null,
    psml_last_measured timestamp not null);

create table if not exists scout_pro_development.transfermarkt_info(
	id int primary key,
    date_of_birth varchar(15) not null,
    age smallserial not null,
    nationality varchar(50) not null,
    national_team varchar(50) not null,
    club_team varchar(50) not null,
    contract_until varchar(12) not null,
    position varchar(40) not null,
    constraint fk_tm_player_id foreign key(id) references scout_pro_development.player(id) on delete no action on update no action);

    create index ix_transfermarkt_info_player_id on scout_pro_development.transfermarkt_info(player_id);

create table if not exists scout_pro_development.market_value(
	id serial primary key,
    worth numeric(15,2) not null,
    date_point date not null,
    club_team varchar(50) not null,
    player_id int not null,
    constraint fk_mv_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action);

	create index ix_market_value_player_id on scout_pro_development.market_value(player_id);

create table if not exists scout_pro_development.transfer(
	id serial primary key,
    from_team varchar(50) not null,
    to_team varchar(50) not null,
    date_of_transfer date not null,
    market_value varchar(25) not null,
    transfer_fee varchar(25) not null,
    player_id int not null,
    constraint fk_tr_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action);

	create index ix_transfer_player_id on scout_pro_development.transfer(player_id);

create table if not exists scout_pro_development.who_scored_info(
	id serial primary key,
    strengths varchar(512) not null,
    weaknesses varchar(512) not null,
    style_of_play varchar(512) not null,
    player_id int not null,
    constraint fk_who_scored_info_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action);

	create index ix_who_scored_info_player_id on scout_pro_development.who_scored_info(player_id);

create table if not exists scout_pro_development.statistics_by_competition(
	id serial primary key,
    competition varchar(100) not null,
    started_apps smallserial not null,
    subApps smallserial not null,
    mins smallserial not null,
    goals smallserial not null,
    assists smallserial not null,
    yellowCards smallserial not null,
    redCards smallserial not null,
    shotsPerGame numeric(5,2) not null,
    passSuccess numeric(5,2) not null,
    aerialsWon numeric(5,2) not null,
    manOfTheMatch smallserial not null,
    rating numeric(5,2) not null,
    who_scored_info_id int not null,
    constraint fk_stcomp_who_scored_info_id foreign key(who_scored_info_id) references scout_pro_development.who_scored_info(id) on delete no action on update no action);

	create index ix_stat_by_comp_who_scored_info_id on scout_pro_development.statistics_by_competition(who_scored_info_id);

create table if not exists scout_pro_development.statistics_by_position(
	id serial primary key,
    position varchar(3) not null,
    apps smallserial not null,
    goals smallserial not null,
    assists smallserial not null,
    rating numeric(5,2) not null,
	who_scored_info_id int not null,
    constraint fk_stpos_who_scored_info_id foreign key(who_scored_info_id) references scout_pro_development.who_scored_info(id) on delete no action on update no action);

	create index ix_stat_by_pos_who_scored_info_id on scout_pro_development.statistics_by_position(who_scored_info_id);

create table if not exists scout_pro_development.who_scored_game(
	id serial primary key,
    competition varchar(50) not null,
    date_of_game date not null,
    team1 varchar(50) not null,
    team2 varchar(50) not null,
    result varchar(5) not null,
    man_of_the_match boolean not null,
    goals smallserial not null,
    assists smallserial not null,
    yellow_card boolean not null,
    red_card boolean not null,
    minutes_played smallserial not null,
    rating numeric(5,2) not null,
	who_scored_info_id int not null,
    constraint fk_gm_who_scored_info_id foreign key(who_scored_info_id) references scout_pro_development.who_scored_info(id) on delete no action on update no action);

	create index ix_game_who_scored_info_id on scout_pro_development.who_scored_game(who_scored_info_id);

create table if not exists scout_pro_development.pes_db_info(
	id serial primary key,
    player_name varchar(256) not null,
    team_name varchar(50) not null,
    foot varchar(5) not null,
    week_condition char(1) not null,
    primary_position varchar(3) not null,
    other_strong_positions varchar(100) null,
    other_weak_positions varchar(100) null,
    attacking_prowess smallserial not null,
    ball_control smallserial not null,
    dribbling smallserial not null,
    low_pass smallserial not null,
    lofted_pass smallserial not null,
    finishing smallserial not null,
    place_kicking smallserial not null,
    swerve smallserial not null,
    header smallserial not null,
    defensive_prowess smallserial not null,
    ball_winning smallserial not null,
    kicking_power smallserial not null,
    speed smallserial not null,
    explosive_power smallserial not null,
    body_control smallserial not null,
    physical_contact smallserial not null,
    jump smallserial not null,
    stamina smallserial not null,
    goalkeeping smallserial not null,
    catching smallserial not null,
    clearing smallserial not null,
    reflexes smallserial not null,
    coverage smallserial not null,
    form smallserial not null,
    injury_resistance smallserial not null,
    weak_foot_usage smallserial not null,
    weak_foot_accuracy smallserial not null,
    overall_rating smallserial not null,
    playing_style varchar(30) not null,
    player_skills varchar(512) not null,
    com_playing_styles varchar(512) not null,
    player_id int not null,
    constraint fk_pdb_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action);

	create index ix_pes_db_info_player_id on scout_pro_development.pes_db_info(player_id);

create table if not exists scout_pro_development.psml_info(
	id int primary key,
    psml_team varchar(50) not null default 'Free Agent',
    psml_value numeric(15,2) null default 00.00,
    constraint fk_psml_player_id foreign key(id) references scout_pro_development.player(id) on delete no action on update no action);

 	create index ix_psml_info_player_id on scout_pro_development.psml_info(player_id);

create table if not exists scout_pro_development.psml_transfer(
    id serial primary key,
    from_team varchar(50) not null,
    to_team varchar(50) not null,
    date_of_transfer date not null,
    psml_info_id int not null,
    constraint fk_psmltr_psml_info_id foreign key(psml_info_id) references scout_pro_development.psml_info(id) on delete no action on update no action);

    create index ix_psml_transfer_psml_info_id on scout_pro_development.psml_transfer(psml_info_id);

create table if not exists scout_pro_development.app_user(
    id serial primary key,
    username varchar(50) not null unique,
    pass varchar(50) not null);

    create index ix_app_user_username on scout_pro_development.app_user(username);

create table if not exists scout_pro_development.user_player(
    user_id serial,
    player_id serial,
    my_player boolean not null,
    primary key(user_id, player_id),
    constraint fk_up_app_user_id foreign key(user_id) references scout_pro_development.app_user(id) on delete no action on update no action,
    constraint fk_up_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action);

create table if not exists scout_pro_development.app_role(
    id serial primary key,
    role_name varchar(10) not null);

create table if not exists scout_pro_development.user_role(
    user_id serial,
    role_id serial,
    primary key(user_id, role_id),
    constraint fk_ur_app_user_id foreign key(user_id) references scout_pro_development.app_user(id) on delete no action on update no action,
    constraint fk_ur_app_role_id foreign key(role_id) references scout_pro_development.app_role(id) on delete no action on update no action);

create table if not exists scout_pro_development.news(
    id serial primary key,
    content text,
    inserted_date timestamp,
    player_id int not null,
    constraint fk_news_player_id foreign key(player_id) references scout_pro_development.player(id) on delete no action on update no action);

    create index ix_news_player_id on scout_pro_development.news(player_id);

create table if not exists scout_pro_development.scrape_reg_expression(
    id serial primary key,
    field_name varchar(50) not null unique,
    regex varchar(128) not null);

    create index ix_scrape_reg_expression_field_name on scout_pro_development.scrape_reg_expression(field_name);

create user scout_pro_dev with encrypted password 'scout_pro_dev_user';
grant select, insert, update, delete on all tables in schema scout_pro_development to scout_pro_dev;