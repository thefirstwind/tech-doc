# LRU算法

* 先进先出算法（FIFO）：最先进入的内容作为替换对象
* 最久未使用算法（LRU）：最久没有访问的内容作为替换对象
* 非最近使用算法（NMRU）：在最近没有使用的内容中随机选择一个作为替换对象

## LRU与LinkedHashMap的结合
* 关键点：protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
* 如果该方法返回true,那么就可以删除最好的entry了，那么就可以重写该方法啊！
  * return size()>capacity;//map的大小大于设定的容量，返回true
* 如果map的大小大于设定的缓存值，那么就是true，要是true，就移除最老的那个，与LRU算法（最久没有访问的内容作为替换对象），正好对上了。
