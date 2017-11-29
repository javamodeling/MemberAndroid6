package com.devpino.memberandroid;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by DHKOH on 2017-11-14.
 */

@Database(entities = {Member.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "memberdb";

    public abstract MemberDao memberDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }

    public List<Member> memberDao_searchAllMembers() {

        List<Member> members = null;

        try {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            Callable<List<Member>> callable = new Callable<List<Member>>() {

                @Override
                public List<Member> call() throws Exception {

                    return memberDao().searchAllMembers();
                }
            };

            Future<List<Member>> future = executorService.submit(callable);

            members = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (members == null) {

            members = new ArrayList<>();
        }

        return members;
    }

    public long memberDao_addMember(final Member member) {

        long result = -1;

        try {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            Callable<Long> callable = new Callable<Long>() {

                @Override
                public Long call() throws Exception {

                    return memberDao().addMember(member);
                }
            };

            Future<Long> future = executorService.submit(callable);

            result = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Member memberDao_getMember(final long no) {

        Member result = null;

        try {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            Callable<Member> callable = new Callable<Member>() {

                @Override
                public Member call() throws Exception {

                    return memberDao().getMember(no);
                }
            };

            Future<Member> future = executorService.submit(callable);

            result = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int memberDao_modifyMember(final Member member) {

        int result = -1;

        try {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            Callable<Integer> callable = new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {

                    return memberDao().modifyMember(member);
                }
            };

            Future<Integer> future = executorService.submit(callable);

            result = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int memberDao_removeMember(final long no) {

        int result = -1;

        try {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            Callable<Integer> callable = new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {

                    return memberDao().removeMember(no);
                }
            };

            Future<Integer> future = executorService.submit(callable);

            result = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }
}
