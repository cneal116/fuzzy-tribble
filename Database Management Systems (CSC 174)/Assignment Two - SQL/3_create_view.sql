CREATE VIEW Film_Sound_Track(film_title, item_ID_st, music_dir) As
	Select I.Title, ST.Item_ID, ST.Music_Director_Name 
	From Item as I, Sound_Track as ST, Music_CD as M
	Where ST.Item_ID = M.Item_ID && M.Item_ID = I.Item_ID;

