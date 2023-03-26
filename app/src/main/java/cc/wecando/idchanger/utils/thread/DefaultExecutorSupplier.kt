package cc.wecando.idchanger.utils.thread

import cc.wecando.idchanger.utils.SingletonHolder
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DefaultExecutorSupplier {
    private val numberOfCore = Runtime.getRuntime().availableProcessors()
    private val forBackgroundTasks = ThreadPoolExecutor(
        numberOfCore * 2,
        numberOfCore * 2,
        60L,
        TimeUnit.SECONDS,
        LinkedBlockingQueue<Runnable>(),
    )
    private val mainThreadExecutor = MainThreadExecutor()

    companion object : SingletonHolder<DefaultExecutorSupplier, Nothing>({
        DefaultExecutorSupplier()
    })

    fun runOnBackground(runnable: Runnable) {
        forBackgroundTasks.execute(runnable)
    }

    fun runOnMain(runnable: Runnable) {
        mainThreadExecutor.execute(runnable)
    }


}