# SwipeToDelete

[ ![Download](https://api.bintray.com/packages/agilie/maven/Swipe2Delete/images/download.svg) ](https://bintray.com/agilie/maven/Swipe2Delete/_latestVersion)

<img src="https://user-images.githubusercontent.com/1777595/27508401-815a208a-58ec-11e7-838a-5ef06a1c4482.gif" width="32%"> 

# What is SwipeToDelete?

SwipeToDelete is a library you can use to simplify implementation of well-known (I believe for every Android developer :) ) swipe-to-delete behavior for your data lists. The main idea is that user has some time to undo unwanted *delete* operation. Swipe twice to remove the row immediately! Also, this library is fully customizable, feel free to use your own UI for *undo* layer, add extra buttons etc. 

# Features
* Based on interface implementation;
* Supports pending deletion with bottom layout;
* Full customization of upper layout and bottom layout;
* Easy way to handle deletion. All you need is to call **removeItem(key: K)**  which should be overriden in your adapter and to invoke **swipeToDeleteDelegate.removeItem(key: K)** at the end of method's definition.

# Usage

### Gradle

Add dependency in your `build.gradle` file:
````gradle
compile 'com.agilie:swipe2delete:1.0'
````

### Maven
Add rependency in your `.pom` file:
````xml
<dependency>
  <groupId>com.agilie</groupId>
  <artifactId>swipe2delete</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
````

### How does it work?

* Implement **ISwipeToDeleteAdapter** in your own adapter or another class.
* Make instance of **SwipeToDeleteDelegate** in your own adapter 

```kotlin
SwipeToDeleteDelegate(context = context, items = mutableList, swipeToDeleteDelegate = this)
```

* Call corresponding methods in your overrided methods

```kotlin
 override fun onBindViewHolder(holder: Holder, position: Int) {
        swipeToDeleteDelegate.onBindViewHolder(holder, mutableList[position].name, position)
    }
    
 override fun removeItem(key: String) {
        swipeToDeleteDelegate.removeItem(key)
    }
```


* Implement **ISwipeToDeleteHolder** in your holder. You need to have container with your regular item layout and if you need bottom container too.
* In your holder you need to have **var pendingDelete** as false by default and you need to override **val topContainer**

```kotlin
override val topContainer: View
            get() =
            if (pendingDelete) undoContainer
            else itemContainer

override var pendingDelete: Boolean = false
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
* Also you can implement **IAnimationUpdateListener** and **IAnimatorListener** to override methods to achieve animation along the axis x with duration which equally deleting duration

```
UserAdapter(...) : ... , IAnimationUpdateListener {
...
  fun onAnimationUpdated(animation: android.animation.ValueAnimator?, options: ModelOptions<*>) {}
  
  fun onAnimationEnd(animation: Animator?, options: ModelOptions<*>) {}
  
  fun onAnimationCancel(animation: Animator?, options: ModelOptions<*>) {}

  fun onAnimationStart(animation: Animator?, options: ModelOptions<*>) {}
  
  fun onAnimationRepeat(animation: Animator, options: ModelOptions<*>) {}
}
```

To get more information refer our [usage example](app/). Just clone the project and run this module. Examples are provided in [Java](app/src/main/java/agilie/example/swipe2delete/java/) and [Kotlin](app/src/main/java/agilie/example/swipe2delete/kotlin/) as well!

## Troubleshooting
Problems? Check the [Issues](https://github.com/agilie/SwipeToDelete/issues) block
to find the solution or create an new issue that we will fix asap. Feel free to contribute.

## Author

This library is open-sourced by [Agilie Team](https://www.agilie.com) <info@agilie.com>

## Contributors

- [Alexander Nekrasov](https://github.com/TermLog)
- [Roman Kapshuk](https://github.com/RomanKapshuk)

## Contact us
If you have any questions, suggestions or just need a help with web or mobile development, please email us at<br/> <android@agilie.com><br/>
You can ask us anything from basic to complex questions. <br/>
We will continue publishing new open-source projects. Stay with us, more updates will follow!<br/>

## License

The [MIT](LICENSE.MD) License (MIT) Copyright © 2017 [Agilie Team](https://www.agilie.com)

