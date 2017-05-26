# SwipeToDelete

# Features
* Based on interface implementation.
* Supports pending deletion with bottom layout.
* Full customization of upper layout and bottom layout.
* Easy way to handle deletion. All you need is to call **removeItem(key: K)**  which should be overriden in your adapter and to invoke **SwipeToDeleteAdapter.removeItem(key: K)** at the end of method's definition.

# Usage

* Implement **ISwipeToDeleteAdapter** in your own adapter or another class.
* Make instance of **SwipeToDeleteAdapter** in your own adapter 

```kotlin
SwipeToDeleteAdapter(context = context, items = mutableList, swipeToDeleteAdapter = this)
```

* Call corresponding methods in your overrided methods

```kotlin
 override fun onBindViewHolder(holder: Holder, position: Int) {
        swipeToDeleteAdapter.onBindViewHolder(holder, mutableList[position].name, position)
    }
    
 override fun removeItem(key: String) {
        swipeToDeleteAdapter.removeItem(key)
    }
```


* Implement **ISwipeToDeleteHolder** in your holder. You need to have container with your regular item layout and if you need bottom container too.
* In your holder you need to have **var isPendingDelete** as false by default and you need to override **val topContainer**

```kotlin
override val topContainer: View
            get() =
            if (isPendingDelete) undoContainer
            else itemContainer

override var isPendingDelete: Boolean = false
```


* Also you need to make default key in holder by yourself 

```kotlin
override var key: Int = -1
```


* If you need bottom container appearence while waiting, simply override **onBindPendingItem(holder: Holder, key: Int, item: User)** method:

```kotlin
override fun onBindPendingItem(holder: Holder, key: Int, item: User) {
...
}
```


* To get more information refer our [usage example](https://github.com/agilie/SwipeToDelete/tree/master/app)
