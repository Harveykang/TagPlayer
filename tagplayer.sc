__command() -> (
	help()
);
__config() -> m( l('stay_loaded', true)); 
global_version = 'Version: 0.0.0-dev.20200121.0824';
global_tag = 'carpetBot';
global_bot_state = m();
__check_offline(player_name) ->(
	f_player = player(player_name);
	if (!f_player, 
		exit('��4����'+player_name+'δ����');
		''
	);
	if (!query(f_player,'has_tag',global_tag), 
		exit('��4'+f_player+'���Ǽ���');
		''
	)
);
__check_online(player_name) ->(
	f_player = player(player_name);
	if (f_player,
		if (query(f_player,'has_tag',global_tag), 
			exit('��4����'+f_player+'�Ѿ�������');
			''
		);
		exit('��4���'+f_player+'�Ѿ�������');
		''
	)
);
__check_pos(x) ->(
	x = number(x);
	if(x == null,
		exit('��4��������ȷ������');
		''
	);
	return(x)
);
__check_tick(tick) ->(
	tick = round(number(tick));
	if(tick == null,
		exit('��4��������ȷ����Ϸ�̼��');
		''
	);
	if(tick < 2,
		print(tick);
		exit(str('��4��������С��2��ȴ������%d',tick));
		''
	);
	return(tick)
);
__check_dim(s_player,f_player) ->(
	if (s_player~'dimension' != f_player~'dimension',
		exit('��4����ͬһά�ȣ�');
		''
	);
);
__player_list(i,player_list) ->(
	one_player_list = split(' ',join('',slice(player_list,i,i+1)));
	if(slice(one_player_list,0,1) == l('�ٵ�'),
		one_player_str = join('',slice(one_player_list,1,2));
		''
	);
	if(slice(one_player_list,0,1) != l('�ٵ�'),
		one_player_str = join('',one_player_list);
		''
	);
	return(one_player_str)
);
__on_statistic(player, category, stat, value) ->(
	if(query(player, 'has_tag', global_tag),
		if(stat == 'leave_game' && category == 'custom',
			modify(player, 'clear_tag', global_tag)
		)
	)
);
help() ->(
	print('--ʹ�ð���(/tagplayer)��');
	print('/tagplayer summon <�����>\n-���ɼ���');
	print('/tagplayer kill <�����>\n-ɾ������');
	print('/tagplayer killall\n-ɾ��ȫ������');
	print('/tagplayer tp <�����> <x> <y> <z>\n-���ͼ��ˣ�<x>��<y>��<z>���滻Ϊ��s�����������λ��');
	print('/tagplayer tp_at_<����> <�����> <x> <y> <z>\n-��<����/���>Ϊ����ԭ�㴫�ͼ���');
	print('/tagplayer check <�����>\n-������״̬');
	print('/tagplayer checkall\n- ������м���״̬');
	print('/tagplayer look <�����> <����> <ƫ��>\n-ת�������ӽǣ�<x>��<y>���滻Ϊ��s������������ӽǷ���');
	print('/tagplayer look_<up/down/east/west/south/north> <�����>\n-�ü�����<��/��/��/��/��/��>����');
	print('/tagplayer turn_<back/left/right> <�����>\n-�ü�����<��/��/��>����');
	print('/tagplayer move_<backward/forward/left/right/stop> <�����>\n-�ü���<��ǰ/���/����/����/ֹͣ>�ƶ�');
	print('/tagplayer <attack/drop/drop_stack/jump/swap_hands/use>_continuous <�����>\n-�ü��˳���<�������ھ�/��һ����Ʒ/��һ����Ʒ/��Ծ/����/ʹ����Ʒ>');
	print('/tagplayer <attack/drop/drop_stack/jump/swap_hands/use>_interval <�����> <����>\n-�ü���ÿ<����>��Ϸ��<����/��һ����Ʒ/��һ����Ʒ/��Ծ/����/ʹ����Ʒ>һ��');
	print('/tagplayer <attack/drop/drop_stack/jump/swap_hands/use>_once <�����>\n-�ü���<�������ھ�/��һ����Ʒ/��һ����Ʒ/��Ծ/����/ʹ����Ʒ>һ��');
	print('/tagplayer <attack/drop/drop_stack/jump/swap_hands/use>_stop <�����>\n-�ü���ֹͣ<�������ھ�/��һ����Ʒ/��һ����Ʒ/��Ծ/����/ʹ����Ʒ>�����û�ж������<����>_once����һ��');
	print('/tagplayer <sneak/unsneak> <�����>\n-�ü���<Ǳ��/վ��>');
	print('/tagplayer <sprint/unsprint> <�����>\n-�ü���׼��<����/����>����ˮ�м�������Ӿ');
	print('/tagplayer <mount/dismount> <�����>\n-�ü���<����/ж��>');
	print('/tagplayer stop <�����>\n-ֹͣ���˵�һ�ж���');
	print(global_version);
	''
);
reload() ->(
	run(str('script unload tagplayer'));
	run(str('script load tagplayer'));
	run(str('tellraw @a {"text":"tagplayer���سɹ���"}'));
	''
);
summon(player_name) ->(
	__check_online(player_name);
	s_player = player();
	run(str('player %s spawn at %f %f %f', player_name, s_player~'x', s_player~'y', s_player~'z'));
	if (!(player(player_name)),
		exit('��4����ʧ��')
	);
	modify(player(player_name), 'tag', global_tag);
	print('��Ϊ'+player_name+'��ӱ�ǩ�Ͷ���');
	game_tick(50);
	f_player = player(player_name);
	global_bot_state:f_player = m();
	''
);
kill(player_name) ->(
	__check_offline(player_name);
	run(str('player %s kill', player_name));
	game_tick(50);
	print('�����'+player_name);
	''
);
killall() ->(
	i = 0;
	player_list = player('all');
	loop(2147483647,,,
		one_player_str = __player_list(i,player_list);
		if(one_player_str == '',
			break()
		);
		if(query(player(one_player_str),'has_tag',global_tag),
			do_fake_player = 1
		);
		run(str('player %s kill', one_player_str)); 
		i += 1
	);
	if(do_fake_player != 1,
		exit('��4�����ڼ���');
		''
	);
	print('�ѽ�ȫ�������ƽ���6FZ\'sDataPack��f�����ǩ');
	game_tick(50);
	print('�����ȫ������');
	''
);
//����
attack_continuous(player_name) ->(
	__check_offline(player_name);
	run(str('player %s attack continuous', player_name));
	global_bot_state:player(player_name):'attack' = '1';
	''
);
attack_interval(player_name,tick) ->(
	__check_offline(player_name);
	tick = __check_tick(tick);
	run(str('player %s attack interval %d', player_name, tick));
	global_bot_state:player(player_name):'attack' = tick;
	''
);
attack_once(player_name) ->(
	__check_offline(player_name);
	run(str('player %s attack once', player_name));
	delete(global_bot_state:player(player_name):'attack');
	''
);
attack_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s attack', player_name));
	delete(global_bot_state:player(player_name):'attack');
	''
);
//����
drop_continuous(player_name) ->(
	__check_offline(player_name);
	run(str('player %s drop continuous', player_name));
	global_bot_state:player(player_name):'drop' = '1';
	''
);
drop_interval(player_name,tick) ->(
	__check_offline(player_name);
	tick = __check_tick(tick);
	run(str('player %s drop interval %d', player_name, tick));
	global_bot_state:player(player_name):'drop' = tick;
	''
);
drop_once(player_name) ->(
	__check_offline(player_name);
	run(str('player %s drop once', player_name));
	delete(global_bot_state:player(player_name):'drop');
	''
);
drop_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s drop', player_name));
	delete(global_bot_state:player(player_name):'drop');
	''
);
//����һ��
drop_stack_continuous(player_name) ->(
	__check_offline(player_name);
	run(str('player %s dropStack continuous', player_name));
	global_bot_state:player(player_name):'drop_stack' = '1';
	''
);
drop_stack_interval(player_name,tick) ->(
	__check_offline(player_name);
	tick = __check_tick(tick);
	run(str('player %s dropStack interval %d', player_name, tick));
	global_bot_state:player(player_name):'drop_stack' = tick;
	''
);
drop_stack_once(player_name) ->(
	__check_offline(player_name);
	run(str('player %s dropStack once', player_name));
	delete(global_bot_state:player(player_name):'drop_stack');
	''
);
drop_stack_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s dropStack', player_name));
	delete(global_bot_state:player(player_name):'drop_stack');
	''
);
//��Ծ
jump_continuous(player_name) ->(
	__check_offline(player_name);
	run(str('player %s jump continuous', player_name));
	global_bot_state:player(player_name):'jump' = '1';
	''
);
jump_interval(player_name,tick) ->(
	__check_offline(player_name);
	tick = __check_tick(tick);
	run(str('player %s jump interval %d', player_name, tick));
	global_bot_state:player(player_name):'jump' = tick;
	''
);
jump_once(player_name) ->(
	__check_offline(player_name);
	run(str('player %s jump once', player_name));
	delete(global_bot_state:player(player_name):'jump');
	''
);
jump_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s jump', player_name));
	delete(global_bot_state:player(player_name):'jump');
	''
);
//����
swap_hands_continuous(player_name) ->(
	__check_offline(player_name);
	run(str('player %s swapHands continuous', player_name));
	global_bot_state:player(player_name):'swap_hands' = '1';
	''
);
swap_hands_interval(player_name,tick) ->(
	__check_offline(player_name);
	tick = __check_tick(tick);
	run(str('player %s swapHands interval %d', player_name, tick));
	global_bot_state:player(player_name):'swap_hands' = tick;
	''
);
swap_hands_once(player_name) ->(
	__check_offline(player_name);
	run(str('player %s swapHands once', player_name));
	delete(global_bot_state:player(player_name):'swap_hands');
	''
);
swap_hands_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s swapHands', player_name));
	delete(global_bot_state:player(player_name):'swap_hands');
	''
);
//�Ҽ�
use_continuous(player_name) ->(
	__check_offline(player_name);
	run(str('player %s use continuous', player_name));
	global_bot_state:player(player_name):'use' = '1';
	''
);
use_interval(player_name,tick) ->(
	__check_offline(player_name);
	tick = __check_tick(tick);
	run(str('player %s use interval %d', player_name, tick));
	global_bot_state:player(player_name):'use' = tick;
	''
);
use_once(player_name) ->(
	__check_offline(player_name);
	run(str('player %s use once', player_name));
	delete(global_bot_state:player(player_name):'use');
	''
);
use_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s use', player_name));
	delete(global_bot_state:player(player_name):'use');
	''
);
//���
mount(player_name) ->(
	__check_offline(player_name);
	run(str('player %s mount', player_name));
	''
);
dismount(player_name) ->(
	__check_offline(player_name);
	run(str('player %s dismount', player_name));
	''
);
//���
sprint(player_name) ->(
	__check_offline(player_name);
	run(str('player %s sprint', player_name));
	''
);
unsprint(player_name) ->(
	__check_offline(player_name);
	run(str('player %s unsprint', player_name));
	''
);
//�¶�
sneak(player_name) ->(
	__check_offline(player_name);
	run(str('player %s sneak', player_name));
	''
);
unsneak(player_name) ->(
	__check_offline(player_name);
	run(str('player %s unsneak', player_name));
	''
);
//ת��
turn_back(player_name) ->(
	__check_offline(player_name);
	run(str('player %s turn back', player_name));
	''
);
turn_left(player_name) ->(
	__check_offline(player_name);
	run(str('player %s turn left', player_name));
	''
);
turn_right(player_name) ->(
	__check_offline(player_name);
	run(str('player %s turn right', player_name));
	''
);
//����
look(player_name,x,y) ->(
	__check_offline(player_name);
	s_player = player();
	if(x == 's',
		x = s_player~'yaw';
	);
	if(y == 's',
		y = s_player~'pitch'
	);
	x = __check_pos(x);
	y = __check_pos(y);
	if(x > 180,
		x = x - 360
	);
	if(x < -180,
		x = x + 360
	);
	if(y > 90,
		y = y - 180
	);
	if(y < -90,
		y = y + 180
	);
	modify(player(player_name), 'yaw', x);
	modify(player(player_name), 'pitch', y);
	print(str('�ѽ�'+player_name+'���ӽ�ָ���a[%.1f,%.1f]',x,y));
	''
);
look_up(player_name) ->(
	__check_offline(player_name);
	run(str('player %s look up', player_name));
	''
);
look_down(player_name) ->(
	__check_offline(player_name);
	run(str('player %s look down', player_name));
	''
);
look_east(player_name) ->(
	__check_offline(player_name);
	run(str('player %s look east', player_name));
	''
);
look_north(player_name) ->(
	__check_offline(player_name);
	run(str('player %s look north', player_name));
	''
);
look_south(player_name) ->(
	__check_offline(player_name);
	run(str('player %s look south', player_name));
	''
);
look_west(player_name) ->(
	__check_offline(player_name);
	run(str('player %s look west', player_name));
	''
);
//�ƶ�
move_backward(player_name) ->(
	__check_offline(player_name);
	run(str('player %s move backward', player_name));
	global_bot_state:player(player_name):'move_vertical' = 'backward';
	''
);
move_forward(player_name) ->(
	__check_offline(player_name);
	run(str('player %s move forward', player_name));
	global_bot_state:player(player_name):'move_vertical' = 'forward';
	''
);
move_left(player_name) ->(
	__check_offline(player_name);
	run(str('player %s move left', player_name));
	global_bot_state:player(player_name):'move_transverse' = 'left';
	''
);
move_right(player_name) ->(
	__check_offline(player_name);
	run(str('player %s move right', player_name));
	global_bot_state:player(player_name):'move_transverse' = 'right';
	''
);
move_stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s move', player_name));
	delete(global_bot_state:player(player_name):'move');
	''
);
//ֹͣ
stop(player_name) ->(
	__check_offline(player_name);
	run(str('player %s stop', player_name));
	''
);
//����
tp(player_name,x,y,z) ->(
	__check_offline(player_name);
	s_player = player();
	f_player = player(player_name);
	if(x == 's',
		__check_dim(s_player,f_player);
		x = s_player~'x'
	);
	if(y == 's',
		__check_dim(s_player,f_player);
		y = s_player~'y'
	);
	if(z == 's',
		__check_dim(s_player,f_player);
		z = s_player~'z'
	);
	x = __check_pos(x);
	y = __check_pos(y);
	if(y>4096,
		exit('��4yֵ���ܳ���4096��');
		''
	);
	z = __check_pos(z);
	modify(f_player,'pos',x,y,z);
	print(str('�ѽ�'+player_name+'��������a[%.2f,%.2f,%.2f]',x,y,z));
	''
);
tp_at_player(player_name,x,y,z) ->(
	__check_offline(player_name);
	s_player = player();
	f_player = player(player_name);
	__check_dim(s_player,f_player);
	x = s_player~'x'+__check_pos(x);
	y = s_player~'y'+__check_pos(y);
	if(y>4096,
		exit('��4yֵ���ܳ���4096��');
		''
	);
	z = s_player~'z'+__check_pos(z);
	modify(f_player,'pos',x,y,z);
	print(str('�ѽ�'+player_name+'��������a[%.2f,%.2f,%.2f]',x,y,z));
	''
);
tp_at_bot(player_name,x,y,z) ->(
	__check_offline(player_name);
	f_player = player(player_name);
	x = f_player~'x'+__check_pos(x);
	y = f_player~'y'+__check_pos(y);
	if(y>4096,
		exit('��4yֵ���ܳ���4096��');
		''
	);
	z = f_player~'z'+__check_pos(z);
	modify(f_player,'pos',x,y,z);
	print(str('�ѽ�'+player_name+'��������a[%.2f,%.2f,%.2f]',x,y,z));
	''
);
//״̬���
check(player_name) ->(
	__check_offline(player_name);
	f_player = player(player_name);
	print('���ˡ�'+f_player+'����');
	//Ѫ��
	inner_health = number(str('%d',query(f_player,'health')+0.9));
	if(inner_health <= 5,
		inner_color = '��4';
	);
	if(inner_health > 5 && inner_health <= 10,
		inner_color = '��c';
	);
	if(inner_health > 10 && inner_health <= 15,
		inner_color = '��e';
	);
	if(inner_health > 15,
		inner_color = '��a';
	);
	print('- Ѫ����'+inner_color+inner_health);
	//λ��
	if(f_player~'dimension' == 'overworld',
		f_dimension = '��2������'
	);
	if(f_player~'dimension' == 'the_nether',
		f_dimension = '��4����'
	);
	if(f_player~'dimension' == 'the_end',
		f_dimension = '��7ĩ��'
	);
	print(str('- λ�ڣ�%s��a[%.2f,%.2f,%.2f]',f_dimension,f_player~'x',f_player~'y',f_player~'z'));
	//ָ��
	f_yaw = f_player~'yaw';
	if(f_yaw > 180,
		f_yaw = f_yaw - 360
	);
	if(f_yaw < -180,
		f_yaw = f_yaw + 360
	);
	if(f_yaw > -112.5 && f_yaw <= -67.5,
		f_yaw_curt = '��'
	);
	if(f_yaw > -22.5 && f_yaw <= 22.5,
		f_yaw_curt = '��'
	);
	if(f_yaw > 67.5 && f_yaw <= 112.5,
		f_yaw_curt = '��'
	);
	if(f_yaw > 157.5 || f_yaw <= -157.5,
		f_yaw_curt = '��'
	);
	if(f_yaw > -157.5 && f_yaw <= -112.5,
		f_yaw_curt = '����'
	);
	if(f_yaw > -67.5 && f_yaw <= -22.5,
		f_yaw_curt = '����'
	);
	if(f_yaw > 112.5 && f_yaw <= 157.5,
		f_yaw_curt = '����'
	);
	if(f_yaw > 22.5 && f_yaw <= 67.5,
		f_yaw_curt = '����'
	);
	f_pitch = f_player~'pitch';
	if(f_pitch > -15 && f_pitch <= 15,
		f_pitch_curt = 'ǰ��'
	);
	if(f_pitch > 15 && f_pitch <= 75,
		f_pitch_curt = 'б�·�'
	);
	if(f_pitch > 75 && f_pitch <= 90,
		f_pitch_curt = '�·�'
	);
	if(f_pitch > -75 && f_pitch <= -15,
		f_pitch_curt = 'б�Ϸ�'
	);
	if(f_pitch >= -90 && f_pitch <= -75,
		f_pitch_curt = '�Ϸ�'
	);
	print(str('- ָ�򣺡�e%s,%s��a[%.1f,%.1f]',f_yaw_curt,f_pitch_curt,f_yaw,f_pitch));
	f_state = global_bot_state:f_player;
	//Ǳ��/����/��Ӿ
	inner_sneak = query(f_player,'sneaking');
	inner_sprint = query(f_player,'sprinting');
	inner_swim = query(f_player,'swimming');
	if(f_state == m() && inner_sneak == false && inner_sprint == false,
		exit(
			print('- �޶���');
			''
		)
	);
	//����/�ھ�
	if(f_state:'attack' != null,
		if(f_state:'attack' == 1,
			print('- ���ڷ�������Ȼ���ѣ����ھ�')
		);
		if(f_state:'attack' > 1,
			inner_sec = f_state:'attack'/20;
			print('- ÿ'+f_state:'attack'+'��Ϸ�̣�'+inner_sec+'��Ϸ�룩���Թ���һ��')
		)
	);
	//�Ӷ���
	if(f_state:'drop' != null,
		inner_sec = f_state:'drop'/20;
		print('- ÿ'+f_state:'drop'+'��Ϸ�̣�'+inner_sec+'��Ϸ�룩��1����Ʒ')
	);
	if(f_state:'drop_stack' != null,
		inner_sec = f_state:'drop_stack'/20;
		print('- ÿ'+f_state:'drop_stack'+'��Ϸ�̣�'+inner_sec+'��Ϸ�룩��1����Ʒ')
	);
	//��Ծ
	if(f_state:'jump' != null,
		if(f_state:'jump' == 1,
			print('- ���ڳ�����Ծ')
		);
		if(f_state:'jump' > 1,
			inner_sec = f_state:'jump'/20;
			print('- ÿ'+f_state:'jump'+'��Ϸ�̣�'+inner_sec+'��Ϸ�룩������Ծһ��')
		)
	);
	//����
	if(f_state:'swap_hands' != null,
		inner_sec = f_state:'swap_hands'/20;
		print('- ÿ'+f_state:'swap_hands'+'��Ϸ�̣�'+inner_sec+'��Ϸ�룩����һ�������ֵ���Ʒ')
	);
	//ʹ����Ʒ
	if(f_state:'use' != null,
		if(f_state:'use' == 1,
			print('- ���ڳ���ʹ������Ʒ')
		);
		if(f_state:'use' > 1,
			inner_sec = f_state:'use'/20;
			print('- ÿ'+f_state:'use'+'��Ϸ�̣�'+inner_sec+'��Ϸ�룩����ʹ��һ�����Ʒ')
		)
	);
	//�ƶ�**********************************�ص�*********************************
	if(f_state:'move_vertical' != null || f_state:'move_transverse' != null,
		if(inner_sneak == true,
			if(f_state:'move_vertical' == 'forward',
				if(f_state:'move_transverse' == 'right',
					print('- ��������ǰ��Ǳ�ШJ')
				);
				if(f_state:'move_transverse' == 'left',
					print('- ��������ǰ��Ǳ�ШI')
				);
				if(f_state:'move_transverse' == null,
					print('- ������ǰǱ�С�');
				)
			);
			if(f_state:'move_vertical' == 'backward',
				if(f_state:'move_transverse' == 'right',
					print('- �������Һ�Ǳ�ШK')
				);
				if(f_state:'move_transverse' == 'left',
					print('- ���������Ǳ�ШL')
				);
				if(f_state:'move_transverse' == null,
					print('- �������Ǳ�С�');
				)
			)
		);
		if(inner_sprint == true,
			if(inner_swim == true,
				if(f_state:'move_vertical' == 'forward',
					if(f_state:'move_transverse' == 'right',
						print('- ��������ǰ����Ӿ�J')
					);
					if(f_state:'move_transverse' == 'left',
						print('- ��������ǰ����Ӿ�I')
					);
					if(f_state:'move_transverse' == null,
						print('- ������ǰ��Ӿ��');
					)
				);
				if(f_state:'move_vertical' == 'backward',
					if(f_state:'move_transverse' == 'right',
						print('- �������Һ���Ӿ�K')
					);
					if(f_state:'move_transverse' == 'left',
						print('- �����������Ӿ�L')
					);
					if(f_state:'move_transverse' == null,
						print('- ���������Ӿ��');
					)
				)
			);
			if(inner_swim == false,
				if(f_state:'move_vertical' == 'forward',
					if(f_state:'move_transverse' == 'right',
						print('- ��������ǰ�����ܨJ')
					);
					if(f_state:'move_transverse' == 'left',
						print('- ��������ǰ�����ܨI')
					);
					if(f_state:'move_transverse' == null,
						print('- ������ǰ���ܡ�');
					)
				);
				if(f_state:'move_vertical' == 'backward',
					if(f_state:'move_transverse' == 'right',
						print('- �������Һ󷽼��ܨK��e��û�������Կ��ң�')
					);
					if(f_state:'move_transverse' == 'left',
						print('- ��������󷽼��ܨL��e��û�������Կ��ң�')
					);
					if(f_state:'move_transverse' == null,
						print('- ��������ܡ���e��û�������Կ��ң�');
					)
				)
			);
			
		);
		if(inner_sprint == false && inner_sneak == false,
			if(f_state:'move_vertical' == 'forward',
				if(f_state:'move_transverse' == 'right',
					print('- ��������ǰ���ߨJ')
				);
				if(f_state:'move_transverse' == 'left',
					print('- ��������ǰ���ߨI')
				);
				if(f_state:'move_transverse' == null,
					print('- ������ǰ�ߡ�');
				)
			);
			if(f_state:'move_vertical' == 'backward',
				if(f_state:'move_transverse' == 'right',
					print('- �������Һ��ߨK')
				);
				if(f_state:'move_transverse' == 'left',
					print('- ����������ߨL')
				);
				if(f_state:'move_transverse' == null,
					print('- ��������ߡ�');
				)
			);
		)
	);
	if(f_state:'move_vertical' == null && f_state:'move_transverse' == null && inner_sneak == true,
		print('- ����Ǳ��')
	);
	if(f_state:'move_vertical' == null && f_state:'move_transverse' == null && inner_sprint == true && inner_swim == false,
		print('- ����ԭ�ؼ���')
	);
	if(f_state:'move_vertical' == null && f_state:'move_transverse' == null && inner_swim == true,
		print('- ����ԭ����Ӿ')
	);
	''
);
checkall() ->(
	i = 0;
	player_list = player('all');
	loop(2147483647,,,
		one_player_str = __player_list(i,player_list);
		if(one_player_str == '',
			break()
		);
		if(query(player(one_player_str),'has_tag',global_tag),
			check(one_player_str);
			do_fake_player = 1
		);
		i += 1
	);
	if(do_fake_player != 1,
		print('��4��ǰ�޼���')
	);
	''
);