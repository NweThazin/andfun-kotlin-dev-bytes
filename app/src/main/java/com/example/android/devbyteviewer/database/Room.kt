/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface VideoDao {
    @Query("SELECT * FROM databasevideo")
    fun getVideos(): MutableLiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase : RoomDatabase() {
    abstract val video: VideoDao
}

fun getDatabase(context: Context): VideosDatabase {
    synchronized(VideosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    VideosDatabase::class.java,
                    "videos"
            ).build()
        }
    }
    return INSTANCE
}

private lateinit var INSTANCE: VideosDatabase