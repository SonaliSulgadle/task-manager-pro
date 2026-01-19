package com.sonalisulgadle.taskmanagerpro.data.entity.task

import com.google.firebase.Timestamp

data class TaskDto(
    var title: String = "",
    var description: String? = null,
    var completed: Boolean = false,
    var createdAt: Timestamp = Timestamp.now(),
    var updatedAt: Timestamp = Timestamp.now()
) {
    constructor() : this("", null, false, Timestamp.now(), Timestamp.now())
}