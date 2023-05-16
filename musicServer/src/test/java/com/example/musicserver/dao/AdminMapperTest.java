package com.example.musicserver.dao;

import com.example.musicserver.entity.Song;
import com.example.musicserver.entity.SongList;
import com.example.musicserver.entity.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author CCNICE
 * @date 2023/5/15
 */
@SpringBootTest
class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongListMapper songListMapper;

    @Test
    void test01() {

        ArrayList<HashMap<String, String>> objects = new ArrayList<>();

        HashMap<String, String> hashMap1 = new HashMap<>();
        List<UserData> userData1 = adminMapper.selectUserCount();
        hashMap1.put("totalUsers", userData1.get(0).getTotalUsers());
        hashMap1.put("recentUsers", userData1.get(0).getRecentUsers());

        HashMap<String, String> hashMap2 = new HashMap<>();
        List<UserData> userData2 = adminMapper.selectGenderCounter();
        hashMap2.put("male", userData2.get(0).getGenderCount());
        hashMap2.put("female", userData2.get(1).getGenderCount());
        hashMap2.put("unknown", userData2.get(2).getGenderCount());

        HashMap<String, String> hashMap3 = new HashMap<>();
        Song song = new Song();
        List<Song> songs = songMapper.selectSong(song);
        hashMap3.put("totalSongs", String.valueOf(songs.size()));

        HashMap<String, String> hashMap4 = new HashMap<>();
        SongList songList = new SongList();
        List<SongList> songLists = songListMapper.selectSongList(songList);
        hashMap4.put("totalSongList", String.valueOf(songLists.size()));

        HashMap<String, String> hashMap5 = new HashMap<>();
        List<Song> songPlayNum = adminMapper.selectPlayNum();
        hashMap5.put(songPlayNum.get(0).getName(), String.valueOf(songPlayNum.get(0).getPhoto()));
        hashMap5.put(songPlayNum.get(1).getName(), String.valueOf(songPlayNum.get(1).getPhoto()));
        hashMap5.put(songPlayNum.get(2).getName(), String.valueOf(songPlayNum.get(2).getPhoto()));
        hashMap5.put(songPlayNum.get(3).getName(), String.valueOf(songPlayNum.get(3).getPhoto()));
        hashMap5.put(songPlayNum.get(4).getName(), String.valueOf(songPlayNum.get(4).getPhoto()));
        hashMap5.put(songPlayNum.get(5).getName(), String.valueOf(songPlayNum.get(5).getPhoto()));
        hashMap5.put(songPlayNum.get(6).getName(), String.valueOf(songPlayNum.get(6).getPhoto()));
        hashMap5.put(songPlayNum.get(7).getName(), String.valueOf(songPlayNum.get(7).getPhoto()));
        hashMap5.put(songPlayNum.get(8).getName(), String.valueOf(songPlayNum.get(8).getPhoto()));
        hashMap5.put(songPlayNum.get(9).getName(), String.valueOf(songPlayNum.get(9).getPhoto()));

        objects.add(hashMap1);
        objects.add(hashMap2);
        objects.add(hashMap3);
        objects.add(hashMap4);
        objects.add(hashMap5);

        System.out.println(objects);
    }
}