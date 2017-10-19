/**
 * 
 */
package auto.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanghao
 */
public class FileLockUtils {
    
    public static final Map<String, FileLock> path2FileLock = new HashMap<String, FileLock>();
    
    @SuppressWarnings("resource")
    public static synchronized boolean tryLock(String path) {
        RandomAccessFile file = null;
        FileLock lock = null;
        try {
            file = new RandomAccessFile(path, "rw");
            lock = file.getChannel().tryLock();
            if (lock != null) {
                path2FileLock.put(path, lock);
                return true;
            }
            return false;
        } catch(Exception e) {
            try {
                if (lock != null) {
                    lock.release();
                }
            } catch (IOException e1) {}
            return false;
        }
    }
    
    public static boolean release(String path) {
        FileLock lock = path2FileLock.get(path);
        if (lock == null) return false;
        try {
            lock.release();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
