package deniskaminskiy.paperboy.core

interface Presenter<V : View> {
    /**
     * Called when the view is attached to the presenter. Presenters should normally not use this
     * method since it's only used to link the view to the presenter which is done by the base impl.
     *
     * @param view the view
     */
    fun onViewAttached(view: V)

    /**
     * Called every time the view starts, the view is guarantee to be not null starting at this
     * method, until [.onStop] is called.
     *
     * @param viewCreated true if it's the has been just created, false if its just restarting after a stop
     */
    fun onStart(viewCreated: Boolean)

    /**
     * Called every time the view stops. After this method, the view will be null.
     */
    fun onStop()

    /**
     * Called when the view is detached from the presenter. Presenters should normally not use this
     * method since it's only used to unlink the view from the presenter which is done by the base impl.
     */
    fun onViewDetached()

    /**
     * Called when the presenter is definitely destroyed, you should use this method only to release
     * any resource used by the presenter (cancel HTTP requests, close database connection...)
     */
    fun onDestroy()

    fun onAnimationStart()
    fun onAnimationEnd()
}