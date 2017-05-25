# SwipeToDelete

# FEATURES
* Interface implementation. You don`t need to extend any class.
* Support pending deletion with bottom layout.
* Full customization of upper layout and bottom layout.
* Easy way to handle deletion. All what you need it`s call **removeItem(key: K)**  which should be overriden in your adapter. And invoke **SwipeToDeleteAdapter.removeItem(key: K)** in the end of definition of method.

# USAGE

* Implement **ISwipeToDeleteAdapter** via your own adapter or anouther class.
* Make instance of **SwipeToDeleteAdapter** in your own adapter **SwipeToDeleteAdapter(context = context, items = mutableList, swipeToDeleteAdapter = this**
* Call some corresponding methods in your overrided methods
```
 override fun onBindViewHolder(holder: Holder, position: Int) {
        swipeToDeleteAdapter.onBindViewHolder(holder, mutableList[position].name, position)
    }
    
 override fun removeItem(key: String) {
        swipeToDeleteAdapter.removeItem(key)
    }
```


* Implement **ISwipeToDeleteHolder** via your holder. You need to have container with your regular item layout and if you need bottom container too.
* In your holder you need to have **var isPendingDelete** as false by default and you need to override **val topContainer**
```
override val topContainer: View
            get() =
            if (isPendingDelete) undoContainer
            else itemContainer

override var isPendingDelete: Boolean = false
```


* Also you need to make default key in holder by you self 
```
override var key: Int = -1
```


* If you need bottom container appearence while waiting, You can override **onBindPendingItem(holder: Holder, key: Int, item: User)**
```
override fun onBindPendingItem(holder: Holder, key: Int, item: User) {
...
}
```


* For get more information you can refer to the [example](https://github.com/agilie/SwipeToDelete/tree/master/app)
