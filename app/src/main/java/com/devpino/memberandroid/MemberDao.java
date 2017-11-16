package com.devpino.memberandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by DHKOH on 2017-11-14.
 */

@Dao
public interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addMember(Member member) ;

    @Query("DELETE FROM devpino_member WHERE _id = :rowId")
    public int removeMember(long rowId);

    @Query("SELECT * FROM devpino_member")
    public List<Member> searchAllMembers();

    @Query("SELECT * FROM devpino_member WHERE _id = :rowId")
    public Member getMember(long rowId) ;

    @Update
    public int modifyMember(Member member) ;
}
