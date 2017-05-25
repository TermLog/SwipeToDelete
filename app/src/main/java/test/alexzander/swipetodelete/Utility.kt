package test.alexzander.swipetodelete

import test.alexzander.swipetodelete.sample.User

    fun prepareContactList(count: Int): MutableList<User> {
        val result = ArrayList<User>(count)
        (0..count - 1).mapTo(result) { User(it, "NAME " + it.toString()) }
        return result
    }
