package com.example.projectsertifkasijmp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RegistrationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * from student ORDER BY id ASC")
    fun getAllRegistration() : LiveData<List<Student>>
}

